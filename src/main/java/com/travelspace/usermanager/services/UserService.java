package com.travelspace.usermanager.services;

import com.travelspace.usermanager.domain.entities.User;
import com.travelspace.usermanager.repositories.UserRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService{
  @Autowired
  private UserRepository repository;
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public User RegisterUser(User user) {
    String jpql = "FROM User WHERE email = :email";
    Query query = entityManager.createQuery(jpql)
            .setParameter("email", user.getEmail());
    List resultUserData = query.getResultList();

    if (resultUserData.isEmpty()) {
      return repository.save(user);
    }

    throw new EntityExistsException("User already registered");
  }

  @Override
  public User LoginUser(String email, String password) throws NoSuchFieldException {
    try {
      String jpql = "FROM User WHERE email = :email AND password = :password";
      User resultUserData = entityManager.createQuery(jpql, User.class)
              .setParameter("email", email)
              .setParameter("password", password)
              .getSingleResult();

      return resultUserData;
    }
    catch (NoResultException e) {
      throw new NoSuchFieldException("Invalid credentials!");
    }
  }
}
