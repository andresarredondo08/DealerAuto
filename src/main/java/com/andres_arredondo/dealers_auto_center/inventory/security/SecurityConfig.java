package com.andres_arredondo.dealers_auto_center.inventory.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            return http
                    .csrf(csrf -> csrf.disable())
                    .authorizeHttpRequests(auth -> auth
                            .requestMatchers("/admin/**").hasRole("GLOBAL_ADMIN")
                            .requestMatchers(
                                    "/swagger-ui/**",
                                    "/v3/api-docs/**",
                                    "/h2-console/**"
                            ).permitAll()
                            .anyRequest().permitAll()
                    )
                    .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                    .httpBasic(Customizer.withDefaults())
                    .build();
        }

        @Bean
        public UserDetailsService userDetailsService() {
            var admin = User.withUsername("admin")
                    .password("{noop}admin123")
                    .roles("GLOBAL_ADMIN")
                    .build();

            var user = User.withUsername("user")
                    .password("{noop}user123")
                    .roles("USER")
                    .build();

            return new InMemoryUserDetailsManager(admin, user);
        }
}
