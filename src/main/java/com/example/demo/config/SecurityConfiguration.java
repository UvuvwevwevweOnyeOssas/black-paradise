package com.example.demo.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static com.example.demo.entity.Permission.*;
import static com.example.demo.entity.Role.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {
    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/horny-guy/register",
            "/api/v1/date-girl/register",
            "/api/v1/admin/register",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html"
    };

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())
                                .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasAuthority(ADMIN_READ.name())
                                .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasAuthority(ADMIN_CREATE.name())
                                .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasAuthority(ADMIN_UPDATE.name())
                                .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasAuthority(ADMIN_DELETE.name())
//                                .requestMatchers("/api/v1/date-girl/**").hasAnyRole(ADMIN.name(),DATE_GIRL.name())
//                                .requestMatchers(HttpMethod.GET,"/api/v1/date-girl/**").hasAnyAuthority(ADMIN_READ.name(),DATE_GIRL_READ.name())
//                                .requestMatchers(HttpMethod.POST,"/api/v1/date-girl/**").hasAnyAuthority(ADMIN_CREATE.name(),DATE_GIRL_CREATE.name())
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/date-girl/**").hasAnyAuthority(ADMIN_UPDATE.name(),DATE_GIRL_UPDATE.name())
//                                .requestMatchers(HttpMethod.DELETE,"/api/v1/date-girl/**").hasAnyAuthority(ADMIN_DELETE.name(),DATE_GIRL_DELETE.name())
//                                .requestMatchers("/api/v1/horny-guy/**").hasAnyRole(ADMIN.name(),HORNY_GUY.name())
//                                .requestMatchers(HttpMethod.GET,"/api/v1/horny-guy/**").hasAnyAuthority(ADMIN_READ.name(),HORNY_GUY_READ.name())
//                                .requestMatchers(HttpMethod.POST,"/api/v1/horny-guy/**").hasAnyAuthority(ADMIN_CREATE.name(),HORNY_GUY_CREATE.name())
//                                .requestMatchers(HttpMethod.PUT,"/api/v1/horny-guy/**").hasAnyAuthority(ADMIN_UPDATE.name(),HORNY_GUY_UPDATE.name())
//                                .requestMatchers(HttpMethod.DELETE,"/api/v1/horny-guy/**").hasAnyAuthority(ADMIN_DELETE.name(),HORNY_GUY_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        return http.build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8080/swagger-ui/**"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
