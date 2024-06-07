package oop.paradigm.todo.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {

	OK("COM-000"),
	MEMBER_NOT_FOUND("MEM-001"),
	MEMBER_NO_AUTHORITY("MEM-003");

	private final String responseCode;
}
