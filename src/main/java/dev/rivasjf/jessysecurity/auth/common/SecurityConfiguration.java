package dev.rivasjf.jessysecurity.auth.common;

import dev.rivasjf.jessysecurity.auth.services.CustomUserServiceDetails;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(CustomUserServiceDetails userDetails) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetails);
        provider.setPasswordEncoder(this.passwordEncoder());
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        int SALT_LENGTH = 16;
        int HASH_LENGTH = 32;
        int PARALLELISM = 1;
        int MEMORY = 1 << 14;
        int ITERATIONS = 2;
        return new Argon2PasswordEncoder(SALT_LENGTH, HASH_LENGTH, ITERATIONS, MEMORY, PARALLELISM);
    }
}
