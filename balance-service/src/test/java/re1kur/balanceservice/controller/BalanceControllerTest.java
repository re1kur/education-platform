package re1kur.balanceservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dto.BalanceDto;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import re1kur.balanceservice.service.BalanceService;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BalanceController.class)
class BalanceControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private BalanceService service;

    private static final String url = "/api/balance";

    private Jwt jwt;

    @BeforeEach
    void setUp() {
        jwt = mock(Jwt.class);
        Mockito.when(jwt.getSubject()).thenReturn("user@example.com");

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                jwt, null, new ArrayList<>());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterEach
    void cleanUp() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void testGetBalance_returnsOk() throws Exception {
        BalanceDto dto = new BalanceDto("user@example.com", new BigDecimal("123.123"));

        Mockito.when(service.getBalance("user@example.com")).thenReturn(dto);

        mvc.perform(
                        get(url + "/get")
                                .content(mapper.writeValueAsString(jwt))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isFound())
                .andExpect(content().string(mapper.writeValueAsString(dto)));
    }
}