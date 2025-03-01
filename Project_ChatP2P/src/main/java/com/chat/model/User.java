package com.chat.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class User implements Serializable {

    /** Propiedades **/
    private static User currentUser;           // Instancia estática del usuario actual
    
    private String userId;                     // Id de usuario
    private String username;                   // Nombre de usuario
    private String ip;                         // Ip del usuario
    private int port;                          // Puerto del usuario
    private Avatar avatar;                     // Avatar del usuario

    @SerializedName("contacts")
    private List<String> contactsIds;          // Lista de IDs de los contactos del usuario

    /** Constructor por parámetros **/
    public User(String userId, String username, String ip, int port, Avatar avatar) {
        this.userId = userId;
        this.username = username;
        this.ip = ip;
        this.port = port;
        this.avatar = avatar;
        this.contactsIds = new ArrayList();
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

        map.put("contacts", contactsIds);

        return map;
    }

    /**
     * Getters y Setters
     */
    public static void setCurrentUser(User currentUser) {
        User.currentUser = currentUser;
    }
    
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

    public List<String> getContactsIds() { return contactsIds; }

    public void setContactsIds(List<String> contactsIds) { this.contactsIds = contactsIds; }

}
