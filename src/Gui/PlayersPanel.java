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
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import objects.Jugador;

/**
 *
 * @author Citlalli
 */
public class PlayersPanel extends JPanel{
    private Jugador jugadorPrincipal;
    private Jugador jugadorEnemigo;
    private PanelJugador pnlJugadorPrincipal;
    private PanelJugador pnlJugadorEnemigo;
    private JLabel minasTotales;
    private JPanel pnlMinasTotales;
    private BuscaminasMp buscaminas;
    public PlayersPanel(Jugador principal, Jugador enemigo, BuscaminasMp buscaminas) {
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        //super.setBackground(Color.BLACK);
        
        super.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        this.jugadorPrincipal = principal;
        this.jugadorEnemigo = enemigo;
        this.buscaminas = buscaminas;
        pnlJugadorPrincipal = new PanelJugador(principal);
        
        
        minasTotales = new JLabel(String.format("%d", this.buscaminas.getMinas()));
        minasTotales.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
        minasTotales.setForeground(Color.CYAN);
        
        pnlMinasTotales = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlMinasTotales.add(minasTotales);
        pnlMinasTotales.setBackground(new Color(134, 29, 215));
        pnlMinasTotales.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        
        pnlJugadorEnemigo = new PanelJugador(enemigo);
        
        super.add(pnlJugadorPrincipal);
        super.add(pnlMinasTotales);
        super.add(pnlJugadorEnemigo);
        
    }

    public Jugador getJugadorPrincipal() {
        return jugadorPrincipal;
    }

    public void setJugadorPrincipal(Jugador jugadorPrincipal) {
        this.jugadorPrincipal = jugadorPrincipal;
    }

    public Jugador getJugadorEnemigo() {
        return jugadorEnemigo;
    }

    public void setJugadorEnemigo(Jugador jugadorEnemigo) {
        this.jugadorEnemigo = jugadorEnemigo;
    }

    public PanelJugador getPnlJugadorPrincipal() {
        return pnlJugadorPrincipal;
    }

    public void setPnlJugadorPrincipal(PanelJugador pnlJugadorPrincipal) {
        this.pnlJugadorPrincipal = pnlJugadorPrincipal;
    }

    public PanelJugador getPnlJugadorEnemigo() {
        return pnlJugadorEnemigo;
    }

    public void setPnlJugadorEnemigo(PanelJugador pnlJugadorEnemigo) {
        this.pnlJugadorEnemigo = pnlJugadorEnemigo;
    }

    public JLabel getMinasTotales() {
        return minasTotales;
    }

    public void setMinasTotales(JLabel minasTotales) {
        this.minasTotales = minasTotales;
    }

    public JPanel getPnlMinasTotales() {
        return pnlMinasTotales;
    }

    public void setPnlMinasTotales(JPanel pnlMinasTotales) {
        this.pnlMinasTotales = pnlMinasTotales;
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
