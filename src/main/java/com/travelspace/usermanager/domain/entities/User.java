package com.travelspace.usermanager.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private UUID id;
  private String name;
  private String birthDate;
  private String email;
  private String password;
  private String country;
  private String state;
  private String city;

  public User(){
  }
}
