package org.example.bo.custom;

import org.example.bo.SuperBO;
import org.example.dto.CategoryDto;

import java.util.List;

public interface CategoryBO extends SuperBO {
    boolean addCategory(CategoryDto dto);
    List<CategoryDto> getAllCategory();
    boolean updateCategory(CategoryDto dto);
    boolean isExistCategory(String id);
    CategoryDto searchCategory(String id);
    boolean deleteCategory(String id);
    int generateNextCategoryId();
}
