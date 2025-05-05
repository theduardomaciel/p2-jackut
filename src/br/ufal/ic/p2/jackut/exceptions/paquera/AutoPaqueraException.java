package br.ufal.ic.p2.jackut.exceptions.paquera;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AutoPaqueraException extends JackutException {
    public AutoPaqueraException() {
        super("Usuário não pode ser paquera de si mesmo.");
    }
}