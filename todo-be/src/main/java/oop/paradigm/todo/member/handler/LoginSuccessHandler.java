package oop.paradigm.todo.member.handler;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.entity.response.LoginSuccessResponse;
import oop.paradigm.todo.member.repository.MemberRepository;
import oop.paradigm.todo.member.util.JwtTokenProvider;

@Slf4j
@Transactional
@RequiredArgsConstructor
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final MemberRepository memberRepository;
	private final JwtTokenProvider jwtTokenProvider;
	private final ObjectMapper objectMapper;

	// @Value("${jwt.cookie.expiration}")
	// private Integer COOKIE_EXPIRATION;

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
		Authentication authentication) throws IOException, ServletException {

		UserDetails jwtMemberDetail = (UserDetails) authentication.getPrincipal();

		Member member = memberRepository.findMemberByUserName(jwtMemberDetail.getUsername()).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such member"));

		Authentication genAuthentication = jwtTokenProvider.createAuthentication(member);

		String accessToken = jwtTokenProvider.generateAccessToken(genAuthentication);
		// String refreshToken = jwtTokenProvider.generateRefreshToken();

		response.setHeader("Authorization", accessToken);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		LoginSuccessResponse loginSuccessResponse = LoginSuccessResponse.of(jwtMemberDetail.getUsername());

		try {
			String responseBody = objectMapper.writeValueAsString(Response.ok(loginSuccessResponse));
			response.getWriter().write(responseBody);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// ResponseCookie cookie = ResponseCookie.from("refreshToken", refreshToken)
		// 	.path("/")
		// 	.httpOnly(true)
		// 	.maxAge(COOKIE_EXPIRATION)
		// 	.sameSite("Lax")
		// 	.secure(false)
		// 	.build();
		//
		// response.setHeader("Set-Cookie", cookie.toString());
		//
		// member.updateRefreshToken(refreshToken);

		SecurityContextHolder.getContext().setAuthentication(authentication);
	}
}
