/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.chat.ui.cui;
import java.util.Scanner;

/**
 *
 * @author wenfi
 */
public class Login {
    
    /** Propiedades **/
    private Scanner scanner;        // Entrada de datos
        
    public Login() {
        this.scanner = new Scanner(System.in);
    }
    
    public String getUserId() {
        System.out.println("=== LOGIN ===");
        System.out.print("Introduce tu ID: ");
        
        return scanner.nextLine();
    }
    
    public void setVisible(boolean visible) {
        if (visible) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }  
    
}
