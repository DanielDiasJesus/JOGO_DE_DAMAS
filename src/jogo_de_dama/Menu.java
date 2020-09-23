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
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

import java.awt.Insets;
import java.awt.Font;

import java.awt.GraphicsEnvironment;

import java.io.File;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Menu extends JFrame implements ActionListener {
    static Clip clip;

    public Menu() {
        super("DAMAS VERSAO FINAL");
        tocador(true);
        Container container = getContentPane();
        JPanel tela = new JPanel();
        JImagePanel fundo = new JImagePanel("src/img/fundo.png");
        JImagePanel titulo = new JImagePanel("src/img/Title5.png");

        JButton pvP = new JButton();
        JButton pvB = new JButton();
        JButton ext = new JButton();

        tela.setLayout(new BorderLayout());
        
        setSize(820, 711);
        setLocation(320, 30);
        setVisible(true);
        // setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        pvP.setSize(new Dimension(300, 50));
        pvB.setSize(new Dimension(300, 50));
        ext.setSize(new Dimension(300, 50));

        pvP.setBounds((getWidth() / 2) - (pvP.getWidth() / 2), 237, pvP.getWidth(), pvP.getHeight());
        pvB.setBounds((getWidth() / 2) - (pvB.getWidth() / 2), 307, pvB.getWidth(), pvB.getHeight());
        ext.setBounds((getWidth() / 2) - (ext.getWidth() / 2), 377, ext.getWidth(), ext.getHeight());

        pvP.setName("btnPvP");
        pvB.setName("btnPvB");
        ext.setName("btnExt");

        pvP.setText("PLAYER VS PLAYER");
        pvB.setText("PLAYER VS BOT");
        ext.setText("SAIR");        

        pvP.addActionListener(this);
        pvB.addActionListener(this);
        ext.addActionListener(this);

        titulo.setSize(new Dimension(420, 200));
        titulo.setBounds((getWidth()/2) - (titulo.getWidth()/2), 10, titulo.getWidth(), titulo.getHeight());
        titulo.setOpaque(false);

        tela.add(pvP);
        tela.add(pvB);
        tela.add(ext);

        tela.add(titulo);
        tela.add(fundo);        

        container.add(tela);

        Component comps[] = tela.getComponents();
        
        for(Component c: comps){
            if(c instanceof JButton){
                JButton botao = (JButton) c;
                
                botao.setFont(new Font("Dialog.plain", 1, 20));
                botao.setForeground(new Color(13, 15, 23));   
                botao.setBackground(new Color(204, 204, 204));             
                botao.setBorder(javax.swing.BorderFactory.createLineBorder(new Color(137, 137, 137), 3));
                botao.setMargin(new Insets(2, 1000, 2, 14));
                botao.setRequestFocusEnabled(false);
                botao.setRolloverEnabled(false);                
            }
        }
    }

    public static void main(String[] args) {        
        new Menu();
    }

    public static void tocador(Boolean tocar) {
        try {
            if(tocar){
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("src/SFX/musica.wav").getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(-20.0f);
                clip.start();
            }
            else
                clip.stop();
                
        }  
        catch (Exception e) {    
            System.out.println("Erro. Verifique o diretorio de sons");    
         }    
 
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        System.out.println(botao.getName());
        
        if(botao.getName().equals("btnPvP")){
            new Tabuleiro();
            tocador(false);
            this.setVisible(false);
            dispose();
        }
        if(botao.getName().equals("btnExt")){
            System.exit(0);
        }
    }
}