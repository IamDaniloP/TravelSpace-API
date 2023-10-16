package com.travelspace.usermanager.controllers;

import com.travelspace.usermanager.domain.entities.User;
import com.travelspace.usermanager.domain.dtos.LoginUserDto;
import com.travelspace.usermanager.domain.dtos.RegisterUserDto;

import com.travelspace.usermanager.infra.security.TokenResponse;
import com.travelspace.usermanager.infra.security.TokenService;
import com.travelspace.usermanager.services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class UserController {
  @Autowired
  private UserService userService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private TokenService tokenService;

  @PostMapping("/register")
  public ResponseEntity register(@RequestBody @Valid RegisterUserDto userDto) {
    if (this.userService.loadUserByUsername(userDto.getEmail()) != null) return ResponseEntity.badRequest().body("User already exist!");
    try {
      User copiedUser = new User();
      String encryptedPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
      userDto.setPassword(encryptedPassword);
      BeanUtils.copyProperties(userDto, copiedUser);

      User newUser = userService.RegisterUser(copiedUser);
      return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }
    catch (EntityExistsException e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
  }

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginUserDto userDto) {
    try {
      var usernamePassword = new UsernamePasswordAuthenticationToken(userDto.getEmail(), userDto.getPassword());
      var auth = this.authenticationManager.authenticate(usernamePassword);

      var token = tokenService.generateToken((User) auth.getPrincipal());

      return ResponseEntity.ok().body(new TokenResponse(token));
    }
    catch (Exception e) {
      System.out.println(e.getMessage());
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
  }
}