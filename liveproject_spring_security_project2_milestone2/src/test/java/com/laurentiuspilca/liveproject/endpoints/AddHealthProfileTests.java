package com.laurentiuspilca.liveproject.endpoints;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.laurentiuspilca.liveproject.entities.HealthProfile;
import com.laurentiuspilca.liveproject.services.HealthProfileService;
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
public class AddHealthProfileTests {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private HealthProfileService healthProfileService;

  @Test
  @DisplayName("Considering an authenticated request, assert that the returned HTTP status" +
          " is HTTP 200 OK and the service method is called.")
  public void addHealthProfileTest() throws Exception {
    HealthProfile p = new HealthProfile();
    p.setUsername("john");

    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJsonBody = ow.writeValueAsString(p);

    mvc.perform(
            post("/profile")
                    .with(jwt())
            .content(requestJsonBody)
            .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isOk());

    verify(healthProfileService).addHealthProfile(p);
  }

  @Test
  @DisplayName("Considering an unauthenticated request, assert that the returned HTTP status" +
          " is HTTP 403 Forbidden and the service method is not called.")
  public void addHealthProfileUnauthenticatedTest() throws Exception {
    HealthProfile p = new HealthProfile();
    p.setUsername("john");

    ObjectMapper mapper = new ObjectMapper();
    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
    String requestJsonBody = ow.writeValueAsString(p);

    mvc.perform(
            post("/profile")
                    .content(requestJsonBody)
                    .contentType(MediaType.APPLICATION_JSON)
    )
            .andExpect(status().isForbidden());

    verify(healthProfileService, never()).addHealthProfile(p);
  }
}
