package oop.paradigm.todo.member.entity.oauth2;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.Builder;
import lombok.Getter;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.entity.Role;

@Getter
public class KakaoOAuthAttribute {

	private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
	private Oauth2UserInfo oauth2UserInfo;
	private PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

	@Builder
	private KakaoOAuthAttribute(String nameAttributeKey, Oauth2UserInfo oauth2UserInfo) {
		this.nameAttributeKey = nameAttributeKey;
		this.oauth2UserInfo = oauth2UserInfo;
	}

	public KakaoOAuthAttribute(String nameAttributeKey) {
		this.nameAttributeKey = nameAttributeKey;
	}

	public static KakaoOAuthAttribute of(String nameAttributeKey, Map<String, Object> attribute) {
		return KakaoOAuthAttribute.builder()
			.nameAttributeKey(nameAttributeKey)
			.oauth2UserInfo(new KakaoOAuthUserInfo(attribute))
			.build();
	}

	public Member toEntity(Oauth2UserInfo kakaoOAuthUserInfo) {
		return Member.builder()
			.password(passwordEncoder.encode(UUID.randomUUID().toString()))
			.userName(kakaoOAuthUserInfo.getNickname() + UUID.randomUUID().toString().substring(1, 9))
			.socialId(kakaoOAuthUserInfo.getId())
			.role(Role.USER)
			.build();
	}


}
