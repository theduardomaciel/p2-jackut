package br.ufal.ic.p2.jackut.exceptions.paquera;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class PaqueraExistenteException extends JackutException {
    public PaqueraExistenteException() {
        super("Usu�rio j� est� adicionado como paquera.");
    }
}