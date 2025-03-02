package com.components.avatar;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.Serializable;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Avatar extends JLabel implements Serializable {
    private static final long serialVersionUID = 1L;
    // Updated path to match Maven resource structure
    private static final String DEFAULT_IMAGE_PATH = "/imagenes/";
    private int cornerRadius = 0;
    private Shape shape;
    private String imagePath = "";

    public Avatar() {
        setOpaque(false);
        setPreferredSize(new Dimension(100, 100));
        // Set a default background color to make the component visible
        setBackground(Color.WHITE);
    }
    @Override
    protected void paintComponent(Graphics g) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibuja el fondo si es necesario
        if (getBackground() != null) {
            g2.setColor(getBackground());
            g2.fill(shape);
        }
        
        // Dibuja la imagen con esquinas redondeadas
        if (getIcon() != null) {
            g2.setClip(shape);
            super.paintComponent(g2);
        }
        
        // Dibuja el borde si es necesario
        if (getBorder() != null) {
            g2.setColor(getForeground());
            g2.draw(shape);
        }
        
        g2.dispose();
    }

    public int getCornerRadius() {
        return cornerRadius;
    }

    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imageName) {
        this.imagePath = imageName;
        if (imageName != null && !imageName.isEmpty()) {
            try {
                // Try to load from resources first
                java.net.URL imageURL = Avatar.class.getResource(DEFAULT_IMAGE_PATH + imageName);
                if (imageURL != null) {
                    ImageIcon icon = new ImageIcon(imageURL);
                    // Resize icon to fit component
                    Image img = icon.getImage().getScaledInstance(
                        getPreferredSize().width, 
                        getPreferredSize().height, 
                        Image.SCALE_SMOOTH
                    );
                    setIcon(new ImageIcon(img));
                    System.out.println("Image loaded from: " + imageURL);
                } else {
                    System.err.println("Could not find image: " + DEFAULT_IMAGE_PATH + imageName);
                    // Try as absolute path
                    File file = new File(imageName);
                    if (file.exists()) {
                        ImageIcon icon = new ImageIcon(imageName);
                        Image img = icon.getImage().getScaledInstance(
                            getPreferredSize().width, 
                            getPreferredSize().height, 
                            Image.SCALE_SMOOTH
                        );
                        setIcon(new ImageIcon(img));
                        System.out.println("Image loaded from file: " + file.getAbsolutePath());
                    } else {
                        System.err.println("Image file not found: " + imageName);
                    }
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }
        repaint();
    }
}