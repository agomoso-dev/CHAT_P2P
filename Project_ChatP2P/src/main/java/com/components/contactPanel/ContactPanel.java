/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.components.contactPanel;

/**
 *
 * @author jairo
 */

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.chat.controller.ChatManager;
import com.chat.model.User;

import java.awt.BorderLayout;
import java.awt.Component;

import com.components.avatar.Avatar;

/**
 * Panel que representa un contacto individual
 */
public class ContactPanel extends JPanel {
    
    /** Propiedades **/
    private User user;                 // Usuario de contacto
    private String peerId;             // ID de la conexión Peer
    private boolean isConnected;       // Estado de la conexión
    
    /** Componentes UI **/
    private JLabel lblAvatar;
    private JLabel lblName;
    private JButton btnConnect;
    
    /**
     * Constructor
     * @param user Usuario de contacto
     */
    public ContactPanel(User user) {
        this.user = user;
        this.isConnected = false;
        this.peerId = null;
        
        initComponents();
        setupLayout();
        setupListeners();
        displayUserInfo();
            
    }
    
    /**
     * Inicializa los componentes del panel
     */
    private void initComponents() {
        lblAvatar = new JLabel();
        
        lblName = new JLabel("Usuario");
        lblName.setFont(new Font("Arial", Font.PLAIN, 11));
        
        btnConnect = new JButton("Conectar");
        btnConnect.setFocusPainted(false);
        btnConnect.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConnect.setFont(new Font("Arial", Font.PLAIN, 10));
        Avatar avatar = new Avatar();
        avatar.setPreferredSize(new Dimension(30, 30));
        avatar.setCornerRadius(15); // Make it circular (radius = width/2)
        lblAvatar = avatar;
    }

