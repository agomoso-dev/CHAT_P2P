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

/**
 * Componente personalizado para mostrar avatares de usuario con bordes redondeados.
 * Extiende JLabel para mostrar imágenes y permite personalizar el radio de las esquinas.
 * 
 * @author wenfi
 */
public class Avatar extends JLabel implements Serializable {
    
    /** Propiedades **/
    private static final long serialVersionUID = 1L;
    
    private static final String DEFAULT_IMAGE_PATH = "/imagenes/";
    private static final String DEFAULT_BACKGROUND = "default_avatar.png";
    
    private int cornerRadius = 0;
    private Shape shape;
    
    private String imagePath = "";

    /**
     * Constructor por defecto.
     * Inicializa un avatar transparente de 100x100 píxeles con fondo por defecto.
     */
    public Avatar() {
        setOpaque(false);
        setPreferredSize(new Dimension(100, 100));
        try {
            java.net.URL imageURL = Avatar.class.getResource(DEFAULT_IMAGE_PATH + DEFAULT_BACKGROUND);
            if (imageURL != null) {
                ImageIcon defaultIcon = new ImageIcon(imageURL);
                Image scaledImage = defaultIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                setIcon(new ImageIcon(scaledImage));
            } else {
                setBackground(Color.WHITE);
            }
        } catch (Exception e) {
            System.err.println("Error loading default background: " + e.getMessage());
            setBackground(Color.WHITE);
        }
    }
    
    /**
     * Dibuja el componente con las características personalizadas.
     * Aplica el recorte redondeado y centra la imagen si existe.
     *
     * @param g El contexto gráfico donde se dibujará el componente
     */
    @Override
    protected void paintComponent(Graphics g) {
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius);
        }
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getBackground() != null) {
            g2.setColor(getBackground());
            g2.fill(shape);
        }
        
        if (getIcon() != null) {
            g2.setClip(shape);
            ImageIcon icon = (ImageIcon) getIcon();
            int x = (getWidth() - icon.getIconWidth()) / 2;
            int y = (getHeight() - icon.getIconHeight()) / 2;
            g2.drawImage(icon.getImage(), x, y, null);
        }
        
        // Draw border if needed
        if (getBorder() != null) {
            g2.setColor(getForeground());
            g2.draw(shape);
        }
        
        g2.dispose();
    }

    /**
     * Obtiene el radio actual de las esquinas.
     *
     * @return El radio de las esquinas en píxeles
     */
    public int getCornerRadius() {
        return cornerRadius;
    }

    /**
     * Establece el radio de las esquinas del avatar.
     *
     * @param radius El nuevo radio en píxeles
     */    
    public void setCornerRadius(int radius) {
        this.cornerRadius = radius;
        repaint();
    }

    /**
     * Obtiene la ruta de la imagen actual.
     *
     * @return La ruta de la imagen
     */
    public String getImagePath() {
        return imagePath;
    }

    /**
     * Establece y carga una nueva imagen para el avatar.
     * La imagen se redimensiona automáticamente para ajustarse al tamaño del componente
     * manteniendo la proporción y centrándola.
     *
     * @param imageName Nombre del archivo de imagen o ruta completa
     */
    public void setImagePath(String imageName) {
        this.imagePath = imageName;
        if (imageName != null && !imageName.isEmpty()) {
            try {
                ImageIcon icon = null;
                java.net.URL imageURL = Avatar.class.getResource(DEFAULT_IMAGE_PATH + imageName);
                if (imageURL != null) {
                    icon = new ImageIcon(imageURL);
                } else {
                    File file = new File(imageName);
                    if (file.exists()) {
                        icon = new ImageIcon(imageName);
                    }
                }
    
                if (icon != null) {
                    int targetWidth = getWidth() > 0 ? getWidth() : getPreferredSize().width;
                    int targetHeight = getHeight() > 0 ? getHeight() : getPreferredSize().height;
                    int originalWidth = icon.getIconWidth();
                    int originalHeight = icon.getIconHeight();
    
                    double scale = Math.max(
                        (double) targetWidth / originalWidth,
                        (double) targetHeight / originalHeight
                    );
    
                    int scaledWidth = (int) (originalWidth * scale);
                    int scaledHeight = (int) (originalHeight * scale);
    
                    Image scaledImage = icon.getImage().getScaledInstance(
                        scaledWidth,
                        scaledHeight,
                        Image.SCALE_SMOOTH
                    );
                    
                    setIcon(new ImageIcon(scaledImage));
                }
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        }
        repaint();
    }

    /**
     * Actualiza las dimensiones del componente y redimensiona la imagen si existe.
     *
     * @param x Coordenada X
     * @param y Coordenada Y
     * @param width Nuevo ancho
     * @param height Nuevo alto
     */
    @Override
    public void setBounds(int x, int y, int width, int height) {
        super.setBounds(x, y, width, height);
        if (!imagePath.isEmpty()) {
            setImagePath(imagePath);
        }
    }
}