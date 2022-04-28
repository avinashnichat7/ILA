package com.neo.v1.repository.urbis;

import com.neo.core.exception.ServiceException;
import com.neo.v1.entity.urbis.AccountPendingTransactionEntity;
import com.neo.v1.entity.urbis.AccountTransactionEntity;
import com.neo.v1.enums.TransactionsServiceKeyMapping;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.StoredProcedureQuery;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.neo.v1.constants.TransactionEnrichmentConstants.CARD_MASK_REPLACEMENT;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CARD_NUMBER_MASK_CHARACTER;
import static com.neo.v1.constants.TransactionEnrichmentConstants.DATABASE_DOWN;
import static com.neo.v1.constants.TransactionEnrichmentConstants.URBIS_DB_PERSISTENCE_UNIT;
import static java.util.Objects.isNull;
import static javax.persistence.ParameterMode.IN;
import static javax.persistence.ParameterMode.INOUT;

@Repository
@Slf4j
public class TransactionsRepository {

    private static final String ACCOUNT_TRANSACTIONS_PROCEDURE_NAME = "API_AccountTransactionsV2";
    private static final String PENDING_ACCOUNT_TRANSACTIONS_PROCEDURE_NAME = "API_AccountPendingTransactionV2";

    private static final String PARAM_CUSTOMER_ID = "customer_id";
    private static final String PARAM_ACCOUNT_ID = "id";
    private static final String PARAM_OFFSET = "offset";
    private static final String PARAM_PAGE_SIZE = "page_size";
    private static final String PARAM_FILTER = "filter";
    private static final String PARAM_FROM_DATE = "from_date";
    private static final String PARAM_TO_DATE = "to_date";
    private static final String PARAM_FROM_AMOUNT = "from_amount";
    private static final String PARAM_TO_AMOUNT = "to_amount";
    private static final String PARAM_DEBIT_CREDIT_INDICATOR = "debit_credit_indicator";
    private static final String PARAM_MASKED_CARD_NUMBER = "masked_card_number";
    private static final String PARAM_EXCLUDE_CARD_TRANSACTIONS = "exclude_card_transactions";
    private static final String PARAM_ERROR_CODE = "error_code";
    private static final String PARAM_ERROR_MESSAGE = "error_message";
    private static final String ERROR_MESSAGE_VALUE = "Error";
    private static final Integer ERROR_CODE_VALUE = 1;
    private static final Integer DEFAULT_OFFSET = 0;
    private static final Integer DEFAULT_PAGE_SIZE = 5;

    @PersistenceContext(unitName = URBIS_DB_PERSISTENCE_UNIT)
    private EntityManager entityManager;

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<AccountTransactionEntity> getAccountTransactions(String customerId, AccountTransactionsRequest request) {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(ACCOUNT_TRANSACTIONS_PROCEDURE_NAME, AccountTransactionEntity.class);
            setParameters(customerId, request, storedProcedure);
            addParameter(storedProcedure, PARAM_PAGE_SIZE, isNull(request.getPageSize()) ? DEFAULT_PAGE_SIZE : request.getPageSize().intValue(), Integer.class, IN);
            return storedProcedure.getResultList();
        } catch (PersistenceException pe) {
            if (pe.getMessage().contains(DATABASE_DOWN)) {
                throw new ServiceException(TransactionsServiceKeyMapping.URBIS_SERVICE_DOWN, pe);
            }
            throw new ServiceException(TransactionsServiceKeyMapping.URBIS_SERVICE_ERROR, pe);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<AccountPendingTransactionEntity> getPendingAccountTransactions(String customerId, AccountTransactionsRequest request, int pageSize) {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(PENDING_ACCOUNT_TRANSACTIONS_PROCEDURE_NAME, AccountPendingTransactionEntity.class);
            setParameters(customerId, request, storedProcedure);
            addParameter(storedProcedure, PARAM_PAGE_SIZE, pageSize, Integer.class, IN);
            return storedProcedure.getResultList();
        } catch (PersistenceException pe) {
            if (pe.getMessage().contains(DATABASE_DOWN)) {
                throw new ServiceException(TransactionsServiceKeyMapping.URBIS_SERVICE_DOWN, pe);
            }
            throw new ServiceException(TransactionsServiceKeyMapping.URBIS_SERVICE_ERROR, pe);
        }
    }

    private void setParameters(String customerId, AccountTransactionsRequest request, StoredProcedureQuery storedProcedure) {
        addParameter(storedProcedure, PARAM_CUSTOMER_ID, customerId, String.class, IN);
        addParameter(storedProcedure, PARAM_ACCOUNT_ID, request.getId(), String.class, IN);
        addParameter(storedProcedure, PARAM_OFFSET, isNull(request.getOffset()) ? DEFAULT_OFFSET : request.getOffset().intValue(), Integer.class, IN);
        addParameter(storedProcedure, PARAM_FILTER, request.getFilter(), String.class, IN);
        addParameter(storedProcedure, PARAM_FROM_DATE, request.getFromDate(), LocalDate.class, IN);
        addParameter(storedProcedure, PARAM_TO_DATE, request.getToDate(), LocalDate.class, IN);
        addParameter(storedProcedure, PARAM_FROM_AMOUNT, request.getFromAmount(), BigDecimal.class, IN);
        addParameter(storedProcedure, PARAM_TO_AMOUNT, request.getToAmount(), BigDecimal.class, IN);
        addParameter(storedProcedure, PARAM_DEBIT_CREDIT_INDICATOR, request.getDebitCreditIndicator(), String.class, IN);
        addParameter(storedProcedure, PARAM_EXCLUDE_CARD_TRANSACTIONS, request.isExcludeCardTransactions(), Boolean.class, IN);
        String maskedCardNumber = StringUtils.isNotBlank(request.getMaskedCardNumber()) ? request.getMaskedCardNumber().replaceAll(CARD_NUMBER_MASK_CHARACTER, CARD_MASK_REPLACEMENT) : request.getMaskedCardNumber();
        addParameter(storedProcedure, PARAM_MASKED_CARD_NUMBER,maskedCardNumber, String.class, IN);
        addParameter(storedProcedure, PARAM_ERROR_CODE, ERROR_CODE_VALUE, Integer.class, INOUT);
        addParameter(storedProcedure, PARAM_ERROR_MESSAGE, ERROR_MESSAGE_VALUE, String.class, INOUT);
    }

    private void addParameter(StoredProcedureQuery storedProcedure, String paramName, Object paramValue, Class<?> clazz, ParameterMode parameterMode) {
        storedProcedure.registerStoredProcedureParameter(paramName, clazz, parameterMode);
        storedProcedure.setParameter(paramName, paramValue);
    }

}
