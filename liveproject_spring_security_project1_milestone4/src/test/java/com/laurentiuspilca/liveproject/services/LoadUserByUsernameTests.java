package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.User;
import com.laurentiuspilca.liveproject.repositories.UserRepository;
import com.laurentiuspilca.liveproject.security.services.JpaUserDetailsService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
public class LoadUserByUsernameTests {

  @Autowired
  private JpaUserDetailsService userDetailsService;

  @MockBean
  private UserRepository userRepository;

  @Test
  @DisplayName("Considering that the user doesn't exist in the database," +
          "test that the method throws UsernameNotFoundException.")
  public void loadUserByUsernameWhenUserDoesntExistTest() {
    String username = "username";

    when(userRepository.findUserByUsername(username))
            .thenReturn(Optional.empty());

    assertThrows(UsernameNotFoundException.class,
            () -> userDetailsService.loadUserByUsername(username));
  }

  @Test
  @DisplayName("Considering that the user already exists in the database," +
          "test that the method returns a valid UserDetails object.")
  public void loadUserByUsernameWhenUserExistsTest() {
    User user = new User();
    user.setUsername("username");

    when(userRepository.findUserByUsername(user.getUsername()))
            .thenReturn(Optional.of(user));

    UserDetails result = userDetailsService.loadUserByUsername(user.getUsername());

    assertEquals(result.getUsername(), user.getUsername());
  }
}
