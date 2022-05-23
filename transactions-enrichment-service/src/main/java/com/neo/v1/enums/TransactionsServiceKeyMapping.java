package com.neo.v1.enums;

import com.neo.core.provider.ServiceKeyMapping;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_SERVICE_DOWN_AUDIT_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_SERVICE_DOWN_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_SERVICE_DOWN_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_SERVICE_ERROR_AUDIT_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_SERVICE_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_SERVICE_ERROR_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CATEGORY_REFERENCE_NOT_FOUND_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREATE_CATEGORY_INVALID_CATEGORY;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREATE_CATEGORY_INVALID_COLOR;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREATE_CATEGORY_INVALID_CUSTOMER_TYPE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREATE_CATEGORY_INVALID_ICON;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREATE_HOLD_TRANSACTION_EXPIRY_DATE_INVALID_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREDIT_CARD_SERVICE_DOWN_AUDIT_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREDIT_CARD_SERVICE_DOWN_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREDIT_CARD_SERVICE_DOWN_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.DELETE_CATEGORY_INVALID_CATEGORY_ID_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.EXCHANGE_SERVICE_DOWN_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.EXCHANGE_SERVICE_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.HOLD_NUMBER_REQUIRED_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.IBAN_REQUIRED_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.LINK_CATEGORY_INVALID_CATEGORY_ID_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.LINK_CATEGORY_INVALID_IBAN_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.LINK_CATEGORY_INVALID_LINK_TYPE_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.LINK_CATEGORY_INVALID_REFERENCE_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.PRODUCT_CATALOGUE_UNAVAILABLE_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TMSX_INVALID_OPERATION_TYPE_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_URBIS_AUDIT_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_URBIS_DOWN_AUDIT_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_URBIS_DOWN_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_URBIS_DOWN_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_URBIS_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTIONS_URBIS_MESSAGE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_UNAVAILABLE_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSFER_SERVICE_DOWN_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSFER_SERVICE_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.UPDATE_CATEGORY_INVALID_CATEGORY_ID_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.UPDATE_CATEGORY_INVALID_COLOR_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.UPDATE_CATEGORY_INVALID_ICON_ERROR_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_ID_ERROR_CODE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@AllArgsConstructor
@Getter
public enum TransactionsServiceKeyMapping implements ServiceKeyMapping {

