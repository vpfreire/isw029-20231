package br.edu.fateccotia.tasklist.controller;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.fateccotia.tasklist.model.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
