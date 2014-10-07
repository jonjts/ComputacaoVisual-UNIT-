/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit;

import br.com.imagetoolkit.util.Util;
import java.awt.Frame;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JOptionPane;
import javax.xml.ws.ServiceMode;

/**
 *
 * @author 2091140052
 */
public class JanelaAWT extends Frame {

    private ImageCanvas canvas;

    public JanelaAWT(final ImageCanvas canvas) {
        setCanvas(canvas);
        add(canvas);
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                int retorno = JOptionPane.showConfirmDialog(null,
                        "File ja existe, sobreescrever?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.NO_OPTION);
                if (retorno == JOptionPane.NO_OPTION) {
                    System.exit(0);
                }
                Util.salvarBufferedImage(canvas.getImg(), "C:\\Users\\Public\\Pictures\\Sample Pictures\\temp.png");
                System.exit(0);
            }
        });
    }

    public void setCanvas(ImageCanvas canvas) {
        if (this.canvas != null) {
            remove(this.canvas);
        }
        this.canvas = canvas;
        add(canvas);
        setSize(canvas.getImg().getWidth(), canvas.getImg().getHeight());
    }

    public ImageCanvas getCanvas() {
        return canvas;
    }
    
    
}
