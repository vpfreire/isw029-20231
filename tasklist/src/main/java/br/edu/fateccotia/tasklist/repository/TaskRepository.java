package br.edu.fateccotia.tasklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.edu.fateccotia.tasklist.enums.TaskStatus;
import br.edu.fateccotia.tasklist.model.Task;
import br.edu.fateccotia.tasklist.model.User;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByDescriptionStartingWithIgnoreCase(String query);

	@Query("select t from Task t where t.description like ?1% and t.status = ?2 and t.user = ?3")
	List<Task> findByDescAndStatus(String query, TaskStatus status, User user);

	List<Task> findSomething();
}
