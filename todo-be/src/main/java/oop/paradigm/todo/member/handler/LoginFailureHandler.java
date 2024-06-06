package oop.paradigm.todo.member.handler;

import java.io.IOException;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.base.ResponseCode;

@Slf4j
@RequiredArgsConstructor
public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final ObjectMapper objectMapper;

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
		AuthenticationException exception) throws IOException, ServletException {

		try{
			String string = objectMapper.writeValueAsString(Response.fail(ResponseCode.MEMBER_NOT_FOUND));
			response.getWriter().write(string);
		} catch (IOException e){
			throw new RuntimeException("error occur while writing");
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json");

		log.info("로그인에 실패했습니다. 메시지 : {}", exception.getMessage());

	}
}