    URBIS_SERVICE_ERROR(TRANSACTIONS_URBIS_ERROR_CODE, INTERNAL_SERVER_ERROR, TRANSACTIONS_URBIS_MESSAGE, TRANSACTIONS_URBIS_AUDIT_MESSAGE),
    URBIS_SERVICE_DOWN(TRANSACTIONS_URBIS_DOWN_CODE, SERVICE_UNAVAILABLE, TRANSACTIONS_URBIS_DOWN_MESSAGE, TRANSACTIONS_URBIS_DOWN_AUDIT_MESSAGE),
    ACCOUNT_SERVICE_ERROR(ACCOUNT_SERVICE_ERROR_CODE, INTERNAL_SERVER_ERROR, ACCOUNT_SERVICE_ERROR_MESSAGE, ACCOUNT_SERVICE_ERROR_AUDIT_MESSAGE),
    ACCOUNT_SERVICE_DOWN(ACCOUNT_SERVICE_DOWN_CODE, SERVICE_UNAVAILABLE, ACCOUNT_SERVICE_DOWN_MESSAGE, ACCOUNT_SERVICE_DOWN_AUDIT_MESSAGE),
    CREDIT_CARD_SERVICE_DOWN(CREDIT_CARD_SERVICE_DOWN_CODE, SERVICE_UNAVAILABLE, CREDIT_CARD_SERVICE_DOWN_MESSAGE, CREDIT_CARD_SERVICE_DOWN_AUDIT_MESSAGE),
    CREATE_HOLD_EXPIRY_DATE_INVALID(CREATE_HOLD_TRANSACTION_EXPIRY_DATE_INVALID_CODE, BAD_REQUEST, "com.neo.transactions.create.hold.expiry.date.invalid.message",
            "com.neo.transactions.create.hold.expiry.date.invalid.error.message"),
    TMSX_INVALID_OPERATION_TYPE(TMSX_INVALID_OPERATION_TYPE_CODE, BAD_REQUEST, "com.neo.transactions.tmx.post.release.hold.operation.type.invalid.message",
            "com.neo.transactions.tmx.post.release.hold.operation.type.invalid.error.message"),
    TMSX_SERVICE_ERROR("TRNE-0080", INTERNAL_SERVER_ERROR, "com.neo.transactions.tmsx.failed.response.message", "com.neo.transactions.tmsx.failed.response.error.message"),
    TMSX_SERVICE_DOWN("GENE-1013", SERVICE_UNAVAILABLE, "com.neo.transactions.tmsx.down.message", "com.neo.transactions.tmsx.down.error.message"),
    EXCHANGE_SERVICE_ERROR(EXCHANGE_SERVICE_ERROR_CODE, INTERNAL_SERVER_ERROR, "com.neo.transactions.release.hold.exchange.failure.message", "com.neo.transactions.release.hold.exchange.failure.error.message"),
    EXCHANGE_SERVICE_DOWN(EXCHANGE_SERVICE_DOWN_CODE, SERVICE_UNAVAILABLE, "com.neo.transactions.release.hold.exchange.unavailable.message", "com.neo.transactions.release.hold.exchange.unavailable.error.message"),
    TRANSFER_SERVICE_ERROR(TRANSFER_SERVICE_ERROR_CODE, INTERNAL_SERVER_ERROR, "com.neo.transactions.release.hold.transfer.failure.message", "com.neo.transactions.release.hold.transfer.failure.error.message"),
    TRANSFER_SERVICE_DOWN(TRANSFER_SERVICE_DOWN_CODE, SERVICE_UNAVAILABLE, "com.neo.transactions.release.hold.transfer.unavailable.message", "com.neo.transactions.release.hold.transfer.unavailable.error.message"),
    TRANSFER_LIMIT_EXCEEDED("TRNE-0090", BAD_REQUEST, "com.neo.transactions.tmx.transfer.limit.exceeded.message",
            "com.neo.transactions.tmx.transfer.limit.exceeded.error.message"),
    CUSTOMER_SERVICE_ERROR("TRNE-0091", INTERNAL_SERVER_ERROR, "com.neo.transactions.release.hold.customer.service.failure.message",
            "com.neo.transactions.release.hold.customer.service.error.message"),
    CHARITY_SERVICE_ERROR("GENE-2006", INTERNAL_SERVER_ERROR, "com.neo.transactions.release.hold.charity.unavailable.message",
            "com.neo.transactions.release.hold.charity.unavailable.error.message"),
    CUSTOMER_SERVICE_UNAVAILABLE("GENE-2004", SERVICE_UNAVAILABLE, "com.neo.transactions.customer.service.unavailable.message", "com.neo.transactions.customer.service.unavailable.error.message"),
    CONTENT_SERVICE_ERROR("TRNE-0092", INTERNAL_SERVER_ERROR, "com.neo.transactions.content.service.failure.message", "com.neo.transactions.content.service.failure.error.message"),
    CONTENT_SERVICE_UNAVAILABLE("GENE-2014", SERVICE_UNAVAILABLE, "com.neo.transactions.content.service.unavailable.message", "com.neo.transactions.content.service.unavailable.error.message"),
    CHARITY_SERVICE_UNAVAILABLE("GENE-2027", SERVICE_UNAVAILABLE, "com.neo.transactions.charity.service.unavailable.message",
            "com.neo.transactions.charity.service.unavailable.message"),
    UNIT_DOES_NOT_EXISTS(
            "TRNE-1020",
            INTERNAL_SERVER_ERROR,
            "com.neo.transactions.unit.does.not.exist.message",
            "com.neo.transactions.unit.does.not.exist.error.message"
    ),

    TRANSACTION_REFERENCE_DOES_NOT_EXISTS(
            "TRNE-0097",
            INTERNAL_SERVER_ERROR,
            "com.neo.transactions.transaction.reference.does.not.exist.message",
            "com.neo.transactions.transaction.reference.does.not.exist.error.message"
    ),

