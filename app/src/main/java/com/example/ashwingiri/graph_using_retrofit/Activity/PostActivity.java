package com.example.ashwingiri.graph_using_retrofit.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashwingiri.graph_using_retrofit.Holders.JsonPlaceholder;
import com.example.ashwingiri.graph_using_retrofit.Holders.Post;
import com.example.ashwingiri.graph_using_retrofit.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostActivity extends AppCompatActivity {

    ArrayList<Post> post=new ArrayList<>();
    ListView lvPost;
    URL url;
    PostAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lvPost= findViewById(R.id.lv);
        adapter=new PostAdapter();
        lvPost.setAdapter(adapter);
        Retrofit r=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();

        JsonPlaceholder api=r.create(JsonPlaceholder.class);

        api.getPost().enqueue(
                new Callback<ArrayList<Post>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Post>> call, Response<ArrayList<Post>> response) {
                        post=response.body();
                       adapter.notifyDataSetChanged();
                        Log.d("TAG", "onResponse: "+post);
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Post>> call, Throwable t) {

                    }
                }
        );
//        new DownloadPostTask().execute();
        lvPost.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startActivity(
                                new Intent(
                                        PostActivity.this, CommentActivity.class
                                ).putExtra("Position",i+1)
                        );
                    }
                }
        );
    }


    private class PostAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return post.size();
        }

        @Override
        public Post getItem(int i) {
            return post.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView==null) {
                convertView=getLayoutInflater().inflate(R.layout.activity_post,viewGroup,false);
            }
            Post temp_post = getItem(i);
            ((TextView) convertView.findViewById(R.id.tvPostTitle)).setText(temp_post.getTitle());
            ((TextView) convertView.findViewById(R.id.tvPostBody)).setText(temp_post.getBody());
            ((TextView) convertView.findViewById(R.id.tvPostId)).setText(temp_post.getId());
            ((TextView) convertView.findViewById(R.id.tvPostUserId)).setText(temp_post.getUserId());

            return convertView;
        }
    }
}
