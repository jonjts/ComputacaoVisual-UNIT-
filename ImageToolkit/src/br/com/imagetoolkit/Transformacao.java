/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.imagetoolkit;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author 2091140052
 */
public class Transformacao {


    public static BufferedImage transformeNivelCinza(BufferedImage img, int banda){
        BufferedImage cinza = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        WritableRaster r1 = img.getRaster();
        WritableRaster grey = cinza.getRaster();

        int valor = 0;
        for(int y = 0; y < img.getHeight(); y++){
            for(int x = 0; x < img.getWidth(); x++){
                valor = r1.getSample(x, y, banda);
                grey.setSample(x, y, 0, valor);
            }
        }
        return cinza;
    }
}
