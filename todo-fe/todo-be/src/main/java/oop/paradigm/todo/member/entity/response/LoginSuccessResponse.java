package oop.paradigm.todo.member.entity.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class LoginSuccessResponse {

	private String userName;

	public static LoginSuccessResponse of(String userName){
		return new LoginSuccessResponse(userName);
	}

}
