package com.neo.v1.repository.a24;

import com.neo.core.exception.ServiceException;
import com.neo.v1.entity.a24.A24AccountTransactionEntity;
import com.neo.v1.enums.TransactionsServiceKeyMapping;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;

import lombok.extern.slf4j.Slf4j;
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

import static com.neo.v1.constants.TransactionEnrichmentConstants.A24_DB_PERSISTENCE_UNIT;
import static com.neo.v1.constants.TransactionEnrichmentConstants.DATABASE_DOWN;
import static com.neo.v1.constants.TransactionEnrichmentConstants.DATABASE_DOWN;
import static java.util.Objects.isNull;
import static javax.persistence.ParameterMode.IN;

@Repository
@Slf4j
public class A24TransactionsRepository {

    private static final String ACCOUNT_TRANSACTIONS_PROCEDURE_NAME = "API_A24_AccountTransactions";
    private static final String PARAM_ACCOUNT_IBAN = "iban";
    private static final String PARAM_OFFSET = "start_offset";
    private static final String PARAM_PAGE_SIZE = "page_size";
    private static final String PARAM_FROM_DATE = "from_date";
    private static final String PARAM_TO_DATE = "to_date";
    private static final String PARAM_FROM_AMOUNT = "from_amount";
    private static final String PARAM_TO_AMOUNT = "to_amount";
    private static final String PARAM_DEBIT_CREDIT_INDICATOR = "debit_credit_indicator";
    private static final Integer DEFAULT_PAGE_SIZE = 5;
    private static final Integer DEFAULT_OFFSET = 0;

    @PersistenceContext(unitName = A24_DB_PERSISTENCE_UNIT)
    private EntityManager entityManager;

    private void addParameter(StoredProcedureQuery storedProcedure, String paramName, Object paramValue, Class clazz, ParameterMode parameterMode) {
        storedProcedure.registerStoredProcedureParameter(paramName, clazz, parameterMode);
        storedProcedure.setParameter(paramName, paramValue);
    }

    @Transactional(propagation = Propagation.SUPPORTS, isolation = Isolation.READ_UNCOMMITTED, readOnly = true)
    public List<A24AccountTransactionEntity> getAccountTransactions(String customerId, AccountTransactionsRequest request) {
        try {
            StoredProcedureQuery storedProcedure = entityManager.createStoredProcedureQuery(ACCOUNT_TRANSACTIONS_PROCEDURE_NAME, A24AccountTransactionEntity.class);
            setParameters(request, storedProcedure);
            return storedProcedure.getResultList();
        } catch (PersistenceException pe) {
            if (pe.getMessage().contains(DATABASE_DOWN)) {
                throw new ServiceException(TransactionsServiceKeyMapping.A24_SERVICE_DOWN, pe);
            }
            throw new ServiceException(TransactionsServiceKeyMapping.A24_SERVICE_ERROR, pe);
        }
    }

    private void setParameters(AccountTransactionsRequest request, StoredProcedureQuery storedProcedure) {
        addParameter(storedProcedure, PARAM_ACCOUNT_IBAN, request.getId(), String.class, IN);
        addParameter(storedProcedure, PARAM_OFFSET, isNull(request.getOffset()) ? DEFAULT_OFFSET : request.getOffset(), Integer.class, IN);
        addParameter(storedProcedure, PARAM_PAGE_SIZE, isNull(request.getPageSize()) ? DEFAULT_PAGE_SIZE : request.getPageSize(), Integer.class, IN);
        addParameter(storedProcedure, PARAM_FROM_DATE, request.getFromDate(), LocalDate.class, IN);
        addParameter(storedProcedure, PARAM_TO_DATE, request.getToDate(), LocalDate.class, IN);
        addParameter(storedProcedure, PARAM_FROM_AMOUNT, request.getFromAmount(), BigDecimal.class, IN);
        addParameter(storedProcedure, PARAM_TO_AMOUNT, request.getToAmount(), BigDecimal.class, IN);
        addParameter(storedProcedure, PARAM_DEBIT_CREDIT_INDICATOR, request.getDebitCreditIndicator(), String.class, IN);
    }
}
