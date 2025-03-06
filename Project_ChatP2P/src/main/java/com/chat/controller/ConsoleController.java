/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.controller;


import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

import com.chat.model.Message;
import com.chat.model.MessageEntry;
import com.chat.model.User;
import com.chat.ui.cui.AddContact;
import com.chat.ui.cui.Chat;
import com.chat.ui.cui.Login;
import com.chat.ui.cui.Register;

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
    public boolean menuCenter=true;

    /**
     * Inicializa el controlador de consola.
     * Crea las ventanas necesarias y configura el scanner para entrada de usuario.
     */
    public ConsoleController() {
        initializeWindows();
        this.scanner = new Scanner(System.in);
        this.isRunning = true;
        
    }
    /**
     * Inicializa las ventanas de la interfaz de consola.
     * Crea instancias de Login, Register, Chat y AddContact.
     */
    private void initializeWindows() {
        loginWindow = new Login();
        registerWindow = new Register();
        chatWindow = new Chat();
        addContactWindow = new AddContact();
    }
    /**
     * Muestra el menú inicial con opciones de login, registro y salida.
     * Maneja la selección del usuario y redirige a la opción correspondiente.
     */
    public void showInitialMenu() {
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
                    menuCenter=false;
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
    /**
     * Procesa la entrada continua del usuario en el chat.
     * Maneja comandos especiales (empiezan con /) y mensajes normales.
     */
    private void handleUserInput() {
        while (isRunning) {
            try {
                String input = scanner.nextLine().trim();
                
                if (input.equals("/menu")) {
                    //showInitialMenu();
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
    /**
     * Procesa los comandos especiales del chat.
     * Comandos disponibles:
     * - /addcontact: Añade un nuevo contacto
     * - /contacts: Muestra la lista de contactos
     * - /connect <ip> <puerto>: Conecta con un peer
     * - /disconnect <userId>: Desconecta de un usuario
     * - /select <id>: Selecciona un chat
     * - /help: Muestra los comandos disponibles
     * - /exit: Cierra la aplicación
     *
     * @param command Comando introducido por el usuario
     */
    private void handleCommand(String command) {
        String[] parts = command.split(" ");
        switch (parts[0]) {
            case "/addcontact":
                showAddContactWindow();
                break;
            case "/contacts":
                chatWindow.displayContacts();
                break;
            case "/conect":
                if (parts.length > 2) {
                    String ip = parts[1];
                    try {
                        int port = Integer.parseInt(parts[2]);
                        if(ChatManager.getInstance().connectToPeer(ip, port)){
                            showMessage("Conectado "+ ip + ":" + port);
                            showMessage("Usa /select para hablar con él");
                        }
                    } catch (NumberFormatException e) {
                        showErrorMessage("El puerto debe ser un número");
                    }
                 }                
                break;
            case "/disconnect":

                break;
            case "/select":
            
                break;
            case "/help":
                showChatWindow();
            case "/exit":
                System.out.println("salida");
                isRunning = false;
                System.exit(0);
                break;
            default:
                System.out.println("Comando no reconocido. Use /help para ver los comandos disponibles.");
        }
    }

    /**
     * Procesa y envía un mensaje de texto al chat actual.
     * 
     * @param message Mensaje a enviar
     */
    private void handleMessage(String message) {
        if (!message.isEmpty()) {
            Message msg = Message.createTextMessage(message);
            ChatManager.getInstance().handleTextMessageSent(msg);
        }
    }
    /**
     * Gestiona el proceso de login del usuario.
     * Obtiene el ID del usuario y lo valida antes de iniciar sesión.
     */
    private void manageLogin() {
        String userId = loginWindow.getUserId();
        if (userId.isEmpty()) {
            showErrorMessage("Por favor, introduzca un ID válido");
            return;
        }
        ChatManager.getInstance().handleLogin(userId);
    }
    /**
     * Gestiona el proceso de registro de un nuevo usuario.
     * Obtiene username y puerto, y los valida antes de registrar.
     * 
     * @throws UnknownHostException Si hay problemas con la dirección IP
     * @throws IOException Si hay problemas de E/S durante el registro
     */
    private void manageRegister() throws UnknownHostException, IOException {
        String username = registerWindow.getUsername();
        Integer port = registerWindow.getPort();
        
        if (username.isEmpty() || port == null) {
            showErrorMessage("Por favor, introduzca un nombre de usuario y puerto válidos");
            return;
        }
        
        ChatManager.getInstance().handleRegister(username, port, null);
    }
    /**
     * Gestiona el proceso de añadir un nuevo contacto.
     * Obtiene IP y puerto del nuevo contacto y los valida.
     */
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

    /**
     * Muestra la ventana de chat y sus comandos disponibles.
     * Inicia el procesamiento de entrada del usuario.
     */
    @Override
    public void showChatWindow() {
        chatWindow.setVisible(true);
        chatWindow.showCommands();
        handleUserInput(); 
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
        manageAddNewContact();
        chatWindow.setVisible(true);  
        chatWindow.showCommands(); 
        handleUserInput(); 
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