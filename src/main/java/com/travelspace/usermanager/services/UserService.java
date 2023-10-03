package com.travelspace.usermanager.services;

import com.travelspace.usermanager.domain.entities.User;
import com.travelspace.usermanager.repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService implements IUserService{

  @Autowired
  private UserRepository repository;
  @Override
  public User RegisterUser(User user) throws EntityExistsException {
    List<User> userList = repository.findAll();
    boolean existingUser = userList.stream().anyMatch(existingUserEmail -> existingUserEmail.getEmail().equals(user.getEmail()));

    if (!existingUser) {
      return repository.save(user);
    }

    throw new EntityExistsException("Already registered user!");
  }

  @Override
  public User LoginUser(String email, String password) throws NoSuchFieldException {
    List<User> userList = repository.findAll();
    boolean emailFound = userList.stream().anyMatch(existingUserEmail -> existingUserEmail.getEmail().equals(email));
    boolean passwordFound = userList.stream().anyMatch(existingUserPassword -> existingUserPassword.getPassword().equals(password));

    if (emailFound && passwordFound) {
      for (User user : userList) {
        if (user.getEmail().equals(email)) {
          return user;
        }
      }
    }
    throw new NoSuchFieldException("User not registered");
  }
}
