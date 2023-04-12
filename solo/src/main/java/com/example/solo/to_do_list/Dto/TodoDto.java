package com.example.solo.to_do_list.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class TodoDto {
    @Getter
    @AllArgsConstructor
    public static class Post{
        private String title;
        private boolean completed;
    }

    @Getter
    @AllArgsConstructor
    public static class Patch{
        private long todoId;

        private String title;
        private int todo_order;
        private boolean completed;

        public void setTodoId(long todoId) {
            this.todoId = todoId;
        }
    }

    @AllArgsConstructor
    @Getter
    public static class response{
        private long todoId;
        private String title;
        private int todo_order;
        private boolean completed;
    }
}
