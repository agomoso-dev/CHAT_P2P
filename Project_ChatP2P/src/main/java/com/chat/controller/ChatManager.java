package com.chat.controller;

import com.chat.model.Avatar;
import com.chat.model.ChatSession;
import com.chat.model.Message;
import static com.chat.model.Message.MessageType.CONNECTION;
import com.chat.model.MessageEntry;
import com.chat.model.User;
import com.chat.network.api.UserClient;
import com.chat.network.socket.ChatClient;
import com.chat.network.socket.ChatServer;
import com.chat.network.socket.PeerConnection;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

public class ChatManager {

    /** Propiedades **/
    private static ChatManager instance;                    // Singleton
    
    private ViewManager viewManager;                        // View Manager
    
    private Map<String, PeerConnection> connections;        // Conexiones de Peers activas
    private Map<String, ChatClient> chatClients;            // Conexiones a Peers activas
    private Map<String, User> contacts;                     // Lista de contactos
    private Map<String, String> contactIdToPeerIdMap;       // Mapa que relaciona el ID de un contacto con el ID de su Peer
    
    private PeerConnection actualPeer;                      // Conexión Peer con la que se está chateando actualmente
    private Map<String, ChatSession> chatSessions;          // Sesiones de Chat activas
    
    private int localPort;                                  // Puerto local

    /** Constructor privado Singleton **/
    private ChatManager() {
        initializeProperties();
    }

    /** Inicializa y devuelve el Singleton del Chat Manager
     *
     * @return Singleton del Chat Manager
     */
    public static synchronized ChatManager getInstance() {
        if (instance == null) {
            instance = new ChatManager();
        }

        return instance;
    }
    
    /** Inicializa las propiedades del ChatManager **/
    private void initializeProperties() {
        this.localPort = 0;
        
        this.connections = new ConcurrentHashMap<>();
        this.chatClients = new ConcurrentHashMap<>();
        this.contacts = new ConcurrentHashMap<>();
        this.contactIdToPeerIdMap = new ConcurrentHashMap<>();
        
        this.chatSessions = new ConcurrentHashMap<>();
        
        switch (OSIdentifier()) {
            case "windows":
                this.viewManager = new UIController();
                break;
            case "linux":
                this.viewManager = new ConsoleController();
                break;
            default:
                throw new UnsupportedOperationException("Sistema operativo no soportado: " + OSIdentifier());
        }
    }
    
    /**
     * Devuelve el ViewManager
     * @return ViewManager
     */
    public ViewManager getViewmanager() { return this.viewManager; }
    
    /**
     * Identifica el sistema operativo actual del usuario
     * 
     * @return String con el nombre del sistema operativo ("window", "linux", "unix", "macOS")
     */
    public String OSIdentifier() {
        String osName = System.getProperty("os.name").toLowerCase();
            
            if (osName.contains("win")) {
                return "windows";
            } else if (osName.contains("nux")) {
                return "linux";
            } else if (osName.contains("nix")) {
                return "unix";
            } else if (osName.contains("mac")) {
                return "macOS";
            } else {
                System.out.println("Sistema operativo desconocido");
                return "desconocido";
            }
            
    }
    
    /**
     * Gestiona el flujo de logearse en la app
     * @param userId ID del usuario que se logea
     */
    public void handleLogin(String userId){
            UserClient.getInstance().getUser(userId, new UserClient.UserCallback<User>() {
            @Override
            public void onSuccess(User result) {
                loadData(result);
            }

            @Override
            public void onError(String errorMessage) {
                viewManager.showErrorMessage("No se ha encontrado ningún usuario con el siguiente ID: " + userId);
                if (viewManager instanceof ConsoleController c) {
                    c.showInitialMenu();
                }
            }
            
        });
    }
    
