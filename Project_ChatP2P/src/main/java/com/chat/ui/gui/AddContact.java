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
public class AddContact extends javax.swing.JFrame {

    /** Propiedades **/
    int xMouse, yMouse;

    /**
     * Constructor
     */
    public AddContact() {
        initComponents();
        setLocationRelativeTo(null);
    }

    /**
     * Botones
     */
    
    public JLabel getBtnExit() {
        return TxtExit;
    }
    
    public JLabel getBtnGoChat() {
        return TxtGoChat;
    }
    
    public JLabel getBtnAddContact() {
        return TxtAddContact;
    }
    
    /**
     * Datos de contacto
     */
    
    public String getIp() {
        return (IP.getText() != null) ? IP.getText().trim() : "";
    }
    
    public Integer getPort() {
        try {
            return Integer.parseInt(Port.getText().trim());
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jEditorPane1 = new javax.swing.JEditorPane();
        bg = new javax.swing.JPanel();
        TxtAppChat = new javax.swing.JLabel();
        Image_Right = new javax.swing.JLabel();
        TxtTitle = new javax.swing.JLabel();
        TxtIP = new javax.swing.JLabel();
        IP = new javax.swing.JTextField();
        IpSeparator = new javax.swing.JSeparator();
        TxtPort = new javax.swing.JLabel();
        Port = new javax.swing.JTextField();
        PortSeparator = new javax.swing.JSeparator();
        Logo = new javax.swing.JLabel();
        BtnAddContct = new javax.swing.JPanel();
        TxtAddContact = new javax.swing.JLabel();
        Fondo_Derecha = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        Exitbtn = new javax.swing.JPanel();
        TxtExit = new javax.swing.JLabel();
        Minimizebtn = new javax.swing.JPanel();
        TxtMinimize = new javax.swing.JLabel();
        BtnGoChat = new javax.swing.JPanel();
        TxtGoChat = new javax.swing.JLabel();

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

        TxtAppChat.setBackground(new java.awt.Color(255, 255, 255));
        TxtAppChat.setFont(new java.awt.Font("Roboto Black", 0, 36)); // NOI18N
        TxtAppChat.setForeground(new java.awt.Color(153, 204, 255));
        TxtAppChat.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtAppChat.setText("APP CHAT");
        bg.add(TxtAppChat, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 180, 310, 40));

        Image_Right.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/mobile-phone-6721915_1280.png"))); // NOI18N
        bg.add(Image_Right, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, 310, 380));

