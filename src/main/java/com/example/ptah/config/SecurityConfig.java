package com.example.ptah.config;

import com.example.ptah.jwt.JwtConfig;
import com.example.ptah.jwt.JwtUsernamePasswordAuthenticationFilter;
import com.example.ptah.jwt.JwtVerifier;
import com.example.ptah.user.UserService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Configuration
class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtConfig jwtConfig;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                // no CSRF protection is needed for REST API
                .csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilter(new JwtUsernamePasswordAuthenticationFilter(authenticationManager(), jwtConfig))
                .addFilterAfter(new JwtVerifier(jwtConfig), JwtUsernamePasswordAuthenticationFilter.class)
                .authorizeRequests().antMatchers("/api/v1/auth/**").permitAll().anyRequest().authenticated();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userService);

        return provider;
    }

}
