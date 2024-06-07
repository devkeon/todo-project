package oop.paradigm.todo.todo.entity;

import java.time.LocalDate;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import oop.paradigm.todo.base.BaseEntity;
import oop.paradigm.todo.member.entity.Member;

@Getter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATe todo SET active_status='DELETED' WHERE todo_id=?")
@SQLRestriction(value = "active_status <> 'DELETED'")
public class Todo extends BaseEntity {

	@Id @Column(name = "todo_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;
	private Boolean done;

	@ManyToOne
	@JoinColumn(name = "member_id")
	private Member member;

	private LocalDate date;

	public void updateMember(Member member) {
		if (this.member != member){
			this.member = member;
		}
		if (!member.getTodos().contains(this)){
			member.updateTodo(this);
		}
	}

	public void updateTitle(String title) {
		this.title = title;
	}

	public void updateDone(Boolean done) {
		this.done = done;
	}

}
