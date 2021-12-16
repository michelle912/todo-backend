package com.afs.Todo.service;

import com.afs.Todo.TodoItem;
import com.afs.Todo.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;


@ExtendWith(SpringExtension.class)
public class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @Spy
    @InjectMocks
    private TodoService todoService;

    @Test
    public void should_return_all_todoItems_when_getAllTodoItems_given_record_exists() {
        // given
        TodoItem todoItem = TodoItem.builder()
                .id("001")
                .text("Text")
                .done(false)
                .build();
        List<TodoItem> todoItemList = Arrays.asList(todoItem);

        // when
        doReturn(todoItemList).when(todoRepository).findAll();
        List<TodoItem> actual = todoService.getAllTodoItems();

        // then
        assertAll(
                () -> assertEquals(1, actual.size()),
                () -> assertEquals("001", actual.get(0).getId()),
                () -> assertEquals("Text", actual.get(0).getText()),
                () -> assertFalse(actual.get(0).getDone())
        );
    }

    @Test
    public void should_empty_list_when_getAllTodoItems_given_records_not_exist() {
        // given

        // when
        doReturn(Collections.emptyList()).when(todoRepository).findAll();
        List<TodoItem> actual = todoService.getAllTodoItems();

        // then
        assertTrue(actual.isEmpty());
    }

    @Test
    public void should_return_saved_todoItem_when_saveTodoItem_given_valid_input() {
        // given
        TodoItem todoItem = TodoItem.builder()
                .id("001")
                .text("Text")
                .done(false)
                .build();

        TodoItem inputTodoItem = TodoItem.builder()
                .text("Text")
                .done(false)
                .build();

        // when
        doReturn(todoItem).when(todoRepository).save(inputTodoItem);
        TodoItem actual = todoService.saveTodoItem(inputTodoItem);

        // then
        assertAll(
                () -> assertEquals("001", actual.getId()),
                () -> assertEquals("Text", actual.getText()),
                () -> assertFalse(actual.getDone())
        );
    }

    @Test
    public void should_update_todoItems_when_updateTodoItem_given_record_exists() {
        // given
        String id = "001";
        TodoItem todoItem = TodoItem.builder()
                .id(id)
                .text("Text")
                .done(false)
                .build();

        TodoItem inputTodoItem = TodoItem.builder()
                .text("Updated text")
                .done(false)
                .build();

        TodoItem updatedTodoItem = TodoItem.builder()
                .id(id)
                .text("Updated text")
                .done(false)
                .build();

        // when
        doReturn(Optional.of(todoItem)).when(todoRepository).findById(id);
        doReturn(updatedTodoItem).when(todoRepository).save(any());
        TodoItem actual = todoService.updateTodoItem(id, inputTodoItem);

        // then
        assertAll(
                () -> assertEquals(id, actual.getId()),
                () -> assertEquals("Updated text", actual.getText()),
                () -> assertFalse(actual.getDone())
        );
    }

    @Test
    public void should_delete_todoItem_when_deleteTodoItemById_given_record_exists() {
        // given
        String id = "001";

        // when
        todoService.deleteTodoItemById(id);

        // then
        Mockito.verify(todoRepository, times(1)).deleteById(id);
    }

}