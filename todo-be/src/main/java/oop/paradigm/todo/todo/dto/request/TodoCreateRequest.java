package oop.paradigm.todo.todo.dto.request;

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
public class TodoCreateRequest {

	private String title;
	private Boolean done;
	private LocalDate date;

	public static Todo toEntity(TodoCreateRequest todoCreateDto) {
		return Todo.builder()
			.title(todoCreateDto.getTitle())
			.done(todoCreateDto.getDone())
			.date(todoCreateDto.getDate())
			.build();
	}

}
