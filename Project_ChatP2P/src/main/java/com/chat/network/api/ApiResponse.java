package com.chat.network.api;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApiResponse {

    /** Propiedades **/
    private boolean success;               // Estado de la respuesta
    private String message;                // Mensaje de la respuesta
    private Map<String, Object> data;      // Datos de la respuesta

    /** Constructor por parámetros **/
    public ApiResponse() {
        data = new HashMap<>();
    }

    /**
     * Obtiene un valor String de la respuesta
     * @param key Clave del valor en la respuesta
     * @return Valor String, o null si no existe
     */
    public String getString(String key) {
        if (data == null) return null;

        Object value = data.get(key);
        return value != null ? value.toString() : null;
    }

    /**
     * Obtiene un valor Integer de la respuesta
     * @param key Clave del valor en la respuesta
     * @return Valor Integer, o null si no existe o no es un Integer
     */
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

    /**
     * Obtiene un valor Double de la respuesta
     * @param key Clave del valor en la respuesta
     * @return Valor Double, o null si no existe o no es un Double
     */
    public boolean getBoolean(String key){
        Object value = data.get(key);
        if (value instanceof Boolean) {
            return (Boolean) value;
        }

        return false;
    }

    /**
     * Obtiene un valor Double de la respuesta
     * @param key Clave del valor en la respuesta
     * @param type Tipo del valor a obtener
     * @return Valor Double, o null si no existe o no es un Double
     */
    public <T> T getObject(String key, Class<T> type) {
        Gson gson = new Gson();
        JsonElement element = data.get(key) != null ? gson.toJsonTree(data.get(key)) : null;

        return element != null ? gson.fromJson(element, type) : null;
    }

    /**
     * Obtiene una lista de objetos de la respuesta
     * @param key Clave del objeto en la respuesta
     * @param type Tipo del objeto a deserializar
     * @param <T> Tipo genérico
     * @return Lista de objetos del tipo especificado, o lista vacía si no existe
     */
    public <T> List<T> getObjectList(String key, Class<T> type) {
        if (data == null || !data.containsKey(key)) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Object value = data.get(key);

        if (value == null) {
            return new ArrayList<>();
        }

        try {
            JsonElement jsonElement = gson.toJsonTree(value);

            if (!jsonElement.isJsonArray()) {
                return new ArrayList<>();
            }

            Type listType = TypeToken.getParameterized(List.class, type).getType();

            return gson.fromJson(jsonElement, listType);
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
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
