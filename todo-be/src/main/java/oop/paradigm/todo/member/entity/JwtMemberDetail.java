package oop.paradigm.todo.member.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import lombok.Builder;
import lombok.Getter;

@Getter
public class JwtMemberDetail extends User {

	private Long userId;

	@Builder(builderMethodName = "jwtMemberBuilder")
	public JwtMemberDetail(String username, String password,
		Collection<? extends GrantedAuthority> authorities, Long userId) {
		super(username, password, authorities);
		this.userId = userId;
	}
}
