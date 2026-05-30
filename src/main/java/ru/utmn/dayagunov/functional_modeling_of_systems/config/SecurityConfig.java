package ru.utmn.dayagunov.functional_modeling_of_systems.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.utmn.dayagunov.functional_modeling_of_systems.security.JpaUserDetailsService;
import ru.utmn.dayagunov.functional_modeling_of_systems.repository.user.UserRepository;


@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public UserDetailsService JpaUserDetailsService(
            UserRepository personRepository,
            BCryptPasswordEncoder bCryptPasswordEncoder) {
        return new JpaUserDetailsService(personRepository, bCryptPasswordEncoder);
    }


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth ->
                auth
                    .requestMatchers("/admin/**").hasRole("ADMIN")
                    .requestMatchers(HttpMethod.POST, "/api/users").permitAll()
                    .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
//                    .requestMatchers("/api/users").hasRole("ADMIN")
                    .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                    .requestMatchers("/login/**").permitAll()
                    .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
