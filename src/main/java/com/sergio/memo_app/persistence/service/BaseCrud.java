package com.sergio.memo_app.persistence.service;

import java.util.List;

public interface BaseCrud<T> {
    List<T> findAll();
    T findById(Long id);
    T update(T data);
    T insert(T data);
    void delete(Long id);
}