    INVALID_TRANSACTION_STATUS_MAPPING(
            "TRNE-1010",
            INTERNAL_SERVER_ERROR,
            "com.neo.transactions.invalid.transaction.status.mapping.message",
            "com.neo.transactions.invalid.transaction.status.mapping.error.message"
    ),
    FILTER_DECODING_ERROR(
            "TRNE-0099",
            INTERNAL_SERVER_ERROR,
            "com.neo.transactions.decode.failure.message",
            "com.neo.transactions.decode.failure.error.message"
    ),
    IBAN_REQUIRED(
            IBAN_REQUIRED_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.transaction.hold.balance.iban.required.message",
            "com.neo.transaction.hold.balance.required.iban.error.message"),
    HOLD_NUMBER_REQUIRED(
            HOLD_NUMBER_REQUIRED_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.transaction.hold.balance.holdNumber.required.message",
            "com.neo.transaction.hold.balance.required.holdNumber.error.message"),
    TRANSACTION_SERVICE_UNAVAILABLE(TRANSACTION_UNAVAILABLE_ERROR_CODE,
                                    INTERNAL_SERVER_ERROR,
            "com.neo.gift.customer.service.error.failure.message",
                                            "com.neo.gift.customer.service.error.failure.audit.message"),
    PRODUCT_CATALOGUE_SERVICE_UNAVAILABLE(PRODUCT_CATALOGUE_UNAVAILABLE_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.category.service.error.failure.message",
            "com.neo.te.category.service.error.failure.audit.message"),
    INVALID_CATEGORY(CREATE_CATEGORY_INVALID_CATEGORY,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.create.category.category.required.message",
            "com.neo.te.create.category.category.required.audit.message"),
    INVALID_ICON(CREATE_CATEGORY_INVALID_ICON,
                     INTERNAL_SERVER_ERROR,
            "com.neo.te.create.category.icon.required.message",
                             "com.neo.te.create.category.icon.required.audit.message"),
    INVALID_COLOR(CREATE_CATEGORY_INVALID_COLOR,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.create.category.color.required.message",
            "com.neo.te.create.category.color.required.audit.message"),
    INVALID_CUSTOMER_TYPE(CREATE_CATEGORY_INVALID_CUSTOMER_TYPE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.create.category.invalid.type.message",
            "com.neo.te.create.category.invalid.type.audit.message"),
    INVALID_CATEGORY_ID(UPDATE_CATEGORY_INVALID_CATEGORY_ID_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.update.category.invalid.id.message",
            "com.neo.te.update.category.invalid.id.audit.message"),
    UPDATE_CATEGORY_INVALID_CATEGORY(CREATE_CATEGORY_INVALID_CATEGORY,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.create.category.category.required.message",
            "com.neo.te.create.category.category.required.audit.message"),
    UPDATE_CATEGORY_INVALID_ICON(UPDATE_CATEGORY_INVALID_ICON_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.create.category.icon.required.message",
            "com.neo.te.create.category.icon.required.audit.message"),
    UPDATE_CATEGORY_INVALID_COLOR(UPDATE_CATEGORY_INVALID_COLOR_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.create.category.color.required.message",
            "com.neo.te.create.category.color.required.audit.message"),
    DELETE_CATEGORY_INVALID_CATEGORY_ID(DELETE_CATEGORY_INVALID_CATEGORY_ID_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.delete.category.invalid.id.message",
            "com.neo.te.delete.category.invalid.id.audit.message"),

    LINK_CATEGORY_INVALID_IBAN(LINK_CATEGORY_INVALID_IBAN_ERROR_CODE,
                                     INTERNAL_SERVER_ERROR,
            "com.neo.te.link.category.iban.required.message",
            "com.neo.te.link.category.iban.required.audit.message"),
    LINK_CATEGORY_INVALID_CATEGORY_ID(LINK_CATEGORY_INVALID_CATEGORY_ID_ERROR_CODE,
                                     INTERNAL_SERVER_ERROR,
            "com.neo.te.link.category.id.required.message",
            "com.neo.te.link.category.id.required.audit.message"),
    LINK_CATEGORY_INVALID_REFERENCE(LINK_CATEGORY_INVALID_REFERENCE_ERROR_CODE,
                                     INTERNAL_SERVER_ERROR,
            "com.neo.te.link.category.transaction.reference.required.message",
            "com.neo.te.link.category.transaction.reference.required.audit.message"),
    LINK_CATEGORY_INVALID_LINK_TYPE(LINK_CATEGORY_INVALID_LINK_TYPE_ERROR_CODE,
                                     INTERNAL_SERVER_ERROR,
            "com.neo.te.link.category.type.required.message",
            "com.neo.te.link.category.type.required.audit.message"),
    CATEGORY_REFERENCE_NOT_FOUND(CATEGORY_REFERENCE_NOT_FOUND_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.link.category.reference.not.found.message",
            "com.neo.te.link.category.reference.not.found.audit.message"),
    ACCOUNT_ID_REQUIRED(ACCOUNT_ID_ERROR_CODE,
            INTERNAL_SERVER_ERROR,
            "com.neo.te.account.id.required.message",
            "com.neo.te.account.id.required.audit.message");

    private String code;
    private HttpStatus httpStatus;
    private String messageKey;
    private String errorMessageKey;
}