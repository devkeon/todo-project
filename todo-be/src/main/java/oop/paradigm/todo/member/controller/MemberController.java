package oop.paradigm.todo.member.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.member.entity.request.SignUpRequest;
import oop.paradigm.todo.member.entity.response.ValidateResponse;
import oop.paradigm.todo.member.service.MemberService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/sign-up")
	Response<Void> signUp(@RequestBody SignUpRequest signUpRequest) {
		return memberService.signUp(signUpRequest);
	}

	@GetMapping("/valid")
	Response<ValidateResponse> validateToken() {
		return memberService.validateToken();
	}

}
