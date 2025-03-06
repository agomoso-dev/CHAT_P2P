package com.chat.network.socket;

import com.chat.controller.ChatManager;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import static com.chat.utils.Constants.MAX_PORT;
import static com.chat.utils.Constants.MIN_PORT;

public class ChatServer {

    /** Propiedades **/
    private static ChatServer instance;                              // Singleton del servidor
    private ServerSocket serverSocket;                               // Socket del servidor
    private final int port;                                          // Puerto del servidor
    private boolean running;                                         // Estado del servidor

    /** Constructor privado Singleton **/
    private ChatServer(int port) {
        if (!validate(port)) {
            System.out.println("Puerto fuera del rango válido");
            this.port = -1;
            this.running = false;
            return;
            //throw new IllegalArgumentException("Puerto fuera del rango válido");
        }

        if (!available(port)) {
            System.out.println("Puerto no válido");
            this.port = -1;
            this.running = false;
            return;
            //throw new IllegalArgumentException("Puerto no válido");
        }

        this.port = port;
        this.running = false;
    }

    /**
     * Inicializa y devuelve el Singleton del servidor
     * @param port Puerto del servidor
     * @return Singleton del servidor
     */
    public static synchronized ChatServer getInstance(int port) {
        if (instance == null) {
            instance = new ChatServer(port);
        }
        return instance;
    }

    /** Devuelve el Singleton del servidor
     *
     * @return Singleton del servidor
     */
    public static synchronized ChatServer getInstance() {
        if (instance == null) {
            throw new IllegalStateException("El servidor aún no ha sido inicializado");
        }
        return instance;
    }

    /**
     * Inicia el servidor
     */
    public void startServer() {
        running = true;

        new Thread(() -> {
            try {
                serverSocket = new ServerSocket(port);
                System.out.println("Servidor iniciado en el puerto " + port);

                while(running) {
                    listenForConnections();
                }

            } catch (IOException ex) {
                System.out.println("No se ha podido iniciar el servidor: " + ex.getMessage());
            }
        }).start();
    }

    /**
     * Detiene el servidor
     */
    public void stopServer() {
        try {
            running = false;

            if (serverSocket != null) {
                serverSocket.close();
                System.out.println("Servidor detenido");
            }
        } catch (IOException ex) {
            System.out.println("Error cerrando el servidor " + ex.getMessage());
        }
    }

    /**
     * Comprueba si un puerto está disponible
     * @param port Puerto a comprobar
     * @return True si está disponible
     */
    private boolean available(int port) {
        try (ServerSocket ss = new ServerSocket(port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Valida si un puerto está en el rango válido
     * @param port Puerto a validar
     * @return True si está en el rango válido
     */
    private boolean validate(int port) {
        return port >= MIN_PORT && port <= MAX_PORT;
    }

    /**
     * Escucha conexiones al servidor
     */
    private void listenForConnections() {
        try {
            if (serverSocket.isClosed()) return;

            Socket socket = serverSocket.accept();

            PeerConnection peerConnection = new PeerConnection(socket);
            handleConnection(peerConnection);
            
            new Thread(new ClientHandler(peerConnection)).start();
        } catch (IOException ex) {
            System.out.println("Error aceptando conexión " + ex.getMessage());
        }
    }

    /**
     * Gestiona una conexión externa, registrándola y avisando al controlador de chat
     * @param peerConnection Conexión externa
     */
    private void handleConnection(PeerConnection peerConnection) {
        String peerId = peerConnection.getPeerId();
        
        ChatManager.getInstance().handleConnectionFromPeer(peerId, peerConnection);
    }

    /**
     * Getters
     */
    public int getPort() {
        return port;
    }

    public boolean isRunning() {
        return running;
    }

    /** PRUEBA **/
    public static void main(String[] args) throws IOException, InterruptedException {
        ChatServer server = ChatServer.getInstance(5000);
        server.startServer();

        ChatClient chatClient = new ChatClient();
        chatClient.connect("localhost", 5000);

    }

}