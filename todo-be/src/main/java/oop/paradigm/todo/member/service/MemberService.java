package oop.paradigm.todo.member.service;

import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.member.entity.request.SignUpRequest;
import oop.paradigm.todo.member.entity.response.ValidateResponse;

public interface MemberService {

	Response<Void> signUp(SignUpRequest signUpRequest);

	Response<ValidateResponse> validateToken();

}
