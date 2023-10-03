package com.travelspace.usermanager.services;

import com.travelspace.usermanager.domain.entities.User;
import jakarta.persistence.EntityExistsException;

public interface IUserService {
  public User RegisterUser(User user) throws EntityExistsException;

  public Object LoginUser(String email, String senha) throws NoSuchFieldException;
}
