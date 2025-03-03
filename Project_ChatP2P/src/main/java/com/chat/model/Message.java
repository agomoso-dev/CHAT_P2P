   package com.chat.model;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    // Enum para el tipo de Mensaje
    public enum MessageType {
        TEXT,             // Mensaje de texto normal
        FILE,             // Envío de archivo
        SYSTEM,           // Mensaje de sistema
        USER_INFO,        // Información de usuario
        CONNECTION,       // Conexión a/de contacto
        DISCONNECTION,    // Desconexión de/de parte de contacto
    }

    /** Propiedades básicas **/
    private String content;                    // Contenido del mensaje
    private MessageType type;                  // Tipo de mensaje
    
    /** Propiedades para USER_INFO **/
    private User userData;                     // Información completa del usuario
    
    /** Propiedades para FILE **/
    private byte[] fileContent;                // Contenido binario del archivo
    private Map<String, Object> fileData;      // Nombre del archivo
    
    /** Constructor vacío **/
    public Message() { fileData = new HashMap<>(); }

    /** Método para crear un mensaje de texto **/
    public static Message createTextMessage(String content){
        Message message = new Message();
        message.setType(MessageType.TEXT);
        message.setContent(content);
        
        return message;
    }
    
    /** Método para crear un mensaje del Sistema **/
    public static Message createSystemMessage(String content){
        Message message = new Message();
        message.setType(MessageType.SYSTEM);
        message.setContent(content);
        
        return message;
    }
    
    /** Método para crear un mensaje de archivo **/
    public static Message createFileMessage(File file) throws IOException {
        Message message = new Message();
        message.setType(MessageType.FILE);
        message.setFileContent(Files.readAllBytes(file.toPath()));
        message.setFileData(file);

        return message;
    }
    
    /** Método para crear un mensaje de información de usuario **/
    public static Message createUserInfoMessage(User user) {
        Message message = new Message();
        message.setType(MessageType.USER_INFO);
        message.setUserData(user);
        
        return message;
    }
    
    /**
     * Método para crear un mensaje de aviso de que se ha establecido conexión con un contacto
     * @param user Usuario actual
     * @return Mensaje a enviar
     */
    public static Message createConnectionMessage(User user) {
        Message message = new Message();
        message.setType(MessageType.CONNECTION);
        message.setUserData(user);
        
        return message;
    }
    
    /**
     * Método para crear un mensaje de aviso de que se va a realizar una desconexión con un contacto
     * @param user Usuario actual
     * @return Mensaje a enviar
     */
    public static Message createDisconnectionMessage(User user) {
        Message message = new Message();
        message.setType(MessageType.DISCONNECTION);
        message.setUserData(user);
        
        return message;
    }
    
    /** Métodos adicionales **/
    
    /**
     * Establece los datos de un archivo en el mapa de los datos del Archivo
     * 
     * @param file Archivo a leer
     */
    private void setFileData(File file){
        fileData.put("name", file.getName());
        fileData.put("size", file.length());
    }

    /** Getters y Setters **/
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
    
    public User getUserData() {
        return userData;
    }
    
    public void setUserData(User userData) {
        this.userData = userData;
    }
    
    public byte[] getFileContent() {
        return fileContent;
    }
    
    public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }

    public Map<String, Object> getFileData() {
        return fileData;
    }

    public void setFileData(Map<String, Object> fileData) {
        this.fileData = fileData;
    }
    
}