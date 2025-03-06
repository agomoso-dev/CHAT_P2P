package com.chat.controller;
import java.util.List;

import com.chat.model.MessageEntry;
import com.chat.model.User;
 

/**
 *
 * @author wenfi
 */
public interface ViewManager {
    
    void showChatWindow();
    
    void showMessage(String message);
    
    void showErrorMessage(String message);
    
    void displayContacts(List<User> contacts);
    
    void setContactsList(List<User> contacts);
    
    void createContactPanel(User contact, String peerId);
    
    void updateContactPanel(String userId, String peerId, boolean connected);
    
    void setContactPanelAsSelected(String contactId);
    
    void displayChat(List<MessageEntry> messageHistory);
    
    void displayTextMessage(MessageEntry messageEntry);
    
    void displayFileMessage(MessageEntry messageEntry);
    
}
