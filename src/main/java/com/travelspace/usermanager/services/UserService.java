package com.travelspace.usermanager.services;

import com.travelspace.usermanager.domain.entities.User;
import com.travelspace.usermanager.repositories.UserRepository;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {
  @Autowired
  private UserRepository repository;

  public User RegisterUser(User user) {
    return repository.save(user);
  }

  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return repository.findByEmail(email);
  }
}
