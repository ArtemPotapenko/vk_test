package ru.vk_test.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.vk_test.excention.InvalidPasswordException;
import ru.vk_test.excention.UsernameNotFoundException;
import ru.vk_test.services.AccountService;
import ru.vk_test.services.AuditService;

import java.io.IOException;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
@Order(SecurityProperties.BASIC_AUTH_ORDER)
public class SecurityConfig extends WebSecurityConfiguration {

    private final AccountService accountService;
    private final AuditService auditService;

    @Bean
    public OncePerRequestFilter requestFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException, ServletException, IOException {
                try {
                    SecurityContextHolder.getContext().setAuthentication(accountService.login(request));
                    log.info("Пользователь успешно авторизован");
                } catch (InvalidPasswordException | UsernameNotFoundException e) {
                    log.info("Пользователь не авторизован");
                }
                filterChain.doFilter(request, response);
            }
        };
    }

    @SneakyThrows
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.cors(Customizer.withDefaults()).csrf(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable).formLogin(httpSecurityFormLoginConfigurer -> {
                }).authorizeHttpRequests(registry ->
                        registry
                                .requestMatchers(HttpMethod.GET, "/api/posts/", "api/posts").hasRole("POSTS_VIEWER")
                                .requestMatchers(HttpMethod.POST, "/api/posts/", "api/posts").hasRole("POSTS_EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/posts/", "api/posts").hasRole("POSTS_EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/posts/", "api/posts").hasRole("POSTS_EDITOR")

                                .requestMatchers(HttpMethod.GET, "/api/albums/", "api/albums").hasRole("ALBUMS_VIEWER")
                                .requestMatchers(HttpMethod.POST, "/api/albums/", "api/albums").hasRole("ALBUMS_EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/albums/", "api/albums").hasRole("ALBUMS_EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/albums/", "api/albums").hasRole("ALBUMS_EDITOR")

                                .requestMatchers(HttpMethod.GET, "/api/users/", "api/users").hasRole("USERS_VIEWER")
                                .requestMatchers(HttpMethod.POST, "/api/users/", "api/users").hasRole("USERS_EDITOR")
                                .requestMatchers(HttpMethod.PUT, "/api/users/", "api/users").hasRole("USERS_EDITOR")
                                .requestMatchers(HttpMethod.DELETE, "/api/users/**", "api/users").hasRole("USERS_EDITOR")

                                .anyRequest().hasRole("ADMIN"))
                .exceptionHandling((handling) -> handling.authenticationEntryPoint(
                        (request, response, exception) -> {
                            if (SecurityContextHolder.getContext().getAuthentication() != null && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()) {
                                auditService.create(request.getParameterMap(),SecurityContextHolder.getContext().getAuthentication().getName());
                            }
                            response.sendError(HttpServletResponse.SC_FORBIDDEN);
                        }))
                .addFilterBefore(requestFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }


}
