package com.example.solo.to_do_list.Service;

import com.example.solo.to_do_list.Repository.TodoRepository;
import com.example.solo.to_do_list.Entity.Todo;
import com.example.solo.to_do_list.exception.BusinessLogicException;
import com.example.solo.to_do_list.exception.ExceptionCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
@Service
@Transactional
public class TodoService {
    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    // 작성만 하는 것이기 때문에 본인에 대한 검증 제외
    // 할일 작성
    public Todo createTodo(Todo todo){
        Todo savedTodo = todoRepository.save(todo);

       return savedTodo;
    }

    //할일 수정
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE)
    public Todo updateTodo(Todo todo){
        Todo findTodo = findverifiedTodo(todo.getTodoId());

        Optional.ofNullable(todo.getTitle())
                .ifPresent(title -> findTodo.setTitle(title));

        return todoRepository.save(findTodo);
    }

    //할일 조회
    @Transactional(readOnly = true)
    public Todo findTodo(long todoId){

        return findverifiedTodo(todoId);
    }

    //할일 모두 조회
    public Page<Todo> findTodos(int page, int size){
        return todoRepository.findAll(PageRequest.of(page, size,
                Sort.by("todoId").descending()));
    }

    //할일 삭제
    public void deleteTodo(long todoId){
        Todo findTodo = findverifiedTodo(todoId);
        todoRepository.delete(findTodo);
    }

    //할일 모두 삭제
    public void deleteTodos(){
        todoRepository.deleteAll();
    }

    //이미 작성한 todo 인지 확인
    @Transactional(readOnly = true)
    public Todo findverifiedTodo(long todoId){
        Optional<Todo> optionalTodo =
                todoRepository.findById(todoId);
        Todo findTodo =
                optionalTodo.orElseThrow(() ->
                        new BusinessLogicException(ExceptionCode.TODO_NOT_FOUND));
        return findTodo;
    }
}
