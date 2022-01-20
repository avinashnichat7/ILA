package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategory;
import com.neo.v1.model.catalogue.CategoryDetail;
import com.neo.v1.transactions.enrichment.model.CategoryListData;
import com.neo.v1.transactions.enrichment.model.CategoryListResponse;
import com.neo.v1.transactions.enrichment.model.MerchantCategoryDetail;
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
        MerchantCategoryDetail category1 = MerchantCategoryDetail.builder().id(1l).name("category1").isCustom(false).build();
        MerchantCategoryDetail category2 = MerchantCategoryDetail.builder().id(2l).name("category2").isCustom(true).build();
        List<MerchantCategoryDetail> categories = new ArrayList<>();
        categories.add(category2);
        categories.add(category1);
        CategoryListResponse expected = CategoryListResponse.builder().data(CategoryListData.builder().categories(categories).build()).build();
        java.util.List<CategoryDetail> categoryList = new ArrayList<>();
        categoryList.add(CategoryDetail.builder().id(1l).name("category1").build());
        List<CustomerCategory> customerCategoryList = Collections.singletonList(CustomerCategory.builder().id(2l).name("category2").build());
        CategoryListResponse result = customerCategoryMapper.map(categoryList, customerCategoryList);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }
}
