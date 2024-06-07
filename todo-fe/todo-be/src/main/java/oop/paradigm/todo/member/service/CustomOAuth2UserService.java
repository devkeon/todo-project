package oop.paradigm.todo.member.service;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.entity.oauth2.CustomOAuthUser;
import oop.paradigm.todo.member.entity.oauth2.KakaoOAuthAttribute;
import oop.paradigm.todo.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

	private final MemberRepository memberRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		log.info("oauth service activate");

		DefaultOAuth2UserService delegate = new DefaultOAuth2UserService();

		OAuth2User oAuth2User = delegate.loadUser(userRequest);

		String userNameAttributeName = userRequest.getClientRegistration()
			.getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		KakaoOAuthAttribute extractAttributes = KakaoOAuthAttribute.of(userNameAttributeName, attributes);

		Member createdUser = getMember(extractAttributes);

		return new CustomOAuthUser(
			Collections.singleton(new SimpleGrantedAuthority(createdUser.getRole().getRole())),
			attributes,
			extractAttributes.getNameAttributeKey(),
			createdUser
		);

	}

	private Member getMember(KakaoOAuthAttribute kakaoOAuthAttribute) {

		Optional<Member> findUser = memberRepository.findMemberBySocialId(kakaoOAuthAttribute.getOauth2UserInfo().getId());

		if(findUser.isEmpty()) {
			Member member = saveMember(kakaoOAuthAttribute);

			return member;
		}

		return findUser.get();

	}

	private Member saveMember(KakaoOAuthAttribute attributes) {
		Member createdUser = attributes.toEntity(attributes.getOauth2UserInfo());
		return memberRepository.save(createdUser);
	}

}
