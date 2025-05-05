package br.ufal.ic.p2.jackut.exceptions.comunidade;

public class ComunidadeExistenteException extends RuntimeException {
    public ComunidadeExistenteException() {
        super("Comunidade com esse nome já existe.");
    }
}
