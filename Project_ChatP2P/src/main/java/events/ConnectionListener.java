package events;

public interface ConnectionListener {

    /**
     * Se llama cuando un cliente se conecta al servidor
     * @param peerId Id del peer que se conecta
     */
    void onClientConnected(String peerId);

    /**
     * Se llama cuando un cliente se desconecta del servidor
     * @param peerId Id del peer que se desconecta
     */
    void onClientDisconnected(String peerId);

    /**
     * Se llama cuando se establece una conexión con otro peer
     * @param peerId Id del peer con el que se conecta
     */
    void onConnectingToPeer(String peerId);

}
