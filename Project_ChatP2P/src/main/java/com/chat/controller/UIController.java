/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.controller;

import com.chat.model.Message;
import com.chat.model.MessageEntry;
import com.chat.model.User;
import com.chat.ui.gui.AddContact;
import com.chat.ui.gui.Chat;
import com.chat.ui.gui.Login;
import com.chat.ui.gui.Register;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
/**
 *
 * @author jairo
 */
public class UIController implements ViewManager {

    /** Propiedades **/
    private Login loginWindow;             // Ventana de Login
    private Register registerWindow;       // Ventana de Registro
    private Chat chatWindow;               // Ventana del Chat
    private AddContact addContactWindow;   // Ventana de Añadir Contacto

    public UIController() {
        initializeWindows();
        initializeListeners();

        loginWindow.setVisible(true);
    }

    /**
     * Inicializa las ventanas *
     */
    private void initializeWindows() {
        loginWindow = new Login();
        registerWindow = new Register();
        chatWindow = new Chat();
        addContactWindow = new AddContact();
    }

    /**
     * Inicializa los listeners para cada ventana *
     */
    private void initializeListeners() {
        initializeLoginWindowListeners();
        initializeRegisterWindowListeners();
        initializeChatWindowListeners();
        initializeAddContactWindowListeners();
    }

