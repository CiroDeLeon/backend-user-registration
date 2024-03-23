package com.cirodeleon.userregistration.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;



@Configuration
public class WebConfig {
	
	@Autowired
    JwtRequestFilter jwtRequestFilter;
	
	@Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
	    return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
	    .cors(cors -> cors.configurationSource(request -> {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("*")); // Permitir cualquier origen
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        config.setAllowedHeaders(List.of("*"));
	        config.setAllowCredentials(true);
	        return config;
	    }))
	    .csrf(csrf -> csrf.disable())
	          // Añade DiscriminanteFilter al principio de la cadena
	         .exceptionHandling(exception -> exception.authenticationEntryPoint(this.unauthorizedEntryPoint()))
	         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	         .authorizeHttpRequests(auth -> auth.requestMatchers("/users/register","/users/login").permitAll()
	                                             .requestMatchers(/*"/swagger-ui/index.html",*/"/swagger-ui/**","/v3/api-docs/swagger-config","/v3/api-docs**","/v2/api-docs**","/swagger**","/user").permitAll()
	                                             .anyRequest().authenticated())
	         .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);;
		return http.build();
	}
}
