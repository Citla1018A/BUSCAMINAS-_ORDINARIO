/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import EnviarListener.btnEnviarListener;
import com.sun.imageio.plugins.jpeg.JPEG;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.net.Socket;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.BevelBorder;

/**
 *
 * @author Citlalli
 */
public class PanelChat extends JPanel{
    private JPanel pnlChat;
    private JPanel pnlEdit;
    private JButton btnSendMsg;
    private JTextArea txtEditText;
    private JTextArea txtShowChat;
    private btnEnviarListener listener;
    private JScrollPane scrollChat;
    private JScrollPane scrollEdt;
    public PanelChat(){
        super.setLayout(new FlowLayout());
        pnlChat = new JPanel();
        pnlChat.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlChat.setPreferredSize(new Dimension(460, 350));
        pnlChat.setBorder(new BevelBorder(BevelBorder.LOWERED));
        pnlEdit = new JPanel();
        pnlEdit.setLayout(new FlowLayout(FlowLayout.LEFT));
        pnlEdit.setPreferredSize(new Dimension(460, 100));
        
        //Inicializacion de componentes
        btnSendMsg = new JButton("ENVIAR");
        btnSendMsg.setPreferredSize(new Dimension(80, 50));
        btnSendMsg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {

                getListener().onClick(getTxtEditText().getText());
                txtEditText.setText("");
            }
        });
        txtEditText = new JTextArea(4, 30);
        txtEditText.setLineWrap(true);
        txtEditText.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        //txtEditText.setPreferredSize(new Dimension(350, 80));
        txtEditText.setBorder(new BevelBorder(BevelBorder.LOWERED));
        scrollEdt = new JScrollPane(txtEditText);
        scrollEdt.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        txtEditText.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent ke) {
            }
            @Override
            public void keyPressed(KeyEvent ke) {
                if(ke.getKeyCode()==KeyEvent.VK_ENTER){
                    getListener().onClick(getTxtEditText().getText());
                    txtEditText.setText("");
                    
                }
            }
            @Override
            public void keyReleased(KeyEvent ke) {
            }

            
        });
        txtShowChat = new JTextArea(22, 40);
        txtShowChat.setLineWrap(true);
        txtShowChat.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        //txtShowChat.setPreferredSize(new Dimension(420, 340));
        txtShowChat.setEditable(false);
        scrollChat = new JScrollPane(txtShowChat, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        
        //agregamos componentes a paneles
        pnlChat.add(scrollChat);
        pnlEdit.add(scrollEdt);
        pnlEdit.add(btnSendMsg);
        super.add(scrollChat);
        super.add(pnlEdit);
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(btnEnviarListener listener) {
        this.listener = listener;
    }

    /**
     * @return the pnlChat
     */
    public JPanel getPnlChat() {
        return pnlChat;
    }

    /**
     * @param pnlChat the pnlChat to set
     */
    public void setPnlChat(JPanel pnlChat) {
        this.pnlChat = pnlChat;
    }

    /**
     * @return the pnlEdit
     */
    public JPanel getPnlEdit() {
        return pnlEdit;
    }

    /**
     * @param pnlEdit the pnlEdit to set
     */
    public void setPnlEdit(JPanel pnlEdit) {
        this.pnlEdit = pnlEdit;
    }

    /**
     * @return the btnSendMsg
     */
    public JButton getBtnSendMsg() {
        return btnSendMsg;
    }

    /**
     * @param btnSendMsg the btnSendMsg to set
     */
    public void setBtnSendMsg(JButton btnSendMsg) {
        this.btnSendMsg = btnSendMsg;
    }

    /**
     * @return the txtEditText
     */
    public JTextArea getTxtEditText() {
        return txtEditText;
    }

    /**
     * @param txtEditText the txtEditText to set
     */
    public void setTxtEditText(JTextArea txtEditText) {
        this.txtEditText = txtEditText;
    }

    

    /**
     * @return the listener
     */
    public btnEnviarListener getListener() {
        return listener;
    }

    /**
     * @return the txtShowChat
     */
    public JTextArea getTxtShowChat() {
        return txtShowChat;
    }

    /**
     * @param txtShowChat the txtShowChat to set
     */
    public void setTxtShowChat(JTextArea txtShowChat) {
        this.txtShowChat = txtShowChat;
    }

    /**
     * @return the scrollChat
     */
    public JScrollPane getScrollChat() {
        return scrollChat;
    }

    /**
     * @param scrollChat the scrollChat to set
     */
    public void setScrollChat(JScrollPane scrollChat) {
        this.scrollChat = scrollChat;
    }
    
}
