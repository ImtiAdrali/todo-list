package com.imti.todolist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.imti.todolist.dtos.TaskDTO;
import com.imti.todolist.model.Task;
import com.imti.todolist.servierimplementation.TaskServiceImpl;

@RestController
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private TaskServiceImpl taskService;

	public TaskController(TaskServiceImpl taskService) {
		this.taskService = taskService;
	}

	@PostMapping("/create")
	public ResponseEntity<?> createTask(@RequestBody final Task task) {
		try {
			Task newTask = taskService.createTask(task);
			return new ResponseEntity<Task>(newTask, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<String>("Unable to create a task", HttpStatus.BAD_REQUEST);
		}
	}

	@GetMapping("/tasks")
	public ResponseEntity<?> tasks() {
		List<TaskDTO> tasks = taskService.getAllTasks();

		if (tasks.isEmpty()) {
			return ResponseEntity.ok("No task to be found at this moment.");
		}
		return ResponseEntity.ok(tasks);
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(Task updatedTask){
		Task task = taskService.updateTask(updatedTask);
		return ResponseEntity.ok(task);
	}
	
	@DeleteMapping("/delete/{taskId}")
	public ResponseEntity<String> delete(@PathVariable final Long taskId){
		taskService.deleteTask(taskId);
		return ResponseEntity.ok("Task deleted successfully.");
	}
	
	@GetMapping("/task/{taskId}")
	public ResponseEntity<Task> getTask(@PathVariable final Long taskId){
		Task task =  taskService.getTask(taskId);
		return ResponseEntity.ok(task);
	}
	
	@PostMapping("/markAllAsComplete")
	public ResponseEntity<String> markAllAsComplete() {
		taskService.markAllTasksAsCompleted();
		return ResponseEntity.ok("All tasks marked as completed.");
	}
	
	@PostMapping("/markAsCompltedNotComplted/{isComplted}/{taskId}")
	public ResponseEntity<String> markIsCompted(@PathVariable Boolean isCompleted, @PathVariable Long taskId) {
		taskService.IsCompleted(isCompleted, taskId);
		return ResponseEntity.ok("");
	}
}
