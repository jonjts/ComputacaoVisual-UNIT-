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
public class Histograma {

    public static int[] calcularHistograma(BufferedImage image){
        int[] hist = new int[256];
        hist = iniciarArray(hist);
        WritableRaster raster = image.getRaster();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = raster.getSample(x, y, 0); 
                hist[rgb] += 1;
            }
        }
        return hist;
    }

    private static int[] iniciarArray(int[] hist) {
        for (int i = 0; i < 256; i++) {
            hist[i] = 0;
        }
        return hist;
    }

}
