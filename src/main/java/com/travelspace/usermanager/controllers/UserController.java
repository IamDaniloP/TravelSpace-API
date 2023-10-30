package com.travelspace.usermanager.controllers;

import com.travelspace.usermanager.domain.entities.User;
import com.travelspace.usermanager.domain.dtos.LoginUserDto;
import com.travelspace.usermanager.domain.dtos.RegisterUserDto;

import com.travelspace.usermanager.infra.security.TokenResponse;
import com.travelspace.usermanager.infra.security.TokenService;
import com.travelspace.usermanager.services.UserService;
import com.travelspace.usermanager.services.interfaces.IUserService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class UserController {
  private IUserService userService;
  private AuthenticationManager authenticationManager;
  private TokenService tokenService;

  @Autowired
  public UserController(IUserService userService, AuthenticationManager authenticationManager, TokenService tokenService) {
    this.userService = userService;
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
  }

  @PostMapping("auth/register")
  public ResponseEntity register(@RequestBody @Valid RegisterUserDto userDto) {
    try {
      User newUser = userService.RegisterUser(userDto);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("auth/login")
  public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginUserDto userDto) {
    try {
      var usernamePassword = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
      var auth = this.authenticationManager.authenticate(usernamePassword);

      var token = tokenService.generateToken((User) auth.getPrincipal());

      return ResponseEntity.status(HttpStatus.ACCEPTED).body(new TokenResponse(token));
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }

  @GetMapping
  public ResponseEntity<List<User>> loadUsersByUsername() {
    try {
      List<User> userList = userService.loadUsersByUsername();
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(userList);
    }
    catch (Exception e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
  }

  @DeleteMapping("delete/{email}")
  public ResponseEntity deleteUser(@PathVariable String email) {
    try {
      userService.deleteUser(email);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body("User deleted!");
    } catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("update")
  public ResponseEntity updateUser(@RequestBody RegisterUserDto userDto) {
    try {
      userService.updateUser(userDto);
      return ResponseEntity.status(HttpStatus.ACCEPTED).body(userDto);
    }
    catch (UsernameNotFoundException e) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }
  }
}