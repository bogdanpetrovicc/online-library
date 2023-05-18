package com.bogdan.onlinelibrary.service.generic.impl;

import com.bogdan.onlinelibrary.repository.generic.GenericRepository;
import com.bogdan.onlinelibrary.service.generic.GenericService;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter(AccessLevel.PROTECTED)
public class GenericServiceImpl<T> implements GenericService<T> {

    protected final GenericRepository<T> genericRepository;

    @Override
    public List<T> findAll() {
        return genericRepository.findAll();
    }

    @Override
    public T save(T object) {
        return genericRepository.save(object);
    }

    @Override
    public T update(T object) {
        return genericRepository.save(object);
    }

    @Override
    public void delete(Integer id) {
        genericRepository.deleteById(id);
    }

    @Override
    public T findById(Integer id) {
        return genericRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }
}
