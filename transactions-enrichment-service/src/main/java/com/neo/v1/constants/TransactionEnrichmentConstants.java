package com.neo.v1.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static java.math.BigDecimal.ONE;

public class TransactionEnrichmentConstants {

    public static final String CODE_TRANSACTION_ID_LENGTH = "TRAN-0015";
    public static final String CODE_TRANSACTION_ID_REQUIRED = "TRAN-0017";
    public static final String CODE_ENTRY_ACCOUNT_ID_REQUIRED = "TRAN-0021";
    public static final String POST_RELEASE_HOLD_ENTRY_ACCOUNT_ID_REQUIRED = "TRAN-0038";
    public static final String CODE_ENTRY_AMOUNT_REQUIRED = "TRAN-0022";
    public static final String POST_RELEASE_HOLD_ENTRY_AMOUNT_REQUIRED = "TRAN-0039";
    public static final String CODE_ENTRY_AMOUNT_INVALID = "TRAN-0023";
    public static final String POST_RELEASE_HOLD_ENTRY_AMOUNT_INVALID = "TRAN-0040";

    public static final String CODE_ENTRY_CURRENCY_REQUIRED = "TRAN-0024";
    public static final String POST_RELEASE_HOLD_ENTRY_CURRENCY_REQUIRED = "TRAN-0041";
    public static final String CODE_ENTRY_TYPE_REQUIRED = "TRAN-0025";
    public static final String POST_RELEASE_HOLD_ENTRY_TYPE_REQUIRED = "TRAN-0042";

    public static final String CODE_ENTRY_NARRATIVE_LENGTH = "TRAN-0027";
    public static final String POST_RELEASE_HOLD_ENTRY_NARRATIVE_LENGTH = "TRAN-0044";

    public static final String CODE_TRANSACTION_ENTRY_INVALID = "TRAN-0029";
    // ACCOUNT TRANSACTIONS CODES AND MESSAGES
    public static final String TRANSACTIONS_ACCOUNT_CODE = "TRAN-1009";
    public static final String TRANSACTIONS_ACCOUNT_MESSAGE = "com.neo.transactions.account.message";

    // URBIS ERRORS CODES AND MESSAGES
    public static final String TRANSACTIONS_URBIS_ERROR_CODE = "TRAN-0001";
    public static final String TRANSACTIONS_URBIS_MESSAGE = "com.neo.transactions.urbis.message";
    public static final String TRANSACTIONS_URBIS_AUDIT_MESSAGE = "com.neo.transactions.urbis.audit.message";

    public static final String TRANSACTIONS_URBIS_DOWN_CODE = "GENE-1006";
    public static final String TRANSACTIONS_URBIS_DOWN_MESSAGE = "com.neo.transactions.urbis.down.message";
    public static final String TRANSACTIONS_URBIS_DOWN_AUDIT_MESSAGE = "com.neo.transactions.urbis.down.audit.message";

    public static final String DATABASE_DOWN = "Unable to acquire JDBC Connection";

    public static final String ACCOUNT_SERVICE_DOWN_CODE = "GENE-2005";
    public static final String ACCOUNT_SERVICE_DOWN_MESSAGE = "com.neo.transactions.account.down.message";
    public static final String ACCOUNT_SERVICE_DOWN_AUDIT_MESSAGE = "com.neo.transactions.account.down.audit.message";

    public static final String ACCOUNT_SERVICE_ERROR_CODE = "TRAN-0002";
    public static final String ACCOUNT_SERVICE_ERROR_MESSAGE = "com.neo.transactions.account.error.message";
    public static final String ACCOUNT_SERVICE_ERROR_AUDIT_MESSAGE = "com.neo.transactions.account.error.audit.message";

