package com.ltp.gradesubmission.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.ltp.gradesubmission.security.filter.AuthenticationFilter;
import com.ltp.gradesubmission.security.filter.ExceptionHandlerFilter;
import com.ltp.gradesubmission.security.filter.JWTAuthorizationFilter;
import com.ltp.gradesubmission.security.manager.CustomAuthenticationManager;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig {

    private final CustomAuthenticationManager customAuthenticationManager;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // prvo se kreira authenticationFilter a zatim se menja url za koji se koristi ovaj filter. Ako se ne promeni url onda je po defaultu /login
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(customAuthenticationManager);
        authenticationFilter.setFilterProcessesUrl("/api/authenticate");

        http        
            .headers().frameOptions().disable() // New Line: the h2 console runs on a "frame". By default, Spring Security prevents rendering within an iframe. This line disables its prevention. - ovo je samo da bi mogli da vidimo H2 bazu
            .and()
            .csrf().disable()
            .authorizeRequests()
            .antMatchers("/h2/**").permitAll() // New Line: allows us to access the h2 console without the need to authenticate. ' ** '  instead of ' * ' because multiple path levels will follow /h2.
            .antMatchers(HttpMethod.POST, SecurityConstants.REGISTER_PATH).permitAll() // svaki post request na /user/register je dozvoljen
            .anyRequest().authenticated() // svaki drugi request (nije /h2 ili /user/register) zahteva autorizaciju
            .and()
            .addFilterBefore(new ExceptionHandlerFilter(), AuthenticationFilter.class) // primeni filter ExceptionHandlerFilter() pre filtera AuthenticationFilter koji je u sledecem redu
            .addFilter(authenticationFilter) // dodaje se nas filter koji proverava username i password 
            .addFilterAfter(new JWTAuthorizationFilter(), AuthenticationFilter.class) // sada se proverava autorizacija
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        return http.build();
    }
}