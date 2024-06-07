package oop.paradigm.todo.member.entity.oauth2;

import java.util.Collection;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

import lombok.Getter;
import oop.paradigm.todo.member.entity.Member;

@Getter
public class CustomOAuthUser extends DefaultOAuth2User {

	private final Member member;

	public CustomOAuthUser(Collection<? extends GrantedAuthority> authorities,
		Map<String, Object> attributes, String nameAttributeKey, Member member) {
		super(authorities, attributes, nameAttributeKey);
		this.member = member;
	}
}
