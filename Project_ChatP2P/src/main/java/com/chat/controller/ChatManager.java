package com.chat.controller;

import com.chat.model.Avatar;
import com.chat.model.Message;
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

public class ChatManager {

    /** Propiedades **/
    private static ChatManager instance;                          // Singleton
    
    private final UIController uiController;                      // UI Manager
    
    private final Map<String, PeerConnection> connections;        // Conexiones activas
    private int localPort;                                        // Puerto local

    /** Constructor privado Singleton **/
    private ChatManager() {
        this.localPort = 0;
        this.connections = new ConcurrentHashMap<>();
        
        this.uiController = new UIController();
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
     * Intenta conectarse al P2P del contacto, si se realiza con éxito
     * se añade como contacto en Firestore, de lo contrario, se le 
     * notifica al usuario
     * 
     * @param ip IP de la conexión P2P del contacto
     * @param port Puerto de la conexión P2P del contacto
     */
    public void handleAddNewContact(String ip, Integer port) {
        System.out.println("Iniciando conexión a " + ip + ":" + port);
        uiController.showMessage("Conectando con " + ip + ":" + port + "...");

        new Thread(() -> {
            try {
                ChatClient chatClient = new ChatClient();

                System.out.println("Thread: Intentando conectar...");
                chatClient.connect(ip, port);

                System.out.println("Thread: Conexión exitosa!");
                uiController.showMessage("Conectado exitosamente a " + ip + ":" + port);

                System.out.println("Thread: Preparando mensaje USER_INFO...");
                User currentUser = User.getCurrentUser();
                Message userInfoMessage = Message.createUserInfoMessage(currentUser);
                
                System.out.println("Thread: Enviando mensaje...");
                chatClient.sendMessage(userInfoMessage);
                System.out.println("Thread: Mensaje enviado");

            } catch (IOException e) {
                System.out.println("Thread: Error durante la conexión: " + e.getMessage());
                e.printStackTrace();
                uiController.showErrorMessage("Error al conectar con " + ip + ":" + port + ": " + e.getMessage());
            }
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
            System.out.println("Error enviando información de usuario: " + ex.getMessage());
        }
    }


    /**
     * Gestiona una conexión a otro Peer
     * @param peerId Id de la conexión Peer
     * @param peerConnection Conexión Peer
     */
    public void handleConnectionToPeer(String peerId, PeerConnection peerConnection) {

    }

    /**
     * Gestiona una desconexión externa
     * @param peerId Id de la conexión Peer
     */
    public void handleDisconnection(String peerId) {

    }
    
    int contador = 1;
    
    /**
     * Gestiona la entrada de un mensaje
     *
     * @param peerId
     * @param message Mensaje recibido
     */
    public void handleMessageReceived(String peerId, Message message) {
        switch (message.getType()) {
            case USER_INFO:
                contador++;
                handleUserInfoMessageReceived(peerId, message);
                break;
            case TEXT:
                // Manejar mensaje de texto normal
                uiController.onMessageReceived(peerId, message);
                break;
            case FILE:
                // Manejar recepción de archivo
                //handleFileMessage(peerId, message);
                break;
            case SYSTEM:
                // Manejar mensaje de sistema
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
    public void handleUserInfoMessageReceived(String peerId, Message message) {
        User contactUser = message.getUserData();

        UserClient.getInstance().addContact(
            User.getCurrentUser().getUserId(), 
            contactUser.getUserId(), 
            new UserClient.UserCallback<User>() {
                @Override
                public void onSuccess(User result) {
                    // 2. Actualizar la lista de contactos en la UI
                    //uiController.addOrUpdateContact(result);
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
                uiController.setContactsList(result);
                
                startApplication();
            }

            @Override
            public void onError(String errorMsg) {
                System.out.println("Error al cargar contactos: " + errorMsg);
                startApplication();
            }
        });
    }
    
    /** Inicializa la app **/
    private void startApplication(){
        ChatServer.getInstance(localPort).startServer();
        uiController.showChatWindow();
    }

}
