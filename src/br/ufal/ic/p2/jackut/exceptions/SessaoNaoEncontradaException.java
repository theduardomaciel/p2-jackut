package br.ufal.ic.p2.jackut.exceptions;

public class SessaoNaoEncontradaException extends JackutException {
    public SessaoNaoEncontradaException() {
        super("Sessão não encontrada.");
    }
}