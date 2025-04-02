package br.ufal.ic.p2.jackut.exceptions;

public class ContaExistenteException extends JackutException {
    public ContaExistenteException() {
        super("Conta com esse nome já existe.");
    }
}