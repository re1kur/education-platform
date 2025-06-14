package re1kur.verificationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import payload.VerificationPayload;
import re1kur.verificationservice.service.impl.DefaultCodeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VerificationController.class)
class VerificationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private DefaultCodeService service;


    @Test
    void testSendCode_returnsOk() throws Exception {
        mockMvc.perform(post("/api/send-code").param("email", "test@example.com"))
                .andExpect(status().isOk())
                .andExpect(content().string("Code generated and sent to email."));
    }

    @Test
    void testVerify_callsService() throws Exception {
        VerificationPayload payload = new VerificationPayload("test@example.com", "123456");

        mockMvc.perform(post("/api/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());

        Mockito.verify(service).verify(payload);
    }

    @Test
    void testVerify_whenUserNotFound_throwsException() throws Exception {
        Mockito.doThrow(new UserNotFoundException("User not found"))
                .when(service).verify(Mockito.any(VerificationPayload.class));

        mockMvc.perform(post("/api/verify")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found"));
    }
}