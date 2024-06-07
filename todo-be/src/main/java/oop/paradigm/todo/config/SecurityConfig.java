package oop.paradigm.todo.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import oop.paradigm.todo.member.filter.JsonAuthenticationFilter;
import oop.paradigm.todo.member.filter.JwtAuthenticationFilter;
import oop.paradigm.todo.member.handler.LoginFailureHandler;
import oop.paradigm.todo.member.handler.LoginSuccessHandler;
import oop.paradigm.todo.member.handler.OAuthLoginFailureHandler;
import oop.paradigm.todo.member.handler.OAuthLoginSuccessHandler;
import oop.paradigm.todo.member.repository.MemberRepository;
import oop.paradigm.todo.member.service.CustomOAuth2UserService;
import oop.paradigm.todo.member.service.LoginService;
import oop.paradigm.todo.member.util.JwtTokenProvider;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final LoginService loginService;
	private final JwtTokenProvider jwtTokenProvider;
	private final MemberRepository memberRepository;
	private final ObjectMapper objectMapper;
	private final OAuthLoginSuccessHandler oAuthLoginSuccessHandler;
	private final OAuthLoginFailureHandler oAuthLoginFailureHandler;
	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http
			.formLogin(AbstractHttpConfigurer::disable)
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.cors((cors) ->
				cors.configurationSource(corsConfiguration()))
			.headers((headers) ->
				headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin)
			)
			.sessionManagement((sessionManagement) ->
				sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			)
			.authorizeHttpRequests((authorizeHttpRequests) ->
				authorizeHttpRequests
					.requestMatchers("/", "/css/**", "/images/**", "/js/**",
						"/index.html",
						"/**",
						"/favicon.ico", "/swagger-ui/**", "/api", "/api/login", "/api/sign-up", "/api/main/**",
						"/ws/**", "/api/feed/**")
					.permitAll()
					.anyRequest()
					.authenticated()
			)
			.oauth2Login((oauth2Login) ->
				oauth2Login.successHandler(oAuthLoginSuccessHandler)
					.failureHandler(oAuthLoginFailureHandler)
					.userInfoEndpoint((userInfoEndPoint) ->
						userInfoEndPoint.userService(customOAuth2UserService)
					)
			);
		http.addFilterAfter(jsonAuthenticationFilter(), LogoutFilter.class);
		http.addFilterBefore(jwtAuthenticationFilter(), JsonAuthenticationFilter.class);

		return http.build();

	}

	@Bean
	public CorsConfigurationSource corsConfiguration() {
		CorsConfiguration corsConfiguration = new CorsConfiguration();

		corsConfiguration.addAllowedHeader("*");
		corsConfiguration.addExposedHeader("*");
		corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
		corsConfiguration.setAllowedOrigins(List.of(
			"http://localhost:3000",
			"http://localhost:8080"
		));
		corsConfiguration.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		source.registerCorsConfiguration("/**", corsConfiguration);

		return source;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

	@Bean
	public AuthenticationManager authenticationManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(loginService);
		return new ProviderManager(provider);
	}

	@Bean
	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtTokenProvider, memberRepository);
	}

	@Bean
	public LoginSuccessHandler loginSuccessHandler() {
		return new LoginSuccessHandler(memberRepository, jwtTokenProvider, objectMapper);
	}

	@Bean
	public LoginFailureHandler loginFailureHandler() {
		return new LoginFailureHandler(objectMapper);
	}

	@Bean
	public JsonAuthenticationFilter jsonAuthenticationFilter() {
		JsonAuthenticationFilter jsonAuthenticationFilter
			= new JsonAuthenticationFilter(objectMapper);

		jsonAuthenticationFilter.setAuthenticationManager(authenticationManager());
		jsonAuthenticationFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
		jsonAuthenticationFilter.setAuthenticationFailureHandler(loginFailureHandler());

		return jsonAuthenticationFilter;
	}

}
