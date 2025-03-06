/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.chat.ui.gui;


import java.awt.Color;
import javax.swing.JLabel;

/**
 *
 * @author wenfi
 */
public class Login extends javax.swing.JFrame {

    /** Propiedades **/
    int xMouse, yMouse;

    /**
     * Constructor
     */
    public Login() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * Botones
     */
    
    public JLabel getBtnLogin() {
        return TxtLogin;
    }

    public JLabel getBtnGoRegister() {
        return URLregister;
    }

    public JLabel getBtnExit() {
        return TxtExit;
    }

    /**
     * Datos de Login *
     */
    
    public String getUsername() {
        return (User.getText() != null) ? User.getText().trim() : "";
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        bg = new javax.swing.JPanel();
        User = new javax.swing.JTextField();
        UserSeparator = new javax.swing.JSeparator();
        Logo = new javax.swing.JLabel();
        BtnLogin = new javax.swing.JPanel();
        TxtLogin = new javax.swing.JLabel();
        header = new javax.swing.JPanel();
        BtnExit = new javax.swing.JPanel();
        TxtExit = new javax.swing.JLabel();
        TxtSessionStart = new javax.swing.JLabel();
        TxtUser = new javax.swing.JLabel();
        BtnRegister = new javax.swing.JPanel();
        URLregister = new javax.swing.JLabel();

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

        User.setBackground(new java.awt.Color(153, 204, 255));
        User.setForeground(new java.awt.Color(153, 153, 153));
        User.setText("Ingrese el ID de usuario");
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
        bg.add(User, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 260, 420, 40));
        bg.add(UserSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 300, 420, -1));

        Logo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 48)); // NOI18N
        Logo.setForeground(new java.awt.Color(51, 102, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LOGO.png"))); // NOI18N
        Logo.setText("WHATSABLUE");
        bg.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        BtnLogin.setBackground(new java.awt.Color(7, 134, 184));

        TxtLogin.setFont(new java.awt.Font("Roboto Condensed", 1, 18)); // NOI18N
        TxtLogin.setForeground(new java.awt.Color(255, 255, 255));
        TxtLogin.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtLogin.setText("ENTRAR");
        TxtLogin.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtLogin.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtLoginMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtLoginMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtLoginMouseExited(evt);
            }
        });

        javax.swing.GroupLayout BtnLoginLayout = new javax.swing.GroupLayout(BtnLogin);
        BtnLogin.setLayout(BtnLoginLayout);
        BtnLoginLayout.setHorizontalGroup(
            BtnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnLoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        BtnLoginLayout.setVerticalGroup(
            BtnLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnLoginLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(BtnLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 400, 120, 40));

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
                .addGap(0, 450, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BtnExit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bg.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 500, 40));

        TxtSessionStart.setFont(new java.awt.Font("Roboto Black", 1, 24)); // NOI18N
        TxtSessionStart.setText("INICIAR SESIÓN");
        bg.add(TxtSessionStart, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 170, -1, -1));

        TxtUser.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        TxtUser.setText("ID");
        bg.add(TxtUser, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 20, -1));

        BtnRegister.setBackground(new java.awt.Color(153, 204, 255));

        URLregister.setBackground(new java.awt.Color(153, 204, 255));
        URLregister.setFont(new java.awt.Font("Roboto Light", 1, 12)); // NOI18N
        URLregister.setText("¿Aún no tienes usuario? Registrate aquí");
        URLregister.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(0, 0, 0)));
        URLregister.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        URLregister.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                URLregisterMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout BtnRegisterLayout = new javax.swing.GroupLayout(BtnRegister);
        BtnRegister.setLayout(BtnRegisterLayout);
        BtnRegisterLayout.setHorizontalGroup(
            BtnRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnRegisterLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(URLregister, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        BtnRegisterLayout.setVerticalGroup(
            BtnRegisterLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BtnRegisterLayout.createSequentialGroup()
                .addComponent(URLregister, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bg.add(BtnRegister, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 350, 230, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UserActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_UserActionPerformed

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

    private void TxtLoginMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtLoginMouseEntered
        BtnLogin.setBackground(new Color(102, 153, 255));
    }//GEN-LAST:event_TxtLoginMouseEntered

    private void TxtLoginMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtLoginMouseExited
        BtnLogin.setBackground(new Color(7, 134, 184));
    }//GEN-LAST:event_TxtLoginMouseExited

    private void UserMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserMousePressed
        if (User.getText().equals("Ingrese el ID de usuario")) {
            User.setText("");
            User.setForeground(Color.black);
        }

    }//GEN-LAST:event_UserMousePressed

    private void TxtLoginMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtLoginMouseClicked

    }//GEN-LAST:event_TxtLoginMouseClicked

    private void URLregisterMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_URLregisterMouseClicked

    }//GEN-LAST:event_URLregisterMouseClicked
    private void URLregisterMouseEntered(java.awt.event.MouseEvent evt) {
        URLregister.setForeground(new Color(102, 153, 255));
    }

    private void URLregisterMouseExited(java.awt.event.MouseEvent evt) {
        URLregister.setForeground(Color.black);
    }

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
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Login.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Login().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BtnExit;
    private javax.swing.JPanel BtnLogin;
    private javax.swing.JPanel BtnRegister;
    private javax.swing.JLabel Logo;
    private javax.swing.JLabel TxtExit;
    private javax.swing.JLabel TxtLogin;
    private javax.swing.JLabel TxtSessionStart;
    private javax.swing.JLabel TxtUser;
    private javax.swing.JLabel URLregister;
    private javax.swing.JTextField User;
    private javax.swing.JSeparator UserSeparator;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel header;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
