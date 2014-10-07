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
public class BrilhoContraste {

    /**
     * @param image
     * @param a
     * @param b
     * @return
     */
    public static BufferedImage modificarBrilhoContraste(BufferedImage image, int fatorBrilho, int fatorContraste){
        WritableRaster raster = image.getRaster();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int valor = raster.getSample(x, y, 0);
                valor = fatorContraste * valor + fatorBrilho;
                raster.setSample(x, y, 0, valor);
            }
        }
        return image;
    }

}
