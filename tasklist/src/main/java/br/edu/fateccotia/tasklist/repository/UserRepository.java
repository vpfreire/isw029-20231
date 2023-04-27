package br.edu.fateccotia.tasklist.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.edu.fateccotia.tasklist.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
