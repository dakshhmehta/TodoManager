package com.dakshhmehta.todomanager;

import android.app.Activity;
import android.content.Context;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dakshhmehta.todomanager.Adapter.TodoAdapter;
import com.dakshhmehta.todomanager.model.Todo;
import com.orm.query.Select;

import java.util.List;


public class MainActivity extends Activity {
    TextView txtNewTask;
    Button btnNewTask;
    ListView todos;
    TodoAdapter adapter;
    Button deleteBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNewTask = (TextView) this.findViewById(R.id.txtNewTask);
        btnNewTask = (Button) this.findViewById(R.id.btnNewTask);
        todos = (ListView) this.findViewById(R.id.todos);
        deleteBtn = (Button) this.findViewById(R.id.deleteBtn);

        adapter = new TodoAdapter(this);

        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Todo.deleteAll(Todo.class, "COMPLETED = ?", "1");
                refreshTodosList();
            }
        });
        refreshTodosList();

        btnNewTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtNewTask.getText() == null || txtNewTask.getText() == "" || txtNewTask.getText().length() == 0){
                    Toast.makeText(v.getContext(), "Enter task title", Toast.LENGTH_LONG).show();
                }
                else {
                    Todo t = new Todo();

                    t.title = txtNewTask.getText().toString();
                    t.save();

                    txtNewTask.setText(null);

                    Toast.makeText(v.getContext(), "Todo Added", Toast.LENGTH_LONG).show();
                    refreshTodosList();
                }
            }
        });
    }

    protected void refreshTodosList(){
        List<Todo> _todos = Select.from(Todo.class).orderBy("id DESC").list();

        adapter.setData(_todos);
        todos.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
