package br.ufal.ic.p2.jackut.exceptions.recado;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class RecadoNaoEncontradoException extends JackutException {
    public RecadoNaoEncontradoException() {
        super("Não há recados.");
    }
}