    public static final String CREATE_HOLD_TRANSACTION_REFERENCE_ID_REQUIRED_CODE = "TRAN-0003";
    public static final String CREATE_HOLD_TRANSACTION_ACCOUNT_ID_REQUIRED_CODE = "TRAN-0004";
    public static final String CREATE_HOLD_TRANSACTION_AMOUNT_REQUIRED_CODE = "TRAN-0005";
    public static final String CREATE_HOLD_TRANSACTION_AMOUNT_INVALID_CODE = "TRAN-0006";
    public static final String CURRENCY_CODE_REQUIRED_CODE = "TRAN-0007";
    public static final String CREATE_HOLD_TRANSACTION_VALUE_DATE_REQUIRED_CODE = "TRAN-0011";
    public static final String CREATE_HOLD_TRANSACTION_NARRATIVE_INVALID_LENGTH_CODE = "TRAN-0012";
    public static final String CREATE_HOLD_TRANSACTION_EXPIRY_DATE_INVALID_CODE = "TRAN-0014";

    public static final String TMSX_CREATE_HOLD_TRANSACTION_ID_REQUIRED_CODE = "TRAN-0050";
    public static final String TMSX_CREATE_HOLD_DEBIT_ACCOUNT_REQUIRED_CODE = "TRAN-0051";
    public static final String TMSX_RELEASE_HOLD_PAYMENT_DETAILS_IBAN_REQUIRED_CODE = "TRAN-0067";
    public static final String TMSX_CREATE_HOLD_AMOUNT_VALUE_REQUIRED_CODE = "TRAN-0052";
    public static final String TMSX_CREATE_HOLD_AMOUNT_VALUE_INVALID_CODE = "TRAN-0053";
    public static final String TMSX_CURRENCY_REQUIRED_CODE = "TRAN-0054";
    public static final String TMSX_CREATE_HOLD_VALUE_DATE_REQUIRED_CODE = "TRAN-0055";
    public static final String TMSX_INVALID_OPERATION_TYPE_CODE = "TRAN-0076";
    public static final String TMSX_CREATE_HOLD_OPERATION_TYPE_REQUIRED_CODE = "TRAN-0098";

    public static final String URBIS_CLIENT_DATE_FORMAT = "yyyyMMdd";

    public static final int NARRATIVE_SIZE = 6;

    public static final String DEBIT_INDICATOR = "debit";
    public static final String CREDIT_INDICATOR = "credit";
    public static final String ALL_INDICATOR = "all";

    public static final String TMSX_POST_RELEASE_HOLD_CREDIT_ACCOUNT_REQUIRED_CODE = "TRAN-0068";
    public static final String TMSX_POST_RELEASE_HOLD_OPERATION_TYPE_REQUIRED_CODE = "TRAN-0063";
    public static final String TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_REQUIRED_CODE = "TRAN-0064";
    public static final String TMSX_POST_RELEASE_HOLD_PAYMENT_ENTRY_AMOUNT_CURRENCY_REQUIRED_CODE = "TRAN-0065";
    public static final String URBIS_REPOSITORY_PACKAGE = "com.neo.v1.repository.urbis";
    public static final String URBIS_DB_PERSISTENCE_UNIT = "urbis";

    public static final BigDecimal TMSX_ACCOUNT_TRANSACTION_EXCHANGE_RATE = ONE;

    public static final String GET_ACCOUNT_TRANSACTIONS_REQUIRED_ACCOUNT_ID_CODE = "TRAN-0074";
    public static final String GET_ACCOUNT_TRANSACTIONS_INVALID_DEBIT_CREDIT_INDICATOR_CODE = "TRAN-0075";
    public static final String GET_ACCOUNT_TRANSACTIONS_INVALID_FILTER_SIZE_CODE = "TRAN-0084";
    public static final String GET_ACCOUNT_TRANSACTIONS_INVALID_FROM_AMOUNT_CODE = "TRAN-0085";
    public static final String GET_ACCOUNT_TRANSACTIONS_INVALID_TO_AMOUNT_CODE = "TRAN-0086";
    public static final long GET_ACCOUNT_TRANSACTION_FROM_DATE_DEFAULT_MONTHS_TO_MINUS = 6;

    public static final String EXCHANGE_SERVICE_DOWN_CODE = "GENE-2010";
    public static final String EXCHANGE_SERVICE_ERROR_CODE = "TRAN-0078";

