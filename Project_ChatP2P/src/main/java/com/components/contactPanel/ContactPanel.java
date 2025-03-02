/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.components.contactPanel;

/**
 *
 * @author jairo
 */

import com.chat.controller.ChatManager;
import com.chat.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
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
        lblName.setFont(new Font("Arial", Font.BOLD, 14));
        
        btnConnect = new JButton("Conectar");
        btnConnect.setFocusPainted(false);
        btnConnect.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnConnect.setPreferredSize(new Dimension(100, 30));
        btnConnect.setMinimumSize(new Dimension(100, 30));
        btnConnect.setMaximumSize(new Dimension(100, 30));

    }
    
    /**
     * Configura el layout del panel
     */
    private void setupLayout() {
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 230)),
                new EmptyBorder(10, 10, 10, 10)
        ));
        setPreferredSize(new Dimension(300, 70));

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        lblAvatar.setPreferredSize(new Dimension(40, 40));
        add(lblAvatar, gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1; 
        lblName.setPreferredSize(new Dimension(120, 30));
        lblName.setHorizontalAlignment(SwingConstants.LEFT);
        add(lblName, gbc);

        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        btnConnect.setPreferredSize(new Dimension(100, 30));
        btnConnect.setFont(new Font("Arial", Font.BOLD, 12));
        add(btnConnect, gbc);
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
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2d.setClip(new Ellipse2D.Float(0, 0, diameter, diameter));
        
        int size = Math.min(originalImage.getWidth(), originalImage.getHeight());
        int x = (originalImage.getWidth() - size) / 2;
        int y = (originalImage.getHeight() - size) / 2;
        
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
            setBackground(Color.yellow);
        } else {
            setBackground(Color.WHITE);
        }
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