    /**
     * Gestiona el flujo de registrarse en la app.
     * Verifica si existe un usuario con los mismos datos, si existe notifica al usuario,
     * si no existe, crea un nuevo registro
     * 
     * @param username Nombre del usuario
     * @param port Puerto del usuario
     * @param avatarPath Ruta del avatar del usuario
     */
    public void handleRegister(String username, Integer port, String avatarPath) throws UnknownHostException, IOException {
        String userIp = InetAddress.getLocalHost().getHostAddress();

        try {
            
            Avatar userAvatar = avatarPath != null && !avatarPath.isEmpty() 
                              ? new Avatar(avatarPath, null) 
                              : null;
            
            String userId = userIp + ":" + port;
            
            User user = new User(userId, username, userIp, port, userAvatar);

            UserClient.getInstance().getUser(userId, new UserClient.UserCallback<User>() {
                @Override
                public void onSuccess(User existingUser) {
                    if (existingUser != null) {
                        viewManager.showErrorMessage("Ya existe un usuario con el mismo puerto. Elige otro puerto.");
                        if (viewManager instanceof ConsoleController c){
                            c.showInitialMenu();
                        }
                        
                    } else {
                        registerNewUser(user);
                    }
                }

                @Override
                public void onError(String errorMessage) {
                    registerNewUser(user);
                }
            });
        } catch (IOException e) {
            viewManager.showErrorMessage("Error al crear avatar: " + e.getMessage());
        }
    }
    
    /**
     * Gestiona el flujo de añadir un nuevo contacto.
     * Verifica si ya está agregado, si está, se notifica al usuario, 
     * de lo contrario, se agrega como contacto
     * 
     * @param ip IP de la conexión P2P del contacto
     * @param port Puerto de la conexión P2P del contacto
     */
    public void handleAddNewContact(String ip, Integer port) {
        String contactId = ip + ":" + port;
        
        if (isContact(contactId)) {
            viewManager.showMessage(contactId + " ya está agregado como contacto");
            return;
        }
        
        viewManager.showMessage("Conectando con " + contactId + "...");
        
        new Thread(() -> {
            connectToPeer(ip, port);
        }).start();
    }

    /**
     * Registra un nuevo usuario en Firestore
     * @param user Usuario a registrar
     */
    private void registerNewUser(User user) {
        UserClient.getInstance().addUser(user, new UserClient.UserCallback<User>() {
            @Override
            public void onSuccess(User result) {
                viewManager.showMessage("Has sido registrado con éxito. Tu ID de acceso es: " + user.getUserId());
                loadData(result);
            }

            @Override
            public void onError(String errorMessage) {
                viewManager.showErrorMessage("Error en el registro: " + errorMessage);
            }
        });
    }

    /**
     * Gestiona una conexión externa
     * @param peerId Id de la conexión Peer
     * @param peerConnection Conexión Peer
     */
    public void handleConnectionFromPeer(String peerId, PeerConnection peerConnection) {
        if (connections.containsKey(peerId)) {
            try {
                PeerConnection existingConnection = connections.get(peerId);
                if (existingConnection != null && existingConnection.isConnected()) {
                    existingConnection.close();
                }
            } catch (Exception e) {
                System.out.println("Error al cerrar conexión existente: " + e.getMessage());
            }

            connections.put(peerId, peerConnection);
        } else {
            connections.put(peerId, peerConnection);
        }

        viewManager.showMessage("Conexión externa desde " + peerId);

        try {
            User currentUser = User.getCurrentUser();
            Message userInfo = Message.createUserInfoMessage(currentUser);

            peerConnection.sendMessage(userInfo);
        } catch(IOException ex) {
            System.out.println("Error enviando información de usuario: " + ex.getMessage());
        }
    }

