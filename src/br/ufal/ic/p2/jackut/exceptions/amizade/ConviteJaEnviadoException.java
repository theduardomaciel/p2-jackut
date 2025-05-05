package br.ufal.ic.p2.jackut.exceptions.amizade;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class ConviteJaEnviadoException extends JackutException {
    public ConviteJaEnviadoException() {
        super("Usuário já está adicionado como amigo, esperando aceitação do convite.");
    }
}