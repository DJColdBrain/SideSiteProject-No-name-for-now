package com.djcoldbrain.giflib.service;

import com.djcoldbrain.giflib.dao.GifDao;
import com.djcoldbrain.giflib.model.Gif;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOError;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GifServiceImpl implements GifService {

    @Autowired
    private GifDao gifDao;

    @Override
    public List<Gif> findAll() {
        return gifDao.findAll();
    }

    @Override
    public List<Gif> findAllStartWith(String q) {
        return gifDao.findAllStartWith(q);
    }

    @Override
    public List<Gif> findAllFavorite() {
        return gifDao.findAll().stream().filter(gif -> gif.isFavorite()).collect(Collectors.toList());
    }

    @Override
    public Gif findById(long id) {
        return gifDao.findById(id);
    }

    @Override
    public void toggleFavorite(Gif gif) {
        gif.setFavorite(!gif.isFavorite());
        gifDao.save(gif);
    }

    @Override
    public void save(Gif gif, MultipartFile file) {
        try {
            gif.setBytes(file.getBytes());
            gifDao.save(gif);
        }catch (IOException e){
            System.err.println("Unnable to get byte array from uploaded file");
        }
    }

    @Override
    public void delete(Gif gif) {
        gifDao.delete(gif);
    }
}
