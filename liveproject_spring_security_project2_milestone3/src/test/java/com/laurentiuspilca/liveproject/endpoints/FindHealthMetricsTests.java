package com.laurentiuspilca.liveproject.endpoints;

import com.laurentiuspilca.liveproject.services.HealthMetricService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FindHealthMetricsTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private HealthMetricService healthMetricService;

  @Test
  @DisplayName("Considering an authenticated request, assert that the returned HTTP status" +
          " is HTTP 200 OK and the service method is called.")
  public void findHealthMetricsTest() throws Exception {
    mvc.perform(
            get("/metric")
                    .with(jwt())
    )
            .andExpect(status().isOk());

    verify(healthMetricService).findHealthMetricHistory();
  }

  @Test
  @DisplayName("Considering an unauthenticated request, assert that the returned HTTP status" +
          " is HTTP 401 Unauthorized and the service method is not called.")
  public void findHealthMetricsUnauthenticatedTest() throws Exception {
    mvc.perform(
            get("/metric")
    )
            .andExpect(status().isUnauthorized());

    verify(healthMetricService, never()).findHealthMetricHistory();
  }
}
