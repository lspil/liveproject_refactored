package com.laurentiuspilca.liveproject.controllers;

import com.laurentiuspilca.liveproject.controllers.dto.HealthAdvice;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.logging.Logger;

@RestController
@RequestMapping("/advice")
public class HealthAdviceController {

  private static Logger logger = Logger.getLogger(HealthAdviceController.class.getName());

  @PostMapping
  public void provideHealthAdviceCallback(@RequestBody List<HealthAdvice> healthAdvice) {
    healthAdvice.forEach(h -> logger.info("Advice for: "+ h.getUsername()+
            " Advice text: "+h.getAdvice()));
  }
}
