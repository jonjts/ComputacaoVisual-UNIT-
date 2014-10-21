/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit;

import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;
import javax.imageio.ImageTypeSpecifier;

/**
 *
 * @author 2091140052
 */
public class ManipulacaoGeometrica {

    public static BufferedImage enlarguecerImagem(BufferedImage img, int fator) {
        int w = img.getWidth() * fator;
        int h = img.getHeight() * fator;
        BufferedImage aumento = new BufferedImage(w, h, img.getType());
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                aumento.setRGB(x, y, img.getRGB(x / fator, y / fator));
            }
        }
        return aumento;
    }

    public static BufferedImage reduzirImagem(BufferedImage img, int fator) {
        int w = img.getWidth() / fator;
        int h = img.getHeight() / fator;
        BufferedImage aumento = new BufferedImage(w, h, img.getType());
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                aumento.setRGB(x, y, img.getRGB(x * fator, y * fator));
            }
        }
        return aumento;
    }

    public static BufferedImage reflexaoXInPlace(BufferedImage img) {
        int w = img.getWidth();
        int xmax = w / 2;
        for (int y = 0; y < img.getHeight(); y++) {
            for (int x = 0; x < xmax; x++) {
                int value = img.getRGB(x, y);
                img.setRGB(x, y, img.getRGB(w - x - 1, y));
                img.setRGB(w - x - 1, y, value);
            }
        }
        return img;
    }

    public static BufferedImage reflexaoYInPlace(BufferedImage img) {
        int h = img.getHeight();
        int hMax = h / 2;
        for (int x = 0; x < img.getWidth(); x++) {
            for (int y = 0; y < hMax; y++) {
                int value = img.getRGB(x, y);
                img.setRGB(x, y, img.getRGB(x, h - y - 1));
                img.setRGB(x, h - y - 1, value);
            }
        }
        return img;
    }

    public static BufferedImage rotação90Graus(BufferedImage image) {
        BufferedImage bf90 = new BufferedImage(image.getHeight(), image.getWidth(), image.getType());
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                bf90.setRGB(y, x, image.getRGB(x, y));
            }
        }
        return bf90;
    }

    public static BufferedImage rotacao270Graus(BufferedImage image) {
        return rotação90Graus(rotação90Graus(rotação90Graus(image)));

    }

    private static BufferedImage mediaImagens(BufferedImage[] images) {
        int n = images.length;
        int w = images[0].getWidth();
        int h = images[0].getHeight();
        BufferedImage media = new BufferedImage(w, h, images[0].getType());
        WritableRaster raster = media.getRaster();
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                float soma = 0f;
                for (BufferedImage bi : images) {
                    soma += bi.getRaster().getSample(x, y, 0);
                }
                raster.setSample(x, y, 0, (int) soma / n);
            }
        }
        return media;
    }
}
