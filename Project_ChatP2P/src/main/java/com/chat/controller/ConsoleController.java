/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.controller;


import com.chat.model.Message;
import com.chat.model.MessageEntry;
import com.chat.model.User;
import com.chat.ui.cui.AddContact;
import com.chat.ui.cui.Chat;
import com.chat.ui.cui.Login;
import com.chat.ui.cui.Register;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author wenfi
 */
public class ConsoleController implements ViewManager {
    private Login loginWindow;
    private Register registerWindow;
    private Chat chatWindow;
    private AddContact addContactWindow;
    private Scanner scanner;
    private boolean isRunning;

    public ConsoleController() {
        initializeWindows();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        showInitialMenu();
    }

    private void initializeWindows() {
        loginWindow = new Login();
        registerWindow = new Register();
        chatWindow = new Chat();
        addContactWindow = new AddContact();
    }
    private void showInitialMenu() {
        clearScreen();
        System.out.println("=== CHAT P2P ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit");
        System.out.print("Seleccione una opción: ");

        String option = scanner.nextLine();
        switch (option) {
            case "1":
                showLoginWindow();
                manageLogin();
                break;
            case "2":
                showRegisterWindow();
                try {
                    manageRegister();
                } catch (IOException e) {
                    showErrorMessage("Error al registrar: " + e.getMessage());
                    showInitialMenu();
                }
                break;
            case "3":
                System.exit(0);
                break;
            default:
                System.out.println("Opción no válida");
                showInitialMenu();
                break;
        }
    }
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    private void handleUserInput() {
        while (isRunning) {
            try {
                String input = scanner.nextLine().trim();
                
                if (input.equals("/menu")) {
                    showInitialMenu();
                } else if (input.startsWith("/")) {
                    handleCommand(input);
                } else {
                    handleMessage(input);
                }
            } catch (Exception e) {
                showErrorMessage("Error: " + e.getMessage());
                showInitialMenu();
            }
        }
    }

    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "/exit":
                isRunning = false;
                System.exit(0);
                break;
            case "/register":
                showRegisterWindow();
                break;
            case "/login":
                showLoginWindow();
                break;
            case "/add":
                showAddContactWindow();
                break;
            case "/select":
                if (parts.length > 1) {
                    ChatManager.getInstance().handleSelectedContact(parts[1]);
                }
                break;
            case "/help":
                chatWindow.showCommands();
                break;
            default:
                System.out.println("Comando no reconocido. Use /help para ver los comandos disponibles.");
        }
    }

    private void handleMessage(String message) {
        if (!message.isEmpty()) {
            Message msg = Message.createTextMessage(message);
            ChatManager.getInstance().handleTextMessageSent(msg);
        }
    }

    private void manageLogin() {
        String userId = loginWindow.getUserId();
        if (userId.isEmpty()) {
            showErrorMessage("Por favor, introduzca un ID válido");
            return;
        }
        ChatManager.getInstance().handleLogin(userId);
    }

    private void manageRegister() throws UnknownHostException, IOException {
        String username = registerWindow.getUsername();
        Integer port = registerWindow.getPort();
        
        if (username.isEmpty() || port == null) {
            showErrorMessage("Por favor, introduzca un nombre de usuario y puerto válidos");
            return;
        }
        
        ChatManager.getInstance().handleRegister(username, port, null);
    }

    private void manageAddNewContact() {
        String ip = addContactWindow.getIp();
        Integer port = addContactWindow.getPort();
        
        if (ip.isEmpty() || port == null) {
            showErrorMessage("Por favor, introduzca una IP y puerto válidos");
            return;
        }
        
        ChatManager.getInstance().handleAddNewContact(ip, port);
    }

    @Override
    public void showMessage(String message) {
        System.out.println("INFO: " + message);
    }

    @Override
    public void showErrorMessage(String message) {
        System.out.println("ERROR: " + message);
    }

    @Override
    public void showChatWindow() {
        chatWindow.setVisible(true);
        chatWindow.showCommands();
    }

    private void showRegisterWindow() {
        loginWindow.setVisible(false);
        registerWindow.setVisible(true);
    }

    private void showLoginWindow() {
        registerWindow.setVisible(false);
        loginWindow.setVisible(true);
    }

    private void showAddContactWindow() {
        chatWindow.setVisible(false);
        addContactWindow.setVisible(true);
    }

    @Override
    public void setContactsList(List<User> contacts) {
        chatWindow.setContactsList(contacts);
    }

    @Override
    public void displayContacts(List<User> contacts) {
        chatWindow.displayContacts();
    }

    @Override
    public void createContactPanel(User contact, String peerId) {
        chatWindow.createPanelContact(contact, peerId);
    }

    @Override
    public void updateContactPanel(String userId, String peerId, boolean connected) {
        chatWindow.updateContactStatus(userId, peerId, connected);
    }

    @Override
    public void setContactPanelAsSelected(String contactId) {
        chatWindow.setContactAsSelected(contactId);
    }

    @Override
    public void displayChat(List<MessageEntry> messageHistory) {
        chatWindow.displayChat(messageHistory);
    }

    @Override
    public void displayTextMessage(MessageEntry messageEntry) {
        chatWindow.displayChat(List.of(messageEntry));
    }

    @Override
    public void displayFileMessage(MessageEntry messageEntry) {
        chatWindow.displayFileMessage(messageEntry);
    }
}