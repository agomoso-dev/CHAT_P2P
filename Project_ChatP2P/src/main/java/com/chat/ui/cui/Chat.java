/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.ui.cui;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import com.chat.model.Message;
import com.chat.model.MessageEntry;
import com.chat.model.User;
/**
 *
 * @author wenfi
 */
public class Chat {
    private Scanner scanner;
    private List<User> contacts;
    private Map<String, String> contactStatuses;
    private String selectedContactId;
    
    public Chat() {
        this.scanner = new Scanner(System.in);
        this.contacts = new CopyOnWriteArrayList<>();
        this.contactStatuses = new ConcurrentHashMap<>();
    }
    
    public void displayChat(List<MessageEntry> messages) {
        System.out.println("\n=== CHAT ===");
        System.out.println("----------------------------------------");
        
        for (MessageEntry messageEntry : messages) {
            String senderUsername = messageEntry.getSender().getUsername();
            if (senderUsername.equals(User.getCurrentUser().getUsername())) {
                senderUsername = "Yo";
            }
            
            System.out.printf("[%s] %s: %s\n", 
                messageEntry.getTimestamp(),
                senderUsername, 
                messageEntry.getMessage().getContent());
        }
        System.out.println("----------------------------------------");
    }
    
    public void setContactsList(List<User> contacts) {
        this.contacts = contacts;
        displayContacts();
    }
    public void createPanelContact(User contact, String peerId) {
        // Agregar el contacto a la lista si no existe
        if (!contacts.contains(contact)) {
            contacts.add(contact);
            // Inicializar el estado del contacto como offline
            contactStatuses.put(contact.getUserId(), "offline");
        }
        
        // Actualizar la vista de contactos
        displayContacts();
    }
    public void displayContacts() {
        System.out.println("\n=== CONTACTOS ===");
        System.out.println("----------------------------------------");
        for (User contact : contacts) {
            String status = contactStatuses.getOrDefault(contact.getUserId(), "offline");
            String selectedMark = contact.getUserId().equals(selectedContactId) ? ">" : " ";
            
            System.out.printf("%s %s (%s)\n", 
                selectedMark,
                contact.getUsername(), 
                status);
        }
        System.out.println("----------------------------------------");
    }
    
    public Message getMessage() {
        System.out.print("\nEscribe tu mensaje: ");
        String content = scanner.nextLine();
        return Message.createTextMessage(content);
    }
    
    public void displaySystemMessage(String message) {
        System.out.println("SISTEMA: " + message);
    }
    
    public void displayFileMessage(MessageEntry messageEntry) {
        Message message = messageEntry.getMessage();
        User sender = messageEntry.getSender();
        
        String fileName = (String) message.getFileData().get("name");
        long fileSize = (long) message.getFileData().get("size");
        
        String senderUsername = sender.getUsername();
        if (senderUsername.equals(User.getCurrentUser().getUsername())) {
            System.out.printf("\n[%s] Yo: He enviado un archivo: %s (%s)\n",
                messageEntry.getTimestamp(),
                fileName,
                formatFileSize(fileSize));
        } else {
            System.out.printf("\n[%s] %s: Ha enviado un archivo: %s (%s)\n",
                messageEntry.getTimestamp(),
                senderUsername,
                fileName,
                formatFileSize(fileSize));
        }
    }
    
    private String formatFileSize(long bytes) {
        final long KB = 1024;
        final long MB = KB * 1024;
        final long GB = MB * 1024;

        if (bytes < KB) {
            return bytes + " B";
        } else if (bytes < MB) {
            return String.format("%.2f KB", (float) bytes / KB);
        } else if (bytes < GB) {
            return String.format("%.2f MB", (float) bytes / MB);
        } else {
            return String.format("%.2f GB", (float) bytes / GB);
        }
    }
    
    public void updateContactStatus(String userId, String peerId, boolean connected) {
        contactStatuses.put(userId, connected ? "online" : "offline");
        displayContacts();
    }
    
    public void setContactAsSelected(String contactId) {
        this.selectedContactId = contactId;
        displayContacts();
    }
    
    public void setVisible(boolean visible) {
        if (visible) {
            clearScreen();
        }
    }
    
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    public void showCommands() {
        System.out.println("\nComandos disponibles:");
        System.out.println("/contacts - Mostrar contactos");
        System.out.println("/addcontact - <Agregar contacto");
        System.out.println("/conect <ip> <puerto> - Conectar con un contacto");
        System.out.println("/disconnect - Desconectar con un contacto");
        System.out.println("/select <id> - Seleccionar contacto");
        System.out.println("/help - Mostrar comandos");
        System.out.println("/exit - Salir");
    }
}