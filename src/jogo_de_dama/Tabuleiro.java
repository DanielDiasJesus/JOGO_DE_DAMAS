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
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Tabuleiro extends JFrame implements ActionListener {

    private Peca[][] botoes;

    private boolean vezBranca = true;
    private int pecasPreto = 12;
    private int pecasBranco = 12;
    private List<Posicao> cliquesAnteriores = new ArrayList();
    private Posicao compOpo = null;

    private Container informacoes = new JPanel(new GridBagLayout());
    
    private JPanel jogadorVez = new JPanel();
    private JPanel menu = new JPanel();

    public Tabuleiro() {
        super("DAMAS VERSAO FINAL");
        Container tela = getContentPane();
        Container tabuleiro = new JPanel(new CardLayout());

        tabuleiro.setLayout(new GridLayout(8, 8));
        informacoes.setPreferredSize(new Dimension(250, 300));
        informacoes.setLayout(new GridLayout(2, 1));
        botoes = new Peca[8][8];

        PrepararTabuleiro(tabuleiro);
        PrepararPecas();
        PrepararInformacoes(informacoes);

        tela.add(tabuleiro, BorderLayout.CENTER);
        tela.add(informacoes, BorderLayout.EAST);

        setSize(950, 711);
        setLocation(300, 30);
        setUndecorated(true);
        setVisible(true);
        setResizable(false);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void atualizarInformacoes() {
        Component comps[] = jogadorVez.getComponents();

        for (Component c : comps) {
            if (c instanceof Label) {
                Label lbl = (Label) c;
                if (lbl.getName().equals("lblQtdBranca"))
                    lbl.setText(Integer.toString(this.pecasBranco));
                if (lbl.getName().equals("lblQtdPreta"))
                    lbl.setText(Integer.toString(this.pecasPreto));
            }
            if (c instanceof JPanel) {
                JPanel jpanel = (JPanel) c;
                if (jpanel.getName().equals("jpnSinalizador"))
                    if (vezBranca)
                        jpanel.setBounds(5, 30, 240, 80);
                    else
                        jpanel.setBounds(5, 110, 240, 80);
            }
        }
    }

    public void playSound(String soundName) {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);

            clip.start();
        } catch (Exception ex) {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();
        }
    }    
    
    private void PrepararInformacoes(Container informacoes) {

        informacoes.removeAll();

        jogadorVez = new JPanel();
        menu = new JPanel();
        
        JPanel sinalizador = new JPanel();
        sinalizador.setName("jpnSinalizador");

        Peca branca = new Peca();
        Peca preta = new Peca();

        Label titulo = new Label("JOGADOR DA VEZ");
        Label nBranca = new Label("BRANCAS");
        Label nPreta = new Label("PRETAS");

        Label qtdBranca = new Label("12");
        Label qtdPreta = new Label("12");
        
        qtdBranca.setName("lblQtdBranca");
        qtdPreta.setName("lblQtdPreta");

        jogadorVez.setLayout(null);
        menu.setLayout(null);

        titulo.setFont(new Font("AndaleMono", 1, 11));        

        titulo.setBounds(10, 10, 100, 20);
        

        /* INFORMAÇÕES DO JOGADOR BRANCO */
        branca.setBounds(10, 40, 55, 55);
        branca.setEstado(Estado.BRANCA);
        branca.setEnabled(false);
        
        nBranca.setFont(new Font("Sans", 0, 21));
        nBranca.setBounds(65, 40, 100, 55);

        qtdBranca.setFont(new Font("Sans", 0, 21));
        qtdBranca.setBounds(180, 40, 30, 55);

        /* INFORMAÇÕES DO JOGADOR PRETO */
        preta.setBounds(10, 120, 55, 55);
        preta.setEstado(Estado.PRETA);
        preta.setEnabled(false);

        nPreta.setFont(new Font("Sans", 0, 21));
        nPreta.setBounds(65, 120, 100, 55);

        qtdPreta.setFont(new Font("Sans", 0, 21));
        qtdPreta.setBounds(180, 120, 30, 55);

        sinalizador.setBounds(5, 30, 240, 80);

        sinalizador.setOpaque(false);
        jogadorVez.setBorder(BorderFactory.createEtchedBorder());

        menu.setBorder(BorderFactory.createEtchedBorder());
        sinalizador.setBorder(BorderFactory.createLineBorder(Color.RED));

        jogadorVez.removeAll();

        
        Label lblmenu = new Label("Menu");
        JButton btnVoltar = new JButton("MENU");
        JButton btnFechar = new JButton("FECHAR");  

        lblmenu.setBounds(10, 10, 100, 20);
        lblmenu.setFont(new Font("AndaleMono", 1, 11));

        btnVoltar.setBounds(10, 40, 100, 25);
        btnFechar.setBounds(140, 40, 100, 25);

        btnVoltar.setBackground(new Color(137, 137, 137));
        btnFechar.setBackground(new Color(137, 137, 137));
        
        btnVoltar.addActionListener(this);
        btnFechar.addActionListener(this);
        
        btnVoltar.setName("btnMenu");
        btnFechar.setName("btnFechar");

        jogadorVez.add(titulo);

        jogadorVez.add(branca);
        jogadorVez.add(qtdBranca);
        jogadorVez.add(nBranca);

        jogadorVez.add(preta);
        jogadorVez.add(qtdPreta);
        jogadorVez.add(nPreta);

        jogadorVez.add(sinalizador);

        menu.add(lblmenu);
        menu.add(btnVoltar);
        menu.add(btnFechar);

        informacoes.add(jogadorVez, new GridBagConstraints());
        informacoes.add(menu, new GridBagConstraints());
        informacoes.revalidate();
        informacoes.repaint();
    }

    public void actionPerformed(ActionEvent e) {
        JButton botao = (JButton) e.getSource();
        
        if(botao.getName().equals("btnMenu")){
            new Menu();
            this.setVisible(false);
            dispose();
        }
        else if(botao.getName().equals("btnFechar"))
            System.exit(0);
        else{
            Peca peca = (Peca) e.getSource();

        int lin = Integer.parseInt(peca.getName().substring(4, 5));
        int col = Integer.parseInt(peca.getName().substring(5, 6));

        System.out.println("PECA: " + lin + ":" + col + "");
        System.out.println("\tESTADO: " + peca.getEstado());

        // cliquesAnteriores.add(new Posicao(lin, col, botoes[lin][col].getEstado()));
        if (botoes[lin][col].getEstado() == Estado.POSSIVEL)
            AtualizarTabuleiro();
        if (vezBranca) {
            if (!Jogou(lin, col)) {
                if (botoes[lin][col].getEstado() == Estado.BRANCA) {
                    playSound("src/SFX/clique5.wav");
                    cliquesAnteriores.clear();
                    cliquesAnteriores.add(new Posicao(lin, col, botoes[lin][col].getEstado()));
                    AtualizarTabuleiro();
                    MostrarPossibilidades(lin, col, Estado.BRANCA);
                }
                if (botoes[lin][col].getEstado() == Estado.COMPROBRANCA) {
                    playSound("src/SFX/clique5.wav");
                    cliquesAnteriores.clear();
                    cliquesAnteriores.add(new Posicao(lin, col, botoes[lin][col].getEstado()));
                    AtualizarTabuleiro();
                    MostrarPossibilidades(lin, col, Estado.COMPROBRANCA);
                }
                if (botoes[lin][col].getEstado() == Estado.DAMABRANCA) {
                    playSound("src/SFX/clique5.wav");
                    RemoverComprometidas();
                    cliquesAnteriores.clear();
                    cliquesAnteriores.add(new Posicao(lin, col, botoes[lin][col].getEstado()));
                    AtualizarTabuleiro();
                    PossibilidadesDamas(lin, col, Estado.DAMABRANCA);
                }
                if (botoes[lin][col].getEstado() == Estado.PRETA || botoes[lin][col].getEstado() == Estado.DAMAPRETA
                        || botoes[lin][col].getEstado() == Estado.COMPROPRETA) {
                    playSound("src/SFX/invalid.wav");
                }
            } else if (botoes[lin][col].getEstado() == Estado.POSSIVELMOV
                    || botoes[lin][col].getEstado() == Estado.ROUBO) {

                if (Roubou(lin, col)) {
                    Posicao primeira = cliquesAnteriores.get(0);
                    if (primeira.getEstado() == Estado.DAMABRANCA) {
                        AtualizarTabuleiro();

                        RemoverComprometidas();
                        cliquesAnteriores.clear();
                        cliquesAnteriores.add(primeira);
                        PossibilidadesDamas(lin, col, Estado.DAMABRANCA);
                        if (PossivelRouboDama(Estado.DAMABRANCA)) {
                            DesativarPecas(new Posicao(lin, col, Estado.DAMABRANCA));
                            this.vezBranca = true;
                        } else {
                            this.vezBranca = false;
                            // RemoverComprometidas();
                            desativarBrancas();
                            cliquesAnteriores.clear();
                        }
                    }

                    else if (PossivelRoubo(lin, col)) {
                        this.vezBranca = true;
                        DesativarPecas(new Posicao(lin, col, Estado.BRANCA));
                        cliquesAnteriores.clear();
                    } else {
                        if (!SeComprometeu(lin, col))
                            desativarBrancas();

                        RemoverComprometidas();
                        this.vezBranca = false;
                        cliquesAnteriores.clear();
                    }
                    this.pecasPreto--;

                    playSound("src/SFX/roubou.wav");                    
                } else {
                    RemoverComprometidas();
                    MoverPeca(lin, col);
                    if (!SeComprometeu(lin, col)) {
                        if (PossivelRoubo(lin, col) && !ExisteComprometida()) {
                            compOpo = new Posicao(lin, col, Estado.BRANCA);
                            AtualizarTabuleiro();
                        }
                        desativarBrancas();
                        if (ExisteComprometida()) {
                            if (!SalvouComprometida(lin, col)) {
                                ForcarRoubo(compOpo);
                                compOpo = null;
                            }
                        }
                    }else{
                        if(compOpo != null){
                            botoes[compOpo.getLin()][compOpo.getCol()].setEnabled(true);
                            compOpo = null;
                        }
                    }

                    this.vezBranca = false;
                    // RemoverComprometidas();
                    cliquesAnteriores.clear();
                }
                AtualizarJogo();
            }
        } else {
            if (!Jogou(lin, col)) {
                if (botoes[lin][col].getEstado() == Estado.PRETA) {
                    playSound("src/SFX/clique5.wav");
                    cliquesAnteriores.clear();
                    cliquesAnteriores.add(new Posicao(lin, col, botoes[lin][col].getEstado()));
                    AtualizarTabuleiro();
                    MostrarPossibilidades(lin, col, Estado.PRETA);
                }
                if (botoes[lin][col].getEstado() == Estado.COMPROPRETA) {
                    playSound("src/SFX/clique5.wav");
                    cliquesAnteriores.clear();
                    cliquesAnteriores.add(new Posicao(lin, col, botoes[lin][col].getEstado()));
                    AtualizarTabuleiro();
                    MostrarPossibilidades(lin, col, Estado.COMPROPRETA);
                }
                if (botoes[lin][col].getEstado() == Estado.DAMAPRETA) {
                    playSound("src/SFX/clique5.wav");
                    RemoverComprometidas();
                    cliquesAnteriores.clear();
                    cliquesAnteriores.add(new Posicao(lin, col, botoes[lin][col].getEstado()));
                    AtualizarTabuleiro();
                    PossibilidadesDamas(lin, col, Estado.DAMAPRETA);
                }
                if (botoes[lin][col].getEstado() == Estado.BRANCA || botoes[lin][col].getEstado() == Estado.DAMABRANCA
                        || botoes[lin][col].getEstado() == Estado.COMPROBRANCA) {
                    playSound("src/SFX/invalid.wav");
                }
            } else if (botoes[lin][col].getEstado() == Estado.POSSIVELMOV
                    || botoes[lin][col].getEstado() == Estado.ROUBO) {

                if (Roubou(lin, col)) {
                    Posicao primeira = cliquesAnteriores.get(0);
                    if (primeira.getEstado() == Estado.DAMAPRETA) {
                        AtualizarTabuleiro();

                        RemoverComprometidas();
                        cliquesAnteriores.clear();
                        cliquesAnteriores.add(primeira);
                        PossibilidadesDamas(lin, col, Estado.DAMAPRETA);
                        if (PossivelRouboDama(Estado.DAMAPRETA)) {
                            DesativarPecas(new Posicao(lin, col, Estado.DAMAPRETA));
                            this.vezBranca = false;
                        } else {
                            this.vezBranca = true;
                            // RemoverComprometidas();
                            desativarPretas();
                            cliquesAnteriores.clear();
                        }
                    } else if (PossivelRoubo(lin, col)) {
                        this.vezBranca = false;
                        DesativarPecas(new Posicao(lin, col, Estado.PRETA));
                        cliquesAnteriores.clear();
                    } else {
                        if (!SeComprometeu(lin, col))
                            desativarPretas();
                        RemoverComprometidas();
                        this.vezBranca = true;
                        cliquesAnteriores.clear();
                    }
                    this.pecasBranco--;

                    playSound("src/SFX/roubou.wav");                                        
                }
                else{
                    RemoverComprometidas();
                    MoverPeca(lin, col);
                    if(!SeComprometeu(lin, col)){                        
                        if(PossivelRoubo(lin, col) && !ExisteComprometida()){                        
                            compOpo = new Posicao(lin, col, Estado.PRETA);
                            AtualizarTabuleiro();                     
                        }
                        desativarPretas();
                        if(ExisteComprometida()){
                            if(!SalvouComprometida(lin, col)){
                                ForcarRoubo(compOpo);
                                compOpo = null;                            
                            }
                        }                        
                    }else{
                        if(compOpo != null){
                            botoes[compOpo.getLin()][compOpo.getCol()].setEnabled(true);
                            compOpo = null;
                        }
                    }                
                    this.vezBranca = true;
                    // RemoverComprometidas();                    
                    cliquesAnteriores.clear();
                }                
                AtualizarJogo();                
            }  
        }            
            System.out.println(this.toString());
            if (Ganhou()) {
                if(this.pecasBranco == 0)
                    System.out.println("-_-_- PRETAS GANHARAM -_-_-");
                if(this.pecasPreto == 0)
                    System.out.println("-_-_- BRANCAS GANHARAM -_-_-");
                playSound("src/SFX/vencedor.wav");
                try{
                    Thread.sleep(1500);
                }catch (InterruptedException e1) {                            
                    e1.printStackTrace();
                }                
            } 
        }
    }
    private void AtualizarJogo(){
        AtualizarTabuleiro();
        atualizarInformacoes();
    }
    private void AtualizarTabuleiro(){
        for(int x = 0; x < 8; x++){
            for(int y = 0; y < 8; y++){
                if( botoes[x][y].getEstado() == Estado.POSSIVELMOV ||
                    botoes[x][y].getEstado() == Estado.ROUBO){
                    botoes[x][y].setEstado(Estado.POSSIVEL);                    
                    repaint();
                }                
            }
        }
    }

    private boolean PossivelRouboDama(Estado dama){
        if(dama == Estado.DAMAPRETA){
            for (int i = 0; i < cliquesAnteriores.size(); i++) {
                Posicao comproPreta = cliquesAnteriores.get(i);
                if(comproPreta.getEstado() == Estado.COMPROBRANCA)
                    return true;
            }
            return false;
        }
        if(dama == Estado.DAMABRANCA){
            for (int i = 0; i < cliquesAnteriores.size(); i++) {
                Posicao comproPreta = cliquesAnteriores.get(i);
                if(comproPreta.getEstado() == Estado.COMPROPRETA)
                    return true;
            }
            return false;
        }
        return false;
    }

    private void MoverPeca(int l, int c){
        Posicao posPeca = cliquesAnteriores.get(0);
        if(this.vezBranca)
        {
            if(l == 0){
                botoes[l][c].setEstado(Estado.DAMABRANCA);
                repaint();
            }else{
                if(botoes[posPeca.getLin()][posPeca.getCol()].getEstado() == Estado.COMPROBRANCA){
                    botoes[l][c].setEstado(Estado.BRANCA);
                    repaint();
                }
                else{
                    botoes[l][c].setEstado(botoes[posPeca.getLin()][posPeca.getCol()].getEstado());
                    repaint();
                }
            }
        }
        else{
            if(l == 7){
                botoes[l][c].setEstado(Estado.DAMAPRETA);
                repaint();
            }else{
                if(botoes[posPeca.getLin()][posPeca.getCol()].getEstado() == Estado.COMPROPRETA){
                    botoes[l][c].setEstado(Estado.PRETA);
                    repaint();
                }
                else{
                    botoes[l][c].setEstado(botoes[posPeca.getLin()][posPeca.getCol()].getEstado());
                    repaint();
                }
            }
        }
        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);            
    }
    
    private boolean ExisteComprometida(){
        if(this.vezBranca){
            for (int i = 0; i < botoes.length; i++)
                for (int j = 0; j < botoes.length; j++)               
                    if(botoes[i][j].getEstado() == Estado.COMPROBRANCA)
                        return true;
        }else{
            for (int i = 0; i < botoes.length; i++)
                for (int j = 0; j < botoes.length; j++)               
                    if(botoes[i][j].getEstado() == Estado.COMPROPRETA)
                        return true;
        }
        return false;
    }

    private boolean SalvouComprometida(int lin, int col){        
        boolean entrou = false;
        if(this.vezBranca){
            if(col == 0)
                if(botoes[lin - 1][col + 1].getEstado() == Estado.COMPROBRANCA){
                    botoes[lin - 1][col + 1].setEstado(Estado.BRANCA);
                    return true;
                }
            if(col == 7)
                if(botoes[lin - 1][col - 1].getEstado() == Estado.COMPROBRANCA){
                    botoes[lin - 1][col - 1].setEstado(Estado.BRANCA);
                    return true;
                }
            if(botoes[lin - 1][col - 1].getEstado() == Estado.COMPROBRANCA){
                botoes[lin - 1][col - 1].setEstado(Estado.BRANCA);
                entrou = true;
            }
            if(botoes[lin - 1][col + 1].getEstado() == Estado.COMPROBRANCA){
                botoes[lin - 1][col + 1].setEstado(Estado.BRANCA);
                entrou = true;
            }
            return entrou;
        }
        else{
            if(col == 0)
                if(botoes[lin + 1][col + 1].getEstado() == Estado.COMPROPRETA){
                    botoes[lin + 1][col + 1].setEstado(Estado.PRETA);
                    return true;
                }
            if(col == 7)
                if(botoes[lin + 1][col - 1].getEstado() == Estado.COMPROPRETA){
                    botoes[lin + 1][col - 1].setEstado(Estado.PRETA);
                    return true;
                }
            if(botoes[lin + 1][col - 1].getEstado() == Estado.COMPROPRETA){
                botoes[lin + 1][col - 1].setEstado(Estado.PRETA);
                entrou = true;
            }
            if(botoes[lin + 1][col + 1].getEstado() == Estado.COMPROPRETA){
                botoes[lin + 1][col + 1].setEstado(Estado.PRETA);
                entrou = true;
            }
            return entrou;
        }    
    }

    private boolean PossivelRoubo(int lin, int col){            
        boolean entrou = false;
        
        if(this.vezBranca){
            if(lin > 1){
                if(col == 0 || col == 1)
                {
                    if( botoes[lin - 1][col + 1].getEstado() == Estado.PRETA &&
                        botoes[lin - 2][col + 2].getEstado() == Estado.POSSIVEL){
                            
                            botoes[lin - 2][col + 2].setEstado(Estado.ROUBO);                            
                            botoes[lin - 1][col + 1].setEstado(Estado.COMPROPRETA);
                            repaint();                            
                            return true;                            
                        }
                        if( botoes[lin - 1][col + 1].getEstado() == Estado.DAMAPRETA &&
                            botoes[lin - 2][col + 2].getEstado() == Estado.POSSIVEL){
                            
                            botoes[lin - 2][col + 2].setEstado(Estado.ROUBO);                            
                            botoes[lin - 1][col + 1].setEstado(Estado.COMPRODAMAPRETA);
                            repaint();                            
                            return true;
                        }
                        if(botoes[lin - 1][col + 1].getEstado() == Estado.COMPROPRETA){
                            if(botoes[lin - 2][col + 2].getEstado() == Estado.POSSIVEL){
                                botoes[lin - 2][col + 2].setEstado(Estado.ROUBO); 
                                repaint();                            
                                return true;
                            }
                        }                            
                    return false;
                }
                if(col == 7 || col == 6){                        
                    if( botoes[lin - 1][col - 1].getEstado() == Estado.PRETA &&
                        botoes[lin - 2][col - 2].getEstado() == Estado.POSSIVEL){
                            
                            botoes[lin - 2][col - 2].setEstado(Estado.ROUBO);    
                            botoes[lin - 1][col - 1].setEstado(Estado.COMPROPRETA);                                                     
                            repaint();                            
                            return true;
                        }
                        if( botoes[lin - 1][col - 1].getEstado() == Estado.DAMAPRETA &&
                            botoes[lin - 2][col - 2].getEstado() == Estado.POSSIVEL){
                            
                            botoes[lin - 2][col - 2].setEstado(Estado.ROUBO);    
                            botoes[lin - 1][col - 1].setEstado(Estado.COMPRODAMAPRETA);                                                     
                            repaint();                            
                            return true;
                        }
                        if(botoes[lin - 1][col - 1].getEstado() == Estado.COMPROPRETA){
                            if(botoes[lin - 2][col - 2].getEstado() == Estado.POSSIVEL){
                                botoes[lin - 2][col - 2].setEstado(Estado.ROUBO);                           
                                repaint();                            
                                return true;   
                            }
                        }
                    return false;
                }
                if(botoes[lin - 1][col - 1].getEstado() == Estado.PRETA && 
                    botoes[lin - 2][col - 2].getEstado() == Estado.POSSIVEL){                                                
                        botoes[lin - 2][col - 2].setEstado(Estado.ROUBO); 
                        botoes[lin - 1][col - 1].setEstado(Estado.COMPROPRETA);                       
                        repaint();                        
                        entrou = true;
                    }
                if(botoes[lin - 1][col - 1].getEstado() == Estado.DAMAPRETA && 
                    botoes[lin - 2][col - 2].getEstado() == Estado.POSSIVEL){                                                
                        botoes[lin - 2][col - 2].setEstado(Estado.ROUBO); 
                        botoes[lin - 1][col - 1].setEstado(Estado.COMPRODAMAPRETA);                       
                        repaint();                        
                        entrou = true;
                    }                                  
                if( botoes[lin - 1][col + 1].getEstado() == Estado.PRETA &&
                    botoes[lin - 2][col + 2].getEstado() == Estado.POSSIVEL){                        
                        botoes[lin - 2][col + 2].setEstado(Estado.ROUBO);                            
                        botoes[lin - 1][col + 1].setEstado(Estado.COMPROPRETA);
                        repaint();                                              
                        entrou = true;                        
                    } 
                if( botoes[lin - 1][col + 1].getEstado() == Estado.DAMAPRETA &&
                    botoes[lin - 2][col + 2].getEstado() == Estado.POSSIVEL){                        
                        botoes[lin - 2][col + 2].setEstado(Estado.ROUBO);                            
                        botoes[lin - 1][col + 1].setEstado(Estado.COMPRODAMAPRETA);
                        repaint();                                                    
                        entrou = true;
                    }                  
                if(botoes[lin - 1][col + 1].getEstado() == Estado.COMPROPRETA){
                    if(botoes[lin - 2][col + 2].getEstado() == Estado.POSSIVEL){
                        botoes[lin - 2][col + 2].setEstado(Estado.ROUBO);
                        repaint();                    
                        entrou = true;
                    }
                }
                if(botoes[lin - 1][col - 1].getEstado() == Estado.COMPROPRETA){
                    if(botoes[lin - 2][col - 2].getEstado() == Estado.POSSIVEL) {
                        botoes[lin - 2][col - 2].setEstado(Estado.ROUBO);
                        repaint();                    
                        entrou = true;
                    }
                }
                if(botoes[lin - 1][col + 1].getEstado() == Estado.COMPRODAMAPRETA){
                    if(botoes[lin - 2][col + 2].getEstado() == Estado.POSSIVEL){
                        botoes[lin - 2][col + 2].setEstado(Estado.ROUBO);
                        repaint();                    
                        entrou = true;
                    }
                }
                if(botoes[lin - 1][col - 1].getEstado() == Estado.COMPRODAMAPRETA){
                    if(botoes[lin - 2][col - 2].getEstado() == Estado.POSSIVEL){
                        botoes[lin - 2][col - 2].setEstado(Estado.ROUBO);
                        repaint();                    
                        entrou = true;
                    }
                }
            }                       
            return entrou;
        }else{
            if(lin < 6){
                if(col == 0 || col == 1)
                {
                    if( botoes[lin + 1][col + 1].getEstado() == Estado.BRANCA &&
                        botoes[lin + 2][col + 2].getEstado() == Estado.POSSIVEL){

                            botoes[lin + 2][col + 2].setEstado(Estado.ROUBO);
                            botoes[lin + 1][col + 1].setEstado(Estado.COMPROBRANCA);                            
                            repaint();                            
                            return true;                            
                        }
                    if( botoes[lin + 1][col + 1].getEstado() == Estado.DAMABRANCA &&
                        botoes[lin + 2][col + 2].getEstado() == Estado.POSSIVEL){

                            botoes[lin + 2][col + 2].setEstado(Estado.ROUBO);
                            botoes[lin + 1][col + 1].setEstado(Estado.COMPRODAMABRANCA);                            
                            repaint();                            
                            return true;                            
                        }
                    if(botoes[lin + 1][col + 1].getEstado() == Estado.COMPROBRANCA){
                        if(botoes[lin + 2][col + 2].getEstado() == Estado.POSSIVEL){
                            botoes[lin + 2][col + 2].setEstado(Estado.ROUBO);                        
                            repaint();                        
                            return true;
                        }
                    }
                    return false;
                }
                if(col == 7 || col == 6){                        
                    if( botoes[lin + 1][col - 1].getEstado() == Estado.BRANCA &&
                        botoes[lin + 2][col - 2].getEstado() == Estado.POSSIVEL){
                            
                            botoes[lin + 2][col - 2].setEstado(Estado.ROUBO);
                            botoes[lin + 1][col - 1].setEstado(Estado.COMPROBRANCA);                                                                                           
                            repaint();                              
                            return true;                              
                        }
                    if( botoes[lin + 1][col - 1].getEstado() == Estado.DAMABRANCA &&
                        botoes[lin + 2][col - 2].getEstado() == Estado.POSSIVEL){
                            
                            botoes[lin + 2][col - 2].setEstado(Estado.ROUBO);
                            botoes[lin + 1][col - 1].setEstado(Estado.COMPRODAMABRANCA);                                                                                           
                            repaint();                              
                            return true;                              
                        }
                        
                    if(botoes[lin + 1][col - 1].getEstado() == Estado.COMPROBRANCA){
                        if(botoes[lin + 2][col - 2].getEstado() == Estado.POSSIVEL){
                            botoes[lin + 2][col - 2].setEstado(Estado.ROUBO);
                            repaint();                        
                            return true;
                        }
                    }
                    return false;
                }
                if( botoes[lin + 1][col - 1].getEstado() == Estado.BRANCA && 
                    botoes[lin + 2][col - 2].getEstado() == Estado.POSSIVEL){                        
                        botoes[lin + 2][col - 2].setEstado(Estado.ROUBO);                                                   
                        botoes[lin + 1][col - 1].setEstado(Estado.COMPROBRANCA);
                        repaint();
                        entrou = true;                                         
                    }
                if( botoes[lin + 1][col - 1].getEstado() == Estado.DAMABRANCA && 
                    botoes[lin + 2][col - 2].getEstado() == Estado.POSSIVEL){                        
                        botoes[lin + 2][col - 2].setEstado(Estado.ROUBO);                                                   
                        botoes[lin + 1][col - 1].setEstado(Estado.COMPRODAMABRANCA);
                        repaint();       
                        entrou = true;                        
                    }    
                
                if( botoes[lin + 1][col + 1].getEstado() == Estado.BRANCA &&
                    botoes[lin + 2][col + 2].getEstado() == Estado.POSSIVEL){                        
                                                
                        botoes[lin + 1][col + 1].setEstado(Estado.COMPROBRANCA);
                        botoes[lin + 2][col + 2].setEstado(Estado.ROUBO);                                             
                        repaint();       
                        entrou = true;                        
                    }
                if( botoes[lin + 1][col + 1].getEstado() == Estado.DAMABRANCA &&
                    botoes[lin + 2][col + 2].getEstado() == Estado.POSSIVEL){                        
                        botoes[lin + 1][col + 1].setEstado(Estado.COMPRODAMABRANCA);
                        botoes[lin + 2][col + 2].setEstado(Estado.ROUBO);                                             
                        repaint();      
                        entrou = true;                        
                    }
                if(botoes[lin + 1][col - 1].getEstado() == Estado.COMPROBRANCA){
                    if(botoes[lin + 2][col - 2].getEstado() == Estado.POSSIVEL){
                        botoes[lin + 2][col - 2].setEstado(Estado.ROUBO);
                        repaint();                    
                        entrou = true;
                    }
                }
                if(botoes[lin + 1][col - 1].getEstado() == Estado.COMPRODAMABRANCA){
                    if(botoes[lin + 2][col - 2].getEstado() == Estado.POSSIVEL){
                        botoes[lin + 2][col - 2].setEstado(Estado.ROUBO);
                        repaint();
                        entrou = true;
                    }
                }              
                if(botoes[lin + 1][col + 1].getEstado() == Estado.COMPROBRANCA){
                    if(botoes[lin + 2][col + 2].getEstado() == Estado.POSSIVEL){
                        botoes[lin + 2][col + 2].setEstado(Estado.ROUBO);
                        repaint();
                        entrou = true;
                    }
                }
                if(botoes[lin + 1][col + 1].getEstado() == Estado.COMPRODAMABRANCA){
                    if(botoes[lin + 2][col + 2].getEstado() == Estado.POSSIVEL){
                        botoes[lin + 2][col + 2].setEstado(Estado.ROUBO);
                        repaint();
                        entrou = true;
                    }   
                }
            }
            return entrou;          
        }        
    }
    private boolean posRoubo;
    private Posicao posVez;
    private Posicao posOpo;
    private int opostas;
    private void PossibilidadesDamas(int linA, int colA, Estado pob){
        posRoubo = false;
        posVez = cliquesAnteriores.get(0);
        posOpo = null;
        opostas = 0;
        
        int lin = linA;
        int col = colA;
        
        //, boolean posRoubo, Posicao posOpo, Posicao posVez, int opostas
        if(col == 0){
            if(lin == 7){                   
                for(int l = --lin, c = ++col;l >= 0 && c <= 7; l--, c++){
                    if(!MovimentosDama(l, c, "SD"))
                    break;                            
                }
                opostas = 0;
                lin = linA;
                col = colA;
                posRoubo = false;
                posOpo = null;                    
            }
                
            else{
                for(int l = --lin, c = ++col;l >= 0 && c <= 7; l--, c++){
                    if(!MovimentosDama(l, c, "SD"))
                    break;                
                    
                }

                opostas = 0;
                lin = linA;
                col = colA;
                posRoubo = false;
                posOpo = null;                    
                
                for(int l = ++lin, c = ++col;l <= 7 && c >=0 ; l++, c++){
                    if(!MovimentosDama(l, c, "ID"))
                    break;                
                }                    
                 
                opostas = 0;
                lin = linA;
                col = colA;
                posRoubo = false;
                posOpo = null;                    
            }
        }
        else if(col == 7){
            if(lin == 0){                   
                for(int l = ++lin, c = --col;l <= 7 && c >= 0; l++, c--){                    
                    if(!MovimentosDama(l, c, "IE"))
                        break;                                     
                }                    

                opostas = 0;
                lin = linA;
                col = colA;
                posRoubo = false;
                posOpo = null;                    
            }
                
            else{
                for(int l = --lin, c = --col;l >= 0 && c >= 0; l--, c--){
                    if(!MovimentosDama(l, c, "SE"))
                        break;                                      
                }
                
                opostas = 0;
                lin = linA;
                col = colA;
                posRoubo = false;
                posOpo = null;                    
                
                for(int l = ++lin, c = --col;l <= 7 && c >= 0; l++, c--){                    
                    if(!MovimentosDama(l, c, "IE"))
                        break;                          
                }                    
                
                opostas = 0;
                lin = linA;
                col = colA;
                posRoubo = false;
                posOpo = null;                              
            }
        }           
        else if(lin == 7){
            for(int l = --lin, c = --col;l >= 0 && c >= 0; l--, c--){                
                if(!MovimentosDama(l, c, "SE"))
                    break;       
            }
            
            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;                        

            for(int l = --lin, c = ++col;l >= 0 && c <= 7; l--, c++){
                if(!MovimentosDama(l, c, "SD"))
                    break;
            }

            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;
        }
        else if(lin == 0){
            for(int l = ++lin, c = --col;l <= 7 && c >= 0; l++, c--){                
                if(!MovimentosDama(l, c, "IE"))
                    break;         
            }                    
            
            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;

            for(int l = ++lin, c = ++col;l <= 7 && c <=7 ; l++, c++){                
                if(!MovimentosDama(l, c, "ID"))
                break;                       
            }                    

            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;
        }
        else{
            for(int l = ++lin, c = --col;l <= 7 && c >= 0; l++, c--){                
                if(!MovimentosDama(l, c, "IE"))
                    break;            
            }                    

            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;

            for(int l = ++lin, c = ++col;l <= 7 && c <=7 ; l++, c++){
                if(!MovimentosDama(l, c, "ID"))
                    break;                
            }                    

            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;

            for(int l = --lin, c = --col;l >= 0 && c >= 0; l--, c--){                
                if(!MovimentosDama(l, c, "SE"))
                    break;                        
            }            
            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;

            for(int l = --lin, c = ++col;l >= 0 && c <= 7; l--, c++){                
                if(!MovimentosDama(l, c, "SD"))
                    break;
            }

            opostas = 0;
            lin = linA;
            col = colA;
            posRoubo = false;
            posOpo = null;
        }        
    }
    private boolean MovimentosDama(int l, int c, String dir){        
     if(botoes[l][c].isEnabled()){ 
        if(botoes[l][c].getEstado() == Estado.POSSIVEL){                    
            if(!posRoubo){
                botoes[l][c].setEstado(Estado.POSSIVELMOV);
                cliquesAnteriores.add(new Posicao(l, c, Estado.POSSIVELMOV));
                repaint();
            }else{
                botoes[l][c].setEstado(Estado.ROUBO);
                cliquesAnteriores.add(new Posicao(l, c, Estado.ROUBO));
                repaint();
            }
            if(posOpo != null){
                botoes[posOpo.getLin()][posOpo.getCol()].setEstado(posOpo.getEstado());
                cliquesAnteriores.add(posOpo);
                posOpo = null;
            }                    
        }
    }if(posVez.getEstado() == Estado.DAMABRANCA){
            if(botoes[l][c].getEstado() == Estado.PRETA ){
                opostas++;
                if(opostas > 1)
                    return false;    
                else{
                    posOpo = new Posicao(l, c, Estado.COMPROPRETA, dir);
                    posRoubo = true;
                }   
            }
            if(botoes[l][c].getEstado() == Estado.DAMAPRETA){
                opostas++;
                if(opostas > 1)
                    return false;    
                else{
                    posOpo = new Posicao(l, c, Estado.COMPRODAMAPRETA, dir);                        
                    posRoubo = true;
                }
            }
            else if( botoes[l][c].getEstado() == Estado.BRANCA || 
                botoes[l][c].getEstado() == Estado.DAMABRANCA) 
                    return false;
        }
        if(posVez.getEstado() == Estado.DAMAPRETA){
            if(botoes[l][c].getEstado() == Estado.BRANCA){
                opostas++;
                if(opostas > 1)
                    return false;    
                else{
                    posOpo = new Posicao(l, c, Estado.COMPROBRANCA, dir);                                
                    posRoubo = true;
                }   
            }
            if(botoes[l][c].getEstado() == Estado.DAMABRANCA){
                opostas++;
                    if(opostas > 1)
                        return false;    
                    else{
                        posOpo = new Posicao(l, c, Estado.COMPRODAMABRANCA, dir);                                
                        posRoubo = true;
                    }
            }
            else if( botoes[l][c].getEstado() == Estado.PRETA || 
                botoes[l][c].getEstado() == Estado.DAMAPRETA)
                    return false;
        }
        return true;
    }
    private void MostrarPossibilidades(int lin, int col, Estado pob){        
        System.out.println(lin + ":"+ col);
        if(PossivelRoubo(lin, col))
            return;
        if(col == 0){
            if(pob == Estado.BRANCA || pob == Estado.COMPROBRANCA){
                if(botoes[lin - 1][1].getEstado() == Estado.POSSIVEL){
                    botoes[lin - 1][1].setEstado(Estado.POSSIVELMOV);
                    cliquesAnteriores.add(new Posicao(lin - 1, 1, Estado.POSSIVELMOV));
                    repaint();
                }                
            }
            if(pob == Estado.PRETA || pob == Estado.COMPROPRETA){
                if(botoes[lin + 1][1].getEstado() == Estado.POSSIVEL){
                    botoes[lin + 1][1].setEstado(Estado.POSSIVELMOV);
                    cliquesAnteriores.add(new Posicao(lin + 1, 1, Estado.POSSIVELMOV));
                    repaint();                    
                }
            }
            return;
        }
        if(col == 7){
            if(pob == Estado.BRANCA || pob == Estado.COMPROBRANCA){
                if(botoes[lin - 1][6].getEstado() == Estado.POSSIVEL){
                    botoes[lin - 1][6].setEstado(Estado.POSSIVELMOV);
                    cliquesAnteriores.add(new Posicao(lin - 1, 6, Estado.POSSIVELMOV));
                    repaint();
                }
            }
            if(pob == Estado.PRETA || pob == Estado.COMPROPRETA){
                if(botoes[lin + 1][6].getEstado() == Estado.POSSIVEL){
                    botoes[lin + 1][6].setEstado(Estado.POSSIVELMOV);
                    cliquesAnteriores.add(new Posicao(lin + 1, 6, Estado.POSSIVELMOV));
                    repaint();
                }
            }
            return;
        }

        if(pob == Estado.BRANCA || pob == Estado.COMPROBRANCA){                  
            if(botoes[lin - 1][col + 1].getEstado() == Estado.POSSIVEL){                
                botoes[lin - 1][col + 1].setEstado(Estado.POSSIVELMOV);
                cliquesAnteriores.add(new Posicao(lin - 1, col + 1, Estado.POSSIVELMOV));                
                repaint();
            }
            if(botoes[lin - 1][col - 1].getEstado() == Estado.POSSIVEL){
                botoes[lin - 1][col - 1].setEstado(Estado.POSSIVELMOV);
                cliquesAnteriores.add(new Posicao(lin - 1, col - 1, Estado.POSSIVELMOV));                
                repaint();
            }
        }
        if(pob == Estado.PRETA || pob == Estado.COMPROPRETA){                  
            if(botoes[lin + 1][col + 1].getEstado() == Estado.POSSIVEL){                
                botoes[lin + 1][col + 1].setEstado(Estado.POSSIVELMOV);
                cliquesAnteriores.add(new Posicao(lin + 1, col + 1, Estado.POSSIVELMOV));                
                repaint();
            }
            if(botoes[lin + 1][col - 1].getEstado() == Estado.POSSIVEL){
                botoes[lin + 1][col - 1].setEstado(Estado.POSSIVELMOV);                
                cliquesAnteriores.add(new Posicao(lin + 1, col - 1, Estado.POSSIVELMOV));                
                repaint();
            }  
        }    
    }
    
    private void PrepararTabuleiro(Container container){

        for(int x = 0; x < 8; x++){            
            for(int y = 0; y < 8; y++){                
                botoes[x][y] = new Peca();                      // Cria uma nova Peca		      
                botoes[x][y].setName("peca"+x+y+"");            // Salva o nome
                container.add(botoes[x][y]);                    // Adiciona no container da janela
                botoes[x][y].addActionListener(this);           // Adiciona um evento para o botão            
            }
        }

        //SETANDO TODAS AS POSICOES POSSIVEIS E IMPOSSIVEIS DE SE COLOCAR PEÇAS
        for(int linha = 0; linha < 8; linha ++){
            for(int coluna = 0; coluna < 8; coluna ++){
                if(((linha % 2) == 0 && coluna % 2 == 0) || 
                    ((linha % 2) != 0 && coluna % 2 != 0)){
                        botoes[linha][coluna].setEstado(Estado.IMPOSSIVEL);
                        botoes[linha][coluna].setEnabled(false);
                    }
                
                else
                    botoes[linha][coluna].setEstado(Estado.POSSIVEL);                    
            }            
        }
    }

    private void PrepararPecas(){
        for(int lin = 0; lin < 8; lin++){
            for(int col = 0; col < 8; col++){
                if(botoes[lin][col].getEstado() != Estado.IMPOSSIVEL){
                    if(lin < 3)
                        botoes[lin][col].setEstado(Estado.PRETA);                
                    if(lin > 4)
                        botoes[lin][col].setEstado(Estado.BRANCA);           
                }
            }
        }
        
        //CANTOS ESQUERDOS
        /*this.vezBranca = false;
        botoes[1][0].setEstado(Estado.DAMAPRETA);
        botoes[3][0].setEstado(Estado.DAMAPRETA);
        botoes[7][0].setEstado(Estado.DAMAPRETA);        
        //CANTOS DIREITOS
        botoes[0][7].setEstado(Estado.DAMAPRETA);
        botoes[2][7].setEstado(Estado.DAMAPRETA);        
        botoes[4][7].setEstado(Estado.DAMAPRETA);
        //INFERIOR
        botoes[7][2].setEstado(Estado.DAMAPRETA);
        botoes[7][4].setEstado(Estado.DAMAPRETA);
        botoes[7][6].setEstado(Estado.DAMAPRETA);
        //SUPERIOR
        botoes[7][2].setEstado(Estado.DAMABRANCA);
        // botoes[2][3].setEstado(Estado.DAMAPRETA);
        
        
        // botoes[4][5].setEstado(Estado.PRETA);   
        // botoes[3][6].setEstado(Estado.PRETA);        
        botoes[3][4].setEstado(Estado.PRETA);        

        this.vezBranca = false;*/
    }

    private void desativarPretas(){
        for(int lin = 0; lin < 8; lin ++){
            for(int col = 0; col < 8; col ++){
                if( botoes[lin][col].getEstado() == Estado.BRANCA ||
                    botoes[lin][col].getEstado() == Estado.COMPROBRANCA ||
                    botoes[lin][col].getEstado() == Estado.DAMABRANCA ||
                    botoes[lin][col].getEstado() == Estado.COMPRODAMABRANCA ||                    
                    botoes[lin][col].getEstado() == Estado.POSSIVEL)
                    botoes[lin][col].setEnabled(true);
                
                if( botoes[lin][col].getEstado() == Estado.PRETA ||
                    botoes[lin][col].getEstado() == Estado.COMPROPRETA ||
                    botoes[lin][col].getEstado() == Estado.DAMAPRETA ||
                    botoes[lin][col].getEstado() == Estado.COMPRODAMAPRETA ||                    
                    botoes[lin][col].getEstado() == Estado.IMPOSSIVEL)
                    botoes[lin][col].setEnabled(false);
            }
        }
    }

    private void desativarBrancas(){
        for(int lin = 0; lin < 8; lin ++){
            for(int col = 0; col < 8; col ++){
                if( botoes[lin][col].getEstado() == Estado.BRANCA ||
                    botoes[lin][col].getEstado() == Estado.COMPROBRANCA ||
                    botoes[lin][col].getEstado() == Estado.DAMABRANCA ||
                    botoes[lin][col].getEstado() == Estado.COMPRODAMABRANCA ||
                    botoes[lin][col].getEstado() == Estado.IMPOSSIVEL)
                    botoes[lin][col].setEnabled(false);
                
                if( botoes[lin][col].getEstado() == Estado.PRETA ||
                    botoes[lin][col].getEstado() == Estado.COMPROPRETA ||
                    botoes[lin][col].getEstado() == Estado.DAMAPRETA ||
                    botoes[lin][col].getEstado() == Estado.COMPRODAMAPRETA ||
                    botoes[lin][col].getEstado() == Estado.POSSIVEL)
                    botoes[lin][col].setEnabled(true);
            }
        }
    }    

    private boolean Jogou(int linA, int colA){
        if(this.cliquesAnteriores.size() > 0){            
            Posicao posPec = this.cliquesAnteriores.get(0);
            
            if( posPec.getEstado() == Estado.BRANCA || 
                posPec.getEstado() == Estado.PRETA ||
                posPec.getEstado() == Estado.DAMABRANCA || 
                posPec.getEstado() == Estado.DAMAPRETA ||
                posPec.getEstado() == Estado.COMPROBRANCA||
                posPec.getEstado() == Estado.COMPROPRETA){
                if(botoes[linA][colA].getEstado() == Estado.POSSIVELMOV || botoes[linA][colA].getEstado() == Estado.ROUBO){
                    return true;
                }
            }
        }
        return false;
    }
    
    private void ForcarRoubo(Posicao atual){
        for (int i = 0; i < botoes.length; i++) {
            for (int j = 0; j < botoes.length; j++) {
                if(botoes[i][j] != botoes[atual.getLin()][atual.getCol()])
                    if(botoes[i][j].getEstado() != Estado.POSSIVEL)
                        botoes[i][j].setEnabled(false);
            }
        }
    }

    private void DesativarPecas(Posicao atual){                
        if(atual == null)
            return;        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {                

                if(botoes[i][j] != botoes[atual.getLin()][atual.getCol()])
                    if(botoes[i][j].getEstado() != Estado.ROUBO)
                            botoes[i][j].setEnabled(false);                    
                
            }
        }
    }    
    private void verificarComprometidas(Estado estado){
        if(estado == Estado.BRANCA){

        }
    }
    private boolean SeComprometeu(int lin, int col){
        boolean entrou = false;
        if( col == 0 ||
            col == 7)
            return false;
        if( lin == 0 ||
            lin == 7)
            return false;

        if(this.vezBranca){                        
            desativarPretas();
            //desativarBrancas();
            if( botoes[lin - 1][col + 1].getEstado() == Estado.PRETA ||
                botoes[lin - 1][col + 1].getEstado() == Estado.DAMAPRETA){
                if(botoes[lin + 1][col - 1].getEstado() == Estado.POSSIVEL){                                        
                    
                    botoes[lin][col].setEstado(Estado.COMPROBRANCA);
                    botoes[lin - 1][col + 1].setEnabled(true);
                    entrou = true;
                    playSound("src/SFX/comprometeu.wav");
                }
            }
            
            if( botoes[lin - 1][col - 1].getEstado() == Estado.PRETA ||
                botoes[lin - 1][col - 1].getEstado() == Estado.DAMAPRETA){
                if(botoes[lin + 1][col + 1].getEstado() == Estado.POSSIVEL){                                        
                    
                    botoes[lin][col].setEstado(Estado.COMPROBRANCA);
                    botoes[lin - 1][col - 1].setEnabled(true);
                    entrou = true;
                    playSound("src/SFX/comprometeu.wav");
                }
            }

            for (int i = 0; i < botoes.length; i++) {
                for (int j = 0; j < botoes.length; j++) {
                    if(botoes[i][j].getEstado() == Estado.DAMAPRETA){
                        if(Math.abs(i - lin) == Math.abs(j - col)){                            
                            if((i - lin) > 0){
                                if(botoes[lin - 1][col + 1].getEstado() == Estado.POSSIVEL){
                                    if(botoes[lin + 1][col - 1].getEstado() == Estado.POSSIVEL){
                                        botoes[lin][col].setEstado(Estado.COMPROBRANCA);
                                        botoes[i][j].setEnabled(true);
                                        playSound("src/SFX/comprometeu.wav");
                                        entrou = true;
                                        break;
                                    }
                                }                                
                            }else{
                                if(botoes[lin - 1][col - 1].getEstado() == Estado.POSSIVEL){
                                    if(botoes[lin + 1][col + 1].getEstado() == Estado.POSSIVEL){
                                        botoes[lin][col].setEstado(Estado.COMPROBRANCA);
                                        botoes[i][j].setEnabled(true);
                                        playSound("src/SFX/comprometeu.wav");
                                        entrou = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return entrou;
        }else{
            desativarBrancas();
            //desativarPretas();
            if( botoes[lin + 1][col + 1].getEstado() == Estado.BRANCA ||
                botoes[lin + 1][col + 1].getEstado() == Estado.DAMABRANCA){
                if(botoes[lin - 1][col - 1].getEstado() == Estado.POSSIVEL){
                                        
                    botoes[lin][col].setEstado(Estado.COMPROPRETA);
                    botoes[lin + 1][col + 1].setEnabled(true);
                    entrou = true;
                    playSound("src/SFX/comprometeu.wav");
                }
            }
            if( botoes[lin + 1][col - 1].getEstado() == Estado.BRANCA||
                botoes[lin + 1][col - 1].getEstado() == Estado.DAMABRANCA){
                if(botoes[lin - 1][col + 1].getEstado() == Estado.POSSIVEL){
                     
                    botoes[lin][col].setEstado(Estado.COMPROPRETA);
                    botoes[lin + 1][col - 1].setEnabled(true);
                    entrou = true;
                    playSound("src/SFX/comprometeu.wav");
                }
            }

            for (int i = 0; i < botoes.length; i++) {
                for (int j = 0; j < botoes.length; j++) {
                    if(botoes[i][j].getEstado() == Estado.DAMABRANCA){
                        if(Math.abs(i - lin) == Math.abs(j - col)){                            
                            if((i - lin) > 0){
                                if(botoes[lin - 1][col + 1].getEstado() == Estado.POSSIVEL){
                                    if(botoes[lin + 1][col - 1].getEstado() == Estado.POSSIVEL){
                                        botoes[lin][col].setEstado(Estado.COMPROPRETA);
                                        botoes[i][j].setEnabled(true);
                                        playSound("src/SFX/comprometeu.wav");
                                        entrou = true;
                                        break;
                                    }
                                }                                
                            }else{
                                if(botoes[lin - 1][col - 1].getEstado() == Estado.POSSIVEL){
                                    if(botoes[lin + 1][col + 1].getEstado() == Estado.POSSIVEL){
                                        botoes[lin][col].setEstado(Estado.COMPROPRETA);
                                        botoes[i][j].setEnabled(true);
                                        playSound("src/SFX/comprometeu.wav");
                                        entrou = true;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return entrou;
        }                       
    }    

    private boolean Roubou(int linA, int colA){
        Posicao posPeca = this.cliquesAnteriores.get(0);
        
        if(botoes[linA][colA].getEstado() == Estado.ROUBO){
            if(vezBranca){
                if(botoes[posPeca.getLin()][posPeca.getCol()].getEstado() == Estado.DAMABRANCA){
                    for (int i = 0; i < cliquesAnteriores.size(); i++) {
                        Posicao posCompro = cliquesAnteriores.get(i);
                        if( posCompro.getEstado() == Estado.COMPROPRETA ||
                            posCompro.getEstado() == Estado.COMPRODAMAPRETA){
                            
                            if(posPeca.getLin() > linA){
                                if(posPeca.getCol() < colA){    //ROUBOU PRA CIMA-DIREITA
                                    if(posCompro.getDir() == "SD"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMABRANCA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                                if(posPeca.getCol() > colA){    //ROUBOU PRA CIMA-ESQUERDA
                                    if(posCompro.getDir() == "SE"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMABRANCA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                            }
                            if(posPeca.getLin() < linA){
                                if(posPeca.getCol() < colA){    //ROUBOU PRA BAIXO-DIREITA
                                    if(posCompro.getDir() == "ID"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMABRANCA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                                if(posPeca.getCol() > colA){    //ROUBOU PRA BAIXO-ESQUERDA
                                    if(posCompro.getDir() == "IE"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMABRANCA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                            }
                        }                        
                    }
                    return false;
                }
                else{
                    if(colA == 7){
                        if( botoes[linA + 1][colA - 1].getEstado() == Estado.COMPROPRETA||
                            botoes[linA + 1][colA - 1].getEstado() == Estado.COMPRODAMAPRETA)
                            if(posPeca.getCol() < colA)
                                botoes[linA + 1][colA - 1].setEstado(Estado.POSSIVEL);    
                    }
                    else if(colA == 0){
                        if( botoes[linA + 1][colA + 1].getEstado() == Estado.COMPROPRETA||
                            botoes[linA + 1][colA + 1].getEstado() == Estado.COMPRODAMAPRETA)
                            if(posPeca.getCol() > colA)
                                botoes[linA + 1][colA + 1].setEstado(Estado.POSSIVEL);    
                    }
                    else{
                        if( botoes[linA + 1][colA + 1].getEstado() == Estado.COMPROPRETA||
                            botoes[linA + 1][colA + 1].getEstado() == Estado.COMPRODAMAPRETA)
                            if(posPeca.getCol() > colA)
                                botoes[linA + 1][colA + 1].setEstado(Estado.POSSIVEL);
                        
                        if( botoes[linA + 1][colA - 1].getEstado() == Estado.COMPROPRETA||
                            botoes[linA + 1][colA - 1].getEstado() == Estado.COMPRODAMAPRETA)
                            if(posPeca.getCol() < colA)
                                botoes[linA + 1][colA - 1].setEstado(Estado.POSSIVEL);
                    }
                    repaint();
                    if(linA != 0){                            
                        botoes[linA][colA].setEstado(Estado.BRANCA);                        
                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                    
                        repaint();
                        System.out.println("ROUBOU");                                                
                        return true;
                    }
                    else{                        
                        botoes[linA][colA].setEstado(Estado.DAMABRANCA);                        
                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                                                    
                        repaint();
                        System.out.println("ROUBOU");
                        return true;                    
                    }                                     
                }
            }            
            else{
                if(botoes[posPeca.getLin()][posPeca.getCol()].getEstado() == Estado.DAMAPRETA){
                    for (int i = 0; i < cliquesAnteriores.size(); i++) {
                        Posicao posCompro = cliquesAnteriores.get(i);
                        if( posCompro.getEstado() == Estado.COMPROBRANCA ||
                            posCompro.getEstado() == Estado.COMPRODAMABRANCA){
                            
                            if(posPeca.getLin() > linA){
                                if(posPeca.getCol() < colA){    //ROUBOU PRA CIMA-DIREITA
                                    if(posCompro.getDir() == "SD"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMAPRETA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                                if(posPeca.getCol() > colA){    //ROUBOU PRA CIMA-ESQUERDA
                                    if(posCompro.getDir() == "SE"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMAPRETA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                            }
                            if(posPeca.getLin() < linA){
                                if(posPeca.getCol() < colA){    //ROUBOU PRA BAIXO-DIREITA
                                    if(posCompro.getDir() == "ID"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMAPRETA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                                if(posPeca.getCol() > colA){    //ROUBOU PRA BAIXO-ESQUERDA
                                    if(posCompro.getDir() == "IE"){
                                        botoes[posCompro.getLin()][posCompro.getCol()].setEstado(Estado.POSSIVEL);
                                        botoes[linA][colA].setEstado(Estado.DAMAPRETA);
                                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                        
                                        return true;
                                    }                                    
                                }
                            }
                        }                        
                    }
                    return false;
                }else{
                    if(colA == 7){
                        if( botoes[linA - 1][colA - 1].getEstado() == Estado.COMPROBRANCA ||
                            botoes[linA - 1][colA - 1].getEstado() == Estado.COMPRODAMABRANCA)
                            if(posPeca.getCol() < colA)
                                botoes[linA - 1][colA - 1].setEstado(Estado.POSSIVEL);    
                    }
                    else if(colA == 0){
                        if( botoes[linA - 1][colA + 1].getEstado() == Estado.COMPROBRANCA||
                            botoes[linA - 1][colA + 1].getEstado() == Estado.COMPRODAMABRANCA)
                            if(posPeca.getCol() > colA)
                                botoes[linA - 1][colA + 1].setEstado(Estado.POSSIVEL);    
                    }
                    else{
                        if( botoes[linA - 1][colA + 1].getEstado() == Estado.COMPROBRANCA||
                             botoes[linA - 1][colA + 1].getEstado() == Estado.COMPRODAMABRANCA)
                            if(posPeca.getCol() > colA)
                                botoes[linA - 1][colA + 1].setEstado(Estado.POSSIVEL);
    
                        if( botoes[linA - 1][colA - 1].getEstado() == Estado.COMPROBRANCA||
                            botoes[linA - 1][colA - 1].getEstado() == Estado.COMPRODAMABRANCA)
                            if(posPeca.getCol() < colA)
                                botoes[linA - 1][colA - 1].setEstado(Estado.POSSIVEL);
                        repaint();
                    }
                    
                    if(linA != 7){                            
                        botoes[linA][colA].setEstado(Estado.PRETA);                        
                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);
                        repaint();
                        System.out.println("ROUBOU");                                                
                        return true;
                    }else{                        
                        botoes[linA][colA].setEstado(Estado.DAMAPRETA);                        
                        botoes[posPeca.getLin()][posPeca.getCol()].setEstado(Estado.POSSIVEL);                                                
                        repaint();
                        System.out.println("ROUBOU");
                        return true;
                    }  
                }
            }
        }                        
        return false;            
    }

    private void RemoverComprometidas(){ //Ineficiente
        if(this.vezBranca){
            for (int i = 0; i < botoes.length; i++) {
                for (int j = 0; j < botoes.length; j++) {
                    if(botoes[i][j].getEstado() == Estado.COMPROPRETA)
                        botoes[i][j].setEstado(Estado.PRETA);
                    if(botoes[i][j].getEstado() == Estado.COMPRODAMAPRETA)
                        botoes[i][j].setEstado(Estado.DAMAPRETA);                  
                }
            }
        }else{
            for (int i = 0; i < botoes.length; i++) {
                for (int j = 0; j < botoes.length; j++) {
                    if(botoes[i][j].getEstado() == Estado.COMPROBRANCA)
                        botoes[i][j].setEstado(Estado.BRANCA);
                    if(botoes[i][j].getEstado() == Estado.COMPRODAMABRANCA)
                        botoes[i][j].setEstado(Estado.DAMABRANCA);
                }
            }
        }
    }

    private boolean Ganhou(){
        if(this.pecasBranco == 0)
            return true;
        if(this.pecasPreto == 0)
            return true;
        return false;
    }   

    public String toString(){
        return( "\n\tNumero de cliques: "   + cliquesAnteriores.size()+ 
                " vez da Branca: "          + this.vezBranca+
                " \n\t\t Pecas brancas: "   + this.pecasBranco+
                " \n\t\t Pecas pretas: "    + this.pecasPreto);                
    }
}