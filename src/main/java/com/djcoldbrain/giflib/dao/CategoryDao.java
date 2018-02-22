package com.djcoldbrain.giflib.dao;

import com.djcoldbrain.giflib.model.Category;

import java.util.List;

public interface CategoryDao {

    List<Category> findAll();

    Category findById(long id);

    void save(Category category);

    void delete(Category category);
}
