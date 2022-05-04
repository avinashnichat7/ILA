package com.neo.v1.util;

import com.neo.v1.entity.PaymentEntryType;
import com.neo.v1.entity.TmsxUrbisOperationTypesEntity;
import com.neo.v1.enums.TransactionEntryType;
import com.neo.v1.model.PaymentDetails;
import com.neo.v1.model.charity.CharityItem;
import com.neo.v1.model.charity.CharityItemData;
import com.neo.v1.model.charity.PurposeItem;
import com.neo.v1.model.tmsx.BillDetail;
import com.neo.v1.model.tmsx.Creditor;
import com.neo.v1.model.tmsx.CreditorDebtorAgent;
import com.neo.v1.model.tmsx.Debtor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.neo.v1.constants.TransactionEnrichmentConstants.ABCO_BIC;
import static com.neo.v1.constants.TransactionEnrichmentConstants.FROM_MOBILE_NARRATIVE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.NARRATIVE_LINE_MAX_LENGTH;
import static com.neo.v1.constants.TransactionEnrichmentConstants.NARRATIVE_OPERATION_TYPE_DISPLAY_MAP;
import static com.neo.v1.constants.TransactionEnrichmentConstants.NARRATIVE_SIZE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWATEER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWRI;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWRIPLUS_TRANSFER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWRI_TRANSFER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.PLUS;
import static com.neo.v1.constants.TransactionEnrichmentConstants.REMITTANCE_SEPARATOR;
import static com.neo.v1.constants.TransactionEnrichmentConstants.SENDER_MOBILE_NUMBER_START_WITH;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_CHARITY_TRANSFER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_CHARITY_TRANSFER_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_SALARY_TRANSFER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_SALARY_TRANSFER_CODE;
import static com.neo.v1.entity.PaymentEntryType.CHARGE;
import static com.neo.v1.entity.PaymentEntryType.VAT;
import static com.neo.v1.enums.TransactionEntryType.DEBIT;
import static java.lang.String.format;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Optional.ofNullable;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.substring;

@Slf4j
public final class TmsxUtil {

    private static final String SPLIT_REMITTANCE_INFORMATION_PATTERN = "(?<=\\G.{" + NARRATIVE_LINE_MAX_LENGTH + "})";
    private static final String VAT_OPERATION_TYPE_NARRATIVE = "VAT on %1$s";
    private static final String CHARGES_OPERATION_TYPE_NARRATIVE = "Charges on %1$s";
    private static final Integer NARRATIVE_LINE_CREDITOR_MAX_LENGTH = 34;
    private static final String SPACE = " ";

    private TmsxUtil() {
    }

    public static String[] getNarrativeLines(PaymentDetails paymentDetails, TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity, boolean isTmsxPostTransaction, PaymentEntryType paymentEntryType
            , TransactionEntryType transactionEntryType, CharityItemData charityItemData) {
        String[] narrativeLines;
        if (VAT.equals(paymentEntryType)) {
            narrativeLines = getVatNarrativeLines(paymentDetails, tmsxUrbisOperationTypesEntity);
        } else if (CHARGE.equals(paymentEntryType)) {
            narrativeLines = getChargeNarrativeLines(paymentDetails, tmsxUrbisOperationTypesEntity);
        } else {
            if (OPERATION_TYPE_FAWATEER.equalsIgnoreCase(tmsxUrbisOperationTypesEntity.getTransferOperationTypeEntity().getOperationType())) {
                narrativeLines = getFawateerNarrativeLines(paymentDetails, isTmsxPostTransaction);
            } else if (TRANSACTION_TYPE_CHARITY_TRANSFER_CODE.equalsIgnoreCase(paymentDetails.getTransactionTypeCode())) {
                narrativeLines = getCharityNarrativeLines(charityItemData);
            } else {
                narrativeLines = getFawriFawriPlusNarrativeLines(paymentDetails, tmsxUrbisOperationTypesEntity, transactionEntryType);
            }
        }
        return narrativeLines;
    }

