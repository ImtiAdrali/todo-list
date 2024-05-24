package com.imti.todolist.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.imti.todolist.dtos.TaskDTO;
import com.imti.todolist.model.Task;

public interface TaskService {
	Task createTask(Task newTask);
	Task getTask(Long taskId);
	void deleteTask(Long taskId);
	
	List<TaskDTO> getAllTasks();
	void markAllTasksAsCompleted();
	Task updateTask(Task task);
	Task IsCompleted(Boolean isCompleted, Long id);
}
