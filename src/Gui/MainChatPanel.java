/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import EnviarListener.btnEnviarListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import objects.chat.RecibidorChat;

/**
 *
 * @author Citlalli
 */
public class MainChatPanel extends JPanel{
    private PanelChat pnlPrincipal;
    private String nombreUsuario;
    private JPanel north;
    private JPanel south;
    private JPanel west;
    private JPanel east;
    private Socket socket;
    private DataOutputStream writer;
    private RecibidorChat recibidor;
    
    public MainChatPanel(String nickname, String direccion) throws IOException{
        super.setLayout(new BorderLayout());
        //super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setPreferredSize(new Dimension(480, 480));
        //super.setResizable(false);
        //super.setLocationRelativeTo(null);
        //Declaracion del socket y el output
        socket = new Socket(direccion, 1236);
        System.out.println("-");
        writer = new DataOutputStream(socket.getOutputStream());
        nombreUsuario = nickname;
        pnlPrincipal = new PanelChat();
        recibidor = new RecibidorChat(socket, pnlPrincipal.getTxtShowChat(), this);
        //pnlPrincipal.setBackground(Color.yellow);
        pnlPrincipal.setListener(new btnEnviarListener() {
            @Override
            public void onClick(String text) {
                try {
                    Date date = new GregorianCalendar().getTime();
                    writer.writeUTF(String.format("%s (at %d:%d:%d): %s\n", nombreUsuario,
                            date.getHours(),
                            date.getMinutes(),
                            date.getSeconds(), text));
                    //pnlPrincipal.getTxtShowChat().append();
                } catch (IOException ex) {
                    Logger.getLogger(MainChatPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        recibidor.start();
        
        north = new JPanel();
        north.setBackground(Color.red);
        south = new JPanel();
        south.setBackground(Color.black);
        west = new JPanel();
        west.setBackground(Color.blue);
        east = new JPanel();
        east.setBackground(Color.gray);
        
        super.add(pnlPrincipal, BorderLayout.CENTER);
        super.add(north,BorderLayout.NORTH);
        super.add(south, BorderLayout.SOUTH);
        super.add(west, BorderLayout.WEST);
        super.add(east, BorderLayout.EAST);
        super.setVisible(true);
        
        
    }

    public PanelChat getPnlPrincipal() {
        return pnlPrincipal;
    }

    public void setPnlPrincipal(PanelChat pnlPrincipal) {
        this.pnlPrincipal = pnlPrincipal;
    }
}
