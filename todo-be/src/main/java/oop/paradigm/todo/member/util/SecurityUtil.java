package oop.paradigm.todo.member.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import oop.paradigm.todo.member.entity.JwtMemberDetail;

@Component
public class SecurityUtil {

	public JwtMemberDetail getContextMember() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null){
			return null;
		}

		Object principal = authentication.getPrincipal();

		if (principal instanceof JwtMemberDetail){
			return (JwtMemberDetail) principal;
		}
		return null;
	}
}
