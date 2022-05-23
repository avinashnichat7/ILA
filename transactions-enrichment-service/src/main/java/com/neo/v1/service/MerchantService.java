package com.neo.v1.service;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.entity.CustomerCreditTransactionCategoryEntity;
import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import com.neo.v1.mapper.MerchantCategoryMapper;
import com.neo.v1.product.catalogue.model.MerchantCodeDetail;
import com.neo.v1.product.catalogue.model.MerchantDetail;
import com.neo.v1.repository.CustomerAccountTransactionCategoryCustomRepository;
import com.neo.v1.repository.CustomerAccountTransactionCategoryRepository;
import com.neo.v1.repository.CustomerCategoryRepository;
import com.neo.v1.repository.CustomerCreditTransactionCategoryRepository;
import com.neo.v1.repository.CustomerMerchantCategoryRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactions;
import com.neo.v1.transactions.enrichment.model.CreditCardTransactionsRequest;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CATEGORY_OTHER;
import static java.util.stream.Collectors.toMap;

@Service
@AllArgsConstructor
public class MerchantService {

    private final ProductCatalogueService productCatalogueService;
    private static Map<String, MerchantDetail> merchantDetailsCache;
    private static Map<String, MerchantCodeDetail> merchantCodeDetailsCache;
    private final CustomerAccountTransactionCategoryRepository customerAccountTransactionCategoryRepository;
    private final CustomerMerchantCategoryRepository customerMerchantCategoryRepository;
    private final MerchantCategoryMapper merchantCategoryMapper;
    private final CustomerAccountTransactionCategoryCustomRepository customerAccountTransactionCategoryCustomRepository;

    private final CustomerCategoryRepository customerCategoryRepository;
    private final CustomerCreditTransactionCategoryRepository customerCreditTransactionCategoryRepository;

    public Map<String, MerchantDetail> getCachedMerchantData() {
        if(Objects.isNull(merchantDetailsCache) || merchantDetailsCache.isEmpty()) {
            List<MerchantDetail> productCatalogueMerchant = productCatalogueService.getProductCatalogueMerchant();
            Map<String, MerchantDetail> merchantDetailsMap = productCatalogueMerchant.stream().collect(Collectors.toMap(MerchantDetail::getName, Function.identity()));
            setCachedMerchantData(merchantDetailsMap);
        }
        return merchantDetailsCache;
    }

    public Map<String, MerchantCodeDetail> getProductCatalogueMerchantCode() {
        if(Objects.isNull(merchantCodeDetailsCache) || merchantCodeDetailsCache.isEmpty()) {
            List<MerchantCodeDetail> productCatalogueMerchantCode = productCatalogueService.getProductCatalogueMerchantCode();
            Map<String, MerchantCodeDetail> merchantCodeDetailsMap = productCatalogueMerchantCode.stream().collect(Collectors.toMap(MerchantCodeDetail::getCode, Function.identity()));
            setMerchantCodeDetailsCache(merchantCodeDetailsMap);
        }
        return merchantCodeDetailsCache;
    }

    public static void setCachedMerchantData(Map<String, MerchantDetail> merchantDetailsMap) {
        merchantDetailsCache = merchantDetailsMap;
    }

    public static void setMerchantCodeDetailsCache(Map<String, MerchantCodeDetail> merchantCodeDetailsMap) {
        merchantCodeDetailsCache = merchantCodeDetailsMap;
    }

    @Scheduled(cron = "${te.product.merchantCronConfig}")
    public void clearCacheData() {
        if(Objects.nonNull(merchantDetailsCache) && !merchantDetailsCache.isEmpty()) {
            merchantDetailsCache.clear();
        }
        if(Objects.nonNull(merchantCodeDetailsCache) && !merchantCodeDetailsCache.isEmpty()) {
            merchantCodeDetailsCache.clear();
        }
    }

