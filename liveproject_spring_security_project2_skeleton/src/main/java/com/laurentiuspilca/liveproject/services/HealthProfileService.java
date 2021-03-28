package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.HealthProfile;
import com.laurentiuspilca.liveproject.exceptions.HealthProfileAlreadyExistsException;
import com.laurentiuspilca.liveproject.exceptions.NonExistentHealthProfileException;
import com.laurentiuspilca.liveproject.repositories.HealthProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class HealthProfileService {

  private final HealthProfileRepository healthProfileRepository;

  public HealthProfileService(HealthProfileRepository healthProfileRepository) {
    this.healthProfileRepository = healthProfileRepository;
  }

  public void addHealthProfile(HealthProfile profile) {
    Optional<HealthProfile> healthProfile = healthProfileRepository.findHealthProfileByUsername(profile.getUsername());

    if (healthProfile.isEmpty()) {
      healthProfileRepository.save(profile);
    } else {
      throw new HealthProfileAlreadyExistsException("This health profile already exists.");
    }
  }

  public HealthProfile findHealthProfile(String username) {
    Optional<HealthProfile> healthProfile =
            healthProfileRepository.findHealthProfileByUsername(username);

    return healthProfile
            .orElseThrow(() -> new NonExistentHealthProfileException("No profile found for the provided username."));
  }

  public void deleteHealthProfile(String username) {
    Optional<HealthProfile> healthProfile =
            healthProfileRepository.findHealthProfileByUsername(username);

    healthProfile.ifPresentOrElse(
            p -> healthProfileRepository.delete(p),
            () -> {
              throw new NonExistentHealthProfileException("No profile found for the provided username.");
            });
  }
}
