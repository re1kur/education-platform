package re1kur.clientapp.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import re1kur.clientapp.service.AuthenticationService;
import re1kur.clientapp.service.impl.CookieService;

import java.time.Duration;

@Slf4j
@Controller
@RequestMapping("auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationService service;
    private final CookieService cookieService;

    @ModelAttribute("hasJwtCookie")
    public Boolean hasJwtCookie(HttpServletRequest request) {
        return cookieService.hasJwtCookie(request.getCookies());
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("register")
    public String register() {
        return "register";
    }

    @PostMapping("login-form")
    public String form(
            @RequestParam String email,
            @RequestParam String password,
            HttpServletResponse response) {
        String jwt = service.authenticate(email, password);
        ResponseCookie cookie = ResponseCookie.from("jwt", jwt)
                .httpOnly(true)
                .path("/")
                .maxAge(Duration.ofHours(3))
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
        log.info(jwt);
        return "redirect:/";
    }
}
