package com.laurentiuspilca.liveproject.controllers;

import com.laurentiuspilca.liveproject.model.UserHealthData;
import com.laurentiuspilca.liveproject.services.HealthAdviceService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HealthAdviceController {

  private final HealthAdviceService adviceService;

  public HealthAdviceController(HealthAdviceService adviceService) {
    this.adviceService = adviceService;
  }

  @PostMapping("/data")
  public void collectHealthDataForAdvice(
          @RequestBody List<UserHealthData> userHealthData) {
    adviceService.generateHealthAdvices(userHealthData);
  }
}
