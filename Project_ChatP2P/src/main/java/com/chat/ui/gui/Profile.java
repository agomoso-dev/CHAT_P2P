/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.chat.ui.gui;


import java.awt.Color;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author wenfi
 */
public class Profile extends javax.swing.JFrame {

    /** Propiedades **/
    int xMouse, yMouse;
    String avatarPath;           // Ruta del avatar

    /**
     * Constructor
     */
    public Profile() {
        avatarPath = "";

        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * Botones *
     */
    
    public JLabel getBtnGoChat() {
        return TxtGoChat;
    }

    public JLabel getBtnUpdate() {
        return TxtUpdate;
    }

    public JLabel getBtnExit() {
        return TxtExit;
    }

    /**
     * Datos de Registro *
     */
    
    public Map<String, Object> getData(){
        Map<String, Object> data = new HashMap();
        data.put("username", getUsername());
        data.put("port", getPort());
        data.put("avatarPath", getAvatarPath());
        
        return data;
    }
    
    public String getUsername() {
        return (User.getText() != null) ? User.getText().trim() : "";
    }

    public Integer getPort() {
        try {
            return Integer.parseInt(Port.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public String getAvatarPath(){
        return avatarPath;
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        bg = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        BtnExit = new javax.swing.JPanel();
        TxtExit = new javax.swing.JLabel();
        BtnGoChat = new javax.swing.JPanel();
        TxtGoChat = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        User = new javax.swing.JTextField();
        UserSeparator = new javax.swing.JSeparator();
        BtnUpdate = new javax.swing.JPanel();
        TxtUpdate = new javax.swing.JLabel();
        TxtPort = new javax.swing.JLabel();
        Port = new javax.swing.JTextField();
        UserSeparator1 = new javax.swing.JSeparator();
        TxtUser = new javax.swing.JLabel();
        avatar1 = new com.components.avatar.Avatar();
        BtnMas = new javax.swing.JPanel();
        TxtMas = new javax.swing.JLabel();

        jScrollPane1.setViewportView(jEditorPane1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(null);
        setModalExclusionType(java.awt.Dialog.ModalExclusionType.APPLICATION_EXCLUDE);
        setUndecorated(true);

        bg.setBackground(new java.awt.Color(153, 204, 255));
        bg.setForeground(new java.awt.Color(255, 255, 255));
        bg.setPreferredSize(new java.awt.Dimension(800, 500));
        bg.setRequestFocusEnabled(false);
        bg.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        header.setBackground(new java.awt.Color(153, 204, 255));
        header.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                headerMouseDragged(evt);
            }
        });
        header.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                headerMousePressed(evt);
            }
        });

