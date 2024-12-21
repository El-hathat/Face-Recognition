package com.dao;

import java.util.List;

public interface IDao<T, K> {

    T findOne(K id);

    List<T> findAll();

    void save(T entity);

    void update(T entity);

    void delete(T entity);

    void deleteById(K entityId);


}
