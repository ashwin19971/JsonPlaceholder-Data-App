package com.example.ashwingiri.graph_using_retrofit.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.ashwingiri.graph_using_retrofit.Holders.User.Address;
import com.example.ashwingiri.graph_using_retrofit.Holders.User.Company;
import com.example.ashwingiri.graph_using_retrofit.Holders.User.Geo;
import com.example.ashwingiri.graph_using_retrofit.Holders.JsonPlaceholder;
import com.example.ashwingiri.graph_using_retrofit.R;
import com.example.ashwingiri.graph_using_retrofit.Holders.User.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    ArrayList<User> user=new ArrayList<>();
    ListView lvUser;
    UserAdapter adapter;


        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        lvUser= findViewById(R.id.lv);
        adapter=new UserAdapter();
        lvUser.setAdapter(adapter);
        Retrofit r=new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .build();

        JsonPlaceholder api=r.create(JsonPlaceholder.class);
        api.getUsers().enqueue(
                new Callback<ArrayList<User>>() {
                    @Override
                    public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                        user=response.body();
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<ArrayList<User>> call, Throwable t) {

                    }
                }
        );

    }

    private class UserAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return user.size();
        }

        @Override
        public User getItem(int i) {
            return user.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup viewGroup) {
            if (convertView==null) {
                convertView=getLayoutInflater().inflate(R.layout.activity_user,viewGroup,false);
            }
            final User temp_user = getItem(i);
            ((TextView) convertView.findViewById(R.id.tvUserName)).setText(temp_user.getName());
            ((TextView) convertView.findViewById(R.id.tvUserUN)).setText(temp_user.getUsername());
            ((TextView) convertView.findViewById(R.id.tvUserEmail)).setText(temp_user.getEmail());
            ((TextView) convertView.findViewById(R.id.tvUserPhone)).setText(temp_user.getPhone());
            ((TextView) convertView.findViewById(R.id.tvUserWebsite)).setText(temp_user.getWebsite());

             Address temp_address=temp_user.getAddress();
            ((TextView) convertView.findViewById(R.id.tvUserAddressStreet)).setText(temp_address.getStreet());
            ((TextView) convertView.findViewById(R.id.tvUserAddressSuite)).setText(temp_address.getSuite());
            ((TextView) convertView.findViewById(R.id.tvUserAddressCity)).setText(temp_address.getCity());
            ((TextView) convertView.findViewById(R.id.tvUserAddressZipCode)).setText(temp_address.getZipcode());

            Geo temp_geo=temp_address.getGeo();
            ((TextView) convertView.findViewById(R.id.tvUserAddressLan)).setText(temp_geo.getLat());
            ((TextView) convertView.findViewById(R.id.tvUserAddressLat)).setText(temp_geo.getLng());

            Company temp_company=temp_user.getCompany();
            ((TextView) convertView.findViewById(R.id.tvUserCompanyBs)).setText(temp_company.getBs());
            ((TextView) convertView.findViewById(R.id.tvUserCompanyCatchPhrase)).setText(temp_company.getCatchPhrase());
            ((TextView) convertView.findViewById(R.id.tvUserCompanyName)).setText(temp_company.getName());


            convertView.findViewById(R.id.btUserPosts).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(
                                    new Intent(
                                            UserActivity.this, PostActivity.class
                                    ).putExtra(
                                            "url","https://jsonplaceholder.typicode.com/posts?userId="+temp_user.getId()));
                        }
                    }
             );

            convertView.findViewById(R.id.btUserTodos).setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            startActivity(
                                    new Intent(
                                            UserActivity.this, TodoActivity.class
                                    ).putExtra(
                                            "url","https://jsonplaceholder.typicode.com/todos?userId="+temp_user.getId()));
                        }
                    }
            );

            return convertView;
        }
    }
}
