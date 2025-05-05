package br.ufal.ic.p2.jackut.exceptions.comunidade;

public class UsuarioNaoMembroException extends RuntimeException {
    public UsuarioNaoMembroException() {
        super("Usuario não é membro da comunidade.");
    }
}
