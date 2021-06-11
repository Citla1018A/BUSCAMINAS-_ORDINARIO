/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javafx.scene.paint.Color;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import objects.Jugador;

/**
 *
 * @author Citlalli
 */
public class PanelJugador extends JPanel{
    private JLabel lblEquipo;
    private JPanel pnlEquipo;
    private Tlabel lblImagen;
    private JLabel lblNombre;
    private JPanel pnlNombre;
    private Tlabel lblBanderita;
    private JLabel lblPuntuación;
    private JPanel pnlPuntuacion;
    private JLabel lblTextoPuntuacion;
    private JPanel pnlImagen;
    private JLabel lblTurno;
    public PanelJugador(Jugador jugador){
        super.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        pnlPuntuacion = new JPanel(new FlowLayout());
        pnlNombre = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlEquipo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        lblNombre = new JLabel(jugador.getNombre());
        lblNombre.setFont(new Font("Arial", Font.BOLD, 13));
        lblTurno = new JLabel("");
        lblTurno.setFont(new Font("Arial", Font.BOLD, 13));
        pnlNombre.add(lblNombre);
        pnlNombre.add(lblTurno);
        switch(jugador.getEquipo()){
            case EquipoAzul:
               // super.setBackground(new java.awt.Color(127, 249, 253));
                super.setBackground(java.awt.Color.GRAY);
                //(new java.awt.Color(129, 143, 232));
                lblNombre.setForeground(new java.awt.Color(129, 143, 232));
                lblTurno.setForeground(new java.awt.Color(129, 143, 232));
                lblEquipo = new JLabel("Equipo Uno");
                lblEquipo.setFont(new Font("Arial", Font.BOLD, 13));
                lblEquipo.setForeground(java.awt.Color.CYAN);
                pnlEquipo.add(lblEquipo);
                pnlEquipo.setBackground(new java.awt.Color(129, 143, 232));
                pnlNombre.setBackground(new java.awt.Color(129, 143, 232));
                pnlNombre.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                lblImagen = new Tlabel("/images/bmnas.png");
                lblImagen.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                pnlImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
                pnlImagen.add(lblImagen);
                pnlImagen.setBackground(new java.awt.Color(127, 249, 253));
                lblBanderita = new Tlabel("/images/bom.png");
                lblPuntuación = new JLabel("0");
                lblPuntuación.setFont(new Font("Arial", Font.BOLD, 14));
                lblPuntuación.setForeground(java.awt.Color.BLUE);
                lblTextoPuntuacion = new JLabel("Puntuación");
                lblTextoPuntuacion.setForeground(java.awt.Color.blue);
                lblTextoPuntuacion.setFont(new Font("Arial", Font.ITALIC, 13));
                pnlPuntuacion.add(lblTextoPuntuacion);
                pnlPuntuacion.add(lblBanderita);
                pnlPuntuacion.add(lblPuntuación);
                pnlPuntuacion.setBackground(new java.awt.Color(127, 249, 253));
                super.add(pnlEquipo);
                super.add(pnlImagen);
                super.add(pnlNombre);
                super.add(pnlPuntuacion);
                break;
            case EquipoRojo:
                super.setBackground(new java.awt.Color(252, 96, 102));
                lblNombre.setForeground(new java.awt.Color(232, 129, 132));
                lblTurno.setForeground(new java.awt.Color(232, 129, 132));
                System.out.println("ENTROADOR");
                lblEquipo = new JLabel("Equipo Dos");
                lblEquipo.setFont(new Font("Arial", Font.BOLD, 13));
                lblEquipo.setForeground(new java.awt.Color(142, 0, 5));
                pnlEquipo.add(lblEquipo);
                pnlEquipo.setBackground(new java.awt.Color(252, 96, 102));
                pnlNombre.setBackground(new java.awt.Color(179, 1, 1));
                pnlNombre.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                lblImagen = new Tlabel("/images/otro.png");
                lblImagen.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                pnlImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
                pnlImagen.add(lblImagen);
                pnlImagen.setBackground(new java.awt.Color(252, 96, 102));
                lblBanderita = new Tlabel("/images/bib.jpg");
                lblPuntuación = new JLabel("0");
                lblPuntuación.setForeground(new java.awt.Color(142, 0, 5));
                lblPuntuación.setFont(new Font("Arial", Font.BOLD, 14));
                lblTextoPuntuacion = new JLabel("Puntuación");
                lblTextoPuntuacion.setForeground(new java.awt.Color(142, 0, 5));
                lblTextoPuntuacion.setFont(new Font("Arial", Font.ITALIC, 13));
                pnlPuntuacion.add(lblTextoPuntuacion);
                pnlPuntuacion.add(lblBanderita);
                pnlPuntuacion.add(lblPuntuación);
                pnlPuntuacion.setBackground(new java.awt.Color(252, 96, 102));
                super.add(pnlEquipo);
                super.add(pnlImagen);
                super.add(pnlNombre);
                super.add(pnlPuntuacion);
                break;
        }
    }

    public JLabel getLblEquipo() {
        return lblEquipo;
    }

    public void setLblEquipo(JLabel lblEquipo) {
        this.lblEquipo = lblEquipo;
    }

    public Tlabel getLblImagen() {
        return lblImagen;
    }

    public void setLblImagen(Tlabel lblImagen) {
        this.lblImagen = lblImagen;
    }

    public Tlabel getLblBanderita() {
        return lblBanderita;
    }

    public void setLblBanderita(Tlabel lblBanderita) {
        this.lblBanderita = lblBanderita;
    }

    public JLabel getLblPuntuación() {
        return lblPuntuación;
    }

    public void setLblPuntuación(JLabel lblPuntuación) {
        this.lblPuntuación = lblPuntuación;
    }

    public JPanel getPnlPuntuacion() {
        return pnlPuntuacion;
    }

    public void setPnlPuntuacion(JPanel pnlPuntuacion) {
        this.pnlPuntuacion = pnlPuntuacion;
    }

    public JPanel getPnlEquipo() {
        return pnlEquipo;
    }

    public void setPnlEquipo(JPanel pnlEquipo) {
        this.pnlEquipo = pnlEquipo;
    }

    public JLabel getLblNombre() {
        return lblNombre;
    }

    public void setLblNombre(JLabel lblNombre) {
        this.lblNombre = lblNombre;
    }

    public JPanel getPnlNombre() {
        return pnlNombre;
    }

    public void setPnlNombre(JPanel pnlNombre) {
        this.pnlNombre = pnlNombre;
    }

    public JLabel getLblTextoPuntuacion() {
        return lblTextoPuntuacion;
    }

    public void setLblTextoPuntuacion(JLabel lblTextoPuntuacion) {
        this.lblTextoPuntuacion = lblTextoPuntuacion;
    }

    public JPanel getPnlImagen() {
        return pnlImagen;
    }

    public void setPnlImagen(JPanel pnlImagen) {
        this.pnlImagen = pnlImagen;
    }

    /**
     * @return the lblTurno
     */
    public JLabel getLblTurno() {
        return lblTurno;
    }

    /**
     * @param lblTurno the lblTurno to set
     */
    public void setLblTurno(JLabel lblTurno) {
        this.lblTurno = lblTurno;
    }
}
