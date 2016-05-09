package com.ufcg.virtualmusicbox.Model;


public class Music implements Comparable<Music> {
    public String titulo;
    public String cantor;
    public int votos;

    public Music(String mTitulo, String mCantor, int mVotos){
        titulo = mTitulo;
        cantor = mCantor;
        votos = mVotos;
    }

    public void vote(){
        votos++;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getCantor() {
        return cantor;
    }

    public void setCantor(String cantor) {
        this.cantor = cantor;
    }

    public int getVotos() {
        return votos;
    }

    public void setVotos(int votos) {
        this.votos = votos;
    }

    @Override
    public int compareTo(Music music) {
        if(this.getVotos() > music.getVotos()) {
            return -1;
        }else {
            return 1;
        }
    }

}