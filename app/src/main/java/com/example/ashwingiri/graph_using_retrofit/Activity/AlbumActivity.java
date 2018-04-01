package com.example.ashwingiri.graph_using_retrofit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.ashwingiri.graph_using_retrofit.Holders.Album;
import com.example.ashwingiri.graph_using_retrofit.Holders.JsonPlaceholder;
import com.example.ashwingiri.graph_using_retrofit.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AlbumActivity extends AppCompatActivity {


    ArrayList<Album> album=new ArrayList<>();
    ListView lvAlbum;
    AlbumAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lvAlbum= findViewById(R.id.lv);
        adapter=new AlbumAdapter();
        lvAlbum.setAdapter(adapter);
        //new DownloadAlbumTask().execute();
        lvAlbum.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        startActivity(
                                new Intent(
                                        AlbumActivity.this,PhotoActivity.class
                                ).putExtra("Position",i+1)
                        );
                    }
                }
        );

        Retrofit r=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("http://jsonplaceholder.typicode.com/")
                .build();

        JsonPlaceholder api=r.create(JsonPlaceholder.class);

        api.getAlbum().enqueue(
                new Callback<ArrayList<Album>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Album>> call, Response<ArrayList<Album>> response) {
                        album=response.body();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Album>> call, Throwable t) {

                    }
                }
        );
    }

    private class AlbumAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return album.size();
        }

        @Override
        public Album getItem(int i) {
            return album.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView==null) {
                convertView=getLayoutInflater().inflate(R.layout.activity_album,viewGroup,false);
            }
            Album temp_album = getItem(i);
            ((TextView) convertView.findViewById(R.id.tvAlbumTitle)).setText(temp_album.getTitle());
            ((TextView) convertView.findViewById(R.id.tvAlbumId)).setText(temp_album.getId());
            ((TextView) convertView.findViewById(R.id.tvAlbumUserId)).setText(temp_album.getUserId());

            return convertView;
        }
    }
}
