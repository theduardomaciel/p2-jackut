package br.ufal.ic.p2.jackut.exceptions;

public class UsuarioNaoCadastradoException extends JackutException {
    public UsuarioNaoCadastradoException() {
        super("Usu�rio n�o cadastrado.");
    }
}