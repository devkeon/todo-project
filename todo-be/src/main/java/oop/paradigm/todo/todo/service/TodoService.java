package oop.paradigm.todo.todo.service;

import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.todo.dto.request.TodoCreateRequest;
import oop.paradigm.todo.todo.dto.request.TodoUpdateRequest;
import oop.paradigm.todo.todo.dto.response.TodoInfoResponse;
import oop.paradigm.todo.todo.dto.response.TodoListResponse;

public interface TodoService {

	Response<TodoInfoResponse> createTodo(TodoCreateRequest createRequest);

	Response<TodoListResponse> readTodos();

	Response<TodoListResponse> searchTodos(String keyword);

	Response<TodoInfoResponse> updateTodo(TodoUpdateRequest updateRequest);

	Response<Void> trashTodo(Long todoId);

}
