/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jogo_de_dama;

/**
 *
 * @author daniel
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class JImagePanel extends javax.swing.JPanel {

    BufferedImage imagemFundo;

    public JImagePanel(String path) {        
        try {
            this.imagemFundo = ImageIO.read(new File(path));
        } catch (IOException e) {            
            e.printStackTrace();
        }
    }
    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.drawImage(this.imagemFundo, 0, 0, this.getWidth(), this.getHeight(), null);
    }
}