    /** Configura el Layout **/
    private void setupLayout() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40)); 
        setPreferredSize(new Dimension(200, 40)); 
    
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        
        add(Box.createHorizontalStrut(8));
        
        Avatar avatar = (Avatar) lblAvatar;
        avatar.setPreferredSize(new Dimension(30, 30));
        avatar.setMaximumSize(new Dimension(30, 30));
        avatar.setMinimumSize(new Dimension(30, 30));
        add(avatar);
        
        add(Box.createHorizontalStrut(8));
        
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setOpaque(false);
        lblName.setOpaque(false);
        lblName.setForeground(new Color(50, 50, 50));
        namePanel.add(lblName, BorderLayout.CENTER);
        add(namePanel);
        
        add(Box.createHorizontalStrut(8));
        
        // Connect button
        btnConnect.setPreferredSize(new Dimension(70, 25));
        btnConnect.setMaximumSize(new Dimension(70, 25));
        btnConnect.setMinimumSize(new Dimension(70, 25));
        btnConnect.setBorder(BorderFactory.createEmptyBorder(4, 8, 4, 8));
        btnConnect.setFocusPainted(false);
        add(btnConnect);
        
        add(Box.createHorizontalStrut(8));
    }
    
    /**
     * Configura los listeners para interacción
     */
    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (ChatManager.getInstance() != null && peerId != null) {
                    ChatManager.getInstance().handleSelectedContact(peerId);
                }
            }
        });
        
        // Listener para el botón de conectar
        btnConnect.addActionListener(e -> {
            if (!isConnected) {
                if (ChatManager.getInstance() != null) {
                    ChatManager.getInstance().handleConnectionToPeer(user.getIp(), user.getPort());
                }
            } else {
                if (peerId != null && ChatManager.getInstance() != null) {
                    ChatManager.getInstance().handleDisconnection(user.getUserId(), peerId);
                }
            }
        });
    }
    
    /**
     * Actualiza la interfaz con la información del usuario
     */
    public void displayUserInfo() {
        lblName.setText(user.getUsername());
        
        if (user.getAvatar() != null && user.getAvatar().getStorageUrl() != null) {
            try {
                URL url = new URL(user.getAvatar().getStorageUrl());
                BufferedImage originalImg = ImageIO.read(url);
                int diameter = 40;
                BufferedImage circularImg = createCircularAvatar(originalImg, diameter);
                ImageIcon icon = new ImageIcon(circularImg);
                lblAvatar.setIcon(icon);
            } catch (MalformedURLException ex) {
                Logger.getLogger(ContactPanel.class.getName()).log(Level.SEVERE, "URL del avatar no válida", ex);
                setDefaultAvatar();
            } catch (IOException ex) {
                Logger.getLogger(ContactPanel.class.getName()).log(Level.SEVERE, "Error al cargar la imagen del avatar", ex);
                setDefaultAvatar();
            }
        } else {
            setDefaultAvatar();
        }
        
        btnConnect.setText(isConnected ? "Desconectar" : "Conectar");
        btnConnect.setBackground(isConnected ? new Color(255, 100, 100) : new Color(100, 200, 100));
        btnConnect.setForeground(Color.WHITE);
        
        revalidate();
        repaint();
    }
    
    /**
     * Establece un avatar por defecto
     */
    private void setDefaultAvatar() {
        int diameter = 40;
        BufferedImage defaultAvatar = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = defaultAvatar.createGraphics();
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setColor(new Color(200, 200, 200));
        g2d.fill(new Ellipse2D.Float(0, 0, diameter, diameter));
        
        if (user.getUsername() != null && !user.getUsername().isEmpty()) {
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 20));
            
            String initial = user.getUsername().substring(0, 1).toUpperCase();
            FontMetrics fm = g2d.getFontMetrics();
            int x = (diameter - fm.stringWidth(initial)) / 2;
            int y = ((diameter - fm.getHeight()) / 2) + fm.getAscent();
            
            g2d.drawString(initial, x, y);
        }
        
        g2d.dispose();
        lblAvatar.setIcon(new ImageIcon(defaultAvatar));
    }
    
    /**
     * Crea una imagen circular a partir de una imagen original
     *
     * @param originalImage Imagen original
     * @param diameter Diámetro del círculo
     * @return Imagen circular
     */
    private BufferedImage createCircularAvatar(BufferedImage originalImage, int diameter) {
        BufferedImage circularImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = circularImage.createGraphics();
        
        // Enable antialiasing for smoother edges
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Create circular clip
        Ellipse2D.Float circle = new Ellipse2D.Float(0, 0, diameter, diameter);
        g2d.setClip(circle);
        
        // Calculate dimensions to maintain aspect ratio
        int size = Math.min(originalImage.getWidth(), originalImage.getHeight());
        int x = (originalImage.getWidth() - size) / 2;
        int y = (originalImage.getHeight() - size) / 2;
        
        // Draw the image maintaining aspect ratio
        g2d.drawImage(originalImage, 0, 0, diameter, diameter, x, y, x + size, y + size, null);
        
        g2d.dispose();
        
        return circularImage;
    }
    
    /**
     * Actualiza el estado de conexión del contacto
     * @param connected Estado de conexión
     * @param peerId ID del peer si está conectado
     */
    public void updateConnectionStatus(boolean connected, String peerId) {
        this.isConnected = connected;
        this.peerId = peerId;
        
        SwingUtilities.invokeLater(this::displayUserInfo);
    }
    
    /**
     * Marca como seleccionado el Panel de Contacto cambiando el color de fondo
     * 
     * @param selected Define si está seleccionado o no
     */
    public void setAsSelected(boolean selected) {
        if (selected) {
            setBackground(new Color(255, 255, 200)); // Lighter yellow
            // Make all components match parent background
            for (Component comp : getComponents()) {
                if (comp instanceof JPanel) {
                    comp.setBackground(new Color(255, 255, 200));
                }
            }
        } else {
            setBackground(Color.WHITE);
            // Reset component backgrounds
            for (Component comp : getComponents()) {
                if (comp instanceof JPanel) {
                    comp.setBackground(Color.WHITE);
                }
            }
        }
        repaint();
    }
    
    // Getters y Setters
    public User getUser() {
        return user;
    }
    
    public String getPeerId() {
        return peerId;
    }
    
    public boolean isConnected() {
        return isConnected;
    }
    
    public void setConnected(String peerId) {
        this.peerId = peerId;
        this.isConnected = true;
        
        displayUserInfo();
    }
    
    public void setDisconnected(){
        this.peerId = null;
        this.isConnected = false;
        
        displayUserInfo();
    }
}
