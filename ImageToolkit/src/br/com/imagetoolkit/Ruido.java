/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit;

import java.awt.image.BufferedImage;

/**
 *
 * @author Jonas
 */
public class Ruido {

    public static BufferedImage reduzirRuido(BufferedImage image, int fator) {
        for (int v = 0; v < fator; v++) {
            for (int y = 0; y < image.getHeight(); y++) {
                for (int x = 0; x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int soma = rgb + rgb;
                    image.setRGB(x, y, soma);
                }
            }
        }
        return image;
    }
}
