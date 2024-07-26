package org.example.dao;

import java.util.List;

public interface CrudDAO <T> extends SuperDAO{
    boolean add(T entity);
    List<T> getAll();
    boolean update(T entity);
    boolean isExists(String id);
    T search(String id);
    boolean delete(String id);
}
