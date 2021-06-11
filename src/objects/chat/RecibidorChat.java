/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects.chat;

import Gui.MainChatPanel;
import java.awt.Point;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;

/**
 *
 * @author Citlalli
 */
public class RecibidorChat extends Thread{
    private Socket socket;
    private DataInputStream reader;
    private JTextArea txtChat;
    MainChatPanel parent;
    public RecibidorChat(Socket socket, JTextArea txtChat, MainChatPanel parent) throws IOException{
        this.socket = socket;
        this.txtChat = txtChat;
        this.parent = parent;
        reader = new DataInputStream(socket.getInputStream());
    }

    @Override
    public void run() {
        while(true){
            try {
                
                String strFileContents = null;
                strFileContents = reader.readUTF();
                if(strFileContents != null){
                    txtChat.append(String.format("%s\n", strFileContents));
                    parent.getPnlPrincipal().getScrollChat().getViewport().setViewPosition(
                    new Point(0, txtChat.getDocument().getLength()));
                    
                }
                
            } catch (IOException ex) {
                Logger.getLogger(RecibidorChat.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
