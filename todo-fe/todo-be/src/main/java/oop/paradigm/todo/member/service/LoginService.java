package oop.paradigm.todo.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.repository.MemberRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LoginService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Member user = memberRepository.findMemberByUserName(username).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such user"));

		return User.builder()
			.username(user.getUserName())
			.password(user.getPassword())
			.roles(user.getRole().getRole())
			.build();
	}

}
