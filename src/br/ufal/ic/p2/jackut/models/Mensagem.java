package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;

public class Mensagem implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String conteudo;
    private final String remetente;

    public Mensagem(String conteudo, String remetente) {
        this.conteudo = conteudo;
        this.remetente = remetente;
    }

    public String getConteudo() {
        return conteudo;
    }

    public String getRemetente() {
        return remetente;
    }
}