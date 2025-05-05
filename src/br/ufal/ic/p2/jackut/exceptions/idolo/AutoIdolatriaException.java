package br.ufal.ic.p2.jackut.exceptions.idolo;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class AutoIdolatriaException extends JackutException {
    public AutoIdolatriaException() {
        super("Usuário não pode ser fã de si mesmo.");
    }
}