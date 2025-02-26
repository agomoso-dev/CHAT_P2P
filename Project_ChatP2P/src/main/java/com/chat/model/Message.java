package com.chat.model;

import java.io.File;
import java.io.Serializable;

public class Message implements Serializable {

    // Enum para el tipo de Mensaje
    public enum MessageType {
        TEXT,
        FILE,
        SYSTEM
    }

    /** Propiedades **/
    private String content;         // Contenido del mensaje
    private File attachedFile;      // Archivo adjunto al mensaje
    private MessageType type;       // Tipo de mensaje

    /** Constructor por parámetros que inicializa un mensaje de tipo Texto o Sistema **/
    public Message(String content, MessageType type) {
        if (type != MessageType.TEXT && type != MessageType.SYSTEM) {
            throw new IllegalArgumentException("Tipo de mensaje no válido");
        }

        this.content = content;
        this.type = type;
    }

    /** Constructor por parámetros que inicializa un mensaje de tipo File **/
    public Message(File attachedFile) {
        this.attachedFile = attachedFile;
        this.type = MessageType.FILE;
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
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
