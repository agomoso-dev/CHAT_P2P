package events;

import com.chat.model.Message;
import com.chat.network.PeerConnection;

import java.util.ArrayList;

public class EventManager {

    /** Propiedades **/
    private static EventManager instance;                               // Singleton del EventManager
    private ArrayList<ConnectionListener> connectionListeners;          // Listeners de conexión
    private ArrayList<MessageListener> messageListeners;                // Listeners de mensajería

    /** Constructor privado Singleton **/
    private EventManager() {
        this.connectionListeners = new ArrayList<>();
        this.messageListeners = new ArrayList<>();
    }

    /**
     * Devuelve el Singleton de EventManager
     * @return Singleton de EventManager
     */
    public static EventManager getInstance() {
        if (instance == null) {
            instance = new EventManager();
        }
        return instance;
    }

    /**
     * Métodos para añadir listeners
     */
    public void addConnectionListener(ConnectionListener connectionListener) {
        this.connectionListeners.add(connectionListener);
    }

    public void addMessageListener(MessageListener messageListener) {
        this.messageListeners.add(messageListener);
    }

    /**
     * Métodos para notificar los eventos de conexión
     */
    public void notifyClientConnected(String peerId) {
        for (ConnectionListener listener : connectionListeners) {
            listener.onClientConnected(peerId);
        }
    }

    public void notifyClientDisconnected(String peerId) {
        for (ConnectionListener listener : connectionListeners) {
            listener.onClientDisconnected(peerId);
        }
    }

    public void notifyConnectingToPeer(String peerId) {
        for (ConnectionListener listener : connectionListeners) {
            listener.onConnectingToPeer(peerId);
        }
    }

    /**
     * Métodos para notificar los eventos de mensajes
     */
    public void notifyMessageReceived(String senderId, Message message) {
        for (MessageListener listener : messageListeners) {
            listener.onMessageReceived(senderId, message);
        }
    }

    public void notifyMessageSent(String receiverId, Message message) {
        for (MessageListener listener : messageListeners) {
            listener.onMessageSent(receiverId, message);
        }
    }

}
