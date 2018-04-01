package com.example.ashwingiri.graph_using_retrofit.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashwingiri.graph_using_retrofit.Holders.JsonPlaceholder;
import com.example.ashwingiri.graph_using_retrofit.Holders.Todo;
import com.example.ashwingiri.graph_using_retrofit.R;

import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TodoActivity extends AppCompatActivity {

    ArrayList<Todo> todo=new ArrayList<>();
    URL url;
    ListView lvTodo;
    TodoAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lvTodo= findViewById(R.id.lv);
        adapter=new TodoAdapter();
        lvTodo.setAdapter(adapter);
//        new DownloadTodoTask().execute();
        Retrofit r=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();
        JsonPlaceholder api=r.create(JsonPlaceholder.class);

        api.getTodo().enqueue(
                new Callback<ArrayList<Todo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Todo>> call, Response<ArrayList<Todo>> response) {
                          todo=response.body();
                          adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Todo>> call, Throwable t) {

                    }
                }
        );
    }
        private class TodoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return todo.size();
        }

        @Override
        public Todo getItem(int i) {
            return todo.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView==null) {
                convertView=getLayoutInflater().inflate(R.layout.activity_todo,viewGroup,false);
            }
            Todo temp_todo = getItem(i);
            ((TextView) convertView.findViewById(R.id.tvTodoTitle)).setText(temp_todo.getTitle());
            ((TextView) convertView.findViewById(R.id.tvTodoId)).setText(temp_todo.getId());
            ((TextView) convertView.findViewById(R.id.tvTodoUserId)).setText(temp_todo.getUserId());
            ((CheckBox) convertView.findViewById(R.id.cbTodoCheckbox)).setChecked(temp_todo.isCompleted());

            return convertView;
        }
    }
}
