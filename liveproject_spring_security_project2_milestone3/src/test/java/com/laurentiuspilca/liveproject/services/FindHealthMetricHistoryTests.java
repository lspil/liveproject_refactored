package com.laurentiuspilca.liveproject.services;

import com.laurentiuspilca.liveproject.entities.HealthMetric;
import com.laurentiuspilca.liveproject.repositories.HealthMetricRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest
public class FindHealthMetricHistoryTests {

  @Autowired
  private HealthMetricService healthMetricService;

  @MockBean
  private HealthMetricRepository healthMetricRepository;

  @Test
  @DisplayName("Considering a list of records is found in the database, " +
          "assert this list is returned by the method.")
  void findHealthMetricHistoryTest() {
    HealthMetric m = new HealthMetric();
    m.setId(10);

    when(healthMetricRepository.findHealthMetricHistory()).thenReturn(List.of(m));

    List<HealthMetric> result = healthMetricService.findHealthMetricHistory();

    assertEquals(result, List.of(m));
  }
}
