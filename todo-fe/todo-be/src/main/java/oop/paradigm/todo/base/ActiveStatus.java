package oop.paradigm.todo.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ActiveStatus {

	ACTIVATED("ACTIVATED"), DELETED("DELETED");

	private final String activeStatus;

}
