package com.laurentiuspilca.liveproject.endpoints;

import com.laurentiuspilca.liveproject.services.HealthProfileService;
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
public class DeleteHealthProfileTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private HealthProfileService healthProfileService;

  @Test
  @DisplayName("Considering an authenticated request with a user having admin authority," +
          " assert that the returned HTTP status" +
          " is HTTP 200 OK and the service method is called.")
  public void deleteHealthProfileAuthenticatedWithAdminTest() throws Exception {
    mvc.perform(
            delete("/profile/{username}", "john")
                    .with(jwt().authorities(() -> "admin"))
    )
            .andExpect(status().isOk());

    verify(healthProfileService).deleteHealthProfile("john");
  }

  @Test
  @DisplayName("Considering an authenticated request with a non-admin user ," +
          " assert that the returned HTTP status" +
          " is HTTP 403 Forbidden and the service method is not called.")
  public void deleteHealthProfileAuthenticatedWithNonAdminUserTest() throws Exception {
    mvc.perform(
            delete("/profile/{username}", "john")
                    .with(jwt())
    )
            .andExpect(status().isForbidden());

    verify(healthProfileService, never()).deleteHealthProfile("john");
  }

  @Test
  @DisplayName("Considering an unauthenticated request," +
          " assert that the returned HTTP status" +
          " is HTTP 403 Forbidden and the service method is not called.")
  public void deleteHealthProfileUnauthenticatedTest() throws Exception {
    mvc.perform(
            delete("/profile/{username}", "john")
    )
            .andExpect(status().isForbidden());

    verify(healthProfileService, never()).deleteHealthProfile("john");
  }
}