    public static final String TRANSFER_SERVICE_DOWN_CODE = "GENE-2011";
    public static final String TRANSFER_SERVICE_ERROR_CODE = "TRAN-0079";
    public static final String OPERATION_TYPE_FAWATEER = "FAWATEER";
    public static final String OPERATION_TYPE_FAWRI = "Fawri";
    public static final String OPERATION_TYPE_FAWRIPLUS = "Fawri+";
    public static final String OPERATION_TYPE_FAWRI_TRANSFER = "Fawri Transfer";
    public static final String OPERATION_TYPE_FAWRIPLUS_TRANSFER = "Fawri+ Transfer";
    public static final String NARRATIVE_LINE_MAX_LENGTH = "34";
    public static final String TRANSACTION_DETAIL_BLANK_ACCOUNT_ID_CODE = "TRAN-0094";
    public static final String TRANSACTION_DETAIL_INVALID_ACCOUNT_ID_LENGTH_CODE = "TRAN-0095";
    public static final String TRANSACTION_DETAIL_BLANK_TRANSACTION_REFERENCE_CODE = "TRAN-0093";
    public static final String TRANSACTION_DETAIL_INVALID_TRANSACTION_REFERENCE_LENGTH_CODE = "TRAN-1012";
    public static final String TRANSACTION_TYPE_SALARY_TRANSFER = "Salary Transfer";
    public static final String TRANSACTION_TYPE_CHARITY_TRANSFER = "Contribution to charity";
    public static final String TRANSACTION_TYPE_SALARY_TRANSFER_CODE = "021";
    public static final String TRANSACTION_TYPE_CHARITY_TRANSFER_CODE = "031";
    public static final String REMITTANCE_SEPARATOR = "\r\n";

    public static final String FAWRI_TRANSACTION_TYPE_FOR_PENDING = "EFT-CSCT-DNS";
    public static final String FAWRI_CHARGES_DESCRIPTION_1 = "Charges on Fawri";
    public static final String FAWRI_VAT_DESCRIPTION_1 ="VAT on Fawri";
    public static final String FROM_MOBILE_NARRATIVE="From Mobile: ";
    public static final String SENDER_MOBILE_NUMBER_START_WITH="/PHONE/";
    public static final String PLUS="+";
    public static final String ABCO_BIC ="ABCOBHBM";
    public static final String CARD_NUMBER_MASK_CHARACTER = "\\*";
    public static final String CARD_MASK_REPLACEMENT = "X";
    public static final Map<Long, String> NARRATIVE_OPERATION_TYPE_DISPLAY_MAP = new HashMap<>();
    public static final String BACKSLASH = "/";
    public static final String IBAN_REQUIRED_ERROR_CODE = "TRAN-0100";
    public static final String HOLD_NUMBER_REQUIRED_ERROR_CODE = "TRAN-0101";
    public static final String TRANSACTION_UNAVAILABLE_ERROR_CODE = "GENE-2013";
    public static final String GET_ACCOUNT_TRANSACTIONS_INVALID_STATUS_CODE = "TRAN-0089";
    public static final String GET_ACCOUNT_TRANSACTIONS_INVALID_OFFSET_CODE = "TRAN-0082";
    public static final String GET_ACCOUNT_TRANSACTIONS_INVALID_PAGE_SIZE_CODE = "TRAN-0083";
    public static final int GET_ACCOUNT_TRANSACTION_DEFAULT_PAGE_SIZE = 5;
    
    static {
        NARRATIVE_OPERATION_TYPE_DISPLAY_MAP.put(1L, OPERATION_TYPE_FAWRI);
        NARRATIVE_OPERATION_TYPE_DISPLAY_MAP.put(2L, OPERATION_TYPE_FAWRIPLUS);
        NARRATIVE_OPERATION_TYPE_DISPLAY_MAP.put(3L, "Fawateer");
    }

    private TransactionEnrichmentConstants() {
    }
}
