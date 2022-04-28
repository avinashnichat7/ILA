package com.neo.v1.repository;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.neo.v1.constants.TransactionEnrichmentConstants.ACCOUNT_ID;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CUSTOMER_ID;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_DATE;

@Repository
public class CustomerAccountTransactionCategoryCustomRepositoryImpl implements CustomerAccountTransactionCategoryCustomRepository {

    @Autowired
    @Qualifier("entityManagerFactory")
    EntityManager em;

    @Override
    public List<CustomerAccountTransactionCategoryEntity> findByAccountIdAndCustomerIdAndTransactionDateBetween(String accountId, String customerId, LocalDateTime fromDate, LocalDateTime toDate) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<CustomerAccountTransactionCategoryEntity> cq = cb.createQuery(CustomerAccountTransactionCategoryEntity.class);

        Root<CustomerAccountTransactionCategoryEntity> categoryEntity = cq.from(CustomerAccountTransactionCategoryEntity.class);
        List<Predicate> predicates = new ArrayList<>();
        predicates.add(cb.equal(categoryEntity.get(CUSTOMER_ID), customerId));
        predicates.add(cb.equal(categoryEntity.get(ACCOUNT_ID), accountId));
        if (Objects.nonNull(fromDate)) {
            predicates.add(cb.greaterThanOrEqualTo(categoryEntity.get(TRANSACTION_DATE), fromDate));
        }
        if (Objects.nonNull(toDate)) {
            predicates.add(cb.lessThanOrEqualTo(categoryEntity.get(TRANSACTION_DATE), toDate));
        }
        cq.where(predicates.toArray(new Predicate[] {}));
        TypedQuery<CustomerAccountTransactionCategoryEntity> query = em.createQuery(cq);
        return query.getResultList();
    }
}
