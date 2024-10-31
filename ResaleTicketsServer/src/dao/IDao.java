package dao;

import dm.Ticket;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public interface IDao<T> extends java.io.Serializable {
    boolean delete(String key) throws IOException;

    boolean add(T object) throws IllegalArgumentException, IOException;

    T find(String key) throws IOException;

    List<T> findAll() throws IOException;

    void update(T object) throws IOException;
}