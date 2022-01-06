package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategory;
import com.neo.v1.model.catalogue.CategoryDetail;
import com.neo.v1.transactions.enrichment.model.CategoryListData;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.MerchantCategoryDetail;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_CATEGORY_LIST_SUCCESS_CODE;
import static com.neo.v1.constants.TransactionEnrichmentConstants.GET_CATEGORY_LIST_SUCCESS_MSG;

@Component
@AllArgsConstructor
public class CustomerCategoryMapper {

    private final MetaMapper metaMapper;

    public CategoryListResponse map(List<CategoryDetail> categoryList, List<CustomerCategory> customerCategoryList) {
        List<MerchantCategoryDetail> customerCategories = customerCategoryList.stream().map(customerCategory -> map(customerCategory)).collect(Collectors.toList());
        List<MerchantCategoryDetail> categories = categoryList.stream().map(customerCategory -> map(customerCategory)).collect(Collectors.toList());
        customerCategories.addAll(categories);
        return CategoryListResponse.builder()
                .meta(metaMapper.map(GET_CATEGORY_LIST_SUCCESS_CODE, GET_CATEGORY_LIST_SUCCESS_MSG))
                .data(CategoryListData.builder().categories(customerCategories).build())
                .build();
    }

    private MerchantCategoryDetail map(CustomerCategory customerCategory) {
        return MerchantCategoryDetail.builder().id(customerCategory.getId())
                .name(customerCategory.getName())
                .icon(customerCategory.getIcon())
                .color(customerCategory.getColor())
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
}
