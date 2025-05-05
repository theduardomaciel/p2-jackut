package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

public class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String remetente;
    private final String conteudo;

    public Mensagem(String remetente, String conteudo) {
        this.remetente = remetente;
        this.conteudo = conteudo;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getRemetente() {
        return remetente;
    }
}