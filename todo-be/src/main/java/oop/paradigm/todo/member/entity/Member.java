package oop.paradigm.todo.member.entity;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oop.paradigm.todo.base.BaseEntity;
import oop.paradigm.todo.todo.entity.Todo;

@Getter
@Entity
@Builder
@Table(uniqueConstraints = {
	@UniqueConstraint(
		name = "userName",
		columnNames = {"user_name"}
	)
})
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

	@Id @Column(name = "member_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String userName;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	private Long socialId;
	private String refreshToken;

	@OneToMany(mappedBy = "member")
	private Set<Todo> todos;

	public void updateTodo(Todo todo) {

		todos.add(todo);

		if (todo.getMember() != this){
			todo.updateMember(this);
		}
	}

	public void updateRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

}
