package br.ufal.ic.p2.jackut.exceptions.sessao;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class SessaoNaoEncontradaException extends JackutException {
    public SessaoNaoEncontradaException() {
        super("Sess�o n�o encontrada.");
    }
}