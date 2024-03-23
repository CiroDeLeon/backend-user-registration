package com.cirodeleon.userregistration.security;

import java.util.List;

import org.apache.catalina.connector.Connector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
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
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        tomcat.addAdditionalTomcatConnectors(redirectConnector());
        return tomcat;
    }

    private Connector redirectConnector() {
        Connector connector = new Connector(TomcatServletWebServerFactory.DEFAULT_PROTOCOL);
        connector.setScheme("http");
        connector.setPort(8080);
        connector.setSecure(false);
        connector.setRedirectPort(8443);
        return connector;
    }
	
	@SuppressWarnings("removal")
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
		.headers(headers -> headers
				.frameOptions().sameOrigin()	            
	        )
		.requiresChannel(channel -> channel
	            //.requestMatchers(r -> r.getRequestURI().startsWith("/h2-console")).requiresInsecure() // Permite HTTP para H2 console
	            .anyRequest().requiresSecure()
	        )
		
	    .cors(cors -> cors.configurationSource(request -> {
	        CorsConfiguration config = new CorsConfiguration();
	        config.setAllowedOrigins(List.of("*")); // Permitir cualquier origen
	        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
	        config.setAllowedHeaders(List.of("*"));
	        config.setAllowCredentials(true);
	        return config;
	    }))
	    .csrf(csrf -> csrf
	            .ignoringRequestMatchers("/h2-console/**") // Ignora CSRF para H2 Console
	            .disable())
	          // Añade DiscriminanteFilter al principio de la cadena
	         .exceptionHandling(exception -> exception.authenticationEntryPoint(this.unauthorizedEntryPoint()))
	         .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	         .authorizeHttpRequests(auth -> auth.requestMatchers("/users/register","/users/login").permitAll()
	                                             .requestMatchers("/h2-console/**","/favicon.ico/**","/static/**","/swagger-ui/**","/v3/api-docs/swagger-config","/v3/api-docs**","/v2/api-docs**","/swagger**","/user").permitAll()
	                                             .anyRequest().authenticated())
	         .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);;
		return http.build();

	}
}
