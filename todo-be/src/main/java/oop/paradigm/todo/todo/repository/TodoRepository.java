package oop.paradigm.todo.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oop.paradigm.todo.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	@Modifying
	@Query(nativeQuery = true, value = "delete from todo where todo_id=:todoId")
	void deleteTodo(@Param("todoId") Long id);

	@Query(nativeQuery = true, value = "select * from todo where active_status='DELETED' and member_id=:memberId order by updated_at asc")
	List<Todo> findTrashTodos(@Param("memberId") Long memberId);

	@Query("select t from Todo t where t.title like %:title% order by t.date asc")
	List<Todo> searchTodo(@Param("title") String title);
}
