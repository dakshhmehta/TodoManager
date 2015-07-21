package com.dakshhmehta.todomanager.model;

import com.orm.SugarRecord;

/**
 * Created by daksh on 7/18/15.
 */
public class Todo extends SugarRecord<Todo> {
    public String title;
    public boolean completed;

    public Todo(){
        this.completed = false;
    }

    public Todo(String title, boolean completed){
        this.title = title;
        this.completed = completed;
    }
}
