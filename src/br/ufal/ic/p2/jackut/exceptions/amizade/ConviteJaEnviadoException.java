package br.ufal.ic.p2.jackut.exceptions.amizade;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ConviteJaEnviadoException extends JackutException {
    public ConviteJaEnviadoException() {
        super("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
    }
}