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
public class AddContact {
    
    /** Propiedades **/
    private Scanner scanner;        // Entrada de datos
    
    private boolean isVisible;      // Define el estado del menú
    
    public AddContact() {
        this.scanner = new Scanner(System.in);
        this.isVisible = false;
    }
    
    /**
     * Obtiene la IP del nuevo contacto
     * @return String con la IP introducida
     */
    public String getIp() {
        if(!isVisible) {
            return null;
        }
        
        System.out.println("\n=== AÑADIR CONTACTO ===");
        System.out.print("Introduce la IP: ");
        
        return scanner.nextLine().trim();
    }
    
    /**
     * Obtiene el puerto del nuevo contacto
     * @return Integer con el puerto introducido
     */
    public Integer getPort() {
        System.out.print("Introduce el puerto: ");
        
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Error: El puerto debe ser un número");
            return null;
        }
    }
    
    /**
     * Controla la visibilidad de la interfaz
     * @param visible true para mostrar, false para ocultar
     */
    public void setVisible(boolean visible) {
        if (visible) {
            clearScreen();
            this.isVisible = true;
        } else {
            this.isVisible = false;
        }
    }
    
    /**
     * Limpia la pantalla de la consola
     */
    private void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
}