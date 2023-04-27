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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.fateccotia.tasklist.model.Task;
import br.edu.fateccotia.tasklist.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {

	@Autowired
	private TaskService taskService;

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
	public ResponseEntity<Task> create(@RequestBody Task task) {
		Task taskCreated = taskService.save(task);
		
		return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
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
	public ResponseEntity<List<Task>> findByDescription(@RequestParam(name = "q") String query) {
		List<Task> tasks = taskService.findByDescription(query);
		
		return ResponseEntity.ok(tasks);
	}
}









