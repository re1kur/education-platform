package re1kur.authenticationservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import exception.UserAuthenticationException;
import jakarta.ws.rs.core.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import payload.UserPayload;
import re1kur.authenticationservice.jwt.entity.Token;
import re1kur.authenticationservice.service.AuthenticationService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthenticationController.class)
public class AuthenticationControllerTest {
    @MockitoBean
    private AuthenticationService service;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    public void testRegisterValidUser() throws Exception {
        UserPayload payload = new UserPayload("email@example.com", "password");

        mvc.perform(
                        post("/api/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(payload)))
                .andExpect(status().isOk());

        Mockito.verify(service, Mockito.times(1)).register(Mockito.any(UserPayload.class));
    }

    @Test
    public void testRegisterInvalidUser() throws Exception {
        UserPayload invalidPayload = new UserPayload("ivalidemail", "password");

        mvc.perform(
                        post("/api/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(invalidPayload))
                )
                .andExpect(status().isBadRequest());

        Mockito.verifyNoInteractions(service);
    }

    @Test
    public void testAuthenticateWhenUserExists() throws Exception {
        UserPayload payload = new UserPayload("email@example.com", "password");
        Token token = new Token("body");

        Mockito.when(service.authenticate(payload)).thenReturn(token);

        mvc.perform(
                        post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("body"));

        Mockito.verify(service, Mockito.times(1)).authenticate(payload);
    }

    @Test
    public void testAuthenticateInvalidUser() throws Exception {
        UserPayload payload = new UserPayload("email@example.com", "password");

        Mockito.when(service.authenticate(payload)).thenThrow(UserAuthenticationException.class);

        mvc.perform(
                        post("/api/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(payload))
                )
                .andExpect(status().isUnauthorized());

        Mockito.verify(service, Mockito.times(1)).authenticate(payload);
    }
}
