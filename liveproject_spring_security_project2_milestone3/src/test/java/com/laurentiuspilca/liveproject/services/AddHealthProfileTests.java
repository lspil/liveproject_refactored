package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.HealthProfile;
import com.laurentiuspilca.liveproject.exceptions.HealthProfileAlreadyExistsException;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
public class AddHealthProfileTests {

  @Autowired
  private HealthProfileService healthProfileService;

  @MockBean
  private HealthProfileRepository healthProfileRepository;

  @Test
  @TestUser(username = "bill")
  @DisplayName("Considering a request is done to add a new record for another user than" +
          " the authenticated user assert that the record is not added to the database" +
          " and the application throws an exception.")
  public void addHealthProfileWrongUserTests() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    assertThrows(AccessDeniedException.class,
            () -> healthProfileService.addHealthProfile(healthProfile));
  }

  @Test
  @TestUser(username = "john")
  @DisplayName("Considering a request is done to add a new record for the authenticated user but" +
          " another profile record already exists for the same user," +
          " assert that the record is not added to the database and the application" +
          " throws an exception.")
  public void addHealthProfileHealthProfileExistsTests() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
            .thenReturn(Optional.of(healthProfile));

    assertThrows(HealthProfileAlreadyExistsException.class,
            () -> healthProfileService.addHealthProfile(healthProfile));
  }

  @Test
  @TestUser(username = "john")
  @DisplayName("Considering a request is done to add a new record for the authenticated user and" +
          " no other profile exists for the same user," +
          " assert that the record is added to the database.")
  public void addHealthProfileHealthProfileDoesntExistTests() {
    HealthProfile healthProfile = new HealthProfile();
    healthProfile.setUsername("john");

    when(healthProfileRepository.findHealthProfileByUsername(healthProfile.getUsername()))
            .thenReturn(Optional.empty());

    healthProfileService.addHealthProfile(healthProfile);

    verify(healthProfileRepository).save(healthProfile);
  }
}
