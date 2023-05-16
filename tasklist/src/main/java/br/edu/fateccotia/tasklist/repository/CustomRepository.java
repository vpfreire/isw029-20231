package br.edu.fateccotia.tasklist.repository;

import java.util.List;

import org.springframework.stereotype.Repository;

import br.edu.fateccotia.tasklist.model.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class CustomRepository {
	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	public List<Task> findCustom() {
		return (List<Task>)em
				.createNativeQuery("select * from task where task.user_id = 1"
				, Task.class)
				.getResultList();
	}
}
