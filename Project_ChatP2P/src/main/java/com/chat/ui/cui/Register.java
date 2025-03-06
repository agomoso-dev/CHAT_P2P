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
public class Register {
    
    /** Propiedades **/
    private Scanner scanner;        // Entrada de datos
        
    public Register() {
        this.scanner = new Scanner(System.in);
    }
    
    public String getUsername() {
        System.out.println("=== REGISTRAR ===");
        System.out.print("Introduce tu usuario : ");
        
        return scanner.nextLine();
    }
    
    public Integer getPort() {
        System.out.print("Introduce el puerto : ");
        
        return Integer.parseInt(scanner.nextLine());
    }
    
    public void setVisible(boolean visible) {
        if (visible) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    } 
    
}
