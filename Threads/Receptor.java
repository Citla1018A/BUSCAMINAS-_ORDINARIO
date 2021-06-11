/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Threads;

import Gui.JuegoTerminadoDialog;
import Gui.PlayersPanel;
import Gui.PrincipalFrame;
import buscaminasobjects.BuscaminasMp;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.Parent;
import objects.Coordenada;
import objects.GameEst;
import objects.Jugador;

/**
 *
 * @author Citlalli
 */
public class Receptor extends Thread{
    private Jugador enemy;
    private BuscaminasMp buscaminas;
    private ObjectInputStream reader;
    private ObjectInputStream readerNewGame;
    private ObjectOutputStream writerNewGame;
    private PrincipalFrame parent;
    public Receptor(Jugador player, BuscaminasMp buscaminas, 
            ObjectInputStream reader, ObjectInputStream readerNewGame, ObjectOutputStream writerNewGame, PrincipalFrame parent) throws IOException {
        this.buscaminas = buscaminas;
        this.parent = parent;
        this.enemy = null;
        this.reader = reader;
        this.writerNewGame = writerNewGame;
        this.readerNewGame = readerNewGame;
    }

    @Override
    public void run() {
        try {
            
            Jugador enemigo = (Jugador)reader.readObject();
            enemy = enemigo;
            parent.getJugadorEnemigo().setNombre(enemigo.getNombre()); 
            parent.getPnlJugadores().getPnlJugadorEnemigo().getLblNombre().setText(enemigo.getNombre());
            if(parent.getIsMyTurn()){
            parent.getPnlJugadores().getPnlJugadorPrincipal().getLblTurno().setText("- Tu turno");
            parent.getPnlJugadores().getPnlJugadorEnemigo().getLblTurno().setText("- Esperando...");
        }else{
            parent.getPnlJugadores().getPnlJugadorPrincipal().getLblTurno().setText("- Espera...");
            parent.getPnlJugadores().getPnlJugadorEnemigo().getLblTurno().setText("- Jugando...");
        }
            parent.repaint();
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while(true){
            try {
                System.out.println("ESPERE....");
                Boolean turno = (Boolean)reader.readObject();
                System.out.println(": "+turno.toString());
                Coordenada cord = (Coordenada)reader.readObject();
                
                System.out.println("PUNTUACIÓN: "+cord.getPlayer().getPuntaje());
                getBuscaminas().abrirCelda(cord.getX(), cord.getY(), cord.getPlayer());
                parent.updateMinas();
                parent.updatePuntuaciones();
                parent.setIsMyTurn(turno);
                if(turno == true){
                    parent.getPnlJugadores().getPnlJugadorEnemigo().getLblTurno().setText("Esperando...");
                    parent.getPnlJugadores().getPnlJugadorPrincipal().getLblTurno().setText("- Tu turno");
                }else{
                    parent.getPnlJugadores().getPnlJugadorEnemigo().getLblTurno().setText("- Jugando...");
                }
                parent.getPnlTablero().removeAll();
                parent.getPnlTablero().drawTablero(getBuscaminas());
                parent.repaint();
            } catch (IOException ex) {
                Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(Receptor.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (getBuscaminas().getBlueFlagCount() > 25 || getBuscaminas().getRedFlagCount() > 25
                    && getBuscaminas().getJuego() != GameEst.TERMINADO) {
                System.out.println("DETECTAR");
                getBuscaminas().setJuego(GameEst.TERMINADO);
                parent.terminarJuego();
                JuegoTerminadoDialog terminado = new JuegoTerminadoDialog(parent, writerNewGame, readerNewGame);
            }
        }
    }

    public Jugador getEnemy() {
        return enemy;
    }

    public void setEnemy(Jugador enemy) {
        this.enemy = enemy;
    }

    /**
     * @return the buscaminas
     */
    public BuscaminasMp getBuscaminas() {
        return buscaminas;
    }

    /**
     * @param buscaminas the buscaminas to set
     */
    public void setBuscaminas(BuscaminasMp buscaminas) {
        this.buscaminas = buscaminas;
    }
    
}
