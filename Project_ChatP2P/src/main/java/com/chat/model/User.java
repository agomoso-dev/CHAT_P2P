package com.chat.model;

import com.chat.network.api.ApiResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class User implements Serializable {

    /** Propiedades **/
    private String userId;                     // Id de usuario
    private String username;                   // Nombre de usuario
    private String ip;                         // Ip del usuario
    private int port;                          // Puerto del usuario
    private Avatar avatar;                     // Avatar del usuario
    private List<User> contacts;               // Lista de contactos del usuario

    private static User currentUser;           // Instancia estática del usuario actual

    /** Constructor por parámetros **/
    public User(String userId, String username, String ip, int port, Avatar avatar) {
        this.userId = userId;
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.avatar = avatar;
        this.contacts = new CopyOnWriteArrayList<>();
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
    public synchronized static User getCurrentUser() {
        if (currentUser == null) {
            throw new IllegalStateException("El usuario actual aún no ha sido inicializado");
        }
        return currentUser;
    }

    /**
     * Convierte el usuario a un mapa de datos
     * @return Mapa de datos
     */
    public Map<String, Object> toMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("userId", userId);
        map.put("username", username);
        map.put("ip", ip);
        map.put("port", port);

        if (avatar != null) {
            map.put("avatar", avatar.toMap());
        }

        if (contacts != null && !contacts.isEmpty()) {
            List<String> contactIds = new ArrayList<>();

            for (User contact : contacts) {
                contactIds.add(contact.getUserId());
            }

            map.put("contacts", contactIds);
        } else {
            map.put("contacts", new ArrayList<String>());
        }

        return map;
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

    public List<User> getContacts() { return contacts;}

    public void setContacts(List<User> contacts) { this.contacts = contacts;}

    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }
}
