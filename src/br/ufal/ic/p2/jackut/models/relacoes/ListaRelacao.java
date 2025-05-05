package br.ufal.ic.p2.jackut.models.relacoes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaRelacao implements Relacao {
    private final List<String> lista;

    public ListaRelacao() {
        this.lista = new ArrayList<>();
    }

    @Override
    public void adicionar(String login) {
        if (!lista.contains(login)) {
            lista.add(login);
        }
    }

    @Override
    public boolean contem(String login) {
        return lista.contains(login);
    }

    @Override
    public List<String> listar() {
        return Collections.unmodifiableList(lista);
    }

    @Override
    public void remover(String login) {
        lista.remove(login);
    }
}
