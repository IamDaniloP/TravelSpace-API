package com.travelspace.usermanager.controllers;

import com.travelspace.usermanager.domain.entities.User;
import com.travelspace.usermanager.dtos.LoginUserDto;
import com.travelspace.usermanager.dtos.RegisterUserDto;
import com.travelspace.usermanager.services.IUserService;

import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
  @Autowired
  private IUserService userService;
  @PostMapping("/user")
  public ResponseEntity<Object> registeredUser(@RequestBody @Valid RegisterUserDto userDto) {
    try {
      User copiedUser = new User();
      BeanUtils.copyProperties(userDto, copiedUser);

      User newUser = userService.RegisterUser(copiedUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    catch (EntityExistsException e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/auth")
  public ResponseEntity<Object> loginUser(@RequestBody @Valid LoginUserDto userDto) {
    try {
      User user = (User) userService.LoginUser(userDto.getEmail(), userDto.getPassword());
      return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    catch (NoSuchFieldException e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }
}