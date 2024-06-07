package oop.paradigm.todo.member.entity.oauth2;

import java.util.Map;

public abstract class Oauth2UserInfo {

	protected Map<String, Object> attributes;

	public Oauth2UserInfo(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public abstract Long getId(); // 카카오 - "id"

	public abstract String getNickname();

}
