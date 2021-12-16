package com.afs.Todo.controller;


import com.afs.Todo.TodoItem;
import com.afs.Todo.repository.TodoRepository;
import com.afs.Todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TodoRepository todoRepository;

    @Autowired
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        todoRepository.deleteAll();
    }

    @Test
    public void should_return_all_todo_items_when_getAllTodoItems_given_records_exists() throws Exception {
        // given
        TodoItem todoItem = TodoItem.builder()
                .id("1")
                .text("Test")
                .done(false)
                .build();
        todoRepository.save(todoItem);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(1)))
                .andExpect(jsonPath("$[0].id").isString())
                .andExpect(jsonPath("$[0].text").value("Test"))
                .andExpect(jsonPath("$[0].done").value(false));

    }

    @Test
    public void should_return_empty_list_when_getAllTodoItems_given_records_not_exists() throws Exception {
        // given

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/todos"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$",hasSize(0)));

    }

    @Test
    public void should_save_todo_item_when_saveTodoItem_given_valid_record() throws Exception {
        // given
        String todoItem = "{\n" +
                "        \"text\": \"Test\",\n" +
                "        \"done\": false\n" +
                "    }";

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.post("/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(todoItem))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.text").value("Test"))
                .andExpect(jsonPath("$.done").value(false));
    }

    @Test
    public void should_update_todo_item_when_updateTodoItem_given_valid_input_and_record_exists() throws Exception {
        // given
        String updatedTodoItem = "{\n" +
                "        \"id\": \"001\",\n" +
                "        \"text\": \"UpdatedTest\",\n" +
                "        \"done\": true\n" +
                "    }";

        TodoItem existingRecord = TodoItem.builder()
                .id("001")
                .text("Original test")
                .done(false)
                .build();
        todoRepository.save(existingRecord);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.put("/todos/{id}", "001")
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedTodoItem))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.id").isString())
                .andExpect(jsonPath("$.text").value("UpdatedTest"))
                .andExpect(jsonPath("$.done").value(true));
    }

    @Test
    public void should_delete_todo_item_when_deleteTodoItem_given_record_exists() throws Exception {
        // given
        TodoItem existingRecord = TodoItem.builder()
                .id("001")
                .text("Original test")
                .done(false)
                .build();
        todoRepository.save(existingRecord);

        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete("/todos/{id}", "001"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

}
