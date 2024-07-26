package org.example.dao.custom;

import org.example.dao.CrudDAO;
import org.example.entity.Category;

public interface CategoryDAO extends CrudDAO<Category> {
    int generateNextCategoryId();
}
