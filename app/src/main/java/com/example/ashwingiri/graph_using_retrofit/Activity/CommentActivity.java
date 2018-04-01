package com.example.ashwingiri.graph_using_retrofit.Activity;

import com.example.ashwingiri.graph_using_retrofit.Holders.Comment;
import com.example.ashwingiri.graph_using_retrofit.Holders.JsonPlaceholder;
import com.example.ashwingiri.graph_using_retrofit.Holders.Post;
import com.example.ashwingiri.graph_using_retrofit.R;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CommentActivity extends AppCompatActivity {

    ArrayList<Comment> comment = new ArrayList<>();
    ListView lvComment;
    CommentAdapter adapter;
    int position;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lvComment = findViewById(R.id.lv);
        adapter = new CommentAdapter();
        lvComment.setAdapter(adapter);
        position=getIntent().getIntExtra("Position",0);
//        Retrofit r=new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .build();
//
//        JsonPlaceholder api=new Retrofit.Builder()
//                .addConverterFactory(GsonConverterFactory.create())
//                .baseUrl("https://jsonplaceholder.typicode.com/")
//                .build().create(JsonPlaceholder.class);


        new Retrofit
                .Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build()
                .create(JsonPlaceholder.class)
                .getComment()
                .enqueue(
                        new Callback<ArrayList<Comment>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Comment>> call, Response<ArrayList<Comment>> response) {
                                comment=response.body();
                                adapter.notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Comment>> call, Throwable t) {

                            }
                        }
                );

    }

    class CommentAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return comment.size();
        }

        @Override
        public Comment getItem(int i) {
            return comment.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.activity_comment, viewGroup, false);
            }
            Comment temp_comment = getItem(i);
            ((TextView) convertView.findViewById(R.id.tvCommentName)).setText(temp_comment.getName());
            ((TextView) convertView.findViewById(R.id.tvCommentEmailId)).setText(temp_comment.getEmail());
            ((TextView) convertView.findViewById(R.id.tvCommentBody)).setText(temp_comment.getBody());
            ((TextView) convertView.findViewById(R.id.tvCommentId)).setText(temp_comment.getId());
            ((TextView) convertView.findViewById(R.id.tvCommentPostId)).setText(temp_comment.getPostId());

            return convertView;
        }
    }
}
