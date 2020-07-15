package dao;

import java.util.List;

public interface IDao<T> {

    void add(T obj);
    void update(T obj);
    void delete(T obj);
    List<T> findAll();

}
