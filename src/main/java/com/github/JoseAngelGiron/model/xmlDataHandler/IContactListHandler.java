package com.github.JoseAngelGiron.model.xmlDataHandler;

public interface IContactListHandler<T, K, J> {

    K findAll(J name);
    T save(J entity, T entity2);
    boolean update(T entity);
    boolean delete(String name);
    boolean create(J name, int id);

}
