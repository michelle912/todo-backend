package com.afs.Todo.repository;


import com.afs.Todo.TodoItem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoItem, String> {

}
