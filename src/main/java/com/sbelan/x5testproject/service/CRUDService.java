package com.sbelan.x5testproject.service;

import java.util.List;

public interface CRUDService<T> {

    T findById(Long productId);

    List<T> findAll();

    T save(T product);

    void delete(Long productId);
}
