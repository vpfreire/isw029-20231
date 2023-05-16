package br.edu.fateccotia.tasklist.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fateccotia.tasklist.enums.TaskStatus;
import br.edu.fateccotia.tasklist.model.Task;
import br.edu.fateccotia.tasklist.model.User;
import br.edu.fateccotia.tasklist.service.AuthService;
import br.edu.fateccotia.tasklist.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;
	@Autowired
	private AuthService authService;

	@GetMapping
	public ResponseEntity<?> findAll() {
//		List<Task> list = new ArrayList<Task>();
//		list.add(new Task(1, "Atividade 1", TaskStatus.DONE));
//		list.add(new Task(2, "Atividade 2", TaskStatus.PENDING));
//		list.add(new Task(3, "Atividade 3", TaskStatus.DELETED));
		
		List<Task> list = taskService.findAll();
		
		return ResponseEntity.ok(list);
	}
	
	@PostMapping
	public ResponseEntity<Task> create(@RequestBody Task task
						, @RequestHeader("token") String token) {
		System.out.println(token);
		if (authService.validate(token)) {
			task.setUser(authService.toUser(token));
			Task taskCreated = taskService.save(task);
			
			return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
		}
		return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<Task> edit(@PathVariable(name = "id") Integer id
									,@RequestBody Task task) {
		Optional<Task> taskActual = this.taskService.findById(id);
		if (taskActual.isPresent()) {
			taskActual.get().setDescription(task.getDescription());
			taskActual.get().setStatus(task.getStatus());
			Task saved = taskService.save(taskActual.get());
			return ResponseEntity.ok(saved);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	@GetMapping("/search")
	public ResponseEntity<List<Task>> findByDescription(
			 @RequestParam(name = "q", required = false) String query
			,@RequestParam(name = "s", defaultValue = "p") String status
			,@RequestHeader(name = "token") String token) {
		
		
		if (!authService.validate(token)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
		}
		
		List<Task> tasks = null;
		
		User user = authService.toUser(token);
		if ("p".equals(status)) {
			tasks = taskService.findByDescAndStatus(query
					, TaskStatus.PENDING, user);
		} else if ("d".equals(status)) {
			tasks = taskService.findByDescAndStatus(query
					, TaskStatus.DONE, user);
		} else {
			tasks = taskService.findByDescription(query);
		}
		
		return ResponseEntity.ok(tasks);
	}
	
	@GetMapping("/test")
	public ResponseEntity<List<Task>> test() {
		List<Task> tasks = taskService.findSomething();
		return ResponseEntity.ok(tasks);
	}
	@GetMapping("/test2")
	public ResponseEntity<List<Task>> test2() {
		List<Task> tasks = taskService.findCustom();
		return ResponseEntity.ok(tasks);
	}
}









