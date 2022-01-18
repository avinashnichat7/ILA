package com.neo.v1.util;

import com.neo.v1.entity.TmsxUrbisOperationTypesEntity;
import com.neo.v1.entity.TransferOperationTypeEntity;
import com.neo.v1.enums.TransactionEntryType;
import com.neo.v1.model.PaymentDetails;
import com.neo.v1.model.charity.CharityItem;
import com.neo.v1.model.charity.CharityItemData;
import com.neo.v1.model.charity.PurposeItem;
import com.neo.v1.tmsx.BillDetail;
import com.neo.v1.tmsx.Creditor;
import com.neo.v1.tmsx.CreditorAccount;
import com.neo.v1.tmsx.CreditorDebtorAgent;
import com.neo.v1.tmsx.CustomFeild;
import com.neo.v1.tmsx.Debtor;
import com.neo.v1.tmsx.DebtorAccount;
import com.neo.v1.tmsx.SubscriberIdentification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.neo.v1.constants.TransactionEnrichmentConstants.ABCO_BIC;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWATEER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWRI;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWRIPLUS;
import static com.neo.v1.constants.TransactionEnrichmentConstants.OPERATION_TYPE_FAWRI_TRANSFER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_CHARITY_TRANSFER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_CHARITY_TRANSFER_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_SALARY_TRANSFER;
import static com.neo.v1.entity.PaymentEntryType.URBIS;
import static com.neo.v1.enums.TransactionEntryType.CREDIT;
import static com.neo.v1.enums.TransactionEntryType.DEBIT;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TmsxUtilTest {

    private static final String IBAN = "BH12ABCO6546546";

    @Test
    void getNarrativeLines_NoCustomField_RemittanceAtFourthPosition() {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .billDetails(BillDetail.builder()
                        .billerName("Stark Enterprise")
                        .serviceName("Heavy Artillery")
                        .subscriberIdentification(SubscriberIdentification.builder()
                                .value("0xA1B2C3")
                                .build())
                        .build())
                .remittanceInformation("LASER Equipment")
                .build();
        TransactionEntryType transactionEntryType = DEBIT;
        String[] narratives = TmsxUtil.getNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWATEER).build()).build(), false, URBIS, transactionEntryType, null);
        assertEquals(paymentDetails.getRemittanceInformation(), narratives[3]);
    }

    @Test
    void getNarrativeLines_charityTransfer_returnSuccess() {

        CharityItemData charityItemData = CharityItemData.builder().charities(Collections.singletonList(CharityItem.builder().name("charity").
                purposes(Collections.singletonList(PurposeItem.builder().name("purpose").build())).build())).build();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .billDetails(BillDetail.builder()
                        .billerName("Stark Enterprise")
                        .serviceName("Heavy Artillery")
                        .subscriberIdentification(SubscriberIdentification.builder()
                                .value("0xA1B2C3")
                                .build())
                        .build())
                .remittanceInformation("1/2")
                .transactionTypeCode(TRANSACTION_TYPE_CHARITY_TRANSFER_CODE)
                .build();
        TransactionEntryType transactionEntryType = DEBIT;
        List<String> expected = new ArrayList<>();
        expected.add("charity");
        expected.add("purpose");
        expected.add(TRANSACTION_TYPE_CHARITY_TRANSFER);
        String[] narratives = TmsxUtil.getNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), false, URBIS, transactionEntryType, charityItemData);
        assertEquals(expected.get(0), narratives[0]);
        assertEquals(expected.get(1), narratives[1]);
        assertEquals(expected.get(2), narratives[2]);
    }

    @Test
    void getNarrativeLines_AllDetails_RemittanceAtSixthPosition() {
        List<CustomFeild> customFeilds = new ArrayList<>();
        customFeilds.add(CustomFeild.builder().value("Custom1").build());
        customFeilds.add(CustomFeild.builder().value("Custom2").build());
        String TRUNCATED_STRING = "LASER Equipment LASER Equipment LA";
        TransactionEntryType transactionEntryType = DEBIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .billDetails(BillDetail.builder()
                        .billerName("Stark Enterprise")
                        .serviceName("Heavy Artillery")
                        .subscriberIdentification(SubscriberIdentification.builder()
                                .value("0xA1B2C3")
                                .build())
                        .customFields(customFeilds)
                        .build())
                .remittanceInformation("LASER Equipment LASER Equipment LASER Equipment LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWATEER).build()).build(), false, URBIS, transactionEntryType, null);
        for (String narrative : narratives) {
            System.out.println(narrative);
        }
        assertEquals(TRUNCATED_STRING, narratives[5]);
    }

    @Test
    void getNarrativeLines_NoBillDetail_RemittanceAtSixthPosition() {
        List<CustomFeild> customFeilds = new ArrayList<>();
        customFeilds.add(CustomFeild.builder().value("Custom1").build());
        customFeilds.add(CustomFeild.builder().value("Custom2").build());
        String TRUNCATED_STRING = "LASER Equipment LASER Equipment LA";
        TransactionEntryType transactionEntryType = DEBIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .billDetails(BillDetail.builder()
                        .billerName("Stark Enterprise")
                        .serviceName("Heavy Artillery")
                        .customFields(customFeilds)
                        .build())
                .remittanceInformation("LASER Equipment LASER Equipment LASER Equipment LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWATEER).build()).build(), false, URBIS, transactionEntryType, null);
        assertEquals(TRUNCATED_STRING, narratives[4]);
    }

    @Test
    void getTransferType_WithFawriTransferType_Success() {
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType("Fawri").build())
                .build();
        String result = TmsxUtil.getTransferType(tmsxUrbisOperationTypesEntity);
        assertEquals("Fawri Transfer", result);
    }

    @Test
    void getTransferType_WithFawriPlusTransferType_Success() {
        TmsxUrbisOperationTypesEntity tmsxUrbisOperationTypesEntity = TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType("FawriPlus").build())
                .build();
        String result = TmsxUtil.getTransferType(tmsxUrbisOperationTypesEntity);
        assertEquals("Fawri+ Transfer", result);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_AllDetailsWithFawriOperationWithDebitEntryType_Success() {
        TransactionEntryType transactionEntryType = DEBIT;
        String CREDITOR_IBAN = "BH12ABCO6546546";
        String DEBTOR_IBAN = "BH12ABCO6546547";
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .debtor(Debtor.builder()
                        .name("Yondu")
                        .account(DebtorAccount.builder()
                                .iban(DEBTOR_IBAN)
                                .build())
                        .build())
                .creditor(Creditor.builder()
                        .account(CreditorAccount.builder()
                                .iban(CREDITOR_IBAN)
                                .build())
                        .build())
                .remittanceInformation("LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(OPERATION_TYPE_FAWRI_TRANSFER, narratives[0]);
        assertEquals(CREDITOR_IBAN, narratives[1]);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_AllDetailsWithFawriOperationWithCreditEntryType_Success() {
        TransactionEntryType transactionEntryType = CREDIT;
        String CREDITOR_IBAN = "BH12ABCO6546546";
        String DEBTOR_IBAN = "BH12ABCO6546546";
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .debtor(Debtor.builder()
                        .name("Yondu")
                        .account(DebtorAccount.builder()
                                .iban(DEBTOR_IBAN)
                                .build())
                        .address("address")
                        .build())
                .creditor(Creditor.builder()
                        .name("test")
                        .account(CreditorAccount.builder()
                                .iban(CREDITOR_IBAN)
                                .build())
                        .build())
                .remittanceInformation("LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(OPERATION_TYPE_FAWRI_TRANSFER, narratives[1]);
        assertEquals(DEBTOR_IBAN, narratives[2]);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_AllDetailsWithFawriOperationWithCreditEntryTypeAndMobileNumber_Success() {
        TransactionEntryType transactionEntryType = CREDIT;
        String CREDITOR_IBAN = "BH12ABCO6546546";
        String DEBTOR_IBAN = "BH12ABCO6546546";
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .debtor(Debtor.builder()
                        .name("Yondu")
                        .account(DebtorAccount.builder()
                                .iban(DEBTOR_IBAN)
                                .build())
                        .address("/PHONE/973XXXXXXXX")
                        .build())
                .creditor(Creditor.builder()
                        .name("test")
                        .account(CreditorAccount.builder()
                                .iban(CREDITOR_IBAN)
                                .build())
                        .build())
                .creditorAgent(CreditorDebtorAgent.builder().bic(ABCO_BIC).build())
                .remittanceInformation("LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(OPERATION_TYPE_FAWRI_TRANSFER, narratives[1]);
        assertEquals(DEBTOR_IBAN, narratives[2]);
        assertEquals("From Mobile: +973XXXXXXXX", narratives[3]);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_AllDetailsWithFawriOperationWithCreditEntryTypeAndMobileNumberHasPlus_Success() {
        TransactionEntryType transactionEntryType = CREDIT;
        String CREDITOR_IBAN = "BH12ABCO6546546";
        String DEBTOR_IBAN = "BH12ABCO6546546";
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .debtor(Debtor.builder()
                        .name("Yondu")
                        .account(DebtorAccount.builder()
                                .iban(DEBTOR_IBAN)
                                .build())
                        .address("/PHONE/+973XXXXXXXX")
                        .build())
                .creditor(Creditor.builder()
                        .name("test")
                        .account(CreditorAccount.builder()
                                .iban(CREDITOR_IBAN)
                                .build())
                        .build())
                .creditorAgent(CreditorDebtorAgent.builder().bic(ABCO_BIC).build())
                .remittanceInformation("LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(OPERATION_TYPE_FAWRI_TRANSFER, narratives[1]);
        assertEquals(DEBTOR_IBAN, narratives[2]);
        assertEquals("From Mobile: +973XXXXXXXX", narratives[3]);
    }

    @Test
    void getFawriFawriPlusNarrativeLinesForDebit_SalaryTransfer_SalaryNarratives() {
        String name = "Yondu";
        String codeSalaryTransfer = "021";
        String remittanceInformation = "/SNDT/CPR/861005120/IBAN/BH82NBOK05501300080100//BNT///ACCT///";
        TransactionEntryType transactionEntryType = DEBIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                                  .name(name)
                                  .account(CreditorAccount.builder()
                                                   .iban(IBAN)
                                                   .build())
                                  .build())
                .debtor(Debtor.builder()
                        .name(name)
                        .account(DebtorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .remittanceInformation(remittanceInformation)
                .transactionTypeCode(codeSalaryTransfer)
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(TRANSACTION_TYPE_SALARY_TRANSFER, narratives[0]);
        assertEquals(IBAN, narratives[1]);
        assertEquals(name, narratives[2]);
    }

    @Test
    void getFawriFawriPlusNarrativeLinesForCredit_SalaryTransfer_SalaryNarratives() {
        String name = "Yondu";
        String codeSalaryTransfer = "021";
        String remittanceInformation = "/SNDT/CPR/861005120/IBAN/BH82NBOK05501300080100//BNT///ACCT///";
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name(name)
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .debtor(Debtor.builder()
                        .name(name)
                        .account(DebtorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .remittanceInformation(remittanceInformation)
                .transactionTypeCode(codeSalaryTransfer)
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(TRANSACTION_TYPE_SALARY_TRANSFER, narratives[0]);
        assertEquals(IBAN, narratives[1]);
        assertEquals(name, narratives[2]);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_WithTruncatedValue() {
        String TRUNCATED_STRING = "LASER Equipment LASER Equipment LA";
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name("Yondu")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .debtor(Debtor.builder()
                        .account(DebtorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .remittanceInformation("LASER Equipment LASER Equipment LASER Equipment LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(TRUNCATED_STRING, narratives[3]);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_withSpecialCharacter_splitWithItFirst() {
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name("Yondu")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .debtor(Debtor.builder()
                        .account(DebtorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .remittanceInformation("/SNDT/CPR/861005120/IBAN\r\n/BH82NBOK05501300080100/\r\n/BNT//\r\n/ACCT///")
                .build();
        String[] result = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertThat(result).containsExactlyInAnyOrder("Yondu", OPERATION_TYPE_FAWRI_TRANSFER, IBAN, "/SNDT/CPR/861005120/IBAN", "/BH82NBOK05501300080100/", "/BNT//", "/ACCT///");
    }

    @Test
    void getFawriFawriPlusNarrativeLines_NullCreditorAndFawriOperation_Success() {
        TransactionEntryType transactionEntryType = CREDIT;
        CreditorAccount creditorAccount = CreditorAccount.builder().build();
        Creditor creditor = Creditor.builder()
                .account(creditorAccount)
                .build();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .remittanceInformation("LASER Equipment")
                .creditor(creditor)
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertEquals(OPERATION_TYPE_FAWRI_TRANSFER, narratives[0]);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_AllDetailsWithFawriPlusOperation_Success() {
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name("Yondu")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .remittanceInformation("LASER Equipment")
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRIPLUS).build()).build(), transactionEntryType);
        assertEquals("Fawri+ Transfer", narratives[1]);
    }

    @Test
    void getFawriFawriPlusNarrativeLinesWhenNullCreditorAndFawriPlusOperationPassedThenReturnNarrativeLineWithSuccess() {
        TransactionEntryType transactionEntryType = CREDIT;
        CreditorAccount creditorAccount = CreditorAccount.builder().build();
        Creditor creditor = Creditor.builder()
                .account(creditorAccount)
                .build();
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .remittanceInformation("LASER Equipment")
                .creditor(creditor)
                .build();
        String[] narratives = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRIPLUS).build()).build(), transactionEntryType);
        assertEquals("Fawri+ Transfer", narratives[0]);
    }

    @Test
    void getFawriFawriPlusNarrativeLines_withLongCreditorName_SubString() {
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name("AbdullaHassanAliKhamisHassanMohmmamedSalamn")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .build();
        String[] result = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertThat(result[0]).isEqualTo("AbdullaHassanAliKhamisHassanMohmma");
    }

    @Test
    void getFawriFawriPlusNarrativeLines_withShortCreditorName_SubString() {
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name("Abdulla")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .build();
        String[] result = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertThat(result[0]).isEqualTo("Abdulla");
    }

    @Test
    void getFawriFawriPlusNarrativeLines_withCreditorNameWithSpace_SubString() {
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name("Abdulla Hassan Ai Khais Hassan Ali Ali")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .build();
        String[] result = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertThat(result[0]).isEqualTo("Abdulla Hassan Ai Khais Hassan Ali");
    }

    @Test
    void getFawriFawriPlusNarrativeLines_withLongCreditorNameWithSpace_SubString() {
        TransactionEntryType transactionEntryType = CREDIT;
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .creditor(Creditor.builder()
                        .name("Abdulla Hassan Ai Khais Hassan Abdulla")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .build();
        String[] result = TmsxUtil.getFawriFawriPlusNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .transferOperationTypeEntity(TransferOperationTypeEntity.builder().operationType(OPERATION_TYPE_FAWRI).build()).build(), transactionEntryType);
        assertThat(result[0]).isEqualTo("Abdulla Hassan Ai Khais Hassan");
    }

    @ParameterizedTest
    @MethodSource("operationTypeMapping")
    void getVatNarrativeLines_WithCreditorAndVat_CorrectDescriptionAndBiller(TransferOperationTypeEntity transferOperationTypeEntity, String operationTypeDisplayString) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .remittanceInformation("LASER Equipment")
                .creditor(Creditor.builder()
                        .name("Yondu")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .build();
        String[] narratives = TmsxUtil.getVatNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .vat("CVAT")
                .transferOperationTypeEntity(transferOperationTypeEntity).build());
        assertEquals("VAT on " + operationTypeDisplayString, narratives[0]);
        assertEquals("Yondu", narratives[1]);
    }

    @ParameterizedTest
    @MethodSource("operationTypeMapping")
    void getChargeNarrativeLines_WithCreditorAndCharge_CorrectDescriptionAndBiller(TransferOperationTypeEntity transferOperationTypeEntity, String operationTypeDisplayString) {
        PaymentDetails paymentDetails = PaymentDetails.builder()
                .remittanceInformation("LASER Equipment")
                .creditor(Creditor.builder()
                        .name("Malayude Sivadasan and Mohamed Are")
                        .account(CreditorAccount.builder()
                                .iban(IBAN)
                                .build())
                        .build())
                .build();

        String[] narratives = TmsxUtil.getChargeNarrativeLines(paymentDetails, TmsxUrbisOperationTypesEntity.builder()
                .charges("FWPC")
                .transferOperationTypeEntity(transferOperationTypeEntity).build());
        assertEquals("Charges on " + operationTypeDisplayString, narratives[0]);
        assertEquals("Malayude Sivadasan and Mohamed Are", narratives[1]);
    }

    private static Stream<Arguments> operationTypeMapping() {
        return Stream.of(
                Arguments.of(TransferOperationTypeEntity.builder().id(1L).operationType(OPERATION_TYPE_FAWRI).build(), OPERATION_TYPE_FAWRI),
                Arguments.of(TransferOperationTypeEntity.builder().id(2L).operationType(OPERATION_TYPE_FAWRIPLUS).build(), OPERATION_TYPE_FAWRIPLUS),
                Arguments.of(TransferOperationTypeEntity.builder().id(3L).operationType(OPERATION_TYPE_FAWATEER).build(), "Fawateer"));
    }

}
