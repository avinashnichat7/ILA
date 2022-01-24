package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.transactions.enrichment.model.CategoryData;
import com.neo.v1.transactions.enrichment.model.CreateCategoryResponse;
import com.neo.v1.transactions.enrichment.model.Meta;
import org.springframework.stereotype.Component;

@Component
public class CreateCategoryResponseMapper {

    public CreateCategoryResponse map(CustomerCategoryEntity customerCategory, Meta meta) {
        return CreateCategoryResponse.builder().meta(meta)
                .data(CategoryData.builder()
                        .id(customerCategory.getId())
                        .name(customerCategory.getName())
                        .icon(customerCategory.getIcon())
                        .color(customerCategory.getColor()).build())
                .build();
    }
}
