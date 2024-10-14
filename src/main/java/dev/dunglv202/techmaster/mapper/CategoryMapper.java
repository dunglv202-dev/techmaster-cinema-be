package dev.dunglv202.techmaster.mapper;

import dev.dunglv202.techmaster.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CategoryMapper {
    CategoryMapper INSTANCE = Mappers.getMapper(CategoryMapper.class);

    default String toString(Category category) {
        if (category == null) return null;
        return category.getName();
    }
}