    /**
     * Gestiona el flujo de conectarse a un Peer
     * @param ip IP del Peer
     * @param port Pueto del Peer
     */
    public void handleConnectionToPeer(String ip, Integer port) {
        String contactId = ip + ":" + port;
    
        new Thread(() -> {
            boolean success = connectToPeer(ip, port);

            if (success) {
                if(isContact(contactId)){
                    String existingPeerId = contactIdToPeerIdMap.get(contactId);
                    if (existingPeerId != null) {
                        viewManager.updateContactPanel(contactId, existingPeerId, true);
                    } else {
                        contactIdToPeerIdMap.put(contactId, contactId);
                        viewManager.updateContactPanel(contactId, contactId, true);
                    }
                }
            }
        }).start();
    }
    
    /**
    * Se conecta a un Peer externo
    * 
    * @param ip IP del Peer
    * @param port Puerto del Peer
    * @return True si la conexión fue exitosa, false en caso contrario
    */
    public boolean connectToPeer(String ip, Integer port){
        try {
            ChatClient chatClient = new ChatClient();
            chatClient.connect(ip, port);
            String contactId = ip + ":" + port;

            registerNewConnection(contactId, chatClient);

            viewManager.showMessage("Conectado exitosamente a " + contactId);

            sendInfoUserMessage(chatClient.getPeerConnection());

            return true;
        } catch(IOException e) {
            viewManager.showErrorMessage("No se ha podido realizar la conexión: " + e.getMessage());
            return false;
        }
    }

    /**
     * Gestiona una desconexión externa, avisando al Peer de que nos desconectamos de él
     * y eliminándolo de las listas de conexiones activas
     * 
     * @param peerId Id de la conexión Peer
     */
    public void handleDisconnection(String contactId, String peerId) {
        try {
            String actualPeerId = contactIdToPeerIdMap.getOrDefault(contactId, peerId);

            try {
                ChatClient chatClient = chatClients.get(contactId);
                if (chatClient == null) {
                    chatClient = chatClients.get(actualPeerId);
                }

                if (chatClient != null && chatClient.getPeerConnection() != null) {
                    try {
                        if (chatClient.getPeerConnection().isConnected()) {
                            User currentUser = User.getCurrentUser();
                            Message disconnectMessage = Message.createDisconnectionMessage(currentUser);
                            chatClient.getPeerConnection().sendMessage(disconnectMessage);
                        }
                    } catch (Exception e) {
                        System.out.println("La conexión ya estaba cerrada: " + e.getMessage());
                    }

                    chatClient.disconnect();
                } else {
                    PeerConnection connection = connections.get(actualPeerId);
                    if (connection != null) {
                        try {
                            if (connection.isConnected()) {
                                User currentUser = User.getCurrentUser();
                                Message disconnectMessage = Message.createDisconnectionMessage(currentUser);
                                connection.sendMessage(disconnectMessage);
                            }
                        } catch (Exception e) {
                            System.out.println("La conexión ya estaba cerrada: " + e.getMessage());
                        }

                        connection.close();
                    }
                }
            } catch (Exception e) {
                System.out.println("Error al intentar desconectar: " + e.getMessage());
            } finally {
                connections.remove(actualPeerId);
                chatClients.remove(contactId);
                chatClients.remove(actualPeerId);
                contactIdToPeerIdMap.remove(contactId);
                chatSessions.remove(User.getCurrentUser().getUserId() + ":" + contactId);
                
                if (actualPeer != null && (actualPeer.getPeerId().equals(peerId) || actualPeer.getPeerId().equals(actualPeerId))) {
                    actualPeer = null;
                    viewManager.showMessage("Desconexión realizada. No tienes ningún chat seleccionado");
                    viewManager.setContactPanelAsSelected(null);
                } else {
                    viewManager.showMessage("Desconexión realizada");
                }

                viewManager.updateContactPanel(contactId, actualPeerId, false);
            }
        } catch (Exception ex) {
            viewManager.showErrorMessage("Error al realizar la desconexión con " + contactId);
        }
    }
    
