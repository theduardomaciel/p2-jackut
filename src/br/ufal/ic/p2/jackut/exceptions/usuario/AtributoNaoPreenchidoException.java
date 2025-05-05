package br.ufal.ic.p2.jackut.exceptions.usuario;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AtributoNaoPreenchidoException extends JackutException {
    public AtributoNaoPreenchidoException() {
        super("Atributo não preenchido.");
    }
}