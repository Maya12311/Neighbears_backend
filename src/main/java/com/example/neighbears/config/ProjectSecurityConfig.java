package com.example.neighbears.config;

import com.example.neighbears.exceptionhandling.CustomAccessDeniedHandler;
import com.example.neighbears.exceptionhandling.CustomBasicAuthenticationEntryPoint;
import com.example.neighbears.filter.CsrfCookieFilter;
import com.example.neighbears.filter.RequestValidationBeforeFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.CookieClearingLogoutHandler;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.csrf.CsrfTokenRequestHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.sql.DataSource;
import java.util.List;
@Configuration
@Profile("!prod")
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain (HttpSecurity http) throws Exception {
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();
      //  http.sessionManagement(smc -> smc.maximumSessions(3).maxSessionsPreventsLogin(true)) //.expiredUrl()) -> in case spring is not enough //invalidSession needs to be handled still
//invalidSessionUrl("/test") genau vor maximumSession wenn man zu einer bestimmten url gehen möchte für den fehler
                http.securityContext(contextConfig ->contextConfig.requireExplicitSave(false))
                        .sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                        .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                            config.setAllowedMethods(Collections.singletonList("*"));

                            config.setAllowedHeaders(List.of("*"));
                            config.setAllowCredentials(true);
                            config.setMaxAge(3600L);

                            return config;
                        }


                })) // <-- Das ist neu

             //   .csrf(csrfConfig -> csrfConfig.disable())
                        .csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                                .ignoringRequestMatchers("/register", "/logout")     //here are the endpoints which gets ignored concerning csrf
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                        .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
                        .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
                        .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) //only http

                        .authorizeHttpRequests((requests) -> requests
                              //  .requestMatchers("/test").hasAuthority("VIEWACCOUNT") //its for a single action or allowance
                                .requestMatchers("/test").hasRole("USER") //hasRole is for a wider range of authorizitions
                                .requestMatchers(HttpMethod.PUT, "/profile").authenticated()
                                .requestMatchers(HttpMethod.POST, "/uploadImage", "/logout").authenticated()

                                .requestMatchers( "/profile","/allNeighbears", "/user", "/uploadImage", "/getprofilePic", "/logout").authenticated()
                .requestMatchers( "/error", "/login","/test", "/register", "/invalidSession", "/logout").permitAll())
                .formLogin(Customizer.withDefaults())
                        .logout(logout -> logout
                                .logoutUrl("/logout")                                  //  Endpoint
                                .deleteCookies("JSESSIONID", "SESSION", "XSRF-TOKEN")      //  Browser löscht Cookies
                                .invalidateHttpSession(true)                               //  Server-Session killen
                                .clearAuthentication(true)                                 //  Auth leeren
                                .logoutSuccessHandler((req, res, auth) -> {                // 🔴 204 → keine Redirects
                                    res.setStatus(jakarta.servlet.http.HttpServletResponse.SC_NO_CONTENT);
                                })
                        );

        //flc -> flc.loginPage("/login").defaultSuccessUrl("/profile").failureUrl("login?error=true")); part of 64 Form Login but not with Angular
        http.httpBasic(hbc ->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));


        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler())); // .accessDeniedPage("/denied")
        //http.exceptionHandling(ehc -> ehc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));  //its a global config
        return http.build();
    }

    private static void addDeleteCookie(HttpServletResponse res,
                                        String name, String path,
                                        boolean httpOnly, boolean secure,
                                        String sameSite, String domainOrNull) {
        StringBuilder sb = new StringBuilder();
        sb.append(name).append("=; ");
        sb.append("Max-Age=0; Expires=Thu, 01 Jan 1970 00:00:00 GMT; ");
        sb.append("Path=").append(path != null ? path : "/").append("; ");
        if (domainOrNull != null && !domainOrNull.isBlank()) sb.append("Domain=").append(domainOrNull).append("; ");
        if (secure) sb.append("Secure; ");
        if (httpOnly) sb.append("HttpOnly; ");
        if (sameSite != null && !sameSite.isBlank()) sb.append("SameSite=").append(sameSite).append("; ");
        res.addHeader("Set-Cookie", sb.toString());
    }




    @Bean
    public PasswordEncoder passwordEncoder(){

        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    //how to stop a user from using a simple password
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker(){
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
