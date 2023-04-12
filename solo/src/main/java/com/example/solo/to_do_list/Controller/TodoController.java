package com.example.solo.to_do_list.Controller;

import com.example.solo.dto.MultiResponseDto;
import com.example.solo.dto.SingleResponseDto;
import com.example.solo.to_do_list.Dto.TodoDto;
import com.example.solo.to_do_list.Dto.TodoPatchDto;
import com.example.solo.to_do_list.Dto.TodoPostDto;
import com.example.solo.to_do_list.Dto.TodoResponseDto;
import com.example.solo.to_do_list.Entity.Todo;
import com.example.solo.to_do_list.Mapper.TodoMapper;
import com.example.solo.to_do_list.Service.TodoService;
import com.example.solo.to_do_list.Utils.UriCreator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/v1/todos")
@Validated
@Slf4j
public class TodoController {
    private final static String TODO_DEFAULT_URL = "/v1/todos";
    private final TodoService todoService;
    private final TodoMapper mapper;

    public TodoController(TodoService todoService, TodoMapper mapper) {
        this.todoService = todoService;
        this.mapper = mapper;
    }

    //할일 등록
    @PostMapping
    public ResponseEntity postTodo(@Valid @RequestBody TodoDto.Post requestBody){

        Todo todo = mapper.todoPostToTodo(requestBody);

        Todo createTodo = todoService.createTodo(todo);
        URI location = UriCreator.createUri(TODO_DEFAULT_URL, createTodo.getTodoId());

        return ResponseEntity.created(location).build();
    }

    //할일 수정
    @PatchMapping("{todo-id}")
    public ResponseEntity patchTodo(@PathVariable("todo-id") @Positive long todoId,
                                    @Valid @RequestBody TodoDto.Patch requestBody){
        requestBody.setTodoId(todoId);

        Todo todo =
                todoService.updateTodo(mapper.todoPatchToTodo(requestBody));

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.todoToTodoResponse(todo)), HttpStatus.OK);
    }

    //할일 조회
    @GetMapping("/{todo-id}")
    public ResponseEntity getTodo(@PathVariable("todo-id") @Positive long todoId){
        Todo todo = todoService.findTodo(todoId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(mapper.todoToTodoResponse(todo))
                , HttpStatus.OK);
    }

    //모든 할일 조회
    @GetMapping
    public ResponseEntity getTodos(@Positive @RequestParam int page,
                                   @Positive @RequestParam int size){
        Page<Todo> pageTodos = todoService.findTodos(page-1, size);
        List<Todo> todos = pageTodos.getContent();

        List<TodoResponseDto> todoResponseDtos = mapper.todoToTodoResponses(todos);

        return new ResponseEntity<>(
                new MultiResponseDto<>(mapper.todoToTodoResponses(todos),
                pageTodos), HttpStatus.OK);
    }

    //할일 삭제
    @DeleteMapping("/{todo-id}")
    public ResponseEntity deleteTodo(@PathVariable("todo-id") @Positive long todoId){
        todoService.deleteTodo(todoId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //할일 모두 삭제
    @DeleteMapping()
    public ResponseEntity deleteTodos(){
        todoService.deleteTodos();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
