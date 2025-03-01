package com.chat.network.api;

import com.chat.model.Avatar;
import com.chat.model.User;
import com.chat.utils.Constants;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.List;
import java.util.Map;

public class UserClient {

    /** Propiedades **/
    private static UserClient instance;        // Singleton de UserClient
    private UserService userService;           // Servicio de usuarios

    /** Constructor privado **/
    private UserClient() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(Constants.API_URL)
                .build();

        userService = retrofit.create(UserService.class);
    }

    /** Obtiene la instancia de UserClient **/
    public static synchronized UserClient getInstance() {
        if (instance == null) {
            instance = new UserClient();
        }
        return instance;
    }

    /** MÉTODOS DE REQUESTS A LA API **/

    /** Añade un usuario a la base de datos **/
    public void addUser(User user, UserCallback<User> callback) {
        Call<ApiResponse> call = userService.addUser(user.toMap());

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.isSuccessful() && apiResponse != null && apiResponse.isSuccess()) {
                    if (apiResponse.getData() != null && apiResponse.getData().containsKey("avatar")) {
                        Avatar avatar = apiResponse.getObject("avatar", Avatar.class);
                        if (avatar != null) {
                            user.setAvatar(avatar);
                        }
                    }

                    callback.onSuccess(user);
                } else {
                    String errorMsg = apiResponse != null ?
                            apiResponse.getMessage() :
                            "Error desconocido";
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    /** Obtiene un usuario de la base de datos **/
    public void getUser(String userId, UserCallback<User> callback){
       Call<ApiResponse> call = userService.getUser(userId);

       call.enqueue(new Callback<ApiResponse>() {
           @Override
           public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.isSuccessful() && apiResponse != null && apiResponse.isSuccess()) {
                     User user = apiResponse.getObject("user", User.class);
                     callback.onSuccess(user);
                } else {
                     String errorMsg = apiResponse != null ?
                            apiResponse.getMessage() :
                            "Error desconocido";
                     callback.onError(errorMsg);
                }
           }

           @Override
           public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                callback.onError("Error de conexión: " + throwable.getMessage());
           }
       });
    }

    /**
     * Obtiene los contactos de un usuario
     * @param userId ID del usuario cuyos contactos se quieren obtener
     * @param callback Callback para manejar el resultado
     */
    public void getContacts(String userId, UserCallback<List<User>> callback) {
        Call<ApiResponse> call = userService.getContacts(userId);

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.isSuccessful() && apiResponse != null && apiResponse.isSuccess()) {
                    try {
                        List<User> contacts = apiResponse.getObjectList("contacts", User.class);
                        callback.onSuccess(contacts);
                    } catch (Exception e) {
                        callback.onError("Error al procesar los contactos: " + e.getMessage());
                    }
                } else {
                    String errorMsg = apiResponse != null ?
                            apiResponse.getMessage() :
                            "Error desconocido";
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    /** Actualiza un usuario en la base de datos **/
    public void updateUser(User user, UserCallback<User> callback) {
        Call<ApiResponse> call = userService.updateUser(user.toMap());

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.isSuccessful() && apiResponse != null && apiResponse.isSuccess()) {
                    if (apiResponse.getData().containsKey("user")) {
                        User updatedUser = apiResponse.getObject("user", User.class);
                        user.setUsername(updatedUser.getUsername());
                        user.setIp(updatedUser.getIp());
                        user.setPort(updatedUser.getPort());
                        user.setAvatar(updatedUser.getAvatar());
                    }

                    callback.onSuccess(user);
                } else {
                    String errorMsg = apiResponse != null ?
                            apiResponse.getMessage() :
                            "Error desconocido";
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    /** Añade un contacto a un usuario de la base de datos **/
    public void addContact(String userId, String contactId, UserCallback<User> callback) {
        Call<ApiResponse> call = userService.addContact(Map.of("userId", userId, "contactId", contactId));

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.isSuccessful() && apiResponse != null && apiResponse.isSuccess()) {
                    User contact = apiResponse.getObject("contact", User.class);
                    callback.onSuccess(contact);
                } else {
                    String errorMsg = apiResponse != null ?
                            apiResponse.getMessage() :
                            "Error desconocido";
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    /** Elimina un contacto de un usuario de la base de datos **/
    public void removeContact(String userId, String contactId, UserCallback<User> callback){
        Call<ApiResponse> call = userService.removeContact(Map.of("userId", userId, "contactId", contactId));

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                ApiResponse apiResponse = response.body();

                if (response.isSuccessful() && apiResponse != null && apiResponse.isSuccess()) {
                    User contact = apiResponse.getObject("contact", User.class);
                    callback.onSuccess(contact);
                } else {
                    String errorMsg = apiResponse != null ?
                            apiResponse.getMessage() :
                            "Error desconocido";
                    callback.onError(errorMsg);
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable throwable) {
                callback.onError("Error de conexión: " + throwable.getMessage());
            }
        });
    }

    /**
     * Getter
     */
    public UserService getUserService() {
        return userService;
    }

    /** Interfaz para notificar el resultado de la petición **/
    public interface UserCallback<T> {
        void onSuccess(T result);
        void onError(String errorMessage);
    }
}
