package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.HealthProfile;
import com.laurentiuspilca.liveproject.exceptions.NonExistentHealthProfileException;
import com.laurentiuspilca.liveproject.repositories.HealthMetricRepository;
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
import static org.mockito.Mockito.*;

@SpringBootTest
public class DeleteHealthMetricForUserTests {

  @Autowired
  private HealthMetricService healthMetricService;

  @MockBean
  private HealthMetricRepository healthMetricRepository;

  @MockBean
  private HealthProfileRepository healthProfileRepository;

  @Test
  @TestUser(username = "john", authorities = "admin")
  @DisplayName("Considering a request is done by an admin user to remove health metric records" +
          " and the profile for the metrics exists" +
          " assert that the records are removed from the database.")
  void deleteHealthMetricForUserWithAdminTest() {
    HealthProfile profile = new HealthProfile();
    profile.setUsername("bill");

    when(healthProfileRepository.findHealthProfileByUsername("bill"))
            .thenReturn(Optional.of(profile));

    healthMetricService.deleteHealthMetricForUser("bill");

    verify(healthMetricRepository).deleteAllForUser(profile);
  }

  @Test
  @TestUser(username = "john", authorities = "admin")
  @DisplayName("Considering a request is done by an admin user to remove health metric records" +
          " and the profile for the metrics doesn't exist" +
          " assert that the records are not removed from the database" +
          " and the app throws an exception.")
  void deleteHealthMetricForUserWithAdminProfileDoesntExistTest() {
    when(healthProfileRepository.findHealthProfileByUsername("bill"))
            .thenReturn(Optional.empty());

    assertThrows(NonExistentHealthProfileException.class,
            () -> healthMetricService.deleteHealthMetricForUser("bill"));

    verify(healthMetricRepository, never()).deleteAllForUser(any());
  }

  @Test
  @TestUser(username = "john", authorities = "read")
  @DisplayName("Considering a request is done by a non-admin user to remove a record" +
          " assert that the record is not removed from the database and" +
          " the application throws an exception.")
  void deleteHealthMetricForUserWithNonAdminTest() {
    HealthProfile profile = new HealthProfile();
    profile.setUsername("bill");

    when(healthProfileRepository.findHealthProfileByUsername("bill"))
            .thenReturn(Optional.of(profile));

    assertThrows(AccessDeniedException.class,
            () ->  healthMetricService.deleteHealthMetricForUser("bill"));

    verify(healthMetricRepository, never()).deleteAllForUser(profile);
  }
}
