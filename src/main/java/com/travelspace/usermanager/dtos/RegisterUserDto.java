package com.travelspace.usermanager.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
@Data
public class RegisterUserDto {
  @NotBlank
  private String name;
  @NotBlank
  private String birthDate;
  @NotBlank
  @Email
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  private String country;
  @NotBlank
  private String state;
  @NotBlank
  private String city;
}
