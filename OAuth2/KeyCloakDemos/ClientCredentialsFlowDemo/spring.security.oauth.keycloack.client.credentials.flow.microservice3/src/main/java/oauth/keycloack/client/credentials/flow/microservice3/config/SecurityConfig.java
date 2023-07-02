package oauth.keycloack.client.credentials.flow.microservice3.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public void configureAuthenticationManager(AuthenticationManagerBuilder builder) throws Exception {
        builder.inMemoryAuthentication()
                .withUser("anil").password("anil").roles("USER")
                .and()
                .withUser("bandari").password("bandari").roles("ADMIN");
    }

    @Bean
    public SecurityFilterChain getSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers("/data").hasAnyRole("USER", "ADMIN")
                .anyRequest().authenticated()
                .and().cors().disable()
                .csrf().disable()
                .httpBasic();
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder NoOpPasswordEncoder(){
        return NoOpPasswordEncoder.getInstance();
    }
}
