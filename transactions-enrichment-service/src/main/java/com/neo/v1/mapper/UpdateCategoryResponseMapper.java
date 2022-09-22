package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.transactions.enrichment.model.CategoryData;
import com.neo.v1.transactions.enrichment.model.Meta;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class UpdateCategoryResponseMapper {

    public UpdateCategoryResponse map(CustomerCategoryEntity customerCategory, Meta meta) {
        return UpdateCategoryResponse.builder().meta(meta)
                .data(CategoryData.builder()
                        .id(customerCategory.getId())
                        .name(customerCategory.getName())
                        .icon(customerCategory.getIcon())
                        .color(customerCategory.getColor()).build())
                .build();
    }
}
