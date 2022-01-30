package com.neo.v1.service;

import com.neo.core.exception.ServiceException;
import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.enums.AccountTransactionStatusType;
import com.neo.v1.enums.customer.RecordType;
import com.neo.v1.mapper.AccountTransactionsMapper;
import com.neo.v1.mapper.AccountTransactionsResponseMapper;
import com.neo.v1.mapper.CreateCategoryResponseMapper;
import com.neo.v1.mapper.CustomerCategoryMapper;
import com.neo.v1.mapper.MetaMapper;
import com.neo.v1.mapper.TransferFeesRequestMapper;
import com.neo.v1.mapper.UpdateCategoryResponseMapper;
import com.neo.v1.model.account.TransferCharge;
import com.neo.v1.model.account.TransferFees;
import com.neo.v1.model.catalogue.CategoryDetail;
import com.neo.v1.model.customer.CustomerDetailData;
import com.neo.v1.repository.CustomerCategoryRepository;
import com.neo.v1.transactions.enrichment.model.AccountTransaction;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsRequest;
import com.neo.v1.transactions.enrichment.model.AccountTransactionsResponse;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.CreateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.CreateCategoryResponse;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.neo.core.context.GenericRestParamContextHolder.getContext;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREATE_CATEGORY_SUCCESS_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.CREATE_CATEGORY_SUCCESS_MSG;
import static com.neo.v1.constants.TransactionEnrichmentConstants.FAWRI_TRANSACTION_TYPE_FOR_PENDING;
import static com.neo.v1.constants.TransactionEnrichmentConstants.TRANSACTION_TYPE_CHARITY_TRANSFER_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.UPDATE_CATEGORY_SUCCESS_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.UPDATE_CATEGORY_SUCCESS_MSG;
import static com.neo.v1.enums.AccountTransactionStatusType.FAILED;
import static com.neo.v1.enums.AccountTransactionStatusType.FAILED_PENDING;
import static com.neo.v1.enums.AccountTransactionStatusType.PENDING;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_CATEGORY;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_CATEGORY_ID;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_COLOR;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_CUSTOMER_TYPE;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.INVALID_ICON;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.UPDATE_CATEGORY_INVALID_CATEGORY;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.UPDATE_CATEGORY_INVALID_COLOR;
import static com.neo.v1.enums.TransactionsServiceKeyMapping.UPDATE_CATEGORY_INVALID_ICON;
import static com.neo.v1.util.TransactionsUtils.decodeString;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

@Slf4j
@Service
@AllArgsConstructor
public class TransactionEnrichmentService {

    private final AccountService accountService;
    private final UrbisService urbisService;
    private final TransactionPaginationService transactionPaginationService;
    private final TransferFeesRequestMapper transferFeesRequestMapper;
    private final AccountTransactionsMapper accountTransactionsMapper;
    private final AccountTransactionsResponseMapper accountTransactionsResponseMapper;
    private final TransactionService transactionService;
    private final ProductCatalogueService productCatalogueService;
    private final CustomerCategoryRepository customerCategoryRepository;
    private final CustomerCategoryMapper customerCategoryMapper;
    private final CustomerService customerService;
    private final CreateCategoryResponseMapper createCategoryResponseMapper;
    private final UpdateCategoryResponseMapper updateCategoryResponseMapper;
    private final MetaMapper metaMapper;

    public AccountTransactionsResponse getAccountTransactions(AccountTransactionsRequest request) {
        request.setFilter(decodeString(request.getFilter()));
        List<AccountTransaction> transactions = emptyList();
        AccountTransactionStatusType statusType = AccountTransactionStatusType.forValue(request.getStatus());
        if (FAILED_PENDING == statusType || PENDING == statusType) {
            if (StringUtils.isBlank(request.getMaskedCardNumber())) {
                transactions = transactionService.getAccountTransactionsByStatus(request, statusType.value());
                if (PENDING == statusType) {
                    transactions.addAll(getChargesAndVatDetailsForFawriTransactions(transactions, request));
                }
            }
            transactions.addAll(getUrbisTransactionsByStatus(request));
            transactions = transactions.stream().sorted(Comparator.comparing(AccountTransaction::getTransactionDate).reversed()).collect(toList());
            transactions = transactionPaginationService.getPaginatedRecords(transactions, request.getOffset().intValue(), request.getPageSize().intValue());
        } else if (FAILED == statusType && StringUtils.isBlank(request.getMaskedCardNumber())) {
            transactions = transactionService.getAccountTransactionsByStatus(request, statusType.value());
            transactions = transactionPaginationService.getPaginatedRecords(transactions, request.getOffset().intValue(), request.getPageSize().intValue());
        } else {
            transactions = urbisService.getAccountTransactions(getContext().getCustomerId(), request)
                    .stream().map(accountTransactionsMapper::map)
                    .collect(toList());
        }

        log.info("Transaction Details for Account Id {} has been retrieved successfully.", request.getId());
        return accountTransactionsResponseMapper.map(transactions);
    }

