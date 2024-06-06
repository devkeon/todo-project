package oop.paradigm.todo.todo.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TodoUpdateRequest {

	private Long todoId;
	private String title;
	private Boolean done;

}
