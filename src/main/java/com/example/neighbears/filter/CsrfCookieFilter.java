package com.example.neighbears.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class CsrfCookieFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
     CsrfToken csrfToken=   (CsrfToken) request.getAttribute(CsrfToken.class.getName());
     csrfToken.getToken();

        if (csrfToken != null) {
            String current = Optional.ofNullable(request.getCookies())
                    .map(Arrays::stream).orElseGet(Stream::empty)
                    .filter(c -> "XSRF-TOKEN".equals(c.getName()))
                    .map(Cookie::getValue)
                    .findFirst()
                    .orElse(null);

            if (current == null || !current.equals(csrfToken.getToken())) {
                // Kein HttpOnly, damit Angular es auslesen kann
                // SameSite=Lax reicht fürs Lesen; für echte Cross-Site-Requests wäre None;Secure nötig (siehe Hinweis oben)
                StringBuilder sb = new StringBuilder();
                sb.append("XSRF-TOKEN=").append(csrfToken.getToken()).append("; ");
                sb.append("Path=/; ");
                sb.append("Max-Age=1800; "); // optional
                sb.append("SameSite=Lax; ");
                response.addHeader("Set-Cookie", sb.toString());
            }
        }
     filterChain.doFilter(request, response);
    }
}
