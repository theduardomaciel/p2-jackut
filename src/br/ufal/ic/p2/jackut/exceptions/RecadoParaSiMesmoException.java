package br.ufal.ic.p2.jackut.exceptions;

public class RecadoParaSiMesmoException extends JackutException {
    public RecadoParaSiMesmoException() {
        super("Usu�rio n�o pode enviar recado para si mesmo.");
    }
}