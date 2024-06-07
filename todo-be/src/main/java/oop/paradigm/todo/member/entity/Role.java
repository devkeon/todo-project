package oop.paradigm.todo.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {

	USER("USER");

	private final String role;

}
