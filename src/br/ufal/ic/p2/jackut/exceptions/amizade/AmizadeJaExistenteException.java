package br.ufal.ic.p2.jackut.exceptions.amizade;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AmizadeJaExistenteException extends JackutException {
    public AmizadeJaExistenteException() {
        super("Usuário já está adicionado como amigo.");
    }
}