    /**
     * Gestiona la entrada de un mensaje
     *
     * @param peerId
     * @param message Mensaje recibido
     */
    public void handleMessageReceived(String peerId, Message message) {
        switch (message.getType()) {
            case USER_INFO:
                handleUserInfoMessageReceived(peerId, message);
                break;
            case CONNECTION:
                handleConnectionMessageReceived(peerId, message);
                break;
            case DISCONNECTION:
                handleDisconnectionMessageReceived(peerId, message);
                break;
            case TEXT:
                handleTextMessageReceived(peerId, message);
                break;
            case FILE:
                handleFileMessageReceived(peerId, message);
                break;
            case SYSTEM:
                viewManager.showMessage("Mensaje del sistema: " + message.getContent());
                break;
        }
    }
    
    /**
     * Gestiona la recepción de un mensaje de información de usuario.
     * Se añade como contacto en Firestore, se notifica al usuario y se
     * actualiza la interfaz de la App
     * 
     * @param peerId ID del peer que envió el mensaje
     * @param message Mensaje recibido con información de usuario
     */
    private void handleUserInfoMessageReceived(String peerId, Message message) {
        User contactUser = message.getUserData();
        String contactId = contactUser.getUserId();
        String currentUserId = User.getCurrentUser().getUserId();
        
        contactIdToPeerIdMap.put(contactId, peerId);
        chatSessions.put(currentUserId + ":" + contactId, getOrCreateChatSession(contactId));

        if (isContact(contactId)){
            viewManager.updateContactPanel(contactId, peerId, true);
            return;
        }

        UserClient.getInstance().addContact(
            User.getCurrentUser().getUserId(), 
            contactId, 
            new UserClient.UserCallback<User>() {
                @Override
                public void onSuccess(User result) {
                    contacts.put(contactId, result);

                    
                    if (!chatClients.containsKey(peerId)) {
                        PeerConnection conn = connections.get(peerId);
                        if (conn != null) {
                            try {
                                User currentUser = User.getCurrentUser();
                                Message userInfoMessage = Message.createUserInfoMessage(currentUser);
                                
                                conn.sendMessage(userInfoMessage);
                            } catch (IOException e) {
                                viewManager.showErrorMessage("Error al enviar información de usuario: " + e.getMessage());
                            }
                        }
                    }
                    
                    viewManager.createContactPanel(result, peerId);
                    viewManager.showMessage("Contacto añadido: " + result.getUsername());
                }

                @Override
                public void onError(String errorMessage) {
                    viewManager.showErrorMessage("Error al añadir contacto: " + errorMessage);
                }
            }
        );
    }
    
    /**
     * Gestiona la recepción de un mensaje de conexión externa, actualizando la interfaz
     * 
     * @param peerId ID del Peer que envía el mensaje
     * @param message Mensaje recibido
     */
    private void handleConnectionMessageReceived(String peerId, Message message) {
        User contactUser = message.getUserData();
        
        viewManager.updateContactPanel(contactUser.getUserId(), peerId, true);
        viewManager.showMessage(contactUser.getUsername() + " se acaba de conectar. Conexión establecida");
    }
    
    /**
     * Gestiona la recepción de un mensaje de desconexión externa, actualizando la interfaz
     * 
     * @param peerId ID del Peer que envía el mensaje
     * @param message Mensaje recibido
     */
    private void handleDisconnectionMessageReceived(String peerId, Message message) {
        User contactUser = message.getUserData();
        String contactId = contactUser.getUserId();

        connections.remove(peerId);

        ChatClient chatClient = chatClients.get(peerId);
        if (chatClient != null) {
            chatClients.remove(peerId);
        }

        chatClient = chatClients.get(contactId);
        if (chatClient != null) {
            chatClients.remove(contactId);
        }
        
        String chatSessionId = User.getCurrentUser().getUserId() + ":" + contactId;
        ChatSession chatSession = chatSessions.get(chatSessionId);
        if (chatSession != null){
            chatSessions.remove(chatSessionId);
        }

        contactIdToPeerIdMap.remove(contactId);
        chatSessions.remove(User.getCurrentUser().getUserId() + ":" + contactId);
        
        viewManager.updateContactPanel(contactId, peerId, false);
        if (actualPeer != null && actualPeer.getPeerId().equals(peerId)){
            actualPeer = null;
            viewManager.showMessage(contactUser.getUsername() + " se ha desconectado. No tienes ningun chat seleccionado");
            viewManager.setContactPanelAsSelected(null);
        } else {
            viewManager.showMessage(contactUser.getUsername() + " se ha desconectado.");
        }
    }
    
