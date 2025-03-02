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

        new Thread(new ClientHandler(peerConnection)).start();
    }

    /**
     * Cierra la conexión con el peer
     */
    public void disconnect() {
        if (peerConnection != null) {
            peerConnection.close();
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
