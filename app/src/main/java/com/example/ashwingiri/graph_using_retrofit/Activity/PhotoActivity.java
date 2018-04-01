package com.example.ashwingiri.graph_using_retrofit.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashwingiri.graph_using_retrofit.Holders.JsonPlaceholder;
import com.example.ashwingiri.graph_using_retrofit.Holders.Photo;
import com.example.ashwingiri.graph_using_retrofit.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PhotoActivity extends AppCompatActivity {

    ArrayList<Photo> photo=new ArrayList<>();
    ListView lvPhoto;
    PhotoAdapter adapter;
    int position;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lvPhoto= findViewById(R.id.lv);
        position=getIntent().getIntExtra("Position",0);
        adapter=new PhotoAdapter();
        lvPhoto.setAdapter(adapter);


        Retrofit r=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();

        JsonPlaceholder api=r.create(JsonPlaceholder.class);

        api.getPhotos(position).enqueue(
                new Callback<ArrayList<Photo>>() {
                    @Override
                    public void onResponse(Call<ArrayList<Photo>> call, Response<ArrayList<Photo>> response) {
                        photo=response.body();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<Photo>> call, Throwable t) {

                    }
                }
        );
    }
    private class PhotoAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return photo.size();
        }

        @Override
        public Photo getItem(int i) {
            return photo.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView==null) {
                convertView=getLayoutInflater().inflate(R.layout.activity_photo,viewGroup,false);
            }
            Photo temp_photo = getItem(i);
            ((TextView) convertView.findViewById(R.id.tvPhotoId)).setText(temp_photo.getId());
            ((TextView) convertView.findViewById(R.id.tvPhotoAlbumId)).setText(temp_photo.getAlbumId());
            ((TextView) convertView.findViewById(R.id.tvPhotoTitle)).setText(temp_photo.getTitle());
            if(temp_photo.getUrl().length() < 5)
            {
                (convertView.findViewById(R.id.ivPhotoImage)).setVisibility(View.GONE);
            }else{
                Picasso.with(getApplicationContext())
                        .load(temp_photo.getUrl())
                        .resize(300, 200)
                        .into((ImageView) convertView.findViewById(R.id.ivPhotoImage));
            }
            return convertView;

        }
    }
}
