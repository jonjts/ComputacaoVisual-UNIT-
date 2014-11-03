/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit.mapeamento;

import java.awt.image.BufferedImage;
import java.awt.image.ByteLookupTable;
import java.awt.image.LookupOp;
import java.awt.image.LookupTable;
import java.awt.image.WritableRaster;

/**
 *
 * @author Jonas
 */
public class MapeamentoNaoLinear {

    public static BufferedImage mapeamentoLogaritimo(BufferedImage image, int a) {
        int w = image.getWidth();
        int h = image.getHeight();
        WritableRaster raster = image.getRaster();
        byte[][] table = new byte[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double valor = a * Math.log(raster.getSample(x, y, 0)) + 1;
                table[x][y] = (byte) valor;
            }
        }
        ByteLookupTable byteLookupTable = new ByteLookupTable(0, table);
        LookupOp lookupOp = new LookupOp(byteLookupTable, null);
        return lookupOp.filter(image, null);
    }

    public static BufferedImage mapeamentoRaizQuadrada(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        WritableRaster raster = image.getRaster();
        byte[][] table = new byte[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double a = 255 / Math.sqrt(max(image));
                double valor = a * Math.sqrt(raster.getSample(x, y, 0));
                table[x][y] = (byte) valor;
            }
        }
        ByteLookupTable byteLookupTable = new ByteLookupTable(0, table);
        LookupOp lookupOp = new LookupOp(byteLookupTable, null);
        return lookupOp.filter(image, null);
    }

    public static BufferedImage mapeamentoExponencial(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        WritableRaster raster = image.getRaster();
        byte[][] table = new byte[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double a = 255 / (Math.exp(max(image)) - 1);
                double valor = a * (Math.exp(raster.getSample(x, y, 0)) - 1);
                table[x][y] = (byte) valor;
            }
        }
        ByteLookupTable byteLookupTable = new ByteLookupTable(0, table);
        LookupOp lookupOp = new LookupOp(byteLookupTable, null);
        return lookupOp.filter(image, null);
    }

    public static BufferedImage mapeamentoQuadrado(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        WritableRaster raster = image.getRaster();
        byte[][] table = new byte[w][h];
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                double a = 255 / Math.pow(max(image), 2);
                double valor = a * (Math.pow(raster.getSample(x, y, 0), 2));
                table[x][y] = (byte) valor;
            }
        }
        ByteLookupTable byteLookupTable = new ByteLookupTable(0, table);
        LookupOp lookupOp = new LookupOp(byteLookupTable, null);
        return lookupOp.filter(image, null);
    }

    private static int max(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();
        int max = 0;
        WritableRaster raster = image.getRaster();
        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                int sample = raster.getSample(x, y, 0);
                if (sample > max) {
                    max = sample;
                }
            }
        }
        return max;
    }
}
