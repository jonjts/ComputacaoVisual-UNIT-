/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.imagetoolkit;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author 2091140052
 */
public class ImageCanvas extends Canvas{

    private BufferedImage img;

    public ImageCanvas(BufferedImage img) {
        this.img = img;
    }

    public void paint(Graphics g){
        g.drawImage(img, 0, 0, this);
    }

    public BufferedImage getImg() {
        return img;
    }

    
}
