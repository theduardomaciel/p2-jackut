package br.ufal.ic.p2.jackut.exceptions;

public class AmizadeJaExistenteException extends JackutException {
    public AmizadeJaExistenteException() {
        super("Usu�rio j� est� adicionado como amigo.");
    }
}