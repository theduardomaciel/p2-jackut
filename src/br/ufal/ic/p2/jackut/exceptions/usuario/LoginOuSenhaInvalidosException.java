package br.ufal.ic.p2.jackut.exceptions.usuario;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class LoginOuSenhaInvalidosException extends JackutException {
    public LoginOuSenhaInvalidosException() {
        super("Login ou senha inválidos.");
    }
}