/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit;

import br.com.imagetoolkit.JanelaAWTBonita;
import java.awt.Component;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.print.attribute.standard.Media;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author 2091140052
 */
public class ExibirImagem {

    public static void main(String[] args) {
      /*  File file = selectImage();
        BufferedImage image = carregarBufferedImage(file);
        exibirPretoBranco(image);*/

        exibirTelaComOpcoes();


    }

    private static void exibirTelaComOpcoes(){
        JanelaAWTBonita aWTBonita = new JanelaAWTBonita();
        aWTBonita.setVisible(true);
    }

    private static void exibirPretoBranco(BufferedImage image) {
        BufferedImage pretoBranco = Binarizacao.binarize(image, 0, 100);
        ImageCanvas icPretoBranco = new ImageCanvas(pretoBranco);
        JanelaAWT janelaAWT = new JanelaAWT(icPretoBranco);
        janelaAWT.setVisible(true);
    }

    private static void exibir(BufferedImage image) {
        ImageCanvas canvas = new ImageCanvas(image);
        JanelaAWT aWT = new JanelaAWT(canvas);
        aWT.setVisible(true);
    }

    private static void exibirCinza(BufferedImage image) {
        //Tons de cinza
        BufferedImage cinza = Transformacao.transformeNivelCinza(image, 0);
        ImageCanvas icCinza = new ImageCanvas(cinza);
        JanelaAWT janelaAWT = new JanelaAWT(icCinza);
        janelaAWT.setVisible(true);

    }

    
   
    
}
