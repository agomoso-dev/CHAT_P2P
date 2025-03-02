package com.chat.controller;

import com.chat.model.Avatar;
import com.chat.model.Message;
import static com.chat.model.Message.MessageType.CONNECTION;
import com.chat.model.User;
import com.chat.network.api.UserClient;
import com.chat.network.socket.ChatClient;
import com.chat.network.socket.ChatServer;
import com.chat.network.socket.PeerConnection;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ChatManager {

    /** Propiedades **/
    private static ChatManager instance;                    // Singleton
    
    private UIController uiController;                      // UI Manager
    
    private Map<String, PeerConnection> connections;        // Conexiones de Peers activas
    private Map<String, ChatClient> chatClients;            // Conexiones a Peers activas
    private Map<String, User> contacts;                     // Lista de contactos
    private Map<String, String> contactIdToPeerIdMap;       // Mapa que relaciona el ID de un contacto con el ID de su Peer
    
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
        
        this.uiController = new UIController();
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
                uiController.showErrorMessage("No se ha encontrado ningún usuario con el siguiente ID: " + userId);
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
                        uiController.showErrorMessage("Ya existe un usuario con el mismo puerto. Elige otro puerto.");
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
            uiController.showErrorMessage("Error al crear avatar: " + e.getMessage());
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
            uiController.showMessage(contactId + " ya está agregado como contacto");
            return;
        }
        
        uiController.showMessage("Conectando con " + contactId + "...");
        
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
                uiController.showMessage("Has sido registrado con éxito. Tu ID de acceso es: " + user.getUserId());
                loadData(result);
            }

            @Override
            public void onError(String errorMessage) {
                uiController.showErrorMessage("Error en el registro: " + errorMessage);
            }
        });
    }

    /**
     * Gestiona una conexión externa
     * @param peerId Id de la conexión Peer
     * @param peerConnection Conexión Peer
     */
    public void handleConnectionFromPeer(String peerId, PeerConnection peerConnection) {
        connections.put(peerId, peerConnection);
        
        uiController.showMessage("Conexión externa desde " + peerId);
        
        try {
            User currentUser = User.getCurrentUser();
            Message userInfo = Message.createUserInfoMessage(currentUser);
            
            peerConnection.sendMessage(userInfo);
        } catch(IOException ex) {
            //System.out.println("Error enviando información de usuario: " + ex.getMessage());
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

            if (success && isContact(contactId)) {
                String existingPeerId = contactIdToPeerIdMap.get(contactId);
                if (existingPeerId == null) {
                    uiController.updateContactPanel(contactId, contactId, true);
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
    private boolean connectToPeer(String ip, Integer port){
       try {
            ChatClient chatClient = new ChatClient();
            chatClient.connect(ip, port);

            String contactId = ip + ":" + port;
            String peerId = chatClient.getPeerConnection().getPeerId();
            
            connections.put(peerId, chatClient.getPeerConnection());
            chatClients.put(peerId, chatClient);
            contactIdToPeerIdMap.put(contactId, peerId);
            
            uiController.showMessage("Conectado exitosamente a " + contactId);
            
            sendInfoUserMessage(chatClient.getPeerConnection());
            
            return true;
       } catch(IOException e) {
           uiController.showErrorMessage("No se ha podido realizar la conexión");
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

                uiController.showMessage("Desconexión realizada");
                uiController.updateContactPanel(contactId, actualPeerId, false);
            }
        } catch (Exception ex) {
            uiController.showErrorMessage("Error al realizar la desconexión con " + contactId);
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
                uiController.onMessageReceived(peerId, message);
                break;
            case FILE:
                //handleFileMessage(peerId, message);
                break;
            case SYSTEM:
                uiController.showMessage("Mensaje del sistema: " + message.getContent());
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
        
        contactIdToPeerIdMap.put(contactId, peerId);
        System.out.println("get" + contactIdToPeerIdMap.get(contactId));
        
        if (isContact(contactId)){
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
                                uiController.showErrorMessage("Error al enviar información de usuario: " + e.getMessage());
                            }
                        }
                    }
                    
                    uiController.createContactPanel(result, peerId);
                    uiController.showMessage("Contacto añadido: " + result.getUsername());
                }

                @Override
                public void onError(String errorMessage) {
                    uiController.showErrorMessage("Error al añadir contacto: " + errorMessage);
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
        
        uiController.updateContactPanel(contactUser.getUserId(), peerId, true);
        uiController.showMessage(contactUser.getUsername() + " se acaba de conectar. Conexión establecida");
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

        contactIdToPeerIdMap.remove(contactId);
        
        System.out.println("SIZE");
            System.out.println(connections.size());
            System.out.println(chatClients.size());
            System.out.println(contactIdToPeerIdMap.size());
            System.out.println(contacts.size());

        uiController.updateContactPanel(contactId, peerId, false);
        uiController.showMessage(contactUser.getUsername() + " se ha desconectado.");
    }

    /**
     * Gestiona la salida de un mensaje
     *
     * @param peerId Id de la conexión Peer
     * @param message Mensaje enviado
     */
    public void handleMessageSent(String peerId, Message message) {

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
                uiController.setContactsList(result);
                
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
        uiController.showChatWindow();
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
    private void removePeer(String contactId, String peerId) {
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
    private User findContactByPeerId(String peerId){
        System.out.println(contactIdToPeerIdMap.size());
        System.out.println("PEE" + peerId);
        String contactId = null;
        
        for (Map.Entry<String, String> entry : contactIdToPeerIdMap.entrySet()) {
            if (entry.getValue().equals(peerId)) {
                contactId = entry.getKey();
                break; 
            }
        }
        
        if (contactId == null) return null;
        
        return contacts.get(contactId);
    }
    
    /**
     * Gestiona una desconexión abrupta por parte de un Peer.
     * Establece la conexión Peer como desconectada (si se desconecta sin notificar)
     * 
     * @param peerId ID de la conexión Peer
     */
    public void handleUnexpectedDisconnection(String peerId) {
        User contactUser = findContactByPeerId(peerId);
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

        contactIdToPeerIdMap.remove(contactId);
        
        System.out.println("SIZE");
            System.out.println(connections.size());
            System.out.println(chatClients.size());
            System.out.println(contactIdToPeerIdMap.size());
            System.out.println(contacts.size());

        uiController.updateContactPanel(contactId, peerId, false);
        uiController.showMessage(contactUser.getUsername() + " se ha desconectado.");
    }
    
}
