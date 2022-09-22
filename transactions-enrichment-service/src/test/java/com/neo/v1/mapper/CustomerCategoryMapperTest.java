package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.product.catalogue.model.CategoryDetail;
import com.neo.v1.transactions.enrichment.model.CategoryListData;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.CreateCategoryRequest;
import com.neo.v1.transactions.enrichment.model.MerchantCategoryDetail;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
 class CustomerCategoryMapperTest {

    @InjectMocks
    private CustomerCategoryMapper customerCategoryMapper;

    @Mock
    private MetaMapper metaMapper;

    @Test
    void map_withCategoryListAndCustomCategoryList_returnCategoryListResponse() {
        MerchantCategoryDetail category1 = MerchantCategoryDetail.builder().id("1").name("category1").isCustom(false).build();
        MerchantCategoryDetail category2 = MerchantCategoryDetail.builder().id("2").name("category2").isCustom(true).build();
        List<MerchantCategoryDetail> categories = new ArrayList<>();
        categories.add(category2);
        categories.add(category1);
        CategoryListResponse expected = CategoryListResponse.builder().data(CategoryListData.builder().categories(categories).build()).build();
        java.util.List<CategoryDetail> categoryList = new ArrayList<>();
        categoryList.add(CategoryDetail.builder().id("1").name("category1").build());
        List<CustomerCategoryEntity> customerCategoryEntityList = Collections.singletonList(CustomerCategoryEntity.builder().id(2l).name("category2").build());
        CategoryListResponse result = customerCategoryMapper.map(categoryList, customerCategoryEntityList);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void map_withCreateCategoryRequestAndCustomerId_returnCustomerCategoryEntity() {
        String name = "name";
        String icon = "icon";
        String color = "color";
        CreateCategoryRequest req = CreateCategoryRequest.builder().name(name).icon(icon).color(color).build();
        String customerId = "1";
        CustomerCategoryEntity result = customerCategoryMapper.map(req, customerId);
        CustomerCategoryEntity expected = CustomerCategoryEntity.builder().customerId(customerId).name(name).icon(icon).color(color)
                .active(Boolean.TRUE).createdDate(result.getCreatedDate()).updatedDate(result.getUpdatedDate()).build();
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

    @Test
    void map_withUpdateCategoryRequestAndCustomerId_returnCustomerCategoryEntity() {
        String name = "name";
        String icon = "icon";
        String color = "color";
        Long id = 1l;
        UpdateCategoryRequest req = UpdateCategoryRequest.builder().name(name).icon(icon).color(color).build();
        String customerId = "1";
        CustomerCategoryEntity result = customerCategoryMapper.map(req, customerId, id);
        CustomerCategoryEntity expected = CustomerCategoryEntity.builder().id(id).customerId(customerId).name(name).icon(icon).color(color)
                .active(Boolean.TRUE).createdDate(result.getCreatedDate()).updatedDate(result.getUpdatedDate()).build();
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}
