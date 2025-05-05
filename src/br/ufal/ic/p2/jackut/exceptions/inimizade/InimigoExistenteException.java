package br.ufal.ic.p2.jackut.exceptions.inimizade;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class InimigoExistenteException extends JackutException {
    public InimigoExistenteException() {
        super("Usuário já está adicionado como inimigo.");
    }
}