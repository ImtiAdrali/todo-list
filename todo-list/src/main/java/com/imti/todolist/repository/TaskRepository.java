package com.imti.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.imti.todolist.model.Task;

import jakarta.transaction.Transactional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long>{
	
	@Transactional
	@Modifying
	@Query(name = "Task.updateCompled")
	void updateCompleted();
	
	@Transactional
	@Modifying
	@Query("UPDATE Task t SET t.completed = :isCompleted WHERE t.id = :taskId")
	Task updateIsComplted(@Param("isCompleted") Boolean isBoolean, @Param("taskId") Long taskId);
}
