package com.laurentiuspilca.liveproject.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.laurentiuspilca.liveproject.entities.HealthMetric;
import com.laurentiuspilca.liveproject.services.HealthMetricService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AddHealthMetricTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private HealthMetricService healthMetricService;

  @Test
  @DisplayName("Considering an authenticated request, assert that the returned HTTP status" +
          " is HTTP 200 OK and the service method is called.")
  public void addHealthMetricTest() throws Exception {
    HealthMetric m = new HealthMetric();
    m.setId(10);

    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJsonBody = ow.writeValueAsString(m);

    mvc.perform(
            post("/metric")
                    .with(jwt())
                    .content(requestJsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isOk());

    verify(healthMetricService).addHealthMetric(m);
  }

  @Test
  @DisplayName("Considering an unauthenticated request, assert that the returned HTTP status" +
          " is HTTP 403 Forbidden and the service method is not called.")
  public void addHealthMetricUnauthenticatedTest() throws Exception {
    HealthMetric m = new HealthMetric();
    m.setId(10);

    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJsonBody = ow.writeValueAsString(m);

    mvc.perform(
            post("/metric")
                    .content(requestJsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isForbidden());

    verify(healthMetricService, never()).addHealthMetric(m);
  }
}
