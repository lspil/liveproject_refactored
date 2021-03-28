package com.laurentiuspilca.liveproject.controllers;

import com.laurentiuspilca.liveproject.entities.HealthProfile;
import com.laurentiuspilca.liveproject.services.HealthProfileService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class HealthProfileController {

  private final HealthProfileService healthProfileService;

  public HealthProfileController(HealthProfileService healthProfileService) {
    this.healthProfileService = healthProfileService;
  }

  @PostMapping
  public void addHealthProfile(@RequestBody HealthProfile healthProfile) {
    healthProfileService.addHealthProfile(healthProfile);
  }

  @GetMapping("/{username}")
  public HealthProfile findHealthProfile(@PathVariable String username) {
    return healthProfileService.findHealthProfile(username);
  }

  @DeleteMapping("/{username}")
  public void deleteHealthProfile(@PathVariable String username, Authentication a) {
    healthProfileService.deleteHealthProfile(username);
  }
}
