package com.travelspace.usermanager.repositories.interfaces;

import com.travelspace.usermanager.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends JpaRepository<User, Long> {
  UserDetails findByEmail(String email);

  @Query("SELECT u FROM User u WHERE u.email = :email")
  User userFindByEmail(String email);
}
