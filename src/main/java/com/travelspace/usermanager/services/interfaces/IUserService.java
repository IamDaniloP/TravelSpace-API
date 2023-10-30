package com.travelspace.usermanager.services.interfaces;

import com.travelspace.usermanager.domain.dtos.RegisterUserDto;
import com.travelspace.usermanager.domain.entities.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface IUserService {
    public User RegisterUser(RegisterUserDto userDto);
    public List<User> loadUsersByUsername();
    public void deleteUser(String email);
    public void updateUser(RegisterUserDto newUserDto);
}
