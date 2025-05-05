package br.ufal.ic.p2.jackut.exceptions.inimizade;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AutoInimizadeException extends JackutException {
    public AutoInimizadeException() {
        super("Usuário não pode ser inimigo de si mesmo.");
    }
}