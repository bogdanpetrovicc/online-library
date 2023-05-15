package com.bogdan.onlinelibrary.service.generic;

import java.util.List;

public interface GenericService<T> {

    List<T> findAll();

    T save(T t);

    T update(T t);

    void delete(Integer t);

    T findById(Integer id);
}
