package com.dakshhmehta.todomanager.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dakshhmehta.todomanager.R;
import com.dakshhmehta.todomanager.model.Todo;
import com.orm.SugarRecord;

import java.util.List;

/**
 * Created by daksh on 7/19/15.
 */
public class TodoAdapter extends BaseAdapter {
    private List<Todo> data;
    private LayoutInflater myInflater;

    public TodoAdapter(Context context) {
        myInflater = LayoutInflater.from(context);
    }

    public void setData(List<Todo> data){
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return data.get(position).getId();
    }

    protected void setTextColor(CompoundButton v, Todo t){
        if(t.completed == true)
            v.setTextColor(Color.GREEN);
        else
            v.setTextColor(Color.RED);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final Todo _t = data.get(position);
        final ViewHolder holder;

        convertView     = myInflater.inflate(R.layout.todo_item, null);
        holder          = new ViewHolder();
        holder.id   = (CheckBox) convertView.findViewById(R.id.text);

        setTextColor(holder.id, _t);

        if(_t.completed == true)
            holder.id.setChecked(true);

        holder.id.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                _t.completed = isChecked;

                _t.save();

                setTextColor(buttonView, _t);
            }
        });

        convertView.setTag(holder);

        holder.id.setText(_t.title);


        return convertView;
    }

    static class ViewHolder {
        CheckBox id;
    }
}
