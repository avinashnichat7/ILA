package com.neo.v1.service;

import com.neo.v1.entity.CustomerAccountTransactionCategoryEntity;
import com.neo.v1.entity.CustomerMerchantCategoryEntity;
import com.neo.v1.mapper.MerchantCategoryMapper;
import com.neo.v1.model.catalogue.MerchantCodeDetail;
import com.neo.v1.model.catalogue.MerchantDetail;
import com.neo.v1.repository.CustomerAccountTransactionCategoryRepository;
import com.neo.v1.repository.CustomerMerchantCategoryRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        List<CustomerAccountTransactionCategoryEntity> accountTransactionCategoryList = customerAccountTransactionCategoryRepository.findByAccountIdAndCustomerIdAndTransactionDateBetween(request.getId(), getContext().getCustomerId(), request.getFromDate().atStartOfDay(), request.getToDate().atStartOfDay());
        Map<String, CustomerAccountTransactionCategoryEntity> accountTransactionCategoryMap = accountTransactionCategoryList.stream().collect(toMap(CustomerAccountTransactionCategoryEntity::getTransactionReference, Function.identity()));
        List<CustomerMerchantCategoryEntity> customerMerchantCategoryEntityList = customerMerchantCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE);
        Map<String, CustomerMerchantCategoryEntity> merchantCategoryMap = customerMerchantCategoryEntityList.stream().collect(toMap(CustomerMerchantCategoryEntity::getName, Function.identity()));
        Map<String, MerchantDetail> cachedMerchantData = getCachedMerchantData();
        Map<String, MerchantCodeDetail> merchantCodeDetailMap = getProductCatalogueMerchantCode();
        transactions.forEach(transaction -> {
            if (Objects.nonNull(accountTransactionCategoryMap.get(transaction.getReference()))) {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, accountTransactionCategoryMap.get(transaction.getReference()));
            } else if (Objects.nonNull(merchantCategoryMap.get(transaction.getMerchantName()))) {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, merchantCategoryMap.get(transaction.getMerchantName()));
            } else if(Objects.nonNull(cachedMerchantData.get(transaction.getMerchantName()))) {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, cachedMerchantData.get(transaction.getMerchantName()));
            } else if(Objects.nonNull(merchantCodeDetailMap.get(transaction.getMcCode()))) {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, merchantCodeDetailMap.get(transaction.getMerchantName()));
            } else {
                merchantCategoryMapper.mapAccountTransactionCategory(transaction, cachedMerchantData.get(CATEGORY_OTHER));
            }
        });
    }

}
