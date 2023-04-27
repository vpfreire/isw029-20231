package br.edu.fateccotia.tasklist.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateccotia.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

	List<Task> findByDescriptionStartingWithIgnoreCase(String query);

}
