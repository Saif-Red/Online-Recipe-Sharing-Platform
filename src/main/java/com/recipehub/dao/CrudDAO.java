package com.recipehub.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/** Small generic DAO contract shared by JDBC repositories. */
public interface CrudDAO<T> {
    Optional<T> findById(int id) throws SQLException;
    List<T> findAll() throws SQLException;
    int insert(T entity) throws SQLException;
    boolean update(T entity) throws SQLException;
    boolean delete(int id) throws SQLException;
}
