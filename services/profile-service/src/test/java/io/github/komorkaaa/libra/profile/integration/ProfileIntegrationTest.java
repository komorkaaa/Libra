//package io.github.komorkaaa.libra.profile.integration;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@SpringBootTest
//@AutoConfigureMockMvc(addFilters = false)
//@ActiveProfiles("test")
//class ProfileIntegrationTest {
//
//  @Autowired
//  MockMvc mockMvc;
//
//  @Autowired
//  ObjectMapper objectMapper;
//
//  @Test
//  void createProfile_thenGetById() throws Exception {
//    String body = """
//      {
//        "userId": "11111111-1111-1111-1111-111111111111",
//        "username": "ivan"
//      }
//      """;
//
//    String response = mockMvc.perform(post("/")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .content(body))
//            .andExpect(status().isOk())
//            .andReturn()
//            .getResponse()
//            .getContentAsString();
//
//    String profileId = objectMapper.readTree(response).get("profileId").asText();
//
//    mockMvc.perform(get("/" + profileId))
//            .andExpect(status().isOk())
//            .andExpect(jsonPath("$.id").value(profileId))
//            .andExpect(jsonPath("$.username").value("ivan"));
//  }
//}
