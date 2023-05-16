package br.edu.fateccotia.tasklist.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.edu.fateccotia.tasklist.enums.TaskStatus;
import br.edu.fateccotia.tasklist.model.Task;
import br.edu.fateccotia.tasklist.model.User;
import br.edu.fateccotia.tasklist.repository.CustomRepository;
import br.edu.fateccotia.tasklist.repository.TaskRepository;

@Service 
public class TaskService {

	private final TaskRepository taskRepository;
	private final CustomRepository customRepository;
	
	public TaskService(TaskRepository taskRepository, 
			CustomRepository customRepository) {
		this.taskRepository = taskRepository;
		this.customRepository = customRepository;
	}

	public Task save(Task task) {
		return taskRepository.save(task);
	}

	public List<Task> findAll() {
		return taskRepository.findAll();
	}

	public Optional<Task> findById(Integer id) {
		return taskRepository.findById(id);
	}

	public List<Task> findByDescription(String query) {
		return taskRepository.findByDescriptionStartingWithIgnoreCase(query);
	}

	public List<Task> findByDescAndStatus(String query, TaskStatus status, User user) {
		return taskRepository.findByDescAndStatus(query, status, user);
	}
	
	public List<Task> findSomething() {
		return taskRepository.findSomething();
	}
	
	public List<Task> findCustom() {
		return customRepository.findCustom();
	}
}








