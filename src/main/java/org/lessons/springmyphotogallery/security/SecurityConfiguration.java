package org.lessons.springmyphotogallery.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    /* per definire un AuthenticationProvider ho bisogno di:
  - uno UserDetailsService
  - un PasswordEncoder
 */
    // questo è lo UserDetailsService
    @Bean
    DatabaseUserDetailsService userDetailsService() {
        return new DatabaseUserDetailsService();
    }

    // questo è il PasswordEncoder (che deduce l'algoritmo di encoding da una stringa nella password stessa)
    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        // creo l'authenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        // gli setto il PasswordEncoder
        provider.setPasswordEncoder(passwordEncoder());
        // gli setto lo UserDetailsService
        provider.setUserDetailsService(userDetailsService());
        return provider;
    }

    /*
        ROTTE da proteggere:
        tutte ADMIN tranne:
        - home ("/") e ("/photos): PUBBLICA
        - dettaglio foto ("/photos/{id}"): PUBBLICA
    */

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // definisco la catena di filtri
        http.authorizeHttpRequests()
                .requestMatchers("/photos/create").hasAuthority("ADMIN")
                .requestMatchers("/photos/edit/**").hasAuthority("ADMIN")
                .requestMatchers("/photos/delete/**").hasAuthority("ADMIN")
                .requestMatchers("/photos").permitAll()
                .requestMatchers("/photos/{id}").permitAll()
                .requestMatchers("/categories/**").hasAuthority("ADMIN")
                .requestMatchers("/**").permitAll()
                .and().formLogin()
                .and().logout();
        return http.build();
    }
}
