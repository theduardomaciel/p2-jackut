package br.ufal.ic.p2.jackut.exceptions.idolo;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class IdoloExistenteException extends JackutException {
    public IdoloExistenteException() {
        super("Usuário já está adicionado como ídolo.");
    }
}