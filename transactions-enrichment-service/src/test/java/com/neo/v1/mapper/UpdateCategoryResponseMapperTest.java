package com.neo.v1.mapper;

import com.neo.v1.entity.CustomerCategoryEntity;
import com.neo.v1.transactions.enrichment.model.CategoryData;
import com.neo.v1.transactions.enrichment.model.CreateCategoryResponse;
import com.neo.v1.transactions.enrichment.model.Meta;
import com.neo.v1.transactions.enrichment.model.UpdateCategoryResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryResponseMapperTest {

    @InjectMocks
    private UpdateCategoryResponseMapper updateCategoryResponseMapper;

    @Test
    void map_CustomerCategoryEntityAndMeta_returnUpdateCategoryResponse() {
        Long id = 1l;
        String name = "name";
        String icon = "icon";
        String color = "color";
        LocalDateTime date = LocalDateTime.now();
        CustomerCategoryEntity customerCategory = CustomerCategoryEntity.builder().id(id)
                .name(name).icon(icon).color(color).updatedDate(date).createdDate(date).build();
        Meta meta = Meta.builder().build();
        UpdateCategoryResponse expected = UpdateCategoryResponse.builder().meta(meta).data(CategoryData.builder()
                .id(id).name(name).icon(icon).color(color).build()).build();
        UpdateCategoryResponse result = updateCategoryResponseMapper.map(customerCategory, meta);
        assertThat(result).isEqualToComparingFieldByFieldRecursively(expected);
    }

}
