package oop.paradigm.todo.todo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.base.ResponseCode;
import oop.paradigm.todo.member.entity.Member;
import oop.paradigm.todo.member.repository.MemberRepository;
import oop.paradigm.todo.member.util.SecurityUtil;
import oop.paradigm.todo.todo.dto.request.TodoCreateRequest;
import oop.paradigm.todo.todo.dto.request.TodoUpdateRequest;
import oop.paradigm.todo.todo.dto.response.TodoInfoResponse;
import oop.paradigm.todo.todo.dto.response.TodoListResponse;
import oop.paradigm.todo.todo.entity.Todo;
import oop.paradigm.todo.todo.repository.TodoRepository;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

	private final TodoRepository todoRepository;
	private final MemberRepository memberRepository;
	private final SecurityUtil securityUtil;

	@Override
	public Response<TodoInfoResponse> createTodo(TodoCreateRequest createRequest) {

		Member member = memberRepository.findById(securityUtil.getContextMember().getUserId()).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such member"));

		Todo todo = Todo.builder()
			.title(createRequest.getTitle())
			.done(createRequest.getDone())
			.date(createRequest.getDate())
			.member(member)
			.build();

		Todo save = todoRepository.save(todo);

		return Response.ok(TodoInfoResponse.fromEntity(save));
	}

	@Override
	public Response<TodoListResponse> readTodos() {

		Member member = memberRepository.findMemberWithTodos(securityUtil.getContextMember().getUserId()).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such member"));

		List<TodoInfoResponse> todoList = member.getTodos().stream()
			.map(TodoInfoResponse::fromEntity)
			.toList();

		return Response.ok(new TodoListResponse(todoList));
	}

	@Override
	public Response<TodoListResponse> searchTodos(String keyword) {

		List<TodoInfoResponse> list = todoRepository.searchTodo(keyword).stream()
			.map(TodoInfoResponse::fromEntity)
			.toList();

		return Response.ok(new TodoListResponse(list));
	}

	@Override
	public Response<TodoInfoResponse> updateTodo(TodoUpdateRequest updateRequest) {

		Member member = memberRepository.findById(securityUtil.getContextMember().getUserId()).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such member"));

		Todo todo = todoRepository.findById(updateRequest.getTodoId()).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such member"));

		if (todo.getMember() != member){
			return Response.fail(ResponseCode.MEMBER_NO_AUTHORITY);
		}

		todo.updateTitle(updateRequest.getTitle());
		todo.updateDone(updateRequest.getDone());

		return Response.ok(TodoInfoResponse.fromEntity(todo));
	}

	@Override
	public Response<Void> trashTodo(Long todoId) {

		Todo todo = todoRepository.findById(todoId).stream()
			.findAny()
			.orElseThrow(() -> new RuntimeException("no such todo"));

		todoRepository.delete(todo);

		return Response.ok();
	}
}
