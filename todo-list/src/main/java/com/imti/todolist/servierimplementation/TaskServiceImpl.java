package com.imti.todolist.servierimplementation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.annotations.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.imti.todolist.dtos.TaskDTO;
import com.imti.todolist.exception.ResourceNotFoundException;
import com.imti.todolist.model.Task;
import com.imti.todolist.repository.TaskRepository;
import com.imti.todolist.service.TaskService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService{
	
	private final TaskRepository taskRepository;
	
	@Autowired
	public TaskServiceImpl(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public Task createTask(Task newTask) {
		return taskRepository.save(newTask);
	}

	@Override
	public Task updateTask(Task task) {
		 Task existingTask = taskRepository.findById(task.getId()).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + task.getId()));
		 existingTask.setDescription(task.getDescription());
		 existingTask.setCompleted(task.isCompleted());
		 existingTask.setCreatedAt(task.getCreatedAt());
		 return taskRepository.save(existingTask);
	}

	@Override
	public Task getTask(Long taskId) {
		Task task = taskRepository.findById(taskId).orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + taskId));
		return task;
	}

	@Override
	public void deleteTask(Long taskId) {
		taskRepository.deleteById(taskId);
	}

	@Override
	public List<TaskDTO> getAllTasks() {
		List<Task> tasks = taskRepository.findAll();
		return tasks.stream().map(this::mapTaskToTaskDTO).collect(Collectors.toList());
	}

	@Override
	public void markAllTasksAsCompleted() {
		taskRepository.updateCompleted();
	}

	@Override
	public Task IsCompleted(Boolean isCompleted, Long id) {
		try {
			Task task = taskRepository.updateIsComplted(isCompleted, id);
			return task;
		} catch (DataAccessException e) {
			log.error("An error occured while marking a task as complted or not complted.", e);
			throw new RuntimeException("An error occured while marking a task as complted or not complted");
		}
	}
	
	
	private TaskDTO mapTaskToTaskDTO(Task task) {
		return TaskDTO.builder()
				.id(task.getId())
				.description(task.getDescription())
				.completed(task.isCompleted())
				.createdAt(task.getCreatedAt())
				.build();
	}
	
	private Task mapTaskDTOtoTask(TaskDTO taskDTO) {
		return Task.builder()
				.id(taskDTO.getId())
				.description(taskDTO.getDescription())
				.completed(taskDTO.isCompleted())
				.createdAt(taskDTO.getCreatedAt())
				.build();
	}

}
