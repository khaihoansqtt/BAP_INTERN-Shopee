package com.bap.intern.shopee.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationProvider authenticationProvider() {
    	DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    	authenticationProvider.setUserDetailsService(userDetailsService);
    	authenticationProvider.setPasswordEncoder(passwordEncoder());
    	return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http.csrf((csrf) -> csrf.disable())
    	
    		.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
//    				.requestMatchers("/api/v1/user/hello").hasRole("ADMIN")
    				.anyRequest().permitAll()
        )
    		
    		.exceptionHandling((exceptionHandling) ->
                exceptionHandling.authenticationEntryPoint(jwtAuthenticationEntryPoint)
        )
    		
    		.sessionManagement((sessionManagement) -> 
    			sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
    		
    		.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    	return http.build();
    }
}