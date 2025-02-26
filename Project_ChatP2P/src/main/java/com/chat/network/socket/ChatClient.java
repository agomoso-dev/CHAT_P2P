package com.chat.network.socket;

import com.chat.controller.ChatManager;
import com.chat.model.Message;

import java.io.IOException;
import java.net.Socket;

public class ChatClient {

    /** Propiedades **/
    PeerConnection peerConnection;    // Conexión externa

    /** Constructor por parámetros **/
    public ChatClient() { }

    /**
     * Inicia una conexión con otro peer
     * @param ip IP del peer al que se va a conectar
     * @param port Puerto del peer al que se va a conectar
     * @throws IOException si hay error al conectar
     */
    public void connect(String ip, int port) throws IOException {
        Socket socket = new Socket(ip, port);
        peerConnection = new PeerConnection(socket);

        handleConnection(peerConnection);

        new Thread(new ClientHandler(peerConnection)).start();
    }

    /**
     * Gestiona una conexión a un peer avisando al controlador de chat y de eventos
     * @param peerConnection Peer al que se conecta
     */
    private void handleConnection(PeerConnection peerConnection) {
        String peerId = peerConnection.getPeerId();

        //ChatManager.getInstance().handleConnectionToPeer(peerId, peerConnection);
    }

    /**
     * Gestiona una salida de mensaje avisando al controlador de chat y de eventos
     * @param message Mensaje enviado
     */
    private void handleMessageSent(Message message) {
        String peerId = peerConnection.getPeerId();

        ChatManager.getInstance().handleMessageSent(peerId, message);
    }

    /**
     * Envía un mensaje al peer
     * @param message Mensaje a enviar
     * @throws IOException si hay error al enviar el mensaje
     */
    public void sendMessage(Message message) throws IOException {
        if (peerConnection != null && peerConnection.isConnected()) {
            peerConnection.sendMessage(message);
            handleMessageSent(message);
        } else {
            throw new IOException("No hay conexión establecida");
        }
    }



    /**
     * Cierra la conexión con el peer
     */
    public void disconnect() {
        if (peerConnection != null) {
            String peerId = peerConnection.getPeerId();

            peerConnection.close();

            ChatManager.getInstance().handleDisconnection(peerId);
        }
    }

    /**
     * Getter y Setter
     */
    public PeerConnection getPeerConnection() {
        return peerConnection;
    }

    public void setPeerConnection(PeerConnection peerConnection) {
        this.peerConnection = peerConnection;
    }

}
