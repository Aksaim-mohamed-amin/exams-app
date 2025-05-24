package me.aksaim.backendapi.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import me.aksaim.backendapi.auth.JwtService;
import me.aksaim.backendapi.models.user.User;
import me.aksaim.backendapi.models.user.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private final JwtService jwtService;
	private final UserService userService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		String path = request.getServletPath();

		// Skip JWT filter for login and register endpoints
		if (path.equals("/api/auth/login") || (request.getMethod().equals("POST") && path.equals("/api/professors"))) {
			filterChain.doFilter(request, response);
			return;
		}

		Optional<Cookie> jwtCookie = Optional.ofNullable(request.getCookies())
				.flatMap(
						cookies -> Arrays.stream(cookies)
								.filter(cookie -> cookie.getName().equals("token"))
								.findFirst()
				);

		if (jwtCookie.isPresent()) {
			String token = jwtCookie.get().getValue();

			String email = jwtService.extractUserEmail(token);
			User user = userService.loadUserByUsername(email);

			if (user != null && jwtService.validateToken(token, user)) {
				SecurityContextHolder.getContext().setAuthentication(
						new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities())
				);
			}
		}

		filterChain.doFilter(request, response);
	}
}
