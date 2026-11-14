package org.example.demo.config;

import org.example.demo.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
public class WebSecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public WebSecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider(userDetailsService);

        provider.setPasswordEncoder(passwordEncoder());

        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {

        return (request, response, authentication) -> {

            boolean isAdmin = authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(authority ->
                            authority.getAuthority().equals("ROLE_ADMIN"));

            if (isAdmin) {
                response.sendRedirect(request.getContextPath() + "/admin");
            } else {
                response.sendRedirect(request.getContextPath() + "/user");
            }

        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
                .authenticationProvider(authenticationProvider())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/",
                                "/adatbazis",
                                "/kapcsolat",
                                "/diagram",
                                "/rest",
                                "/register",
                                "/login",
                                "/error",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/assets/**",
                                "/api/**"
                        ).permitAll()

                        .requestMatchers(
                                "/user",
                                "/uzenetek"
                        ).hasAnyRole("USER", "ADMIN")

                        .requestMatchers(
                                "/admin",
                                "/crud",
                                "/crud/**"
                        ).hasRole("ADMIN")

                        // Unmapped public URLs must reach MVC's 404 handling.
                        // Protected application routes are listed above.
                        .anyRequest().permitAll()
                )

                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/api/**")
                )

                .formLogin(form -> form
                        .loginPage("/login")
                        .successHandler(authenticationSuccessHandler())
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutSuccessUrl("/")
                        .permitAll()
                )

                .exceptionHandling(exception -> exception

                        .defaultAuthenticationEntryPointFor(
                                (request, response, authException) ->
                                        response.sendRedirect(request.getContextPath() + "/login"),
                                request ->
                                        request.getServletPath().equals("/user")
                        )

                        .defaultAuthenticationEntryPointFor(
                                (request, response, authException) ->
                                        response.sendRedirect(request.getContextPath() + "/"),
                                request ->
                                        request.getServletPath().equals("/admin")
                        )

                        .accessDeniedHandler(
                                (request, response, accessDeniedException) ->
                                        response.sendRedirect(request.getContextPath() + "/")
                        )
                );

        return http.build();
    }
}
