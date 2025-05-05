package br.ufal.ic.p2.jackut.exceptions.usuario;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class UsuarioNaoCadastradoException extends JackutException {
    public UsuarioNaoCadastradoException() {
        super("Usuário não cadastrado.");
    }
}