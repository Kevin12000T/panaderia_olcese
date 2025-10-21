package com.olcese.panaderia.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        // ========== RUTAS PÚBLICAS ==========
                        .requestMatchers(
                                "/",
                                "/inicio",
                                "/login",
                                "/register",
                                "/catalogo",
                                "/reservas",
                                "/carrito",
                                "/boleta/**",
                                "/public/**",
                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/**.html"
                        ).permitAll()

                        // ========== CATÁLOGO PÚBLICO ==========
                        // Cualquiera puede ver productos y sucursales
                        .requestMatchers(
                                "/api/catalogo/**",
                                "/api/sucursales/**"
                        ).permitAll()

                        // ========== RESERVAS (USUARIOS AUTENTICADOS) ==========
                        // Clientes pueden hacer y ver sus reservas
                        .requestMatchers(
                                "/api/reservas/**",
                                "/api/carrito/**",
                                "/api/pedidos/**"
                        ).authenticated()

                        // ========== API REST (AUTENTICADO) ==========
                        .requestMatchers("/api/**").authenticated()

                        // ========== TODO LO DEMÁS ==========
                        .anyRequest().permitAll()
                )
                .addFilterBefore(jwtAuthenticationFilter,
                        UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
