package com.afs.Todo.service;

import com.afs.Todo.TodoItem;
import com.afs.Todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;


    public List<TodoItem> getAllTodoItems() {
        return todoRepository.findAll();
    }

    public TodoItem saveTodoItem(TodoItem todoItem) {
        return todoRepository.save(todoItem);
    }


    public TodoItem updateTodoItem(String id, TodoItem todoItem) {
        TodoItem existingRecord = todoRepository
                .findById(id)
                .orElseThrow(NoSuchElementException::new);

        if (todoItem.getText() != null && !todoItem.getText().equals(existingRecord.getText())) {
            existingRecord.setText(todoItem.getText());
        }

        if (todoItem.getDone() != null && !todoItem.getDone().equals(existingRecord.getDone())) {
            existingRecord.setDone(todoItem.getDone());
        }

        return todoRepository.save(existingRecord);
    }

    public void deleteTodoItemById(String id) {
        todoRepository.deleteById(id);
    }
}
