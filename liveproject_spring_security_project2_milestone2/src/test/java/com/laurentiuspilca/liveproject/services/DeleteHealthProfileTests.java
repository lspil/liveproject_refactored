package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.HealthProfile;
import com.laurentiuspilca.liveproject.exceptions.NonExistentHealthProfileException;
import com.laurentiuspilca.liveproject.repositories.HealthProfileRepository;
import com.laurentiuspilca.liveproject.services.context.TestUser;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteHealthProfileTests {

  @Autowired
  private HealthProfileService healthProfileService;

  @MockBean
  private HealthProfileRepository healthProfileRepository;

  @Test
  @TestUser(username = "bill", authorities = "read")
  @DisplayName("Considering a request is done by a non-admin user to remove a record" +
          " assert that the record is not removed from the database and" +
          " the application throws an exception.")
  public void deleteHealthProfileNonAdminTest() {
    assertThrows(AccessDeniedException.class,
            () -> healthProfileService.deleteHealthProfile("john"));

    verify(healthProfileRepository, never()).delete(any());
  }

  @Test
  @TestUser(username = "bill", authorities = "admin")
  @DisplayName("Considering a request is done by an admin user to remove a record but" +
          " the record doesn't exists in the database " +
          " assert that the application throws an exception.")
  public void deleteHealthProfileAdminProfileNotPresentTest() {
    when(healthProfileRepository.findHealthProfileByUsername("john"))
            .thenReturn(Optional.empty());

    assertThrows(NonExistentHealthProfileException.class,
            () -> healthProfileService.deleteHealthProfile("john"));
  }

  @Test
  @TestUser(username = "bill", authorities = "admin")
  @DisplayName("Considering a request is done by an admin user to remove a record and" +
          " the record exists in the database " +
          " assert that the record is removed from the database.")
  public void deleteHealthProfileAdminProfileExistsTest() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    when(healthProfileRepository.findHealthProfileByUsername("john"))
            .thenReturn(Optional.of(healthProfile));

    healthProfileService.deleteHealthProfile("john");

    verify(healthProfileRepository).delete(healthProfile);
  }
}
