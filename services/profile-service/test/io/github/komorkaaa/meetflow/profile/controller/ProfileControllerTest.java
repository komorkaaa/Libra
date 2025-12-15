package io.github.komorkaaa.meetflow.profile.controller;

import io.github.komorkaaa.meetflow.profile.dto.CreateProfileRequest;
import io.github.komorkaaa.meetflow.profile.service.ProfileService;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.UUID;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.ArgumentMatchers.any;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

  @Autowired
  MockMvc mvc;

  @MockBean
  ProfileService service;

  @Test
  void createProfile_returnsId() throws Exception {
    UUID id = UUID.randomUUID();
    Mockito.when(service.createProfile(any(CreateProfileRequest.class))).thenReturn(id);

    String body = """
            {
              "userId":"%s",
              "username":"alice"
            }
            """.formatted(UUID.randomUUID().toString());

    mvc.perform(post("/profiles")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(body))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.profileId").exists());
  }
}