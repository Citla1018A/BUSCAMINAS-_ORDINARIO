/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

import objects.Celda;
import objects.CeldaEstado;
import buscaminasobjects.BuscaminasMp;
import Gui.listener.TableroListener;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javafx.scene.layout.Border;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import objects.GameEst;
/**
 *
 * @author Citlalli
 */
public class TableroPanel extends JPanel{
    private TableroListener listener;
    private final Integer BORDE = 10; //10
    private final Integer DISTANCIA = 29; //29
    private final Integer TAMANIO_CELDA = 28; //28
    private JLabel fondo;
    public TableroPanel(ImageIcon imagenFondo){
        super();
        fondo = new JLabel();
        fondo.setLayout(new BorderLayout());
        fondo.setIcon(imagenFondo);
        super.setBackground(Color.BLACK);
        super.setLayout(null);
        super.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
    }
    
    public void drawTablero(BuscaminasMp buscaminas){
        for(int i=0; i<buscaminas.getDimx(); i++){
            for(int j=0; j<buscaminas.getDimy(); j++){
                Celda celda = buscaminas.getTablero()[j][i];
                if(celda.getEstado() == CeldaEstado.ABIERTO){
                    Tlabel abierto = null;
                 
                    switch (celda.getNumero()) {
                        case 0:
                            abierto = new Tlabel("/images/zeroCell.png");
                            break;
                        case 1:
                            //abierto.setForeground(new Color(32, 35, 137));
                            abierto = new Tlabel("/images/oneCell.png");
                            break;
                        case 2:
                            abierto = new Tlabel("/images/twoCell.png");
                            //abierto.setForeground(new Color(10, 102, 10));
                            break;
                        case 3:
                            abierto = new Tlabel("/images/threeCell.png");
                            //abierto.setForeground(Color.RED);
                            break;
                        case 4:
                            abierto = new Tlabel("/images/fourCell.png");
                            //abierto.setForeground(Color.BLUE);
                            break;
                        case 5:
                            abierto = new Tlabel("/images/fiveCell.png");
                            //abierto.setForeground(new Color(102, 44, 11));
                            break;
                        case 6:
                            abierto = new Tlabel("/images/sixCell.png");
                            //abierto.setForeground(new Color(104, 57, 22));
                            break;
                        case 7:
                            abierto = new Tlabel("/images/sevenCell.png");
                            //abierto.setForeground(new Color(91, 15, 15));
                            break;
                        case 8:
                            abierto = new Tlabel("/images/eightCell.png");
                            //abierto.setForeground(Color.BLACK);
                            break;
                        default:
                            throw new AssertionError();

                    }                 
                abierto.setBounds(j*DISTANCIA+BORDE, i*DISTANCIA+BORDE, TAMANIO_CELDA, TAMANIO_CELDA);
                abierto.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                
                //abierto.setVisible(true);
                super.add(abierto);      
                }else if (celda.getEstado() == CeldaEstado.BLUEFLAG || celda.getEstado() == CeldaEstado.REDFLAG) {
                    if (celda.getEstado() == CeldaEstado.REDFLAG) {
                        //System.out.println("ENTRO IF DE REDFLAG");
                        Tlabel flag = new Tlabel("/images/banderaRoja.png");

                        flag.setBounds(j * DISTANCIA + BORDE, i * DISTANCIA + BORDE, TAMANIO_CELDA, TAMANIO_CELDA);
                        flag.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                        super.add(flag);
                    }
                    if (celda.getEstado() == CeldaEstado.BLUEFLAG) {
                        //System.out.println("ENTRO IF DE REDFLAG");
                        Tlabel flag = new Tlabel("/images/banderaAzul.png");

                        flag.setBounds(j * DISTANCIA + BORDE, i * DISTANCIA + BORDE, TAMANIO_CELDA, TAMANIO_CELDA);
                        flag.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
                        super.add(flag);
                    }
                    } else if (buscaminas.getEstado() == GameEst.TERMINADO) {
                        /*Si ya pasamos por los casos en que el tablero está abierto y blueflag red-flag, no tiene caso verificar si esta cerrado*/
                        if (/*buscaminas.getTablero()[i][j].getEstado() == CeldaEstado.CERRADO
                                && */buscaminas.getTablero()[j][i].isMina()) {
                            TButton cerrado = new TButton(celda, j, i, "/images/mina.png");
                            cerrado.setBounds(j * DISTANCIA + BORDE, i * DISTANCIA + BORDE, TAMANIO_CELDA, TAMANIO_CELDA);
                            cerrado.setBackground(new Color(29, 178, 215));
                            //cerrado.setIcon(new ImageIcon("/images/closedCell.png"));
                            cerrado.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                            /*Si el juego ya termino no tiene caso agregarle los action listeners.................*/
                            /*cerrado.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent ae) {
                                    listener.btnCasillaOnClick(cerrado.getXx(), cerrado.getYy());
                                }
                            });*/
                            /*
                            cerrado.addMouseListener(new MouseAdapter() {
                                @Override
                                public void mousePressed(MouseEvent evt) {
                                    if (evt.getButton() == MouseEvent.BUTTON3) {
                                        //System.out.println("CAMBIAR ESTADO");
                                        //listener.onRightClickButton(cerrado.getXx(), cerrado.getYy());
                                    }
                                }
                            });*/
                            super.add(cerrado);
                        } else {
                            TButton cerrado = new TButton(celda, j, i, "/images/closedCell.png");
                            cerrado.setBounds(j * DISTANCIA + BORDE, i * DISTANCIA + BORDE, TAMANIO_CELDA, TAMANIO_CELDA);
                            cerrado.setBackground(new Color(29, 178, 215));
                            //cerrado.setIcon(new ImageIcon("/images/closedCell.png"));
                            cerrado.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                            
                            super.add(cerrado);
                        }
                    }else {
                   TButton cerrado = new TButton(celda, j, i, "/images/closedCell.png");
                   cerrado.setBounds(j*DISTANCIA+BORDE, i*DISTANCIA+BORDE, TAMANIO_CELDA, TAMANIO_CELDA);
                   cerrado.setBackground(new Color(29, 178, 215));
                   //cerrado.setIcon(new ImageIcon("/images/closedCell.png"));
                   cerrado.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
                    cerrado.addActionListener(new ActionListener() {
                       @Override
                       public void actionPerformed(ActionEvent ae) {
                           listener.btnCasillaOnClick(cerrado.getXx(), cerrado.getYy());
                       }
                    });
                    cerrado.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent evt){
                            if(evt.getButton() == MouseEvent.BUTTON3){
                                System.out.println("CAMBIAR ESTADO");
                                listener.onRightClickButton(cerrado.getXx(), cerrado.getYy());
                            }
                        }
                    });
                    super.add(cerrado);
                }                    
            }
        }
    }
    public void setListener(TableroListener listener){
        this.listener = listener;
    }
    private Image getScaledImage(Image srcImg, int w, int h){
    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = resizedImg.createGraphics();

    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(srcImg, 0, 0, w, h, null);
    g2.dispose();

    return resizedImg;
}

}
