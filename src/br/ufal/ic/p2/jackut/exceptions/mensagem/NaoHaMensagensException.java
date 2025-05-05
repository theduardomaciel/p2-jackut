package br.ufal.ic.p2.jackut.exceptions.mensagem;

import br.ufal.ic.p2.jackut.exceptions.JackutException;

public class NaoHaMensagensException extends JackutException {
    public NaoHaMensagensException() {
        super("Não há mensagens.");
    }
}