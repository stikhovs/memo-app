package com.sergio.memo_app.persistence.service;

import java.util.List;

public interface BaseCrud<T, ID> {
    List<T> findAll();
    T findById(ID id);
    T update(T data);
    T insert(T data);
    void delete(ID id);
}
