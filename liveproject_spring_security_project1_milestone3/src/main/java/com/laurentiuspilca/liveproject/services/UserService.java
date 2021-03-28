package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.User;
import com.laurentiuspilca.liveproject.exceptions.UserAlreadyExistsException;
import com.laurentiuspilca.liveproject.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UserService(UserRepository userRepository,
                     PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public void addUser(User user) {
    Optional<User> u = userRepository.findUserByUsername(user.getUsername());

    if (u.isEmpty()) {
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.getAuthorities().forEach(a -> a.setUser(user));
      userRepository.save(user);
    } else {
      throw new UserAlreadyExistsException("User already exists! You cannot add it twice.");
    }
  }

  public List<User> getAllUsers() {
    return userRepository.findAll();
  }
}
