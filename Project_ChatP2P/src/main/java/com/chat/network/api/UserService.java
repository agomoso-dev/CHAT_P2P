package com.chat.network.api;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface UserService {

    @POST("addUser")
    Call<ApiResponse> addUser(@Body Map<String, Object> userData);

    @GET("getUser")
    Call<ApiResponse> getUser(@Query("userId") String userId);

    @GET("getContacts")
    Call<ApiResponse> getContacts(@Query("userId") String userId);

    @PATCH("updateUser")
    Call<ApiResponse> updateUser(@Body Map<String, Object> userData);

    @PATCH("addContact")
    Call<ApiResponse> addContact(@Body Map<String, String> userData);

    @PATCH("removeContact")
    Call<ApiResponse> removeContact(@Body Map<String, String> userData);

}
