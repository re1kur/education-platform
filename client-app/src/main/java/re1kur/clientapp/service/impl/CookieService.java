package re1kur.clientapp.service.impl;

import jakarta.servlet.http.Cookie;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CookieService {

    public boolean hasJwtCookie(Cookie[] cookies) {
        if (cookies == null) {
            return false;
        }
        for (Cookie cookie : cookies) {
            if ("jwt".equals(cookie.getName())) {
                log.info(cookie.toString());
                return true;
            }
        }
        return false;
    }
}