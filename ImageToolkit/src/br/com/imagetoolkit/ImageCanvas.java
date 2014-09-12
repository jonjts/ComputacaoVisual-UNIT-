/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit;

import br.com.imagetoolkit.desenho.ISubImagem;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.event.MouseInputListener;

/**
 *
 * @author 2091140052
 */
public class ImageCanvas extends JComponent implements MouseListener, MouseMotionListener {

    private BufferedImage img;
    private ISubImagem subImagem;
    private int xI, yI, xF, yF;
    private boolean cropping = false;

    public ImageCanvas(BufferedImage img, ISubImagem subImagem) {
        this.img = img;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.subImagem = subImagem;
    }

    public ImageCanvas(BufferedImage img) {
        this.img = img;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.drawImage(img, 0, 0, this);
        if (cropping) {
            int width = xF - xI;
            int heigth = yF - yI;
            if (width < 0) {
                width = width * -1;
                xI = xF;
            }
            if (heigth < 0) {
                heigth = heigth * -1;
                yI = yF;
            }

            // Paint the area we are going to crop.
            //g2.setColor(Color.RED);
            g2.setPaint(new GradientPaint(0, 0, Color.WHITE, 100, 0, Color.BLUE));
            g.drawRect(xI, yI, width, heigth);
            super.paint(g);
        }
    }

    public BufferedImage getImg() {
        return img;
    }

    public void mouseClicked(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        xI = e.getX();//Pegam o X e Y inicial do mouse
        yI = e.getY();
    }

    public void mouseReleased(MouseEvent e) {
        cropping = false;
        if (subImagem != null) {
            int width = xF - xI;
            int heigth = yF - yI;
            Point pInicio = new Point(xI, yI);
            subImagem.addSubImagem(pInicio, width, heigth);
        }
    }

    public void mouseEntered(MouseEvent e) {

    }

    public void mouseExited(MouseEvent e) {

    }

    private void zerarTodoMundoNessaPorra() {
        xF = 0;
        xI = 0;
        yF = 0;
        yI = 0;
    }

    public void mouseDragged(MouseEvent e) {
        cropping = true;
        xF = e.getX();
        yF = e.getY();
        repaint();

    }

    public void mouseMoved(MouseEvent e) {

    }

}