    /**
     * Gestiona la recepción de un mensaje de texto, actualizando la interfaz
     * 
     * @param message Mensaje recibido
     * @param peerId ID del Peer que ha enviuado el mensaje
     */
    private void handleTextMessageReceived(String peerId, Message message) {
        User contact = getContactByPeerId(peerId);
        
        ChatSession chatSession = getChatSessionByPeerId(peerId);
        MessageEntry messageEntry = new MessageEntry(contact, message);
        chatSession.addMessage(contact, messageEntry);
        
        PeerConnection peerConnection = connections.get(peerId);
        if (actualPeer != null && peerConnection.equals(actualPeer)) {
            if (viewManager instanceof ConsoleController c) {
                List<MessageEntry> messageHistory = chatSession.getMessageHistory();
                viewManager.displayChat(messageHistory);
            } else {
                viewManager.displayTextMessage(messageEntry);
            }
        }
    }
    
    /**
     * Gestiona la recepción de un mensaje de archivo, actualizando la interfaz
     * 
     * @param message Mensaje recibido
     * @param peerId ID del Peer que ha enviuado el mensaje
     */
    private void handleFileMessageReceived(String peerId, Message message) {
        User contact = getContactByPeerId(peerId);

        byte[] fileBytes = message.getFileContent();
        String fileName = (String) message.getFileData().get("name");
        if (fileName == null) {
            fileName = "archivo_desconocido_" + System.currentTimeMillis();
        }

        File downloadsDir = new File("downloads");
        if (!downloadsDir.exists()) {
            downloadsDir.mkdir();
        }

        File outputFile = new File(downloadsDir, fileName);

        int counter = 1;
        while (outputFile.exists()) {
            int lastDot = fileName.lastIndexOf('.');
            String name = fileName;
            String extension = "";

            if (lastDot > 0) {
                name = fileName.substring(0, lastDot);
                extension = fileName.substring(lastDot);
            }

            outputFile = new File(downloadsDir, name + "_" + counter + extension);
            counter++;
        }

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(fileBytes);
        } catch (Exception e) {
            viewManager.showErrorMessage("Error al guardar el archivo: " + e.getMessage());
            e.printStackTrace();
        }

        ChatSession chatSession = getChatSessionByPeerId(peerId);
        MessageEntry messageEntry = new MessageEntry(contact, message);
        chatSession.addMessage(contact, messageEntry);

