package br.ufal.ic.p2.jackut.exceptions;

public class AmizadeParaSiMesmoException extends JackutException {
    public AmizadeParaSiMesmoException() {
        super("Usu�rio n�o pode adicionar a si mesmo como amigo.");
    }
}