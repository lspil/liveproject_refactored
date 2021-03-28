package com.laurentiuspilca.liveproject.security.services;

import com.laurentiuspilca.liveproject.entities.User;
import com.laurentiuspilca.liveproject.security.model.UserDetailsSecurityWrapper;
import com.laurentiuspilca.liveproject.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JpaUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public JpaUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findUserByUsername(username);
    return user
            .map(UserDetailsSecurityWrapper::new)
            .orElseThrow(() -> new UsernameNotFoundException("User not found!"));
  }
}