        BtnExit.setBackground(new java.awt.Color(153, 204, 255));
        BtnExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                BtnExitMouseClicked(evt);
            }
        });

        TxtExit.setBackground(new java.awt.Color(255, 255, 255));
        TxtExit.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        TxtExit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtExit.setText("x");
        TxtExit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtExitMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtExitMouseExited(evt);
            }
        });

        javax.swing.GroupLayout BtnExitLayout = new javax.swing.GroupLayout(BtnExit);
        BtnExit.setLayout(BtnExitLayout);
        BtnExitLayout.setHorizontalGroup(
            BtnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnExitLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        BtnExitLayout.setVerticalGroup(
            BtnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnExitLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(BtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 430, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BtnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bg.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 480, 40));

        BtnGoChat.setBackground(new java.awt.Color(7, 134, 184));

        TxtGoChat.setFont(new java.awt.Font("Roboto Condensed", 1, 18)); // NOI18N
        TxtGoChat.setForeground(new java.awt.Color(255, 255, 255));
        TxtGoChat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtGoChat.setText("<<");
        TxtGoChat.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtGoChat.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtGoChatMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtGoChatMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtGoChatMouseExited(evt);
            }
        });

        javax.swing.GroupLayout BtnGoChatLayout = new javax.swing.GroupLayout(BtnGoChat);
        BtnGoChat.setLayout(BtnGoChatLayout);
        BtnGoChatLayout.setHorizontalGroup(
            BtnGoChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnGoChatLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtGoChat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        BtnGoChatLayout.setVerticalGroup(
            BtnGoChatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnGoChatLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtGoChat, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(BtnGoChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 50, 40, 40));

        Logo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 48)); // NOI18N
        Logo.setForeground(new java.awt.Color(51, 102, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LOGO.png"))); // NOI18N
        Logo.setText("PERFIL");
        bg.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        User.setBackground(new java.awt.Color(153, 204, 255));
        User.setForeground(new java.awt.Color(153, 153, 153));
        User.setText("Ingrese el nombre de usuario");
        User.setBorder(null);
        User.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                UserMousePressed(evt);
            }
        });
        User.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UserActionPerformed(evt);
            }
        });
        bg.add(User, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 240, 40));
        bg.add(UserSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 217, 230, -1));

        BtnUpdate.setBackground(new java.awt.Color(7, 134, 184));

        TxtUpdate.setFont(new java.awt.Font("Roboto Condensed", 1, 18)); // NOI18N
        TxtUpdate.setForeground(new java.awt.Color(255, 255, 255));
        TxtUpdate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtUpdate.setText("ACTUALIZAR");
        TxtUpdate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtUpdate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtUpdateMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtUpdateMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtUpdateMouseExited(evt);
            }
        });

        javax.swing.GroupLayout BtnUpdateLayout = new javax.swing.GroupLayout(BtnUpdate);
        BtnUpdate.setLayout(BtnUpdateLayout);
        BtnUpdateLayout.setHorizontalGroup(
            BtnUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TxtUpdate, javax.swing.GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
        );
        BtnUpdateLayout.setVerticalGroup(
            BtnUpdateLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnUpdateLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(BtnUpdate, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 370, 130, 40));

        TxtPort.setFont(new java.awt.Font("Roboto Black", 0, 24)); // NOI18N
        TxtPort.setText("Puerto");
        bg.add(TxtPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, -1, 30));

        Port.setBackground(new java.awt.Color(153, 204, 255));
        Port.setForeground(new java.awt.Color(153, 153, 153));
        Port.setText("Ingrese el puerto");
        Port.setBorder(null);
        Port.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                PortMousePressed(evt);
            }
        });
        Port.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PortActionPerformed(evt);
            }
        });
        bg.add(Port, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 240, 40));
        bg.add(UserSeparator1, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 320, 230, -1));

        TxtUser.setFont(new java.awt.Font("Roboto Black", 0, 24)); // NOI18N
        TxtUser.setText("Nombre de usuario");
        bg.add(TxtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 130, -1, 30));

        avatar1.setCornerRadius(200);
        bg.add(avatar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 110, 125, 125));

        BtnMas.setBackground(new java.awt.Color(7, 134, 184));

        TxtMas.setFont(new java.awt.Font("Roboto Condensed", 1, 24)); // NOI18N
        TxtMas.setForeground(new java.awt.Color(255, 255, 255));
        TxtMas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtMas.setText("+");
        TxtMas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtMas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtMasMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtMasMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtMasMouseExited(evt);
            }
        });

        javax.swing.GroupLayout BtnMasLayout = new javax.swing.GroupLayout(BtnMas);
        BtnMas.setLayout(BtnMasLayout);
        BtnMasLayout.setHorizontalGroup(
            BtnMasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TxtMas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
        );
        BtnMasLayout.setVerticalGroup(
            BtnMasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TxtMas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 46, Short.MAX_VALUE)
        );

        bg.add(BtnMas, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 250, 50, -1));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 437, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void headerMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMousePressed
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_headerMousePressed

    private void headerMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_headerMouseDragged
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();
        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_headerMouseDragged

    private void TxtExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtExitMouseClicked
        
    }//GEN-LAST:event_TxtExitMouseClicked

    private void TxtExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtExitMouseEntered
        BtnExit.setBackground(Color.red);
        TxtExit.setForeground(Color.white);
    }//GEN-LAST:event_TxtExitMouseEntered

    private void TxtExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtExitMouseExited
        BtnExit.setBackground(new Color(153, 204, 255));
        TxtExit.setForeground(Color.black);
    }//GEN-LAST:event_TxtExitMouseExited

    private void BtnExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_BtnExitMouseClicked
    }//GEN-LAST:event_BtnExitMouseClicked

    private void TxtUpdateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUpdateMouseEntered
        BtnUpdate.setBackground(new Color(102, 153, 255));
    }//GEN-LAST:event_TxtUpdateMouseEntered

    private void TxtUpdateMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUpdateMouseExited
        BtnUpdate.setBackground(new Color(7, 134, 184));
    }//GEN-LAST:event_TxtUpdateMouseExited

    private void TxtUpdateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtUpdateMouseClicked
        
    }//GEN-LAST:event_TxtUpdateMouseClicked

    private void TxtGoChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtGoChatMouseClicked
       
     }//GEN-LAST:event_TxtGoChatMouseClicked

    private void TxtGoChatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtGoChatMouseEntered
        BtnGoChat.setBackground(new Color(102, 153, 255));
    }//GEN-LAST:event_TxtGoChatMouseEntered

    private void TxtGoChatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtGoChatMouseExited
        BtnGoChat.setBackground(new Color(7, 134, 184));

    }//GEN-LAST:event_TxtGoChatMouseExited

    private void UserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserActionPerformed

    private void UserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserMousePressed
        if (User.getText().equals("Ingrese el nombre de usuario")) {
            User.setText("");
            User.setForeground(Color.black);
        }
        if (Port.getText().equals("")) {
            Port.setText("Ingrese el puerto");
            Port.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_UserMousePressed

    private void TxtMasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMasMouseClicked
         JFileChooser fileChooser = new JFileChooser();
        // Configurar para mostrar solo imágenes
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "Image Files", "jpg", "jpeg", "png", "gif");
        fileChooser.setFileFilter(filter);

        // Mostrar el diálogo de selección
        int result = fileChooser.showOpenDialog(this);

        // Procesar el resultado
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();

            // Verificar si el archivo realmente es una imagen
            if (selectedFile.exists() && selectedFile.isFile()) {
                avatarPath = selectedFile.getAbsolutePath();
                // Actualizar el avatar con la imagen seleccionada
                avatar1.setImagePath(selectedFile.getAbsolutePath());
            } else {
                JOptionPane.showMessageDialog(this, "Archivo inválido. Seleccione una imagen válida.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_TxtMasMouseClicked

    private void TxtMasMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMasMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtMasMouseEntered

    private void TxtMasMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMasMouseExited
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtMasMouseExited

    private void PortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PortActionPerformed

    private void PortMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PortMousePressed
        if (Port.getText().equals("Ingrese el puerto")) {
            Port.setText("");
            Port.setForeground(Color.black);
        }
        if (User.getText().equals("")) {
            User.setText("Ingrese el nombre de usuario");
            User.setForeground(new Color(153, 153, 153));
        }
    }//GEN-LAST:event_PortMousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Profile.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Profile().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BtnExit;
    private javax.swing.JPanel BtnGoChat;
    private javax.swing.JPanel BtnMas;
    private javax.swing.JPanel BtnUpdate;
    private javax.swing.JLabel Logo;
    private javax.swing.JTextField Port;
    private javax.swing.JLabel TxtExit;
    private javax.swing.JLabel TxtGoChat;
    private javax.swing.JLabel TxtMas;
    private javax.swing.JLabel TxtPort;
    private javax.swing.JLabel TxtUpdate;
    private javax.swing.JLabel TxtUser;
    private javax.swing.JTextField User;
    private javax.swing.JSeparator UserSeparator;
    private javax.swing.JSeparator UserSeparator1;
    private com.components.avatar.Avatar avatar1;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel header;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
