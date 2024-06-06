package oop.paradigm.todo.base;

import java.time.LocalDateTime;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;

@Getter
@MappedSuperclass
public abstract class BaseEntity {

	@Enumerated(EnumType.STRING)
	private ActiveStatus activeStatus = ActiveStatus.ACTIVATED;
	private LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@PrePersist
	public void setCreatedAt() {
		LocalDateTime now = LocalDateTime.now();
		this.createdAt = now;
		this.updatedAt = now;
	}

	@PreUpdate
	public void setUpdatedAt() {
		this.updatedAt = LocalDateTime.now();
	}

	public void softDeleteEntity() {
		this.activeStatus = ActiveStatus.DELETED;
	}

	public void activateEntity() {
		this.activeStatus = ActiveStatus.ACTIVATED;
	}

}
