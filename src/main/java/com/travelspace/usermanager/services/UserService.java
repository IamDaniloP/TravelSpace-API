package com.travelspace.usermanager.services;

import com.travelspace.usermanager.domain.dtos.RegisterUserDto;
import com.travelspace.usermanager.domain.entities.User;
import com.travelspace.usermanager.repositories.interfaces.IUserRepository;
import com.travelspace.usermanager.services.interfaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService, IUserService {
  @Autowired
  private IUserRepository repository;

  @Override
  public User RegisterUser(RegisterUserDto userDto) {
    if (repository.findByEmail(userDto.getEmail()) == null) {
      User copiedUser = new User();
      String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
      userDto.setPassword(encryptedPassword);

      copiedUser.MapFromDto(userDto);
      return repository.save(copiedUser);
    }

    throw new UsernameNotFoundException("User already exist!");
  }

  @Override
  public List<User> loadUsersByUsername() {
    return repository.findAll();
  }
  @Override
  public void deleteUser(String email) {
    User user = repository.userFindByEmail(email);
    if (user != null) {
      repository.delete(user);
    }
    else {
      throw new UsernameNotFoundException("User not found!");
    }
  }

  @Override
  public void updateUser(RegisterUserDto newUserDto) {
    User userDto = repository.userFindByEmail(newUserDto.getEmail());
    if (userDto != null) {
      String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
      newUserDto.setPassword(encryptedPassword);

      userDto.MapFromDto(newUserDto);
      repository.save(userDto);
    } else {
      throw new UsernameNotFoundException("User not found!");
    }
  }
  @Override
  public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    return repository.findByEmail(email);
  }
}
