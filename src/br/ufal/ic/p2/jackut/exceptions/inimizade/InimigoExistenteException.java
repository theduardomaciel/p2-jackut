package br.ufal.ic.p2.jackut.exceptions.inimizade;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class InimigoExistenteException extends JackutException {
    public InimigoExistenteException() {
        super("Usu�rio j� est� adicionado como inimigo.");
    }
}