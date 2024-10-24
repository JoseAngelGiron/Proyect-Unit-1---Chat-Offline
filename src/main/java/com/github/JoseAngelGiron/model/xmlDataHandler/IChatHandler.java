package com.github.JoseAngelGiron.model.xmlDataHandler;

import com.github.JoseAngelGiron.model.entity.User;

public interface IChatHandler <T, K> {

    K findAll(String name);
    T save( T entity, User user, User user2);
    boolean update(T entity);
    boolean delete(String name);
    boolean create(User name, User user);

}
