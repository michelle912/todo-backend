package com.afs.Todo;


import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "todoItem")
public class TodoItem {
    private String id;
    private String text;
    private Boolean done;
}
