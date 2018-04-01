package com.example.ashwingiri.graph_using_retrofit.Holders;

import com.example.ashwingiri.graph_using_retrofit.Holders.User.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Ashwin Giri on 1/21/2018.
 */

public interface JsonPlaceholder  {

    @GET("posts")
    Call<ArrayList<Post>> getPost();

    @GET("posts/{postId}")
    Call<Post> getPostById(
            @Path("postId") Integer postId
    );

    @GET("comments")
    Call<ArrayList<Comment>> getComment();

    @GET("albums")
    Call<ArrayList<Album>> getAlbum();

    @GET("albums/{albumId}/photos")
    Call<ArrayList<Photo>> getPhotos(
            @Path("albumId") Integer albumId
    );

    @GET("todos")
    Call<ArrayList<Todo>> getTodo();


    @GET("users")
    Call<ArrayList<User>> getUsers();
}
