package com.github.JoseAngelGiron.model.xmlDataHandler;

public interface IContactListHandler<T, K> {

    K findAll(T name);
    String save(T entity);
    boolean update(T entity);
    boolean delete(String name);
    boolean create(T name, int id);

}
