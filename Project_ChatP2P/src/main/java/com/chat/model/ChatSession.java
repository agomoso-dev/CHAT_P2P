/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author jairo
 */
public class ChatSession {
    
    /** Propiedades **/
    private final String sessionId;                   // ID de la sesión
    
    private final String localUserId;                 // ID del Usuario actual
    private final String remoteUserId;                // ID del Contacto
    
    private List<MessageEntry> messageHistory;        // Historial de mensajes
    
    /** Constructor por parámetros **/
    public ChatSession(String remoteUserId) {
        this.localUserId = User.getCurrentUser().getUserId();
        this.remoteUserId = remoteUserId;
        this.sessionId = generateSessionId();

        this.messageHistory = new CopyOnWriteArrayList<>();
    }
    
    /**
     * Genera el código de la sesión de Chat
     * 
     * @param localUserId ID del Usuario actual
     * @param contactId ID del Contacto
     * @return ID de la Sesión de Chat
     */
    private String generateSessionId() {
        return localUserId + ":" + remoteUserId;
    }

    /**
     * Registra un mensaje en el historial
     * 
     * @param userId ID del Usuario que envió el mensaje
     * @param messageEntry Entrada de mensaje a registrar
     */
    public void addMessage(User user, MessageEntry messageEntry) {
        messageHistory.add(messageEntry);
    }

    /** Getters **/
    public String getSessionId() {
        return sessionId;
    }
    
    public String getLocalUserId() {
        return localUserId;
    }
    
    public String getRemoteUserId() {
        return remoteUserId;
    }
    
    public List<MessageEntry> getMessageHistory() {
        return messageHistory;
    }
    
}
