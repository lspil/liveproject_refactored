package com.laurentiuspilca.liveproject.repositories;

import com.laurentiuspilca.liveproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

  Optional<User> findUserByUsername(String username);
}
