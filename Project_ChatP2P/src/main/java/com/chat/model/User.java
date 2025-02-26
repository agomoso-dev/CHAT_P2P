package com.chat.model;

import java.io.Serializable;

public class User implements Serializable {

    /** Propiedades **/
    private String userId;                     // Id de usuario
    private String username;                   // Nombre de usuario
    private String ip;                         // Ip del usuario
    private int port;                          // Puerto del usuario
    private Avatar avatar;                     // Avatar

    private static User currentUser;           // Instancia estática del usuario actual

    /** Constructor por parámetros **/
    public User(String userId, String username, String ip, int port, Avatar avatar) {
        this.userId = userId;
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.avatar = avatar;
    }

    /**
     * Inicializa el usuario actual de la sesión
     */
    public static void initializeCurrentUser(User user) {
        currentUser = user;
    }

    /**
     * Devuelve la instancia estática del usuario actual
     * @return Instancia estática del usuario actual
     */
    public static User getCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("El usuario actual aún no ha sido inicializado");
        }
        return currentUser;
    }

    /**
     * Getters y Setters
     */
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public Avatar getAvatar() {
        return avatar;
    }

    public void setAvatar(Avatar avatar) {
        this.avatar = avatar;
    }

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }
}
