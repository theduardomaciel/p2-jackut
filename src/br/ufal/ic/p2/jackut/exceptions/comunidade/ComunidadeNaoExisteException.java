package br.ufal.ic.p2.jackut.exceptions.comunidade;

public class ComunidadeNaoExisteException extends RuntimeException {
    public ComunidadeNaoExisteException() {
        super("Comunidade não existe.");
    }
}
