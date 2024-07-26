package org.example.bo.custom.impl;

import org.example.bo.custom.CategoryBO;
import org.example.dao.DAOFactory;
import org.example.dao.custom.CategoryDAO;
import org.example.dto.CategoryDto;
import org.example.entity.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoryBOImpl implements CategoryBO {
    private CategoryDAO categoryDAO= (CategoryDAO) DAOFactory.getDaoFactory().getDao(DAOFactory.DAOType.CATEGORY);

    @Override
    public boolean addCategory(CategoryDto dto) {
        return categoryDAO.add(new Category(dto.getCategoryId(),dto.getCategoryName()));
    }

    @Override
    public List<CategoryDto> getAllCategory() {
        List<Category> all = categoryDAO.getAll();
        List<CategoryDto> allCategory = new ArrayList<>();

        for (Category category: all) {
            allCategory.add(new CategoryDto(category.getCategoryId(),category.getCategoryName()));
        }

        return allCategory;
    }

    @Override
    public boolean updateCategory(CategoryDto dto) {
        return categoryDAO.update(new Category(dto.getCategoryId(),dto.getCategoryName()));
    }

    @Override
    public boolean isExistCategory(String id) {
        return categoryDAO.isExists(id);
    }

    @Override
    public CategoryDto searchCategory(String id) {
        Category search = categoryDAO.search(id);
        CategoryDto dto = new CategoryDto(search.getCategoryId(),search.getCategoryName());
        return dto;
    }

    @Override
    public boolean deleteCategory(String id) {
        return categoryDAO.delete(id);
    }

    @Override
    public int generateNextCategoryId() {
        return categoryDAO.generateNextCategoryId();
    }
}
