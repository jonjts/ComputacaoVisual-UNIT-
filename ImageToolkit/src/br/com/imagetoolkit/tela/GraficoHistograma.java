/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.imagetoolkit.tela;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Jonas
 */
public class GraficoHistograma extends javax.swing.JFrame {

    private static int[] hist;

    /**
     * Creates new form GraficoHistograma
     */
    public GraficoHistograma(int[] hist) {
        this.hist = hist;
        initComponents();
        
        repaint();
    }

    @Override
    public void paint(Graphics g) {
        try {
            Graphics2D graphics2D = (Graphics2D) g;
            int x = 1;
            for (int i = 0; i < 255; i++) {
                graphics2D.drawLine(x, 0, x++, hist[i]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
