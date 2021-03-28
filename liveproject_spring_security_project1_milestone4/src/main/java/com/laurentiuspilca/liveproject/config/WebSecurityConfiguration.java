package com.laurentiuspilca.liveproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class WebSecurityConfiguration
        extends WebSecurityConfigurerAdapter {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  @Bean
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.csrf(c -> c.ignoringAntMatchers(
            "/user/**", "/client/**"
    ));

    http.authorizeRequests()
            .mvcMatchers("/user/**").permitAll()
            .mvcMatchers("/client/**").permitAll();

    super.configure(http);

  }
}
