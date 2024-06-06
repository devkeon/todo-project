package oop.paradigm.todo.member.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.entity.Role;
import oop.paradigm.todo.member.entity.request.SignUpRequest;
import oop.paradigm.todo.member.entity.response.ValidateResponse;
import oop.paradigm.todo.member.repository.MemberRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public Response<Void> signUp(SignUpRequest signUpRequest) {

		Member member = Member.builder()
			.userName(signUpRequest.getUserName())
			.password(passwordEncoder.encode(signUpRequest.getPassword()))
			.role(Role.USER)
			.build();

		memberRepository.save(member);

		return Response.ok();
	}

	@Override
	public Response<ValidateResponse> validateToken() {
		return Response.ok(new ValidateResponse(true));
	}

}
