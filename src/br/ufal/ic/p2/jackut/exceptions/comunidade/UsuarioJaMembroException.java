package br.ufal.ic.p2.jackut.exceptions.comunidade;

public class UsuarioJaMembroException extends RuntimeException {
    public UsuarioJaMembroException() {
        super("Usuario já faz parte dessa comunidade.");
    }
}
