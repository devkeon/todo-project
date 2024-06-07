package oop.paradigm.todo.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import oop.paradigm.todo.member.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findMemberByUserName(String userName);

	@EntityGraph(attributePaths = {"todos"})
	@Query("select m from Member m where m.id=:memberId")
	Optional<Member> findMemberWithTodos(@Param("memberId") Long memberId);

	Optional<Member> findMemberBySocialId(Long socialId);


}
