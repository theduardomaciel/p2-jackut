package br.ufal.ic.p2.jackut.exceptions.comunidade;

public class UsuarioNaoMembroException extends RuntimeException {
    public UsuarioNaoMembroException() {
        super("Usuario n�o � membro da comunidade.");
    }
}
