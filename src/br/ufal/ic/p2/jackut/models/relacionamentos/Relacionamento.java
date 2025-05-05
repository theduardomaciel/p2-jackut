package br.ufal.ic.p2.jackut.models.relacionamentos;

import java.io.Serializable;
import java.util.List;

public interface Relacionamento extends Serializable {
    void adicionar(String login);
    boolean contem(String login);
    List<String> listar();
    void remover(String login);
}