    /**
     * Inicializa los listeners de la ventana de Login
     */
    private void initializeLoginWindowListeners() {
        loginWindow.getBtnGoRegister().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showRegisterWindow();
            }
        });

        loginWindow.getBtnExit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });

        loginWindow.getBtnLogin().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manageLogin();
            }
        });
    }
    
    /**
     * Inicializa los listeners de la ventana de Registro
     */
    private void initializeRegisterWindowListeners(){
        registerWindow.getBtnGoLogin().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showLoginWindow();
            }
        });
        
        registerWindow.getBtnExit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        
        registerWindow.getBtnRegister().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    manageRegister();
                } catch (UnknownHostException ex) {
                    Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(UIController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    /** Inicializa los listeners de la ventana de Chat **/
    private void initializeChatWindowListeners() {
        chatWindow.getBtnAddContact().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddContactWindow();
            }
        });
        
        chatWindow.getBtnExit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        
        chatWindow.getBtnSendMessage().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manageMessageSent();
            }
            
        });
        
        chatWindow.getBtnSendFile().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manageFileSent();
            }
            
        });
        
    }
    
    /** Inicializa los listeners de la ventana de Añadir Contactos **/
    private void initializeAddContactWindowListeners() {
        addContactWindow.getBtnGoChat().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showChatWindow();
            }
        });
        
        addContactWindow.getBtnExit().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }
        });
        
        addContactWindow.getBtnAddContact().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                manageAddNewContact();
            }
            
        });
    }
    
    /** ACCIONES **/

    /** Gestiona el flujo de logearse en la app **/
    private void manageLogin() {
        String userId = loginWindow.getUsername();

        if (userId.isEmpty()) {
            showErrorMessage("Por favor, introduzca un ID válido");
            return;
        }
        
        ChatManager.getInstance().handleLogin(userId);
    }
    
    /** Gestiona el flujo de registrarse en la app **/
    private void manageRegister() throws UnknownHostException, IOException {
        Map<String, Object> data = registerWindow.getData();
        String username = (String) data.get("username");
        Integer port = (Integer) data.get("port");
        String avatarPath = (String) data.get("avatarPath");
        
        if (username.isEmpty() || port == null){
            showErrorMessage("Por favor, introduzca un nombre de usuario y puerto válidos");
            return;
        }
        
        
        ChatManager.getInstance().handleRegister(username, port, avatarPath);
    }
    
    /** Gestiona el flujo de enviar un mensaje en la app **/
    private void manageMessageSent() {
        Message message = chatWindow.getMessage();
        chatWindow.clearMessageTextField();
        
        ChatManager.getInstance().handleTextMessageSent(message);
    }
    
    /** Gestiona el flujo de enviar un mensaje en la app **/
    private void manageFileSent() {
        ChatManager.getInstance().handleFileMessageSentGUI();
    }
    
    /** Gestiona el flujo de añadir un nuevo contacto en la app **/
    private void manageAddNewContact() {
        String ip = addContactWindow.getIp();
        Integer port = addContactWindow.getPort();
        
        if (ip.isEmpty() || port == null){
            showErrorMessage("Por favor, introduzca una IP y puerto válidos");
            return;
        }
        
        ChatManager.getInstance().handleAddNewContact(ip, port);
    }

    /** MÉTODOS PARA GESTIONAR LAS VENTANAS **/

    /** Muestra la ventana de registro ocultando la de Login **/
    private void showRegisterWindow(){
        loginWindow.setVisible(false);
        registerWindow.setVisible(true);
    }
    
    /** Muestra la ventana de login ocultando la de Registro **/
    private void showLoginWindow(){
        registerWindow.setVisible(false);
        loginWindow.setVisible(true);
    }
    
    /** Muestra la ventana de Chat cerrando la de Login y Registro **/
    @Override
    public void showChatWindow(){
        loginWindow.dispose();
        registerWindow.dispose();
        addContactWindow.dispose();
        chatWindow.setVisible(true);
    }
    
    /** Muestra la ventana de Añadir Contactos ocultando la de Chat **/
    private void showAddContactWindow() {
        chatWindow.setVisible(false);
        addContactWindow.setVisible(true);
    }

    /** MÉTODOS PARA ACTUALIZAR LA INTERFAZ **/

    /** Actualiza la lista de contactos de la ventana del Chat
     * 
     * @param contacts Lista de contactos del Usuario actual
    **/
    @Override
    public void setContactsList(List<User> contacts){
        chatWindow.setContactsList(contacts);
    }
    
    /** Muestra los contactos en la ventana de Chat
     * 
     * @param contacts Lista de contactos del Usuario actual
    **/
    @Override
    public void displayContacts(List<User> contacts) {
        chatWindow.setContactsList(contacts);
    }
    
    /** Crea un nuevo panel de Contacto
     * @param contact
     * @param peerId 
    **/
    @Override
    public void createContactPanel(User contact, String peerId) {
        chatWindow.createPanelContact(contact, peerId);
    }
    
    /** Actualiza un Panel de Contacto
     * 
     * @param userId ID del contacto
     * @param peerId ID de la conexión Peer del contacto
     * @param connected true si está conectado, falso si no lo está
    **/
    @Override
    public void updateContactPanel(String userId, String peerId, boolean connected) {
        chatWindow.updateContactPanel(userId, peerId, connected);
    }
    
    /** Establece el panel del Peer seleccionado como marcado
     * 
     * @param contactId ID del contacto
    **/
    @Override
    public void setContactPanelAsSelected(String contactId){
        chatWindow.setContactPanelAsSelected(contactId);
    }
    
    /** Muestra un chat en la pantalla de Chat
     * 
     * @param messageHistory Historial de mensajes
     **/
    @Override
    public void displayChat(List<MessageEntry> messageHistory){
        chatWindow.displayChat(messageHistory);
    }
    
    /** Muestra una entrada de mensaje de texto en la pantalla de Chat
     * 
     * @param messageEntry Mensaje recibido
    **/
    @Override
    public void displayTextMessage(MessageEntry messageEntry){
        chatWindow.displayTextMessage(messageEntry);
    }
    
    /** Muestra una entrada de mensaje de archivo en la pantalla de Chat
     * 
     * @param messageEntry Mensaje recibido
    **/
    @Override
    public void displayFileMessage(MessageEntry messageEntry){
        chatWindow.displayFileMessage(messageEntry);
    }

    /** MÉTODOS PARA NOTIFICAR AL USUARIO **/
    
    /**
     * Muestra una ventana con un mensaje informativo
     * 
     * @param message Mensaje a mostrar
     */
    @Override
    public void showMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                null, message, "Información", JOptionPane.INFORMATION_MESSAGE);
        });
        
    }

    /**
     * Muestra una ventana de error con un mensaje
     * 
     * @param message Mensaje a mostrar
     */
    @Override
    public void showErrorMessage(String message) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(
                null, message, "Error", JOptionPane.ERROR_MESSAGE);
        });
    }
    
}
