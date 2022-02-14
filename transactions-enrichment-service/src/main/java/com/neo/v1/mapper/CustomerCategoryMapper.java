package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.model.catalogue.CategoryDetail;
import com.neo.v1.transactions.enrichment.model.CategoryListData;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.CreateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.MerchantCategoryDetail;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_CATEGORY_LIST_SUCCESS_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_CATEGORY_LIST_SUCCESS_MSG;

@Component
@AllArgsConstructor
public class CustomerCategoryMapper {

    private final MetaMapper metaMapper;

    public CategoryListResponse map(List<CategoryDetail> categoryList, List<CustomerCategoryEntity> customerCategoryEntityList) {
        List<MerchantCategoryDetail> customerCategories = customerCategoryEntityList.stream()
                .map(customerCategory -> map(customerCategory)).collect(Collectors.toList());
        List<MerchantCategoryDetail> categories = categoryList.stream().map(customerCategory -> map(customerCategory)).collect(Collectors.toList());
        customerCategories.addAll(categories);
        return CategoryListResponse.builder()
                .meta(metaMapper.map(GET_CATEGORY_LIST_SUCCESS_CODE, GET_CATEGORY_LIST_SUCCESS_MSG))
                .data(CategoryListData.builder().categories(customerCategories).build())
                .build();
    }

    private MerchantCategoryDetail map(CustomerCategoryEntity customerCategoryEntity) {
        return MerchantCategoryDetail.builder().id(customerCategoryEntity.getId().toString())
                .name(customerCategoryEntity.getName())
                .icon(customerCategoryEntity.getIcon())
                .color(customerCategoryEntity.getColor())
                .isCustom(Boolean.TRUE)
                .build();
    }

    private MerchantCategoryDetail map(CategoryDetail categoryDetail) {
        return MerchantCategoryDetail.builder().id(categoryDetail.getId())
                .name(categoryDetail.getName())
                .icon(categoryDetail.getIcon())
                .color(categoryDetail.getColor())
                .isCustom(Boolean.FALSE)
                .build();
    }

    public CustomerCategoryEntity map(CreateCategoryRequest req, String customerId) {
        return CustomerCategoryEntity.builder()
                .customerId(customerId)
                .name(req.getName())
                .icon(req.getIcon())
                .color(req.getColor())
                .active(Boolean.TRUE)
                .updatedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
    }

    public CustomerCategoryEntity map(UpdateCategoryRequest req, String customerId, Long id) {
        return CustomerCategoryEntity.builder()
                .id(id)
                .customerId(customerId)
                .name(req.getName())
                .icon(req.getIcon())
                .color(req.getColor())
                .active(Boolean.TRUE)
                .updatedDate(LocalDateTime.now())
                .createdDate(LocalDateTime.now())
                .build();
    }
}
