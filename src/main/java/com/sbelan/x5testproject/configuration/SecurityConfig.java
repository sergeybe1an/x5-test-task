package com.sbelan.x5testproject.configuration;

import com.sbelan.x5testproject.security.jwt.JwtConfigurer;
import com.sbelan.x5testproject.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private JwtTokenProvider jwtTokenProvider;

    private static final String LOGIN_ENDPOINT = "/api/v1/auth/login";
    private static final String REGISTRATION_ENDPOINT = "/api/v1/users/register";
    private static final String ROLE_SAVE_ENDPOINT = "/api/v1/roles/save";
    private static final String SWAGGER = "/v3/api-docs/";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .httpBasic().disable()
            .csrf().disable()
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authorizeRequests()
            .antMatchers(LOGIN_ENDPOINT).permitAll()
            .antMatchers(REGISTRATION_ENDPOINT).permitAll()
            .antMatchers(ROLE_SAVE_ENDPOINT).permitAll()
            .antMatchers(SWAGGER).permitAll()
            .anyRequest().authenticated()
            .and()
            .apply(new JwtConfigurer(jwtTokenProvider));
    }

    @Autowired
    public void setJwtTokenProvider(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }
}
