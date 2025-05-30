package me.aksaim.backendapi.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	private final JwtAuthenticationFilter jwtAuthenticationFilter;

	// Filter chain
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		return http
				.cors(cors -> cors.configurationSource(corsConfiguration()))
				.csrf(AbstractHttpConfigurer::disable)
				.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(request -> request
						// Permit all requests to the authentication and create professor
						.requestMatchers(HttpMethod.POST, "/api/v1/auth/**").permitAll()

						// Block all other requests to the authentication
						.requestMatchers("/api/v1/professors/**").hasRole("PROFESSOR")
						.requestMatchers("/api/v1/exams/**").hasRole("PROFESSOR")
						.requestMatchers("/api/v1/questions/**").hasRole("PROFESSOR")
						.requestMatchers("/api/v1/answers/**").hasRole("PROFESSOR")
						.requestMatchers("/api/v1/students/**").hasRole("STUDENT")
						.requestMatchers("/api/v1/assignments/**").hasAnyRole("PROFESSOR", "STUDENT")
						.requestMatchers("/api/v1/results/**").hasAnyRole("PROFESSOR", "STUDENT")
						.requestMatchers("/api/v1/users/**").hasAnyRole("PROFESSOR", "STUDENT")
						.anyRequest().authenticated()
				)
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
				.build();
	}

	// Cors configuration
	@Bean
	public CorsConfigurationSource corsConfiguration() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(List.of("http://localhost:4200"));
		configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
		configuration.addAllowedHeader("*");
		configuration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}