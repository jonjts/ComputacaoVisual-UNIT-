/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit.util;

import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JFrame;

/**
 *
 * @author 2091140052
 */
public abstract class Desenho extends JFrame implements MouseListener, MouseMotionListener {

    private int xI,  yI,  xF,  yF;

    public Desenho() {
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void mouseClicked(MouseEvent e) {
        xI = e.getX();//Pegam o X e Y inicial do mouse
        yI = e.getY();
    }

    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void mouseDragged(MouseEvent e) {
        xF = e.getX();
        yF = e.getY();
        repaint();
    }

    public void mouseMoved(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
     public void paint (Graphics g){
        g.clearRect(0, 0, this.getWidth(),this.getHeight());//Limpa o frame, pra num ficar uma forma por cima da outra....
        g.drawRect(xI,yI,xF,yF);
    }
}
