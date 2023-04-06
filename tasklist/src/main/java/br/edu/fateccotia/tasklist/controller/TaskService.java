package br.edu.fateccotia.tasklist.controller;

import java.util.List;

import org.springframework.stereotype.Service;

import br.edu.fateccotia.tasklist.model.Task;

@Service 
public class TaskService {

	private final TaskRepository taskRepository;
	
	public TaskService(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	public Task save(Task task) {
		return taskRepository.save(task);
	}

	public List<Task> findAll() {
		return taskRepository.findAll();
	}

}
