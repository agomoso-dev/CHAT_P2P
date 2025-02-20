package com.chat.network;

import com.chat.controller.ChatManager;
import com.chat.model.Message;

import java.io.IOException;
import java.util.List;

public class ClientHandler implements Runnable {

    /** Propiedades **/
    PeerConnection peerConnection;        // Conexión externa

    /** Constructor por parámetros **/
    public ClientHandler(PeerConnection peerConnection) {
        this.peerConnection = peerConnection;
    }

    /**
     * Escucha mientra una conexión esté activa todos los mensajes recibidos
     */
    @Override
    public void run() {
        while (peerConnection.isConnected()) {
            try {
                Message message = peerConnection.receiveMessage();
                handleMessageReceived(message);
            } catch (IOException | ClassNotFoundException e) {
                handleDisconnection();
                break;
            }
        }
    }

    /**
     * Gestiona una entrada de mensaje avisando al controlador de chat y de eventos
     *
     * @param message Mensaje enviado
     */
    private void handleMessageReceived(Message message) {
        String peerId = peerConnection.getPeerId();

        ChatManager.getInstance().handleMessageReceived(peerId, message);
    }

    /**
     * Gestiona una desconexión eliminándola y avisando al controlador de chat y de eventos
     */
    private void handleDisconnection() {
        String peerId = peerConnection.getPeerId();

        peerConnection.close();
        ChatManager.getInstance().handleDisconnection(peerId);

        System.out.println("Cliente desconectado: " + peerId);
    }

    /**
     * Getter
     */
    public PeerConnection getPeerConnection() {
        return peerConnection;
    }

}
