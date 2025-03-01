package com.chat.model;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

public class Message implements Serializable {
    private static final long serialVersionUID = 1L;

    // Enum para el tipo de Mensaje
    public enum MessageType {
        TEXT,       // Mensaje de texto normal
        FILE,       // Envío de archivo
        SYSTEM,     // Mensaje de sistema
        USER_INFO   // Información de usuario
    }

    /** Propiedades básicas **/
    private String content;                // Contenido del mensaje
    private File attachedFile;             // Archivo adjunto al mensaje
    private MessageType type;              // Tipo de mensaje
    private LocalDateTime timestamp;       // Momento de creación del mensaje
    
    /** Propiedades para USER_INFO **/
    private User userData;                 // Información completa del usuario
    
    /** Propiedades para de archivos **/
    private byte[] fileContent;            // Contenido binario del archivo
    private String fileName;               // Nombre del archivo
    
    /** Constructor vacío **/
    public Message() {
        this.timestamp = LocalDateTime.now();
    }

    /** Constructor para mensajes de texto o sistema **/
    public Message(String content, MessageType type) {
        if (type != MessageType.TEXT && type != MessageType.SYSTEM) {
            throw new IllegalArgumentException("Tipo de mensaje no válido para este constructor");
        }
        
        this.content = content;
        this.type = type;
        this.timestamp = LocalDateTime.now();
    }

    /** Constructor para mensajes de archivo **/
    public Message(File attachedFile) {
        this.attachedFile = attachedFile;
        this.type = MessageType.FILE;
        this.fileName = attachedFile.getName();
        this.timestamp = LocalDateTime.now();
    }
    
    /** Constructor para mensajes de archivo con contenido binario **/
    public Message(String fileName, byte[] fileContent) {
        this.fileName = fileName;
        this.fileContent = fileContent;
        this.type = MessageType.FILE;
        this.timestamp = LocalDateTime.now();
    }
    
    /** Método para crear un mensaje de información de usuario **/
    public static Message createUserInfoMessage(User user) {
        Message message = new Message();
        message.setType(MessageType.USER_INFO);
        message.setUserData(user);
        
        return message;
    }

    /** Getters y Setters **/
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public File getAttachedFile() {
        return attachedFile;
    }

    public void setAttachedFile(File attachedFile) {
        this.attachedFile = attachedFile;
        if (attachedFile != null) {
            this.fileName = attachedFile.getName();
        }
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
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
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    @Override
    public String toString() {
        switch (type) {
            case TEXT:
                return "[" + timestamp + "] " + content;
            case FILE:
                return "[" + timestamp + "] Archivo: " + (fileName != null ? fileName : "desconocido");
            case SYSTEM:
                return "[" + timestamp + "] Sistema: " + content;
            case USER_INFO:
                return "[" + timestamp + "] Info de usuario: " + 
                       (userData != null ? userData.getUsername() : "desconocido");
            default:
                return "Mensaje desconocido";
        }
    }
}