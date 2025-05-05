package br.ufal.ic.p2.jackut.exceptions.paquera;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class PaqueraExistenteException extends JackutException {
    public PaqueraExistenteException() {
        super("Usuário já está adicionado como paquera.");
    }
}