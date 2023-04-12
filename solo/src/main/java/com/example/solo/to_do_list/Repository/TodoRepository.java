package com.example.solo.to_do_list.Repository;

import com.example.solo.to_do_list.Entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface TodoRepository extends JpaRepository<Todo, Long> {
    @Query(value = "SELECT c FROM Todo c WHERE c.todoId = :todoId")
    Optional<Todo> findByTodo(Long todoId);

}
