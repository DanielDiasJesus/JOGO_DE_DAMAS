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
class Posicao{
    private int lin, col;
    private Estado estado;
    private String dir;
    
    public Posicao(){}
    
    public Posicao(int lin, int col, Estado estado){
        setLin(lin);
        setCol(col);
        setEstado(estado);
        setDir("");
    }
    public Posicao(int lin, int col, Estado estado, String dir){
        setLin(lin);
        setCol(col);
        setEstado(estado);
        setDir(dir);
    }
    public void setDir(String dir){
        this.dir = dir;
    }
    public String getDir(){
        return this.dir;
    }
    public void setEstado(Estado est){
        this.estado = est;
    }

    public Estado getEstado(){
        return this.estado;
    }

    public void setLin(int l){
        this.lin = l;
    }
    
    public void setCol(int c){
        this.col = c;
    }

   public int getLin(){
       return this.lin;
   }
   public int getCol(){
       return this.col;
   }
   @Override
   public boolean equals(Object obj){
        if(obj == null)
            return false;
        if(obj.getClass() != this.getClass())
            return false;
        
        Posicao pos = (Posicao) obj;

        if(this.getLin() != pos.getLin())
            return false;
        if(this.getCol() != pos.getCol())
            return false;
        if(this.getEstado() != pos.getEstado())
            return false;
        return true;
   }
}
