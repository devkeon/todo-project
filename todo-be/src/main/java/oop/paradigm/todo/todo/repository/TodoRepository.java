package oop.paradigm.todo.todo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oop.paradigm.todo.todo.entity.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long> {

	@Query("select t from Todo t where t.title like %:title% order by t.date asc")
	List<Todo> searchTodo(@Param("title") String title);
}
