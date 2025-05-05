package br.ufal.ic.p2.jackut.exceptions.usuario;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ContaExistenteException extends JackutException {
    public ContaExistenteException() {
        super("Conta com esse nome já existe.");
    }
}