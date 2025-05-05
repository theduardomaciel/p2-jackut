package br.ufal.ic.p2.jackut.exceptions.recado;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class RecadoParaSiMesmoException extends JackutException {
    public RecadoParaSiMesmoException() {
        super("Usuário não pode enviar recado para si mesmo.");
    }
}