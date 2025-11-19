package ru.KostarevaAnastasia.NauJava;

import java.util.Scanner;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ru.KostarevaAnastasia.NauJava.security.CustomUserDetailsService;

@Configuration
@EnableWebSecurity
@Profile("!test")
public class Config
{
    private final CustomUserDetailsService userDetailsService;
    public Config(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/registration", "/login", "/logout").permitAll()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").hasRole("ADMIN")
                        .requestMatchers("/create/**").hasAuthority("ROLE_CREATOR")
                        .requestMatchers("/api/reports/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .permitAll()
                )
                .userDetailsService(userDetailsService);

        return http.build();
    }

    @PostConstruct
    public void printAppInfo() {
        System.out.println("Запущено приложение: " + appName + " v" + appVersion);
    }
}


