/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import buscaminasobjects.BuscaminasMp;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import objects.Equipo;

/**
 *
 * @author Citlalli
 */
public class JuegoTerminadoDialog extends JDialog{
    private JPanel pnlPrincipal;
    private JPanel pnlBtns;
    private JButton btnAceptar;
    private JButton btnCancelar;
    private PrincipalFrame parent;
    private JLabel lblPregunta;
    private JLabel estado;
    public JuegoTerminadoDialog(PrincipalFrame parent, ObjectOutputStream writer,
            ObjectInputStream reader){
        super(parent, "Nuevo juego", true);
        this.parent = parent;
                
        super.setAlwaysOnTop(true);
        //super.setSize(800, 600);
        super.setDefaultCloseOperation(HIDE_ON_CLOSE);
        super.setLayout(new FlowLayout());
        super.setSize(380, 150);
        super.setResizable(false);
        super.setLocationRelativeTo(parent);
        super.setAlwaysOnTop(rootPaneCheckingEnabled);
        
        lblPregunta = new JLabel("¿Iniciar nuevo juego?");
        lblPregunta.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        switch (parent.getJugador().getEquipo()) {
            case EquipoAzul:
                lblPregunta.setForeground(Color.BLUE);
                break;
            case EquipoRojo:
                lblPregunta.setForeground(Color.RED);
                break;
            default:
                throw new AssertionError();
        }
        estado = new JLabel("Esperando");
        pnlPrincipal = new JPanel();
        pnlPrincipal.setLayout(new FlowLayout(FlowLayout.CENTER));
        pnlPrincipal.add(lblPregunta);
        pnlPrincipal.add(estado);
                
        btnAceptar = new JButton("Aceptar");
        btnAceptar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    estado.setText("Esperando");

                    if(parent.getJugador().getEquipo() == Equipo.EquipoAzul){
                        writer.writeObject(true);
                        if ((Boolean) reader.readObject() == true) {
                            BuscaminasMp buscaminas = (BuscaminasMp) reader.readObject();
                            estado.setText("Iniciando nuevo Juego");
                            Thread.sleep(3000);
                            System.out.println("---------");
                            parent.iniciarNuevoJuego(buscaminas);
                            JuegoTerminadoDialog.this.setVisible(false);
                        } else {
                            parent.setVisible(false);
                            System.exit(0);
                        }
                    } else {
                        if((Boolean)reader.readObject() == true){
                            writer.writeObject(true);
                            BuscaminasMp buscaminas = (BuscaminasMp) reader.readObject();
                            estado.setText("Iniciando Nuevo Juego");
                            Thread.sleep(1000);
                            //parent.setIsMyTurn((Boolean) reader.readObject());
                            System.out.println("-------------");
                            parent.iniciarNuevoJuego(buscaminas);
                            JuegoTerminadoDialog.this.setVisible(false);
                        }else{
                            writer.writeObject(false);
                            parent.setVisible(false);
                            System.exit(0);
                        }
                    }
                } catch (IOException | ClassNotFoundException | InterruptedException ex) {
                    Logger.getLogger(JuegoTerminadoDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        btnCancelar = new JButton("Cancelar");
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if(parent.getJugador().getEquipo() == Equipo.EquipoAzul){
                                    try {
                    writer.writeObject(false);
                } catch (IOException ex) {
                    Logger.getLogger(JuegoTerminadoDialog.class.getName()).log(Level.SEVERE, null, ex);
                }
                   JuegoTerminadoDialog.this.setVisible(false);
                   parent.setVisible(false);
                   System.exit(0);
                }else{
                    try {
                        writer.writeObject(false);
                        parent.setVisible(false);
                        if((Boolean)reader.readObject() == true){;
                            System.exit(0);
                        }
                    } catch (IOException | ClassNotFoundException ex) {
                        Logger.getLogger(JuegoTerminadoDialog.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            }
        });
        pnlBtns = new JPanel(new FlowLayout());
        pnlBtns.add(btnAceptar);
        pnlBtns.add(btnCancelar);
        super.add(pnlPrincipal);
        super.add(pnlBtns);
        super.setVisible(true);
    }
}
