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
import com.chat.model.Message.MessageType;
import com.chat.model.MessageEntry;
import com.chat.model.User;
import java.io.File;
/**
 *
 * @author wenfi
 */
public class Chat {
    
    /** Propiedades **/
    private Scanner scanner;                       // Entrada de datos
    
    private List<User> contacts;                   // Lista de contactos
    private Map<String, String> contactStatuses;   // Status de cada contacto
    
    private String selectedContactId;              // Contacto seleccionado
    
    public Chat() {
        this.scanner = new Scanner(System.in);
        this.contacts = new CopyOnWriteArrayList<>();
        this.contactStatuses = new ConcurrentHashMap<>();
    }
    
    public void displayChat(List<MessageEntry> messageHistory) {
        System.out.println("\n=== CHAT ===");
        System.out.println("----------------------------------------");
        
        if (messageHistory.size() > 0) {
            for (MessageEntry messageEntry : messageHistory) {
                MessageType messageType = messageEntry.getMessage().getType();
                
                switch(messageType){
                    case Message.MessageType.TEXT:
                        displayTextMessage(messageEntry);
                        break;
                    case Message.MessageType.FILE:
                        displayFileMessage(messageEntry);
                        break;
                    default:
                        return;
                }
            }
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
            String selectedMark = contact.getUserId().equals(selectedContactId) ? ">>" : " ";
            
            System.out.printf("%s %s (%s) [%s]\n", 
                selectedMark,
                contact.getUsername(),
                contact.getUserId(),
                status);
        }
        
        System.out.println("----------------------------------------");
    }
    
    public Message getMessage() {
        System.out.print("\nEscribe tu mensaje: ");
        String content = scanner.nextLine();
        
        return Message.createTextMessage(content);
    }
    
    public void displayTextMessage(MessageEntry messageEntry) {
        String senderUsername = messageEntry.getSender().getUsername();
        if (senderUsername.equals(User.getCurrentUser().getUsername())) {
            senderUsername = "Yo";
        }
            
        System.out.printf("[%s] %s: %s\n", 
            messageEntry.getTimestamp(),
            senderUsername, 
            messageEntry.getMessage().getContent());
    }
    
    public void displaySystemMessage(String message) {
        System.out.println("SISTEMA: " + message);
    }
    
    public void displayFileMessage(MessageEntry messageEntry) {
        Message message = messageEntry.getMessage();
        User sender = messageEntry.getSender();
        
        String fileName = (String) message.getFileData().get("name");
        long fileSize = (long) message.getFileData().get("size");
        
        String formattedSize = formatFileSize(fileSize);
        
        String senderUsername = sender.getUsername();
        
        if (senderUsername.equals(User.getCurrentUser().getUsername())) {
            System.out.printf("\n[%s] Yo: He enviado un archivo: %s (%s)\n",
                messageEntry.getTimestamp(),
                fileName,
                formattedSize);
        } else {
            File downloadsDir = new File("downloads");
            File downloadedFile = new File(downloadsDir, fileName);
            
            System.out.printf("\n[%s] %s: Ha enviado un archivo: %s (%s)\nGuardado en: %s\n",
                messageEntry.getTimestamp(),
                senderUsername,
                fileName,
                formattedSize,
                downloadedFile.getAbsolutePath());
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
        System.out.println("/addcontact - Agregar contacto");
        System.out.println("/connect <ip> <puerto> - Conectar con un contacto");
        System.out.println("/disconnect <id> - Desconectar con un contacto");
        System.out.println("/select <id> - Seleccionar contacto");
        System.out.println("/file <ruta> - Mandar archivo durante un chat");
        System.out.println("/id - Ver mi propio ID");
        System.out.println("/help - Mostrar comandos");
        System.out.println("/exit - Salir");
        System.out.println("");
    }
    
}