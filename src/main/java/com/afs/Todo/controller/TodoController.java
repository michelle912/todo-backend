package com.afs.Todo.controller;


import com.afs.Todo.TodoItem;
import com.afs.Todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
@CrossOrigin(origins = "http://localhost:3000")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public List<TodoItem> getAllTodoItems() {
        return todoService.getAllTodoItems();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoItem saveTodoItem(@RequestBody TodoItem todoItem) {
        return todoService.saveTodoItem(todoItem);
    }

    @PutMapping("/{id}")
    public TodoItem updateTodoItem(@PathVariable String id, @RequestBody TodoItem todoItem) {
        if (todoItem == null) {
            return null;
        }
        return todoService.updateTodoItem(id, todoItem);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTodoItem(@PathVariable String id) {
        todoService.deleteTodoItemById(id);
    }


}
