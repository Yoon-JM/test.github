package com.example.solo.to_do_list.Dto;

import lombok.Getter;

@Getter
public class TodoPatchDto {
    private long todoId;
    private String title;
    private int todo_order;
    private boolean completed;

    public void setTitle(String title) {
        title = title;
    }
}
