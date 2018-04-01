package com.example.ashwingiri.graph_using_retrofit.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ashwingiri.graph_using_retrofit.R;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    ImageView ivPost,ivAlbum,ivTodo,ivUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPost= findViewById(R.id.ivPost);
        ivAlbum= findViewById(R.id.ivAlbum);
        ivTodo= findViewById(R.id.ivTodo);
        ivUser= findViewById(R.id.ivUser);

        ivPost.setOnClickListener(this);
        ivAlbum.setOnClickListener(this);
        ivTodo.setOnClickListener(this);
        ivUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.ivPost:
                startActivity(
                        new Intent(getApplicationContext(),PostActivity.class)
                );
                break;

            case R.id.ivAlbum:
                startActivity(new Intent(getApplicationContext(),AlbumActivity.class));
                break;

            case R.id.ivTodo:
                startActivity(
                        new Intent(getApplicationContext(),TodoActivity.class)
                        .putExtra("url","https://jsonplaceholder.typicode.com/todos")
                );
                break;

            case R.id.ivUser:
                startActivity(new Intent(getApplicationContext(),UserActivity.class));
                break;

        }
    }
}
