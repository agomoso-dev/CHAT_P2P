/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.chat;

import com.chat.controller.ChatManager;
import com.chat.controller.ConsoleController;
import com.chat.controller.ViewManager;

/**
 *
 * @author wenfi
 */
public class Project_ChatP2P {

    public static void main(String[] args) {
        ChatManager chatManager = ChatManager.getInstance();
        if(chatManager.getViewmanager() instanceof ConsoleController c ){
            c.showInitialMenu();
        }
    }
}
