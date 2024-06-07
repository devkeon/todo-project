package oop.paradigm.todo.member.entity.oauth2;

import java.util.Map;

public class KakaoOAuthUserInfo extends Oauth2UserInfo{

	public KakaoOAuthUserInfo(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public Long getId() {
		return (Long) attributes.get("id");
	}

	@Override
	public String getNickname() {
		Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");

		if (kakaoAccount == null) return null;

		Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");

		if (profile == null) return null;

		return (String) profile.get("nickname");
	}

}
