package com.auspost.location.api.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * @author : github.com/deepakpal1982
 * @project : locationMS
 * @created : 14/07/2021, Tuesday
 **/
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
  @Value("${app.authentication.exclude}")
  private String[] noAuthEndpoints;
  @Value("${app.authentication.username}")
  private String username;

  /**
   * Adds security to endpoints except noAuthEndpoints defined in properties
   * @param http
   * @throws Exception
   */
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // loads h2-console (for development only)
     http.headers().frameOptions().sameOrigin();
    // endpoint authentication
    http.csrf().disable()
            .authorizeRequests()
            .antMatchers(HttpMethod.GET,noAuthEndpoints).permitAll()
            .anyRequest().authenticated()
            .and().httpBasic();
  }

  /**
   *
   * @param auth - creates authorized user with bcrypt password
   * @throws Exception
   */
  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    // username:password
    auth.inMemoryAuthentication()
            .passwordEncoder(new BCryptPasswordEncoder())
            .withUser(username)
            .password("$2a$12$XRYxzl5K4DtMjvbdsN4cVO1CKrndk7XJlrd3AImAvQUhGVB5pd5PC")
            .authorities("USER");
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
