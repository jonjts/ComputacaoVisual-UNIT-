/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit.mapeamento;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

/**
 *
 * @author Jonas
 */
public class MapeamentoLinear {

    public static BufferedImage mapeamentoLinearRampa(BufferedImage img, float gain, float bias) {
        int w = img.getWidth();
        int h = img.getHeight();
        BufferedImage map = new BufferedImage(w, h, img.getType());
        WritableRaster saida = map.getRaster();
        WritableRaster entrada = img.getRaster();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w ; x++) {
                final int sample = entrada.getSample(x, y, 0);
                saida.setSample(x, y, 0, rampa(gain*sample+bias));
            }
        }
        return map;
    }

    public static BufferedImage mapeamentoLinearRampa(BufferedImage img, int f1, int f2, int g1, int g2){
        float gain = ((float) (g2-g1) / (f2-f1));
        float bias = g1-gain*f1;
        return mapeamentoLinearRampa(img, gain, bias);
    }
    
    private static int rampa(float value) {
        return Math.min((int) Math.round(value), 255);
    }

}
