package com.github.JoseAngelGiron.model.xmldatahandler;


import java.util.List;

public interface IXMLHandler<T, K> {

    K findAll();
    T save(T entity);
    boolean update(T entity);
    boolean delete(String name);
    boolean create();

}
