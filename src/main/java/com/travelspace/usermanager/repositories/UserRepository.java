package com.travelspace.usermanager.repositories;

import com.travelspace.usermanager.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  UserDetails findByEmail(String email);
}
