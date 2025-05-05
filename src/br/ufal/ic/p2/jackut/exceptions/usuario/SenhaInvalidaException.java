package br.ufal.ic.p2.jackut.exceptions.usuario;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class SenhaInvalidaException extends JackutException {
    public SenhaInvalidaException() {
        super("Senha inválida.");
    }
}