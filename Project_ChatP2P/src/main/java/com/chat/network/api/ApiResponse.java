package com.chat.network.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import java.util.Map;

public class ApiResponse {

    /** Propiedades **/
    private boolean success;               // Estado de la respuesta
    private String message;                // Mensaje de la respuesta
    private Map<String, Object> data;      // Datos de la respuesta

    /** Constructor por parámetros **/
    public ApiResponse() { }

    /** Obtiene un valor String de la respuesta **/
    public String getString(String key) {
        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    /** Obtiene un valor Integer de la respuesta **/
    public Integer getInteger(String key) {
        Object value = data.get(key);
        if (value instanceof Integer) {
            return (Integer) value;
        } else if (value instanceof String) {
            try {
                return Integer.parseInt((String) value);
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    /** Obtiene un valor Boolean de la respuesta **/
    public boolean getBoolean(String key){
        Object value = data.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        return false;
    }

    /** Obtiene un objeto de la respuesta **/
    public <T> T getObject(String key, Class<T> type) {
        Gson gson = new Gson();
        JsonElement element = data.get(key) != null ? gson.toJsonTree(data.get(key)) : null;

        return element != null ? gson.fromJson(element, type) : null;
    }

    /** Getters **/
    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, Object> getData() {
        return data;
    }
}
