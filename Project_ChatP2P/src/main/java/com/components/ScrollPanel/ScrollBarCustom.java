/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.components.scrollPanel;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;
import javax.swing.JScrollBar;


public class ScrollBarCustom extends JScrollBar implements Serializable {
    
    /** Propiedades **/
    private static final long serialVersionUID = 1L;
    
    public ScrollBarCustom() {
        setUI(new ModernScrollBarUI());
        setPreferredSize(new Dimension(8, 8));
        setForeground(new Color(102, 153, 255));
        setBackground(new Color(245, 245, 245));
        setUnitIncrement(16);
        setOpaque(false);
    }
    
    // Getters y Setters
    public Color getScrollForeground() {
        return getForeground();
    }
    
    public void setScrollForeground(Color color) {
        setForeground(color);
    }
    
    public Color getScrollBackground() {
        return getBackground();
    }
    
    public void setScrollBackground(Color color) {
        setBackground(color);
    }
}