package oop.paradigm.todo.todo.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.todo.dto.request.TodoCreateRequest;
import oop.paradigm.todo.todo.dto.request.TodoUpdateRequest;
import oop.paradigm.todo.todo.dto.response.TodoInfoResponse;
import oop.paradigm.todo.todo.dto.response.TodoListResponse;
import oop.paradigm.todo.todo.service.TodoService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todo")
public class TodoController {

	private final TodoService todoService;

	@PostMapping
	public Response<TodoInfoResponse> createTodo(@RequestBody TodoCreateRequest createRequest) {
		return todoService.createTodo(createRequest);
	}

	@GetMapping
	public Response<TodoListResponse> readTodos() {
		return todoService.readTodos();
	}

	@GetMapping("/search")
	public Response<TodoListResponse> searchTodos(@RequestParam("keyword") String keyword) {
		return todoService.searchTodos(keyword);
	}

	@PatchMapping
	public Response<TodoInfoResponse> updateTodo(@RequestBody TodoUpdateRequest updateRequest) {
		return todoService.updateTodo(updateRequest);
	}

	@DeleteMapping("/trashcan")
	public Response<Void> deleteTodo(@RequestParam(value = "todoId") Long todoId) {
		return todoService.trashTodo(todoId);
	}

}
