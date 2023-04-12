package com.example.solo.to_do_list.Mapper;

import com.example.solo.to_do_list.Dto.TodoDto;
import com.example.solo.to_do_list.Dto.TodoResponseDto;
import com.example.solo.to_do_list.Entity.Todo;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface TodoMapper {
    //TodoPostDto 를 변환
    Todo todoPostToTodo(TodoDto.Post requestBody);
    Todo todoPatchToTodo(TodoDto.Patch requestBody);
    TodoResponseDto todoToTodoResponse(Todo todo);
    List<TodoResponseDto> todoToTodoResponses(List<Todo> todos);

}
