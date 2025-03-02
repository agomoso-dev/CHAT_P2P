/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.model;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author jairo
 */
public class MessageEntry {
    
    /** Propiedades **/
    private final User sender;                   // Usuario que envió el mensaje
    private final Message message;               // El mensaje en sí
    private final LocalTime timestamp;           // Momento en que se añadió
        
    /** Constructor por parámetros **/
    public MessageEntry(User sender, Message message) {
        this.sender = sender;
        this.message = message;
        this.timestamp = LocalTime.now();
    }

    /**
     * Getters y Setters
     */
    
    public User getSender() {
        return sender;
    }

    public Message getMessage() {
        return message;
    }

    public String getTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String formattedTime = timestamp.format(formatter);
        
        return formattedTime;
    }
    
}
