package br.ufal.ic.p2.jackut.exceptions.idolo;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class IdoloExistenteException extends JackutException {
    public IdoloExistenteException() {
        super("Usu�rio j� est� adicionado como �dolo.");
    }
}