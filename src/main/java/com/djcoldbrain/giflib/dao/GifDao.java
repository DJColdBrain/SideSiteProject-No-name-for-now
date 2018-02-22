package com.djcoldbrain.giflib.dao;

import com.djcoldbrain.giflib.model.Gif;

import java.util.List;

public interface GifDao {

    List<Gif> findAll();

    List<Gif> findAllStartWith(String q);

    Gif findById(long id);

    void save(Gif gif);

    void delete(Gif gif);
}
