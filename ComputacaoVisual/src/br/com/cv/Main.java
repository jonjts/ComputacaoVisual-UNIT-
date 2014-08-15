/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.cv;

/**
 *
 * @author Jonas
 */
public class Main {
    
    public static void main(String[] args){
        int w = 3, h = 3;
        ByteImage img = new ByteImage(w, h);
        img.setPixel(0, 0, 15);
        img.setPixel(0, 1, 80);
        img.setPixel(0, 2, 190);
        
        img.setPixel(1, 0, 79);
        img.setPixel(1, 1, 250);
        img.setPixel(1, 2, 35);
        
        img.setPixel(2, 0, 22);
        img.setPixel(2, 1, 109);
        img.setPixel(2, 2, 56);
        
        System.err.println(img.toString());
    }
    
}
