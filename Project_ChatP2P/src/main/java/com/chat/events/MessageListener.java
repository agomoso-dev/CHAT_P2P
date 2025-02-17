package com.chat.events;

import com.chat.model.Message;

public interface MessageListener {

    /**
     * Se llama cuando se recibe un mensaje de otro peer
     * @param senderId Id del peer que ha enviado el mensaje
     * @param message Mensaje recibido
     */
    void onMessageReceived(String senderId, Message message);

    /**
     * Se llama cuando se envía un mensaje a otro peer
     * @param receiverId Id del peer al que se envia el mensaje
     * @param message Mensaje enviado
     */
    void onMessageSent(String receiverId, Message message);
}
