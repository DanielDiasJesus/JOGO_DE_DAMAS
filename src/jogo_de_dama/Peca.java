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
import java.awt.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.GeneralPath;
import javax.swing.JButton;

public class Peca extends JButton{
    private static int tamanho = 64;
    private Estado estado;                

    public void setEstado(Estado e){
        this.estado = e;
    }

    public Estado getEstado(){
        return this.estado;
    }

    public Peca(){
        super();
        estado =  Estado.IMPOSSIVEL;
    }
    public Dimension getMaximumSize() { return getPreferredSize(); }
    public Dimension getMinimumSize() { return getPreferredSize(); }
    public Dimension getPreferredSize() { return new Dimension(tamanho,tamanho); }
    
    private void criarCirculo(Graphics2D g2d, Color cor1, Color cor2){
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(cor1);
        g2d.fillOval(5,5,getWidth()-12,getHeight()-12);
        g2d.setColor(cor2);
        g2d.fillOval(9,9,getWidth() - 20,getHeight() - 20);
        setBackground(new Color(130, 130, 130));
    }
    private void criarCoroa(Graphics2D g2d, Color cor1){
        g2d.setColor(cor1);
        int [][] corona_co = {{ 10, 35 }, {30, 50}, {42, 15}, 
                                {53, 50}, {73, 35}, {65, 59}, {19, 59}};
        
        int [][] traz1_co = {{18, 40}, {25, 35}, {32, 40}, {29, 48}};
        int [][] traz2_co = {{51, 40}, {53, 48}, {65, 40}, {58, 35}};

        //Polygon poligono = new Polygon(xCoroa, yCoroa, nPoints);
        //g2d.drawPolygon(poligono);
        
        GeneralPath corona = new GeneralPath();
        GeneralPath traz1 = new GeneralPath();
        GeneralPath traz2 = new GeneralPath();

        corona.moveTo(corona_co[0][0], corona_co[0][1]);
        traz1.moveTo(traz1_co[0][0], traz1_co[0][1]);
        traz2.moveTo(traz2_co[0][0], traz2_co[0][1]);

        for (int k = 1; k < corona_co.length; k++)
            corona.lineTo(corona_co[k][0], corona_co[k][1]);                
        for (int k = 1; k < traz1_co.length; k++)
            traz1.lineTo(traz1_co[k][0], traz1_co[k][1]);
        for (int k = 1; k < traz2_co.length; k++)
            traz2.lineTo(traz2_co[k][0], traz2_co[k][1]); 
        
        corona.closePath();
        g2d.fill(corona);
        g2d.fill(traz1);
        g2d.fill(traz2);

        setBackground(new Color(130, 130, 130));
    }
	private void Desenhar(Graphics g){
        Graphics2D g2d = (Graphics2D) g;                

        if (estado != Estado.IMPOSSIVEL)
		{
			if(estado == Estado.BRANCA) {				                
                criarCirculo(g2d, new Color(237, 237, 237), new Color(200, 200, 200));
			}
			if(estado == Estado.PRETA) {                
                criarCirculo(g2d, new Color(36, 36, 36), new Color(15, 15, 15));                				
				
            }
            if (estado == Estado.COMPROPRETA){
                criarCirculo(g2d, new Color(36, 36, 36), new Color(255, 10, 10)); 
     				
            }
            if (estado == Estado.COMPROBRANCA){
                criarCirculo(g2d, new Color(237, 237, 237), new Color(255, 10, 10)); 
     				
            }
            if(estado == Estado.DAMABRANCA){                
                criarCirculo(g2d, new Color(237, 237, 237), new Color(200, 200, 200));
                criarCoroa(g2d, new Color(0, 0, 0));
            }
            if(estado ==  Estado.DAMAPRETA){                
                criarCirculo(g2d, new Color(36, 36, 36), new Color(15, 15, 15));
                criarCoroa(g2d, new Color(237, 237, 237));                           
            }
            if(estado ==  Estado.COMPRODAMAPRETA){                
                criarCirculo(g2d, new Color(36, 36, 36), new Color(15, 15, 15));
                criarCoroa(g2d, new Color(255, 10, 10));                           
            }
            if(estado == Estado.COMPRODAMABRANCA){
                criarCirculo(g2d, new Color(237, 237, 237), new Color(200, 200, 200));
                criarCoroa(g2d, new Color(255, 10, 10));
            }
            if (estado == Estado.POSSIVEL){
                setBackground(new Color(130, 130, 130));				
            }
            if (estado == Estado.POSSIVELMOV){                
                setBackground(new Color(201, 233, 255));				
            }
            if (estado == Estado.ROUBO){                
                setBackground(new Color(201, 233, 255));				
            }
        }
            else
                setBackground(new Color(237, 237, 237));					
	}    
	protected void paintComponent(Graphics g)
	{
            super.paintComponent(g);
            Desenhar(g);
	}
}
