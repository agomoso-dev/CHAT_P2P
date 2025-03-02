package com.chat.network.socket;

import com.chat.model.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PeerConnection {

    /** Propiedades **/
    private final Socket socket;                   // Socket de la conexión
    private final ObjectInputStream input;         // Entrada de la conexión
    private final ObjectOutputStream output;       // Salida de la conexión
    private final String peerId;                   // Id de la conexión
    private boolean connected;                     // Estado de la conexión

    /** Constructor por parámetros **/
    public PeerConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.peerId = socket.getInetAddress().getHostAddress() + ":" + socket.getPort();

        try {
            this.output = new ObjectOutputStream(socket.getOutputStream());
            this.output.flush();

            this.input = new ObjectInputStream(socket.getInputStream());

            this.connected = true;
        } catch(IOException ex) {
            throw ex;
        }
    }

    /**
     * Envia un mensaje a una conexión
     * @param message Mensaje a enviar
     */
    public void sendMessage(Message message) throws IOException {
        if (!connected) {
            throw new IllegalStateException("La conexión no está activa");
        }

        try {
            output.writeObject(message);
            output.flush();
        } catch (IOException ex) {
            connected = false;
            throw ex;
        }
    }

    /**
     * Recibe un mensaje de una conexión
     * @return El mensaje recibido
     */
    public Message receiveMessage() throws IOException, ClassNotFoundException {
        if (!connected) {
            throw new IllegalStateException("La conexión no está activa");
        }
        
        try {
            Message message = (Message) input.readObject();
            
            return message;
        } catch (IOException | ClassNotFoundException ex) {
            connected = false;
            throw ex;
        }
    }

    /**
     * Cierra la conexión
     */
    public void close() {
        connected = false;

        try {
            if (output != null) output.close();
            if (input != null) input.close();
            if (socket != null) socket.close();
        } catch (IOException ex) {
            System.out.println("Error cerrando la conexión: " + ex.getMessage());

        }
    }

    /**
     * Comprueba si la conexión sigue actia
     */
    public boolean isConnected() {
        return connected &&
                socket != null &&
                socket.isConnected() &&
                !socket.isClosed();
    }

    /**
     * Getters
     */
    public Socket getSocket() {
        return socket;
    }

    public String getPeerId() {
        return peerId;
    }

    /** Equals y HashCode **/
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        PeerConnection that = (PeerConnection) obj;
        return peerId != null && peerId.equals(that.peerId); 
    }

    @Override
    public int hashCode() {
        return peerId != null ? peerId.hashCode() : 0;
    }
    
}