    private List<AccountTransaction> getChargesAndVatDetailsForFawriTransactions(List<AccountTransaction> transactions, AccountTransactionsRequest request) {
        List<AccountTransaction> chargesAndVatTransactions = new ArrayList<>();
        List<AccountTransaction> fawriAccountTransactions = transactions.stream()
                .filter(accountTransaction -> FAWRI_TRANSACTION_TYPE_FOR_PENDING.equalsIgnoreCase(accountTransaction.getTransactionType()))
                .filter(accountTransaction -> !TRANSACTION_TYPE_CHARITY_TRANSFER_CODE.equals(accountTransaction.getTransactionTypeCode()))
                .collect(Collectors.toList());
        if (!fawriAccountTransactions.isEmpty()) {
            TransferFees transferFees = accountService.getFees(transferFeesRequestMapper.mapForPendingFawri(request.getId(), fawriAccountTransactions.get(0))).getData();
            if (isFawriChargesAvailable(transferFees.getCharges())) {
                chargesAndVatTransactions = accountTransactionsMapper.mapFawriChargesAndVat(fawriAccountTransactions, transferFees.getCharges().get(0), request);
            }
        }
        return chargesAndVatTransactions;
    }

    private List<AccountTransaction> getUrbisTransactionsByStatus(AccountTransactionsRequest request) {

        return urbisService.getPendingAccountTransactions(getContext().getCustomerId(), request)
                .stream().map(accountTransactionsMapper::map)
                .collect(toList());
    }


    private Boolean isFawriChargesAvailable(List<TransferCharge> transferCharges) {
        return !transferCharges.isEmpty()
                && transferCharges.get(0).getChargeAmountInAccountCurrency().compareTo(BigDecimal.ZERO) > 0;
    }

    public CategoryListResponse getMerchantCategoryList() {
        List<CategoryDetail> categoryList = productCatalogueService.getProductCatalogueMerchantCategory();
        List<CustomerCategoryEntity> customerCategoryEntityList = customerCategoryRepository.findByCustomerIdAndActive(getContext().getCustomerId(), Boolean.TRUE);
        return customerCategoryMapper.map(categoryList, customerCategoryEntityList);
    }

    public CreateCategoryResponse createCategory(CreateCategoryRequest req) {
        validateCreateCategoryRequest(req);
        CustomerDetailData customerDetail = customerService.getCustomerDetail();
        if (customerDetail.getRecordType() == RecordType.PROSPECT) {
            throw new ServiceException(INVALID_CUSTOMER_TYPE);
        }
        CustomerCategoryEntity categoryEntity = customerCategoryMapper.map(req, customerDetail.getCustomerId());
        CustomerCategoryEntity savedCategory = customerCategoryRepository.save(categoryEntity);
        return createCategoryResponseMapper.map(savedCategory, metaMapper.map(CREATE_CATEGORY_SUCCESS_CODE, CREATE_CATEGORY_SUCCESS_MSG));
    }

    public void validateCreateCategoryRequest(CreateCategoryRequest req) {
        if (StringUtils.isBlank(req.getName())) {
            throw new ServiceException(INVALID_CATEGORY);
        }
        if (StringUtils.isBlank(req.getIcon())) {
            throw new ServiceException(INVALID_ICON);
        }
        if (StringUtils.isBlank(req.getColor())) {
            throw new ServiceException(INVALID_COLOR);
        }
    }

    public UpdateCategoryResponse updateCategory(Long categoryId, UpdateCategoryRequest req) {
        categoryId = Optional.ofNullable(categoryId).orElseThrow( ()-> new ServiceException(INVALID_CATEGORY_ID));
        validateUpdateCategoryRequest(req);
        CustomerCategoryEntity categoryEntity = customerCategoryRepository.findByIdAndActive(categoryId, Boolean.TRUE).orElseThrow(() -> new ServiceException(INVALID_CATEGORY_ID));
        CustomerCategoryEntity updatedEntity = customerCategoryMapper.map(req, categoryEntity.getCustomerId(), categoryId);
        CustomerCategoryEntity savedCategory = customerCategoryRepository.save(updatedEntity);
        return updateCategoryResponseMapper.map(savedCategory, metaMapper.map(UPDATE_CATEGORY_SUCCESS_CODE, UPDATE_CATEGORY_SUCCESS_MSG));
    }

    public void validateUpdateCategoryRequest(UpdateCategoryRequest req) {
        if (StringUtils.isBlank(req.getName())) {
            throw new ServiceException(UPDATE_CATEGORY_INVALID_CATEGORY);
        }
        if (StringUtils.isBlank(req.getIcon())) {
            throw new ServiceException(UPDATE_CATEGORY_INVALID_ICON);
        }
        if (StringUtils.isBlank(req.getColor())) {
            throw new ServiceException(UPDATE_CATEGORY_INVALID_COLOR);
        }
    }
}
