package oop.paradigm.todo.member.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.entity.oauth2.CustomOAuthUser;
import oop.paradigm.todo.member.util.JwtTokenProvider;

@Slf4j
@Component
@Transactional
@RequiredArgsConstructor
public class OAuthLoginSuccessHandler implements AuthenticationSuccessHandler {

	private final JwtTokenProvider jwtTokenProvider;

	@Value("${jwt.cookie.expiration}")
	private Integer COOKIE_EXPIRATION;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		try{
			CustomOAuthUser oAuthUser = (CustomOAuthUser) authentication.getPrincipal();

			Member member = oAuthUser.getMember();

			Authentication authentication1 = jwtTokenProvider.createAuthentication(member);

			String accessToken = jwtTokenProvider.generateAccessToken(authentication1);

			// String refreshToken = jwtTokenProvider.generateRefreshToken();
			//
			// ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
			// 	.path("/")
			// 	.httpOnly(true)
			// 	.maxAge(COOKIE_EXPIRATION)
			// 	.secure(true)
			// 	.build();
			//
			// response.setHeader("Set-Cookie", cookie.toString());

			String redirectUrl = "http://localhost:3000/oauth/callback?token=" + accessToken;

			response.sendRedirect(redirectUrl);

		} catch (Exception e){
			throw new RuntimeException("error occur");
		}
	}
}
