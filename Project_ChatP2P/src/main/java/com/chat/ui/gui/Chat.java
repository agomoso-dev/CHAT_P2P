package com.chat.ui.gui;

import com.chat.model.Message;
import com.chat.model.User;

import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;


/**
 *
 * @author wenfi
 */
public class Chat extends javax.swing.JFrame {

    /** Propiedades **/
    int xMouse, yMouse;
    List<User> contacts;         // Lista de contactos

    /**
     * Constructor
     */
    public Chat() {
        contacts = new ArrayList<>();

        initComponents();
        setLocationRelativeTo(null);
    }

    /** Métodos adicionales **/
    public void setContactsList(List<User> contacts){
        this.contacts = contacts;
        
        displayContacts();
    }

    private void displayContacts() { }

    /**
     * Botones
     */
    public JLabel getBtnExit(){
        return TxtExit;
    }
    
    public JPanel getPnlExit() {
        return BtnExit;
    }
    
    public JLabel getBtnSendMessage(){
        return TxtEnviar;
    }
    
    public JButton getBtnAddContact() {
        return btnAddContact;
    }
    
    /** Datos de Mensaje **/
    public Message getMessage() {
        String content = MessageTxt.getText().trim();
        
        return new Message(content, Message.MessageType.TEXT);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        bg = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        Minimizarbtn = new javax.swing.JPanel();
        TxtMinimizar = new javax.swing.JLabel();
        BtnExit = new javax.swing.JPanel();
        TxtExit = new javax.swing.JLabel();
        usernameLbl = new javax.swing.JLabel();
        Logo = new javax.swing.JLabel();
        BtnEnviar = new javax.swing.JPanel();
        TxtEnviar = new javax.swing.JLabel();
        MessageTxt = new javax.swing.JTextField();
        MenssageSeparator = new javax.swing.JSeparator();
        jScrollPaneContact = new javax.swing.JScrollPane();
        jPanelContact = new javax.swing.JPanel();
        btnAddContact = new javax.swing.JButton();

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

        Minimizarbtn.setBackground(new java.awt.Color(153, 204, 255));
        Minimizarbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MinimizarbtnMouseClicked(evt);
            }
        });

        TxtMinimizar.setBackground(new java.awt.Color(255, 255, 255));
        TxtMinimizar.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        TxtMinimizar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtMinimizar.setText("-");
        TxtMinimizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtMinimizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtMinimizarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtMinimizarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtMinimizarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MinimizarbtnLayout = new javax.swing.GroupLayout(Minimizarbtn);
        Minimizarbtn.setLayout(MinimizarbtnLayout);
        MinimizarbtnLayout.setHorizontalGroup(
            MinimizarbtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MinimizarbtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtMinimizar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        MinimizarbtnLayout.setVerticalGroup(
            MinimizarbtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MinimizarbtnLayout.createSequentialGroup()
                .addComponent(TxtMinimizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

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
                .addComponent(TxtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(122, 122, 122))
        );
        BtnExitLayout.setVerticalGroup(
            BtnExitLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BtnExitLayout.createSequentialGroup()
                .addComponent(TxtExit)
                .addGap(0, 11, Short.MAX_VALUE))
        );

        usernameLbl.setForeground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(BtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Minimizarbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(94, 94, 94)
                .addComponent(usernameLbl, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(216, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Minimizarbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(usernameLbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(BtnExit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        bg.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 30));

        Logo.setFont(new java.awt.Font("Roboto Black", 0, 48)); // NOI18N
        Logo.setForeground(new java.awt.Color(102, 153, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LOGO.png"))); // NOI18N
        bg.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 30, -1, -1));

        BtnEnviar.setBackground(new java.awt.Color(7, 134, 184));

        TxtEnviar.setFont(new java.awt.Font("Roboto Condensed", 1, 18)); // NOI18N
        TxtEnviar.setForeground(new java.awt.Color(255, 255, 255));
        TxtEnviar.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtEnviar.setText("ENVIAR");
        TxtEnviar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtEnviar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtEnviarMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtEnviarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtEnviarMouseExited(evt);
            }
        });

        javax.swing.GroupLayout BtnEnviarLayout = new javax.swing.GroupLayout(BtnEnviar);
        BtnEnviar.setLayout(BtnEnviarLayout);
        BtnEnviarLayout.setHorizontalGroup(
            BtnEnviarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TxtEnviar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
        );
        BtnEnviarLayout.setVerticalGroup(
            BtnEnviarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnEnviarLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtEnviar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(BtnEnviar, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 440, 110, 40));

        MessageTxt.setBackground(new java.awt.Color(153, 204, 255));
        MessageTxt.setForeground(new java.awt.Color(153, 153, 153));
        MessageTxt.setText("Mensaje");
        MessageTxt.setBorder(null);
        MessageTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                MessageTxtMousePressed(evt);
            }
        });
        MessageTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MessageTxtActionPerformed(evt);
            }
        });
        bg.add(MessageTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 440, 400, 40));
        bg.add(MenssageSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 480, 390, -1));

        jPanelContact.setBackground(new java.awt.Color(255, 255, 255));
        jPanelContact.setToolTipText("");
        jPanelContact.setAlignmentX(0.0F);
        jPanelContact.setAlignmentY(0.0F);

        btnAddContact.setText("Añadir contacto");
        btnAddContact.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddContactActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelContactLayout = new javax.swing.GroupLayout(jPanelContact);
        jPanelContact.setLayout(jPanelContactLayout);
        jPanelContactLayout.setHorizontalGroup(
            jPanelContactLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactLayout.createSequentialGroup()
                .addComponent(btnAddContact, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );
        jPanelContactLayout.setVerticalGroup(
            jPanelContactLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelContactLayout.createSequentialGroup()
                .addComponent(btnAddContact)
                .addGap(0, 908, Short.MAX_VALUE))
        );

        jScrollPaneContact.setViewportView(jPanelContact);

        bg.add(jScrollPaneContact, new org.netbeans.lib.awtextra.AbsoluteConstraints(670, 0, 220, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
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

    private void TxtEnviarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtEnviarMouseClicked

    }//GEN-LAST:event_TxtEnviarMouseClicked

    private void TxtEnviarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtEnviarMouseEntered
        BtnEnviar.setBackground(new Color(102,153,255));
    }//GEN-LAST:event_TxtEnviarMouseEntered

    private void TxtEnviarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtEnviarMouseExited
        BtnEnviar.setBackground(new Color(7,134,184));
    }//GEN-LAST:event_TxtEnviarMouseExited

    private void TxtMinimizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMinimizarMouseClicked
        setState(javax.swing.JFrame.ICONIFIED);
    }//GEN-LAST:event_TxtMinimizarMouseClicked

    private void TxtMinimizarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMinimizarMouseEntered
        Minimizarbtn.setBackground(Color.gray);
        TxtMinimizar.setForeground(Color.white);
    }//GEN-LAST:event_TxtMinimizarMouseEntered

    private void TxtMinimizarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMinimizarMouseExited
        Minimizarbtn.setBackground(new Color(153, 204, 255));
        TxtMinimizar.setForeground(Color.black);
    }//GEN-LAST:event_TxtMinimizarMouseExited

    private void MinimizarbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimizarbtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MinimizarbtnMouseClicked

    private void MessageTxtMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MessageTxtMousePressed
        if(MessageTxt.getText().equals("Mensaje")){
            MessageTxt.setText("");
            MessageTxt.setForeground(Color.black);
        }
    }//GEN-LAST:event_MessageTxtMousePressed

    private void MessageTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MessageTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MessageTxtActionPerformed

    private void btnAddContactActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddContactActionPerformed
        
    }//GEN-LAST:event_btnAddContactActionPerformed

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
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Chat.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new Chat().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BtnEnviar;
    private javax.swing.JPanel BtnExit;
    private javax.swing.JLabel Logo;
    private javax.swing.JSeparator MenssageSeparator;
    private javax.swing.JTextField MessageTxt;
    private javax.swing.JPanel Minimizarbtn;
    private javax.swing.JLabel TxtEnviar;
    private javax.swing.JLabel TxtExit;
    private javax.swing.JLabel TxtMinimizar;
    private javax.swing.JPanel bg;
    private javax.swing.JButton btnAddContact;
    private javax.swing.JPanel header;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JPanel jPanelContact;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPaneContact;
    private javax.swing.JLabel usernameLbl;
    // End of variables declaration//GEN-END:variables
}
