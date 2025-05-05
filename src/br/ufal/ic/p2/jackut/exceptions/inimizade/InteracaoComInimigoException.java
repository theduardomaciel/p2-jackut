package br.ufal.ic.p2.jackut.exceptions.inimizade;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class InteracaoComInimigoException extends JackutException {
    public InteracaoComInimigoException(String nomeInimigo) {
        super("Fun��o inv�lida: " + nomeInimigo + " � seu inimigo.");
    }
}