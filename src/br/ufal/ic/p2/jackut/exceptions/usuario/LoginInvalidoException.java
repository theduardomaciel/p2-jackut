package br.ufal.ic.p2.jackut.exceptions.usuario;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class LoginInvalidoException extends JackutException {
    public LoginInvalidoException() {
        super("Login inválido.");
    }
}