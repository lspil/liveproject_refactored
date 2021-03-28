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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeleteHealthMetricForUserTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private HealthMetricService healthMetricService;

  @Test
  @DisplayName("Considering an authenticated request with a user having admin authority," +
          " assert that the returned HTTP status" +
          " is HTTP 200 OK and the service method is called.")
  public void deleteHealthMetricForUserAdminTest() throws Exception {
    mvc.perform(
            delete("/metric/{username}", "john")
                    .with(jwt().authorities(() -> "admin"))
    )
            .andExpect(status().isOk());

    verify(healthMetricService).deleteHealthMetricForUser("john");
  }

  @Test
  @DisplayName("Considering an authenticated request with a non-admin user ," +
          " assert that the returned HTTP status" +
          " is HTTP 403 Forbidden and the service method is not called.")
  public void deleteHealthMetricForNonAdminUserTest() throws Exception {
    mvc.perform(
            delete("/metric/{username}", "john")
                    .with(jwt())
    )
            .andExpect(status().isForbidden());

    verify(healthMetricService, never()).deleteHealthMetricForUser("john");
  }

  @Test
  @DisplayName("Considering an unauthenticated request," +
          " assert that the returned HTTP status" +
          " is HTTP 403 Forbidden and the service method is not called.")
  public void deleteHealthMetricUnauthenticatedTest() throws Exception {
    mvc.perform(
            delete("/metric/{username}", "john")
    )
            .andExpect(status().isForbidden());

    verify(healthMetricService, never()).deleteHealthMetricForUser("john");
  }

}
