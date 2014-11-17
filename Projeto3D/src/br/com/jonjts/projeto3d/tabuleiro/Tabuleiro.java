/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.jonjts.projeto3d.tabuleiro;

import java.awt.BorderLayout;
import java.awt.HeadlessException;
import javax.swing.JFrame;

/**
 *
 * @author Jonas
 */
public class Tabuleiro extends JFrame{

    public Tabuleiro() throws HeadlessException {
        super("Tabluleiro 3D");
        PainelTabuleiro painel3D = new PainelTabuleiro();
        add(painel3D, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }
    
    
    public static void main(String[] args){
        new Tabuleiro();
    }
}
