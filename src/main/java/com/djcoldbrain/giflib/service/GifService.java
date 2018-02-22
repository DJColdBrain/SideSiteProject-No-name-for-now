package com.djcoldbrain.giflib.service;

import com.djcoldbrain.giflib.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface GifService {

    List<Gif> findAll();

    List<Gif> findAllStartWith(String q);

    List<Gif> findAllFavorite();

    Gif findById(long id);

    void toggleFavorite(Gif gif);

    void save(Gif gif, MultipartFile file);

    void delete(Gif gif);
}
