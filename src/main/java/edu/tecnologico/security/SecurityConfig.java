package edu.tecnologico.security;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private CustomSuccessHandler customSuccessHandler;

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager manager = new JdbcUserDetailsManager(dataSource);

        manager.setUsersByUsernameQuery(
            "SELECT username, password, estatus FROM usuario WHERE username = ?"
        );

        manager.setAuthoritiesByUsernameQuery(
        	    "SELECT u.username, CONCAT('ROLE_', p.perfil) AS authority " +
        	    "FROM usuario u " +
        	    "JOIN usuario_perfil up ON u.id = up.id_usuario " +
        	    "JOIN perfil p ON up.id_perfil = p.id " +
        	    "WHERE u.username = ?"
        	);


        return manager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests()
                .requestMatchers("/login", "/registro", "/css/**", "/js/**", "/images/**").permitAll()
                .anyRequest().authenticated()
            .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(customSuccessHandler) 
                .permitAll()
            .and()
                .logout()
                .logoutSuccessUrl("/login?logout")
                .permitAll();

        return http.build();
    }
}