    public static String[] getCharityNarrativeLines(CharityItemData charityItemData) {
        List<String> narratives = new ArrayList<>();
        String charityName = ofNullable(charityItemData).map(CharityItemData::getCharities).map(charityItems -> charityItems.get(0))
                .map(CharityItem::getName).orElse(Strings.EMPTY);
        String purposeName = ofNullable(charityItemData).map(CharityItemData::getCharities).map(charityItems -> charityItems.get(0))
                .map(CharityItem::getPurposes).map(purposeItems -> purposeItems.get(0)).map(PurposeItem::getName).orElse(Strings.EMPTY);
        narratives.add(charityName);
        narratives.add(purposeName);
        narratives.add(TRANSACTION_TYPE_CHARITY_TRANSFER);
        return narratives.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList()).toArray(new String[NARRATIVE_SIZE]);
    }

    public static String[] getFawateerNarrativeLines(PaymentDetails paymentDetails, boolean isTmsxPostTransaction) {
        List<String> narratives = new ArrayList<>();
        BillDetail billDetail = paymentDetails.getBillDetails();

        if (nonNull(billDetail)) {
            logWarningFawateer(billDetail);
            narratives.add(billDetail.getBillerName());
            narratives.add(billDetail.getServiceName());
            narratives.add(nonNull(billDetail.getSubscriberIdentification()) ? billDetail.getSubscriberIdentification().getValue() : null);
            if (nonNull(billDetail.getCustomFields())) {
                billDetail.getCustomFields().forEach(customFeild -> narratives.add(customFeild.getValue()));
            }
        }
        if (!isTmsxPostTransaction) {
            narratives.addAll(getRemittanceNarratives(paymentDetails.getRemittanceInformation()));
        }

        return narratives.stream().filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList()).toArray(new String[NARRATIVE_SIZE]);
    }

    public static String[] getFawriFawriPlusNarrativeLines(PaymentDetails paymentDetails, TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity, TransactionEntryType transactionEntryType) {
        List<String> narratives = new ArrayList<>();
        Creditor creditor = paymentDetails.getCreditor();
        Debtor debtor = paymentDetails.getDebtor();
        logWarningFawri(creditor);
        if (isSalaryTransfer(paymentDetails)) {
            narratives.add(TRANSACTION_TYPE_SALARY_TRANSFER);
            narratives.add(getAccountIban(creditor));
            narratives.add(getName(creditor));
            narratives.add(getAddress(creditor,transactionEntryType));
        } else {
            if (DEBIT.equals(transactionEntryType)) {
                narratives.add(getTransferType(tmsxUrbisOperationTypesEntity));
                narratives.add(getAccountIban(creditor));
                narratives.add(getAddress(creditor,transactionEntryType));
            } else {
                narratives.add(getName(creditor));
                narratives.add(getTransferType(tmsxUrbisOperationTypesEntity));
                narratives.add(getAccountIban(creditor));
                narratives.add(getSenderMobileNumberNarrative(debtor, paymentDetails.getCreditorAgent()));
            }
            narratives.addAll(getRemittanceNarratives(paymentDetails.getRemittanceInformation()));
        }

        return narratives.stream().filter(StringUtils::isNotEmpty).collect(Collectors.toList()).toArray(new String[NARRATIVE_SIZE]);
    }

    public static String[] getVatNarrativeLines(PaymentDetails paymentDetails, TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity) {
        return getVatChargesNarrativeLines(paymentDetails, tmsxUrbisOperationTypesEntity, VAT_OPERATION_TYPE_NARRATIVE);
    }

    public static String[] getChargeNarrativeLines(PaymentDetails paymentDetails, TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity) {
        return getVatChargesNarrativeLines(paymentDetails, tmsxUrbisOperationTypesEntity, CHARGES_OPERATION_TYPE_NARRATIVE);
    }

    private static String[] getVatChargesNarrativeLines(PaymentDetails paymentDetails, TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity, String vatOperationTypeNarrative) {
        Creditor creditor = paymentDetails.getCreditor();

        return Stream.of(
                        format(vatOperationTypeNarrative, NARRATIVE_OPERATION_TYPE_DISPLAY_MAP.get(tmsxUrbisOperationTypesEntity.getTransferOperationTypeEntity().getId())),
                        nonNull(creditor.getName()) ? getName(creditor.getName()) : EMPTY)
                .filter(StringUtils::isNotEmpty)
                .collect(Collectors.toList()).toArray(new String[NARRATIVE_SIZE]);
    }

    public static String getTransferType(TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity) {
        return OPERATION_TYPE_FAWRI.equalsIgnoreCase(tmsxUrbisOperationTypesEntity
                .getTransferOperationTypeEntity().getOperationType()) ? OPERATION_TYPE_FAWRI_TRANSFER : OPERATION_TYPE_FAWRIPLUS_TRANSFER;
    }

    private static void logWarningFawateer(BillDetail billDetail) {
        if (isBlank(billDetail.getBillerName())) {
            log.warn("Biller Name for narrative line 1 is missing");
        }

        if (isBlank(billDetail.getServiceName())) {
            log.warn("Service Name for narrative line 2 is missing");
        }
        if (isNull(billDetail.getSubscriberIdentification())) {
            log.warn("SubscriberIdentification value for narrative line 3 is missing");
        }
    }

    private static void logWarningFawri(Creditor creditor) {
        if (isNull(creditor) || isBlank(creditor.getName())) {
            log.warn("Creditor name for narrative line 1 is missing");
        }

        if (isNull(creditor) || isNull(creditor.getAccount()) || isBlank(creditor.getAccount().getIban())) {
            log.warn("Creditor account for narrative line 3 is missing");
        }

    }

    private static List<String> getRemittanceNarratives(String remittanceInformation) {
        List<String> narrativeLines = new ArrayList<>();
        String[] strings = ofNullable(remittanceInformation).orElse(EMPTY).split(REMITTANCE_SEPARATOR);
        for (String str : strings) {
            narrativeLines.addAll(Stream.of(str.split(SPLIT_REMITTANCE_INFORMATION_PATTERN)).filter(StringUtils::isNotEmpty).collect(Collectors.toList()));
        }
        return narrativeLines;
    }

    private static String getName(String originalName) {
        String name = originalName;
        if (name.contains(SPACE) && !NARRATIVE_LINE_CREDITOR_MAX_LENGTH.equals(name.length())) {
            String subStringName = substring(originalName, 0, NARRATIVE_LINE_CREDITOR_MAX_LENGTH);
            if (NARRATIVE_LINE_CREDITOR_MAX_LENGTH.equals(subStringName.length()) && !SPACE.equals(name.substring(34, 35))) {
                name = subStringName.substring(0, subStringName.lastIndexOf(SPACE));
            } else {
                name = subStringName;
            }
        } else {
            name = substring(originalName, 0, NARRATIVE_LINE_CREDITOR_MAX_LENGTH);
        }
        return name;
    }

    private static String getSenderMobileNumberNarrative(Debtor debtor, CreditorDebtorAgent cre) {
        String creditorBic = Optional.ofNullable(cre).map(CreditorDebtorAgent::getBic).orElse(null);
        String phoneNumber = null;
        if (StringUtils.isNotEmpty(creditorBic) && ABCO_BIC.equalsIgnoreCase(creditorBic)) {
            phoneNumber = Optional.ofNullable(debtor).map(Debtor::getAddress)
                    .map(s -> getFromMobileNumber(s))
                    .orElse(null);
        }
        return StringUtils.isNotEmpty(phoneNumber) ? FROM_MOBILE_NARRATIVE.concat(phoneNumber) : null;
    }

    public static boolean isSalaryTransfer(PaymentDetails paymentDetails) {
        return TRANSACTION_TYPE_SALARY_TRANSFER_CODE.equals(paymentDetails.getTransactionTypeCode());
    }

    private static String getFromMobileNumber(String phoneNumber) {

        String result = null;

        if (phoneNumber.startsWith(SENDER_MOBILE_NUMBER_START_WITH)) {
            result = phoneNumber.replaceFirst(SENDER_MOBILE_NUMBER_START_WITH, "");
            if (!result.startsWith(PLUS)) {
                result = PLUS.concat(result);
            }
        }
        return result;
    }

    private static String getAccountIban(Creditor creditor) {
        return nonNull(creditor) ? creditor.getAccount().getIban() : StringUtils.EMPTY;
    }

    private static String getName(Creditor creditor) {
        return nonNull(creditor) && nonNull(creditor.getName()) ? getName(creditor.getName()) : StringUtils.EMPTY;
    }

    private static String getAddress(Creditor creditor, TransactionEntryType transactionEntryType) {
        return nonNull(creditor) && DEBIT.equals(transactionEntryType) ? creditor.getAddress() : StringUtils.EMPTY;
    }


}
