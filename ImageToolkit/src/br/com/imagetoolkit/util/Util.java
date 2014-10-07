/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit.util;

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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Jonas
 */
public class Util {

    public static File selectImage() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Arquivos de imagens", "jpg", "gif", "png");
        chooser.setFileFilter(filter);
        File file = null;
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = chooser.getSelectedFile();
        }
        return file;
    }

    public static BufferedImage carregarBufferedImage(File file) {
        BufferedImage bufferedImage = null;
        try {
            bufferedImage = ImageIO.read(file);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return bufferedImage;
    }

    public static void salvarBufferedImage(BufferedImage bufferedImage, String path) {
        File file = new File(path);
        if (file.exists()) {
            int retorno = JOptionPane.showConfirmDialog(null,
                    "File ja existe, sobreescrever?",
                    "Confirmação",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.NO_OPTION);
            if (retorno == JOptionPane.NO_OPTION) {
                return;
            }
        }
        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static Image carregarImagem(String file) {
        Image image = Toolkit.getDefaultToolkit().getImage(file);
        MediaTracker tracker = new MediaTracker(new Component() {
        });
        tracker.addImage(image, 0);
        try {
            tracker.waitForID(0);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
        return image;
    }

}
