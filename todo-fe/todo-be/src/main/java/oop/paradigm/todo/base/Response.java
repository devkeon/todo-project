package oop.paradigm.todo.base;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response<T> {

	private String code;
	private T data;

	public Response(String code) {
		this.code = code;
	}

	public static <T> Response<T> ok(T data){
		return new Response<>("COM-000", data);
	}

	public static <T> Response<T> ok() {
		return new Response<>("COM-000");
	}

	public static <T> Response<T> fail(ResponseCode responseCode) {
		return new Response<>(responseCode.getResponseCode());
	}
}