    @Transactional(readOnly = true)
    public void mapMerchantCategory(List<AccountTransaction> transactions, AccountTransactionsRequest request) {
        Map<String, MerchantDetail> cachedMerchantData = getCachedMerchantData();
        List<CustomerAccountTransactionCategoryEntity> accountTransactionCategoryList = customerAccountTransactionCategoryCustomRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getId(), getContext().getCustomerId(),
                Optional.ofNullable(request.getFromDate()).map(LocalDate::atStartOfDay).orElse(null), Optional.ofNullable(request.getToDate()).map(LocalDate::atStartOfDay).orElse(null));
        Map<String, CustomerAccountTransactionCategoryEntity> accountTransactionCategoryMap = accountTransactionCategoryList.stream().collect(toMap(CustomerAccountTransactionCategoryEntity::getTransactionReference, Function.identity()));
        List<CustomerMerchantCategoryEntity> customerMerchantCategoryEntityList = customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE);
        Map<String, CustomerMerchantCategoryEntity> merchantCategoryMap = customerMerchantCategoryEntityList.stream().collect(toMap(CustomerMerchantCategoryEntity::getName, Function.identity()));
        Map<String, MerchantCodeDetail> merchantCodeDetailMap = getProductCatalogueMerchantCode();
        transactions.forEach(transaction -> {
            if (Objects.nonNull(accountTransactionCategoryMap.get(transaction.getReference()))) {
                CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity = accountTransactionCategoryMap.get(transaction.getReference());
                if(customerAccountTransactionCategoryEntity.isCustom()) {
                    Long categoryId = Long.parseLong(customerAccountTransactionCategoryEntity.getCategoryId());
                    CustomerCategoryEntity accountTransactionCategory = customerCategoryRepository.findByIdAndActive(categoryId, Boolean.TRUE);
                    merchantCategoryMapper.mapCustomCategory(transaction, accountTransactionCategory);
                } else if(Objects.nonNull(cachedMerchantData.get(transaction.getMerchantName()))) {
                    merchantCategoryMapper.mapAccountTransactionCategory(transaction, cachedMerchantData.get(transaction.getMerchantName()));
                }
            } else if (Objects.nonNull(merchantCategoryMap.get(transaction.getMerchantName()))) {
                CustomerMerchantCategoryEntity customerMerchantCategoryEntity = merchantCategoryMap.get(transaction.getMerchantName());
                if(customerMerchantCategoryEntity.isCustom()) {
                    Long categoryId = Long.parseLong(customerMerchantCategoryEntity.getCategoryId());
                    CustomerCategoryEntity accountTransactionCategory = customerCategoryRepository.findByIdAndActive(categoryId, Boolean.TRUE);
                    merchantCategoryMapper.mapCustomCategory(transaction, accountTransactionCategory);
                } else if(Objects.nonNull(cachedMerchantData.get(transaction.getMerchantName()))) {
                    merchantCategoryMapper.mapAccountTransactionCategory(transaction, cachedMerchantData.get(transaction.getMerchantName()));
                }
            } else if(Objects.nonNull(cachedMerchantData.get(transaction.getMerchantName()))) {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, cachedMerchantData.get(transaction.getMerchantName()));
            } else if(Objects.nonNull(merchantCodeDetailMap.get(transaction.getMcCode()))) {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, merchantCodeDetailMap.get(transaction.getMcCode()));
            } else {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, cachedMerchantData.get(CATEGORY_OTHER));
            }
        });
    }

    @Transactional
    public void saveCustomerAccountTransactionCategory(CustomerAccountTransactionCategoryEntity customerAccountTransactionCategoryEntity) {
        customerAccountTransactionCategoryRepository.save(customerAccountTransactionCategoryEntity);
    }

    @Transactional
    public void saveCustomerMerchantCategory(CustomerMerchantCategoryEntity customerMerchantCategoryEntity) {
        customerMerchantCategoryRepository.save(customerMerchantCategoryEntity);
    }

    public void mapMerchantCategoryForCreditTransactions(List<CreditCardTransactions> transactions, CreditCardTransactionsRequest request) {
        Map<String, MerchantDetail> cachedMerchantData = getCachedMerchantData();
        List<CustomerCreditTransactionCategoryEntity> accountTransactionCategoryList = customerCreditTransactionCategoryRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getPciNumber(), getContext().getCustomerId(),
                Optional.ofNullable(request.getFromDate()).map(LocalDate::atStartOfDay).orElse(null), Optional.ofNullable(request.getToDate()).map(LocalDate::atStartOfDay).orElse(null));
        Map<String, CustomerCreditTransactionCategoryEntity> accountTransactionCategoryMap = accountTransactionCategoryList.stream().collect(toMap(CustomerCreditTransactionCategoryEntity::getTransactionReference, Function.identity()));
        List<CustomerMerchantCategoryEntity> customerMerchantCategoryEntityList = customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE);
        Map<String, CustomerMerchantCategoryEntity> merchantCategoryMap = customerMerchantCategoryEntityList.stream().collect(toMap(CustomerMerchantCategoryEntity::getName, Function.identity()));
        Map<String, MerchantCodeDetail> merchantCodeDetailMap = getProductCatalogueMerchantCode();
        transactions.forEach(transaction -> {
            if (Objects.nonNull(accountTransactionCategoryMap.get(transaction.getTransactionReference()))) {
                CustomerCreditTransactionCategoryEntity customerCreditTransactionCategoryEntity = accountTransactionCategoryMap.get(transaction.getTransactionReference());
                if(customerCreditTransactionCategoryEntity.isCustom()) {
                    Long categoryId = Long.parseLong(customerCreditTransactionCategoryEntity.getCategoryId());
                    CustomerCategoryEntity accountTransactionCategory = customerCategoryRepository.findByIdAndActive(categoryId, Boolean.TRUE);
                    merchantCategoryMapper.mapCustomCategory(transaction, accountTransactionCategory);
                } else if(Objects.nonNull(cachedMerchantData.get(transaction.getTransactionDescription1()))) {
                    merchantCategoryMapper.mapCreditCardTransactionCategory(transaction, cachedMerchantData.get(transaction.getTransactionDescription1()));
                }
            } else if (Objects.nonNull(merchantCategoryMap.get(transaction.getTransactionDescription1()))) {
                CustomerMerchantCategoryEntity customerMerchantCategoryEntity = merchantCategoryMap.get(transaction.getTransactionDescription1());
                if(customerMerchantCategoryEntity.isCustom()) {
                    Long categoryId = Long.parseLong(customerMerchantCategoryEntity.getCategoryId());
                    CustomerCategoryEntity accountTransactionCategory = customerCategoryRepository.findByIdAndActive(categoryId, Boolean.TRUE);
                    merchantCategoryMapper.mapCustomCategory(transaction, accountTransactionCategory);
                } else if(Objects.nonNull(cachedMerchantData.get(transaction.getTransactionDescription1()))) {
                    merchantCategoryMapper.mapCreditCardTransactionCategory(transaction, cachedMerchantData.get(transaction.getTransactionDescription1()));
                }
            } else if(Objects.nonNull(cachedMerchantData.get(transaction.getTransactionDescription1()))) {
                merchantCategoryMapper.mapCreditCardTransactionCategory(transaction, cachedMerchantData.get(transaction.getTransactionDescription1()));
            } else if(Objects.nonNull(merchantCodeDetailMap.get(transaction.getTransactionDescription2()))) {
                merchantCategoryMapper.mapCreditCardTransactionCategory(transaction, merchantCodeDetailMap.get(transaction.getTransactionDescription2()));
            } else {
                merchantCategoryMapper.mapCreditCardTransactionCategory(transaction, cachedMerchantData.get(CATEGORY_OTHER));
            }
        });
    }
}
