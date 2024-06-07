package oop.paradigm.todo.todo.dto.response;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oop.paradigm.todo.todo.entity.Todo;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoInfoResponse {

	private Long todoId;
	private String title;
	private Boolean done;
	private LocalDate date;

	public static TodoInfoResponse fromEntity(Todo todo) {
		return new TodoInfoResponse(todo.getId(), todo.getTitle(), todo.getDone(), todo.getDate());
	}

}