        PeerConnection peerConnection = connections.get(peerId);
        if (actualPeer != null && peerConnection.equals(actualPeer)) {
            if (viewManager instanceof ConsoleController c) {
                List<MessageEntry> messageHistory = chatSession.getMessageHistory();
                viewManager.displayChat(messageHistory);
            } else {
                viewManager.displayFileMessage(messageEntry);
            }
        }
    }
    
    /**
     * Establece el contacto actual con el que se va a chatear y se actualiza la interfaz con el historial de mensajes.
     * 
     * @param peerId ID del Peer actual
     */
    public void handleSelectedContact(String peerId){
        setActualPeerConnection(peerId);
        
        String contactId = getContactByPeerId(peerId).getUserId();
        String sessionId = User.getCurrentUser().getUserId() + ":" + contactId;
        
        ChatSession chatSession = chatSessions.get(sessionId);
        List<MessageEntry> messageHistory = chatSession.getMessageHistory();
        
        viewManager.setContactPanelAsSelected(contactId);
        viewManager.displayChat(messageHistory);
    }

    /**
     * Gestiona el envío de un mensaje de tipo Texto
     *
     * @param message Mensaje a enviar
     */
    public void handleTextMessageSent(Message message) {
        if (actualPeer == null) {
            viewManager.showErrorMessage("No hay un contacto seleccionado para enviar el mensaje");
            return;
        }

        try {
            actualPeer.sendMessage(message);
            
            User contact = getContactByPeerId(actualPeer.getPeerId());
            if (contact != null) {
                ChatSession chatSession = getOrCreateChatSession(contact.getUserId());
                MessageEntry messageEntry = new MessageEntry(User.getCurrentUser(), message);
                
                chatSession.addMessage(User.getCurrentUser(), messageEntry);
                
                if (viewManager instanceof ConsoleController c) {
                    List<MessageEntry> messageHistory = chatSession.getMessageHistory();
                    viewManager.displayChat(messageHistory);
                } else {
                    viewManager.displayTextMessage(new MessageEntry(User.getCurrentUser(), message));
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(ChatManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Gestiona la creación y envío de un mensaje de tipo File para la interfaz gráfica
     */
    public void handleFileMessageSentGUI() {
        if (actualPeer == null) {
            viewManager.showErrorMessage("No hay un contacto seleccionado para enviar el mensaje");
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(null);

        if (result == JFileChooser.APPROVE_OPTION) {
            final File selectedFile = fileChooser.getSelectedFile();
            final PeerConnection peerToSend = actualPeer;

            viewManager.showMessage("Enviando archivo: " + selectedFile.getName() + "...");

            new Thread(() -> {
                try {
                    Message fileMessage = Message.createFileMessage(selectedFile);

                    peerToSend.sendMessage(fileMessage);
                    User contact = getContactByPeerId(peerToSend.getPeerId());
                    if (contact != null) {
                        ChatSession chatSession = getOrCreateChatSession(contact.getUserId());
                        MessageEntry messageEntry = new MessageEntry(User.getCurrentUser(), fileMessage);
                        chatSession.addMessage(User.getCurrentUser(), messageEntry);
                    }

                    SwingUtilities.invokeLater(() -> {
                        viewManager.displayFileMessage(new MessageEntry(User.getCurrentUser(), fileMessage));
                        viewManager.showMessage("Archivo enviado: " + selectedFile.getName());
                    });
                } catch (IOException ex) {
                    SwingUtilities.invokeLater(() -> {
                        viewManager.showErrorMessage("Error al enviar el archivo: " + ex.getMessage());
                    });
                    Logger.getLogger(ChatManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }).start();
        }
    }

    /**
     * Gestiona la creación y envío de un mensaje de tipo File para la interfaz de consola
     * 
     * @param filePath Ruta del archivo a enviar
     */
    public void handleFileMessageSentCUI(String filePath) {
        if (actualPeer == null) {
            viewManager.showErrorMessage("No hay un contacto seleccionado para enviar el mensaje");
            return;
        }

        File selectedFile = new File(filePath);
        if (!selectedFile.exists()){
            viewManager.showErrorMessage("Por favor, introduce una ruta válida");
            return;
        }

        viewManager.showMessage("Enviando archivo: " + selectedFile.getName() + "...");

        final PeerConnection peerToSend = actualPeer;

        new Thread(() -> {
            try {
                Message fileMessage = Message.createFileMessage(selectedFile);

                peerToSend.sendMessage(fileMessage);
                User contact = getContactByPeerId(peerToSend.getPeerId());
                if (contact != null) {
                    ChatSession chatSession = getOrCreateChatSession(contact.getUserId());
                    MessageEntry messageEntry = new MessageEntry(User.getCurrentUser(), fileMessage);
                    chatSession.addMessage(User.getCurrentUser(), messageEntry);

                    List<MessageEntry> messageHistory = chatSession.getMessageHistory();
                    
                    viewManager.displayChat(messageHistory);
                    viewManager.showMessage("Archivo enviado: " + selectedFile.getName());
                }
            } catch (IOException ex) {
                viewManager.showErrorMessage("Error al enviar el archivo: " + ex.getMessage());
                Logger.getLogger(ChatManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }).start();
    }
    
    /**
     * Actualiza todos los datos necesarios antes de iniciar la app
     * @param user Usuario obtenido de Firestore
     */
    private void loadData(User user) {
        localPort = user.getPort();
        User.setCurrentUser(user);

        UserClient.getInstance().getContacts(user.getUserId(), new UserClient.UserCallback<List<User>>() {
            @Override
            public void onSuccess(List<User> result) {
                setContacts(result);
                viewManager.setContactsList(result);
                
                startApplication();
            }

            @Override
            public void onError(String errorMsg) {
                startApplication();
            }
        });
    }
    
    /** Inicializa la app **/
    private void startApplication(){
        ChatServer.getInstance(localPort).startServer();
        
        if(viewManager instanceof ConsoleController c){
            c.menuCenter=false;
        }
        
        viewManager.showChatWindow();
    }
    
    /**
     * Verifica si un usuario ya está agregado como contacto
     * 
     * @param peerId ID del contacto
     * @return True si está agregado
     */
    private boolean isContact(String userId) {
        return contacts.get(userId) != null;
    }
    
    /**
     * Agrega los contactos al mapa de contactos
     * @param users Lista de contactos
     */
    private void setContacts(List<User> users){
        for (User user : users) {
            contacts.put(user.getUserId(), user);
        }
    }

    /**
     * Elimina una conexión Peer del registro de conexiones activas
     * 
     * @param contactId ID del contacto
     * @param peerId ID del Peer externo
     */
    public void removePeer(String contactId, String peerId) {
        ChatClient chatClient = chatClients.get(peerId);
        if (chatClient != null) {
            User currentUser = User.getCurrentUser();
            Message userInfoMessage = Message.createDisconnectionMessage(currentUser);
                
            try {
                chatClient.getPeerConnection().sendMessage(userInfoMessage);
            } catch (IOException ex) {
                Logger.getLogger(ChatManager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            chatClient.disconnect();
        } else {
            for (Map.Entry<String, ChatClient> entry : chatClients.entrySet()) {
                if (entry.getKey().contains(contactId.split(":")[0])) {
                    entry.getValue().disconnect();
                    chatClients.remove(entry.getKey());
                    break;
                }
            }
        }

        connections.remove(peerId);
    }
    
    /**
     * Envía la información del Usuario actual a un cliente
     * @param client Cliente al que enviar la información
     */
    private void sendInfoUserMessage(PeerConnection peerConnection) {
        User currentUser = User.getCurrentUser();
        Message userInfoMessage = Message.createUserInfoMessage(currentUser);
                
        try {
            peerConnection.sendMessage(userInfoMessage);
        } catch (IOException ex) {
            Logger.getLogger(ChatManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Busca un contacto por su ID del Peer
     * 
     * @param peerId ID de la conexión Peer
     * @return Contacto si existe, null en caso contarrio
     */
    private User getContactByPeerId(String peerId){
        if (peerId == null) return null;
        
        String contactId = null;

        if (contactIdToPeerIdMap.size() == 0) {
            return null;
        }

        for (Map.Entry<String, String> entry : contactIdToPeerIdMap.entrySet()) {
            if (entry.getValue().equals(peerId)) {
                contactId = entry.getKey();
                break; 
            }
        }
        
        return contactId != null ? contacts.get(contactId) : null;
    }
    
    /**
     * Busca un contacto por su ID del Peer
     * 
     * @param contactId ID de la conexión Peer
     * @return ID de la conexión Peer si existe, null en caso contarrio
     */
    public String getPeerIdByContactId(String contactId){
        if (contactId == null) return null;

        if (contactIdToPeerIdMap.size() == 0) {
            return null;
        }
        
        String peerId = contactIdToPeerIdMap.get(contactId);
        
        return peerId != null ? peerId : null;
    }
    
    /**
     * Gestiona una desconexión abrupta por parte de un Peer.
     * Establece la conexión Peer como desconectada (si se desconecta sin notificar)
     * 
     * @param peerId ID de la conexión Peer
     */
    public void handleUnexpectedDisconnection(String peerId) {
        User contactUser = getContactByPeerId(peerId);
        if (contactUser == null) return;
        
        String contactId = contactUser.getUserId();
        
        connections.remove(peerId);

        ChatClient chatClient = chatClients.get(peerId);
        if (chatClient != null) {
            chatClients.remove(peerId);
        }

        chatClient = chatClients.get(contactId);
        if (chatClient != null) {
            chatClients.remove(contactId);
        }

        String chatSessionId = User.getCurrentUser().getUserId() + ":" + contactId;
        contactIdToPeerIdMap.remove(contactId);
        chatSessions.remove(chatSessionId);

        viewManager.updateContactPanel(contactId, peerId, false);
        
        if (actualPeer != null && actualPeer.getPeerId().equals(peerId)){
            actualPeer = null;
            viewManager.showMessage(contactUser.getUsername() + " se ha desconectado. No tienes ningun chat seleccionado");
            viewManager.setContactPanelAsSelected(null);
        } else {
            viewManager.showMessage(contactUser.getUsername() + " se ha desconectado.");
        }
    }
     
    /**
     * Registra una nueva conexión en los mapas de conexiones.
     * 
     * @param contactId ID del Contacto a registrar
     * @param chatClient ChatClient creado con el Contacto
     */
    private synchronized void registerNewConnection(String contactId, ChatClient chatClient){
        String currentUserId = User.getCurrentUser().getUserId();
        String peerId = chatClient.getPeerConnection().getPeerId();
        
        connections.put(peerId, chatClient.getPeerConnection());
        chatClients.put(peerId, chatClient);
        contactIdToPeerIdMap.put(contactId, peerId);
        
        chatSessions.put(currentUserId + ":" + contactId, new ChatSession(contactId));
    }
    
    /**
     * Devuelve una Sesión de Chat por su ID
     * 
     * @param sessionId ID de la Sesión de Chat
     * @return ChatSession registrada con ese ID
     */
    private ChatSession getChatSessionById(String sessionId){
        return chatSessions.get(sessionId);
    }
    
    /**
     * Devuelve una Sesión de Chat por el ID de un Peer
     * 
     * @param peerId ID del Peer
     * @return ChatSession registrada para ese Peer
     */
    private ChatSession getChatSessionByPeerId(String peerId){
        User peerContact = getContactByPeerId(peerId);
        ChatSession chatSession = getOrCreateChatSession(peerContact.getUserId());
        
        return chatSession;
    }
    
    /**
     * Crea una Sesión de Chat si no hay ninguna registrada con el mismo ID y la devuelve.
     * 
     * @param contactId ID del contacto con el que se crea la Sesión de Chat
     * @return Sesión de Chat
     */
    private ChatSession getOrCreateChatSession(String contactId) {
        String sessionId = User.getCurrentUser().getUserId() + ":" + contactId;

        if (!chatSessions.containsKey(sessionId)) {
            chatSessions.put(sessionId, new ChatSession(contactId));
        }

        return chatSessions.get(sessionId);
    }
    
    /**
     * Establece el Peer actual con el que se está chateando
     * @param peerId ID del Peer actual
     */
    private void setActualPeerConnection(String peerId){
        PeerConnection connection = connections.get(peerId);
        
        if (connection != null && connection.isConnected()) {
            actualPeer = connection;
        } else {
            viewManager.showErrorMessage("No hay una conexión activa con este contacto");
            actualPeer = null;
        }
    }

    public void getViewManager() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
