package com.chat.network.api;

import com.chat.model.Avatar;
import com.chat.model.User;
import com.chat.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class Test {
    public static void main2(String[] args) throws IOException {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_URL)
                .build();

        UserService userService = retrofit.create(UserService.class);

        Avatar avatar = new Avatar("C:\\Users\\jairo\\Desktop\\Cat03.jpg", null);
        User user = new User("1", "Jairo", "localhost", 6000, null);

        Map<String, Object> userData = new HashMap<>();
        userData.put("userId", "1");
        userData.put("username", "Jairo");
        userData.put("ip", "localhost");
        userData.put("port", 6000);
        userData.put("avatar", avatar.toBase64());

        Call<ApiResponse> call = userService.addUser(userData);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();
                if (apiResponse != null && apiResponse.isSuccess()) {
                    Map<String, Object> data = apiResponse.getData();
                    String avatarUrl = (String) data.get("avatarUrl");

                    try {
                        URL url = new URL(avatarUrl);
                        BufferedImage img = ImageIO.read(url);
                        ImageIcon icon = new ImageIcon(img);

                        JFrame frame = new JFrame();
                        JButton button = new JButton(icon);

                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setSize(800, 800);
                        frame.setLayout(new FlowLayout());
                        frame.add(button);
                        frame.setVisible(true);

                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    System.out.println("Error al añadir el usuario");
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }


    public static void main(String[] args) throws IOException {
        User userTest = new User("2", "Jairo", "localhost", 5000, new Avatar("C:\\Users\\jairo\\Desktop\\Cr7.jpg", null));

        UserClient.getInstance().addContact("1", "2", new UserClient.UserCallback<User>() {
            @Override
            public void onSuccess(User user) {
                System.out.println(user.getUsername());
                System.out.println(user.getIp());
                System.out.println(user.getAvatar().getLocalPath());
                System.out.println(user.getAvatar().getStorageUrl());
            }

            @Override
            public void onError(String errorMsg) {
                System.out.println("Error al añadir el usuario: " + errorMsg);
            }
        });
    }
}