        TxtTitle.setFont(new java.awt.Font("Roboto Black", 0, 24)); // NOI18N
        TxtTitle.setText("AÑADIR CONTACTO");
        bg.add(TxtTitle, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 120, -1, -1));

        TxtIP.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        TxtIP.setText("IP");
        bg.add(TxtIP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, 70, -1));

        IP.setBackground(new java.awt.Color(153, 204, 255));
        IP.setForeground(new java.awt.Color(153, 153, 153));
        IP.setText("Ingrese la IP");
        IP.setBorder(null);
        IP.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                IPMousePressed(evt);
            }
        });
        IP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                IPActionPerformed(evt);
            }
        });
        bg.add(IP, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 420, 40));
        bg.add(IpSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 420, -1));

        TxtPort.setFont(new java.awt.Font("Roboto Light", 1, 14)); // NOI18N
        TxtPort.setText("PUERTO");
        bg.add(TxtPort, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 270, 70, -1));

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
        bg.add(Port, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 300, 420, 40));
        bg.add(PortSeparator, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 340, 420, -1));

        Logo.setFont(new java.awt.Font("Yu Gothic UI Semibold", 0, 48)); // NOI18N
        Logo.setForeground(new java.awt.Color(51, 102, 255));
        Logo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/LOGO.png"))); // NOI18N
        Logo.setText("WHATSABLUE");
        bg.add(Logo, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 40, -1, -1));

        BtnAddContct.setBackground(new java.awt.Color(7, 134, 184));

        TxtAddContact.setFont(new java.awt.Font("Roboto Condensed", 1, 18)); // NOI18N
        TxtAddContact.setForeground(new java.awt.Color(255, 255, 255));
        TxtAddContact.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtAddContact.setText("AGREGAR");
        TxtAddContact.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtAddContact.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtAddContactMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtAddContactMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtAddContactMouseExited(evt);
            }
        });

        javax.swing.GroupLayout BtnAddContctLayout = new javax.swing.GroupLayout(BtnAddContct);
        BtnAddContct.setLayout(BtnAddContctLayout);
        BtnAddContctLayout.setHorizontalGroup(
            BtnAddContctLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TxtAddContact, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
        );
        BtnAddContctLayout.setVerticalGroup(
            BtnAddContctLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, BtnAddContctLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtAddContact, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bg.add(BtnAddContct, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 390, 120, 40));

        Fondo_Derecha.setBackground(new java.awt.Color(7, 134, 184));

        javax.swing.GroupLayout Fondo_DerechaLayout = new javax.swing.GroupLayout(Fondo_Derecha);
        Fondo_Derecha.setLayout(Fondo_DerechaLayout);
        Fondo_DerechaLayout.setHorizontalGroup(
            Fondo_DerechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 310, Short.MAX_VALUE)
        );
        Fondo_DerechaLayout.setVerticalGroup(
            Fondo_DerechaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 170, Short.MAX_VALUE)
        );

        bg.add(Fondo_Derecha, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 0, 310, 170));

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

        Exitbtn.setBackground(new java.awt.Color(153, 204, 255));
        Exitbtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ExitbtnMouseClicked(evt);
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

        javax.swing.GroupLayout ExitbtnLayout = new javax.swing.GroupLayout(Exitbtn);
        Exitbtn.setLayout(ExitbtnLayout);
        ExitbtnLayout.setHorizontalGroup(
            ExitbtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, ExitbtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        ExitbtnLayout.setVerticalGroup(
            ExitbtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ExitbtnLayout.createSequentialGroup()
                .addComponent(TxtExit, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Minimizebtn.setBackground(new java.awt.Color(153, 204, 255));
        Minimizebtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                MinimizebtnMouseClicked(evt);
            }
        });

        TxtMinimize.setBackground(new java.awt.Color(255, 255, 255));
        TxtMinimize.setFont(new java.awt.Font("Roboto Light", 0, 24)); // NOI18N
        TxtMinimize.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        TxtMinimize.setText("-");
        TxtMinimize.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        TxtMinimize.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                TxtMinimizeMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                TxtMinimizeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                TxtMinimizeMouseExited(evt);
            }
        });

        javax.swing.GroupLayout MinimizebtnLayout = new javax.swing.GroupLayout(Minimizebtn);
        Minimizebtn.setLayout(MinimizebtnLayout);
        MinimizebtnLayout.setHorizontalGroup(
            MinimizebtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MinimizebtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        MinimizebtnLayout.setVerticalGroup(
            MinimizebtnLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, MinimizebtnLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(TxtMinimize, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout headerLayout = new javax.swing.GroupLayout(header);
        header.setLayout(headerLayout);
        headerLayout.setHorizontalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(headerLayout.createSequentialGroup()
                .addComponent(Exitbtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Minimizebtn, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 694, Short.MAX_VALUE))
        );
        headerLayout.setVerticalGroup(
            headerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Exitbtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(Minimizebtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        bg.add(header, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 800, 40));

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 800, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(bg, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void IPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_IPActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_IPActionPerformed

    private void PortActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PortActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PortActionPerformed

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
        Exitbtn.setBackground(Color.red);
        TxtExit.setForeground(Color.white);
    }//GEN-LAST:event_TxtExitMouseEntered

    private void TxtExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtExitMouseExited
        Exitbtn.setBackground(new Color(153, 204, 255));
        TxtExit.setForeground(Color.black);
    }//GEN-LAST:event_TxtExitMouseExited

    private void ExitbtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ExitbtnMouseClicked
    }//GEN-LAST:event_ExitbtnMouseClicked

    private void IPMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IPMousePressed
        if(IP.getText().equals("Ingrese la IP")){
            IP.setText("");
            IP.setForeground(Color.black);
        }
        if(Port.getText().equals("")){
            Port.setText("Ingrese el puerto");
            Port.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_IPMousePressed

    private void PortMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_PortMousePressed
        if(Port.getText().equals("Ingrese el puerto")){
            Port.setText("");
            Port.setForeground(Color.black);
        }
        if(IP.getText().equals("")){
            IP.setText("Ingrese la IP");
            IP.setForeground(new Color(153,153,153));
        }
    }//GEN-LAST:event_PortMousePressed

    private void TxtGoChatMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtGoChatMouseClicked
        
     }//GEN-LAST:event_TxtGoChatMouseClicked

    private void TxtGoChatMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtGoChatMouseEntered
        BtnGoChat.setBackground(new Color(102,153,255));
    }//GEN-LAST:event_TxtGoChatMouseEntered

    private void TxtGoChatMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtGoChatMouseExited
        BtnGoChat.setBackground(new Color(7,134,184));
    }//GEN-LAST:event_TxtGoChatMouseExited

    private void TxtAddContactMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtAddContactMouseExited
        BtnAddContct.setBackground(new Color(7,134,184));
    }//GEN-LAST:event_TxtAddContactMouseExited

    private void TxtAddContactMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtAddContactMouseEntered
        BtnAddContct.setBackground(new Color(102,153,255));
    }//GEN-LAST:event_TxtAddContactMouseEntered

    private void TxtAddContactMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtAddContactMouseClicked
        
    }//GEN-LAST:event_TxtAddContactMouseClicked

    private void TxtMinimizeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMinimizeMouseClicked
        setState(javax.swing.JFrame.ICONIFIED);
    }//GEN-LAST:event_TxtMinimizeMouseClicked

    private void TxtMinimizeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMinimizeMouseEntered
        Minimizebtn.setBackground(Color.gray);
        TxtMinimize.setForeground(Color.white);
    }//GEN-LAST:event_TxtMinimizeMouseEntered

    private void TxtMinimizeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_TxtMinimizeMouseExited
        Minimizebtn.setBackground(new Color(153, 204, 255));
        TxtMinimize.setForeground(Color.black);
    }//GEN-LAST:event_TxtMinimizeMouseExited

    private void MinimizebtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MinimizebtnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_MinimizebtnMouseClicked

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
            java.util.logging.Logger.getLogger(AddContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddContact.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
                new AddContact().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BtnAddContct;
    private javax.swing.JPanel BtnGoChat;
    private javax.swing.JPanel Exitbtn;
    private javax.swing.JPanel Fondo_Derecha;
    private javax.swing.JTextField IP;
    private javax.swing.JLabel Image_Right;
    private javax.swing.JSeparator IpSeparator;
    private javax.swing.JLabel Logo;
    private javax.swing.JPanel Minimizebtn;
    private javax.swing.JTextField Port;
    private javax.swing.JSeparator PortSeparator;
    private javax.swing.JLabel TxtAddContact;
    private javax.swing.JLabel TxtAppChat;
    private javax.swing.JLabel TxtExit;
    private javax.swing.JLabel TxtGoChat;
    private javax.swing.JLabel TxtIP;
    private javax.swing.JLabel TxtMinimize;
    private javax.swing.JLabel TxtPort;
    private javax.swing.JLabel TxtTitle;
    private javax.swing.JPanel bg;
    private javax.swing.JPanel header;
    private javax.swing.JEditorPane jEditorPane1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
