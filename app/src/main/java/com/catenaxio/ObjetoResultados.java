package com.catenaxio;

/**
 * Created by hugoizquierdo on 9/13/14.
 */
public class ObjetoResultados {

    private String resultado;
    private String rival;
    private String abel;
    private String jesus;
    private String cano;
    private String hugo;
    private String javi;
    private String jordan;
    private String juanito;
    private String meri;
    private String indiceResultados;

    public  ObjetoResultados(String res,String rival,String ab,String je,String ca,String hu,
                             String jo,String ju,String me,String indice){
        this.resultado=res;
        this.rival=rival;
        this.abel=ab;
        this.jesus=je;
        this.cano=ca;
        this.hugo=hu;
        //this.javi=ja;
        this.jordan=jo;
        this.juanito=ju;
        this.meri=me;
        this.indiceResultados=indice;

    }

    public String getResultado() {
        return resultado;
    }

    public String getAbel() {
        return abel;
    }

    public String getJesus() {
        return jesus;
    }

    public String getCano() {
        return cano;
    }

    public String getHugo() {
        return hugo;
    }

    public String getJavi() {
        return javi;
    }

    public String getJordan() {
        return jordan;
    }

    public String getJuanito() {
        return juanito;
    }

    public String getMeri() {
        return meri;
    }

    public String getIndiceResultados() {
        return indiceResultados;
    }

    public String getRival() {
        return rival;
    }
}
