package com.djcoldbrain.giflib.service;

import com.djcoldbrain.giflib.model.Category;

import java.util.List;

public interface CategoryService {

    List<Category> findAll();

    Category findById(long id);

    void save(Category category);

    void delete(Category category);
}
