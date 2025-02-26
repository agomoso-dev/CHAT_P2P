package com.chat.controller;

import com.chat.model.Message;
import com.chat.network.socket.PeerConnection;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ChatManager {

    /** Propiedades **/
    private static ChatManager instance;                          // Singleton
    private final Map<String, PeerConnection> connections;        // Conexiones activas
    private final int localPort;                                  // Puerto local

    /** Constructor privado Singleton **/
    private ChatManager(int localPort) {
        this.localPort = localPort;
        this.connections = new ConcurrentHashMap<>();
    }

    /** Inicializa y devuelve el Singleton del Chat Manager
     *
     * @return Singleton del Chat Manager
     */
    public static synchronized ChatManager getInstance(int localPort) {
        if (instance == null) {
            instance = new ChatManager(localPort);
        }

        return instance;
    }

    /**
     * Devuelve el Singleton de Chat Manager
     * @return Singleton del Chat Manager
     */
    public static synchronized ChatManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException("El Chat Manager aún no ha sido inicializado. Usa getInstance(puerto) para inicializarlo");
        }

        return instance;
    }

    /**
     * Gestiona una conexión externa
     * @param peerId Id de la conexión Peer
     * @param peerConnection Conexión Peer
     */
    public void handleConnectionFromPeer(String peerId, PeerConnection peerConnection) {

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

    /**
     * Gestiona la entrada de un mensaje
     *
     * @param peerId
     * @param message Mensaje recibido
     */
    public void handleMessageReceived(String peerId, Message message) {

    }

    /**
     * Gestiona la salida de un mensaje
     *
     * @param peerId Id de la conexión Peer
     * @param message Mensaje enviado
     */
    public void handleMessageSent(String peerId, Message message) {

    }

}
