/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import buscaminasobjects.BuscaminasMp;
import Gui.listener.TableroListener;
import Threads.Receptor;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objects.Equipo;
import objects.GameEst;
import objects.Jugador;

/**
 *
 * @author Citlalli
 */
public class PrincipalFrame extends JFrame{
    private BuscaminasMp buscaminas;
    private TableroPanel pnlTablero;
    private PlayersPanel pnlJugadores;
    private Socket socket;
    private Socket socketNewGame;
    private Jugador jugador;
    private Jugador jugadorEnemigo;
    private Boolean isMyTurn;
    private ObjectOutputStream writer;
    private ObjectInputStream reader;
    private ObjectOutputStream writerNew;
    private ObjectInputStream readerNew;
    private Receptor receptor;
    private MainChatPanel chat;
    public PrincipalFrame(String nombre, String direccion) throws IOException, ClassNotFoundException{
        super("BUSCAMINAS");
        super.setLayout(new FlowLayout(FlowLayout.LEFT));
        super.setDefaultCloseOperation(EXIT_ON_CLOSE);
        super.setSize(new Dimension(1200, 520));
        super.setResizable(false);
        super.setLocationRelativeTo(null);
        super.setBackground(Color.black);
        chat = new MainChatPanel(nombre, direccion);
        socket = new Socket(direccion, 1235);
        socketNewGame = new Socket(direccion, 1235);
        writerNew = new ObjectOutputStream(socketNewGame.getOutputStream());
        readerNew = new ObjectInputStream(socketNewGame.getInputStream());
        System.out.println("Se conectaron");
        writer = new ObjectOutputStream(socket.getOutputStream());
        writer.writeObject(nombre);
        
        reader = new ObjectInputStream(socket.getInputStream());
        
        jugador = (Jugador)reader.readObject();
        switch(jugador.getEquipo()){
            case EquipoAzul:
                jugadorEnemigo = new Jugador("Esperando jugador...", Equipo.EquipoRojo);
                break;
            case EquipoRojo:
                jugadorEnemigo = new Jugador("Esperando jugador...", Equipo.EquipoAzul);
                break;
            default:
                    throw new AssertionError();
        }
        System.out.println(": "+jugador.getEquipo());
        isMyTurn = (Boolean)reader.readObject();
        System.out.println(":"+isMyTurn.toString());
        
        
        
        
        //juego
        this.buscaminas = (BuscaminasMp)reader.readObject();
        
        //Modo JUGANDO
        buscaminas.setJuego(GameEst.JUGANDO);
        //JUGADORES
        
        
        //PANEL JUGADORES
        pnlJugadores = new PlayersPanel(jugador, jugadorEnemigo, buscaminas);
        
        pnlJugadores.setPreferredSize(new Dimension(200,480));
        //PANEL TABLERO
        pnlTablero = new TableroPanel(new ImageIcon("fondo.png"));
        pnlTablero.setPreferredSize(new Dimension(480, 480));
        pnlTablero.drawTablero(this.buscaminas);
        pnlTablero.setListener(new TableroListener() {
            @Override
            public void btnCasillaOnClick(Integer x, Integer y) {
                
                if (getIsMyTurn()) {
                    System.out.printf("Casilla se abre en [%d][%d]", x, y);
                    try {
                        setIsMyTurn(buscaminas.abrirCelda(x, y, jugador, writer));
                        if (buscaminas.getEstado() == GameEst.JUGANDO || buscaminas.getEstado() == GameEst.TERMINADO) {
                            getPnlTablero().removeAll();
                            getPnlTablero().drawTablero(buscaminas);
                            PrincipalFrame.this.repaint();
                        } else {
                            System.out.println(buscaminas.getEstado());
                        }
                        if(!isMyTurn){
                            getPnlJugadores().getPnlJugadorEnemigo().getLblTurno().setText("- Jugando...");
                            getPnlJugadores().getPnlJugadorPrincipal().getLblTurno().setText("- Espera...");
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(PrincipalFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    updatePuntuaciones();
                    updateMinas();
                    if (buscaminas.getBlueFlagCount() > 25 || buscaminas.getRedFlagCount() > 25
                                    && buscaminas.getJuego() != GameEst.TERMINADO) {
                                System.out.println("JUEGO");
                                buscaminas.setJuego(GameEst.TERMINADO);
                                PrincipalFrame.this.terminarJuego();
                                JuegoTerminadoDialog terminado = new JuegoTerminadoDialog(PrincipalFrame.this, writerNew, readerNew);
                                //buscaminas.setJuego(GameEst.JUGANDO);
                            }
                }else{
                    
                    System.out.println("Turno opuesto");
                }
                
                
            }
            @Override
            public void onRightClickButton(Integer x, Integer y){
               System.out.printf("Se abrío en  [%d][%d]",x,y);
               /*buscaminas.marcarCelda(x, y);
               pnlTablero.removeAll();
               pnlTablero.drawTablero(buscaminas);
               PrincipalFrame.this.repaint();
                */
               /*  
               SI GANO O PERDIO, EL CAMBIO DEL ICONITO, LENTES O MUERTO
               */
            }
        });
        super.add(chat);
        super.add(pnlJugadores);
        super.add(pnlTablero);
        receptor = new Receptor(getJugador(), this.buscaminas, reader, readerNew, writerNew, this);
        System.out.println("Creo el receptor");
        receptor.start();
        System.out.println(" _ "+jugador.getEquipo());
        System.out.println("creado");
        super.setVisible(true);
    }
    public void iniciarNuevoJuego(BuscaminasMp NuevoBuscaminas){
        NuevoBuscaminas.setJuego(GameEst.JUGANDO);
        this.buscaminas = NuevoBuscaminas;
        pnlJugadores.setBuscaminas(NuevoBuscaminas);
        receptor.setBuscaminas(NuevoBuscaminas);
        //juez.setBuscaminas(buscaminas);
        pnlTablero.removeAll();
        pnlTablero.drawTablero(NuevoBuscaminas);
        if (getIsMyTurn() == true) {
            this.getPnlJugadores().getPnlJugadorEnemigo().getLblTurno().setText("Esperando...");
            this.getPnlJugadores().getPnlJugadorPrincipal().getLblTurno().setText("- Tu turno");

        } else {
            this.getPnlJugadores().getPnlJugadorEnemigo().getLblTurno().setText("- Jugando...");

        }
        PrincipalFrame.this.repaint();
        updateMinas();
        updatePuntuaciones();
    }
    public void terminarJuego() {
        buscaminas.setJuego(GameEst.TERMINADO);
        pnlTablero.removeAll();
        pnlTablero.drawTablero(buscaminas);
        PrincipalFrame.this.repaint();
        if (buscaminas.getBlueFlagCount() > buscaminas.getRedFlagCount()) {
            if (jugador.getEquipo() == Equipo.EquipoAzul) {
                JOptionPane.showMessageDialog(this, "GANADOR", "Juego terminado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ganó " + this.getPnlJugadores().getPnlJugadorEnemigo().getLblNombre().getText(),
                         "Juego terminado ",
                         JOptionPane.INFORMATION_MESSAGE);
            }
        }
        if (buscaminas.getBlueFlagCount() < buscaminas.getRedFlagCount()) {
            if (jugador.getEquipo() == Equipo.EquipoRojo) {
                JOptionPane.showMessageDialog(this, "GANADOR", "Juego terminado", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Ganó " + this.getPnlJugadores().getPnlJugadorEnemigo().getLblNombre().getText(),
                        "Juego terminado",
                         JOptionPane.INFORMATION_MESSAGE);
            }
            //JuegoTerminadoDialog terminado = new JuegoTerminadoDialog(this);
        }
    }
    public void updateMinas(){
        pnlJugadores.getMinasTotales().setText(String.format("%d", buscaminas.minasTotales()));
    }
    public void updatePuntuaciones(){
       switch(jugador.getEquipo()){
           case EquipoAzul:
               pnlJugadores.getPnlJugadorPrincipal().getLblPuntuación().setText(String.format("%d", buscaminas.getBlueFlagCount()));
               pnlJugadores.getPnlJugadorEnemigo().getLblPuntuación().setText(String.format("%d", buscaminas.getRedFlagCount()));
               break;
           case EquipoRojo:
               pnlJugadores.getPnlJugadorPrincipal().getLblPuntuación().setText(String.format("%d", buscaminas.getRedFlagCount()));
               pnlJugadores.getPnlJugadorEnemigo().getLblPuntuación().setText(String.format("%d", buscaminas.getBlueFlagCount()));
               break;
       }
    }
        

    public TableroPanel getPnlTablero() {
        return pnlTablero;
    }

    public void setPnlTablero(TableroPanel pnlTablero) {
        this.pnlTablero = pnlTablero;
    }

    /**
     * @return the isMyTurn
     */
    public Boolean getIsMyTurn() {
        return isMyTurn;
    }

    /**
     * @param isMyTurn the isMyTurn to set
     */
    public void setIsMyTurn(Boolean isMyTurn) {
        this.isMyTurn = isMyTurn;
    }

    /**
     * @return the jugadorEnemigo
     */
    public Jugador getJugadorEnemigo() {
        return jugadorEnemigo;
    }

    /**
     * @param jugadorEnemigo the jugadorEnemigo to set
     */
    public void setJugadorEnemigo(Jugador jugadorEnemigo) {
        this.jugadorEnemigo = jugadorEnemigo;
    }

    /**
     * @return the jugador
     */
    public Jugador getJugador() {
        return jugador;
    }

    /**
     * @param jugador the jugador to set
     */
    public void setJugador(Jugador jugador) {
        this.jugador = jugador;
    }

    /**
     * @return the pnlJugadores
     */
    public PlayersPanel getPnlJugadores() {
        return pnlJugadores;
    }

    /**
     * @param pnlJugadores the pnlJugadores to set
     */
    public void setPnlJugadores(PlayersPanel pnlJugadores) {
        this.pnlJugadores = pnlJugadores;
    }
}
