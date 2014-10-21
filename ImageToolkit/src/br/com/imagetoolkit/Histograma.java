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

    public static int[] calcularHistograma(BufferedImage image) {
        int[] hist = new int[256];
        hist = iniciarArray(hist);
        WritableRaster raster = image.getRaster();
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                int rgb = raster.getSample(x, y, 0);
                hist[rgb] += 1;
            }
        }
        //exibirConsole(hist);
        return inverter(hist);
    }

    public static int[] calcularHistogramaAcumulativo(BufferedImage image) {
        int[] hist = calcularHistograma(image);
        int valor = 0;
        for (int i = 0; i < 256; i++) {
            valor += hist[i];
            hist[i] = valor;
        }
        //  exibirConsole(hist);
        return hist;
    }

    public static double[] calcularHistogramaNormalizado(BufferedImage image) {
        double numPixel = image.getWidth() * image.getHeight();
        int[] hist = calcularHistograma(image);
        double[] normalizado = new double[255];
        for (int i = 0; i < 255; i++) {
            double valor = hist[i] / numPixel;
            normalizado[i] = valor;
        }
        //exibirConsole(hist);
        return normalizado;
    }

    public static double[] calcularHistogramaNormalizadoAcumulativo(BufferedImage image) {
        double numPixel = image.getWidth() * image.getHeight();
        int[] hist = calcularHistogramaAcumulativo(image);
        double[] normalizado = new double[255];
        for (int i = 0; i < 255; i++) {
            double valor = hist[i] / numPixel;
            normalizado[i] = valor;
        }
        //exibirConsole(hist);
        return normalizado;
    }

    private static void exibirConsole(int[] hist) {
        for (int i = 0; i < 256; i++) {
            System.out.print(hist[i] + ", ");
        }
        System.out.println("\n");
    }

    private static int[] inverter(int[] hist) {
        int[] temp = hist;
        int j = 255;
        for (int i = 0; i < 255; i++) {
            hist[i] = temp[j--];
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
