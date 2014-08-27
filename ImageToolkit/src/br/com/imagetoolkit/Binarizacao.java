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
public class Binarizacao {

    public static BufferedImage binarize(BufferedImage image, int banda, int limiar) {
        BufferedImage bin = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
        WritableRaster r1 = image.getRaster();
        WritableRaster grey = bin.getRaster();

        int valor = 0;
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                valor = r1.getSample(x, y, banda);
                valor = (valor > limiar) ? 1 : 0;
                grey.setSample(x, y, 0, valor);
            }
        }
        return bin;
    }
}
