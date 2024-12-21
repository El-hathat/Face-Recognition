package com.dao;

import java.util.List;

public interface IDao<T, K> {

    T findOne(K id);

    List<T> findAll();


    boolean save(T entity);

    boolean update(T entity);

    boolean delete(T entity);

    boolean deleteById(K entityId);


}
