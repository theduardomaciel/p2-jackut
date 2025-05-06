package br.ufal.ic.p2.jackut.models.relacoes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListaRelacao implements Relacao {
    private final List<String> lista;

    public ListaRelacao() {
        this.lista = new ArrayList<>();
    }

    /**
     * Adiciona um login à lista, caso ele ainda não esteja presente.
     *
     * @param login o login a ser adicionado
     */
    @Override
    public void adicionar(String login) {
        if (!lista.contains(login)) {
            lista.add(login);
        }
    }

    /**
     * Verifica se um login está presente na lista.
     *
     * @param login o login a ser verificado
     * @return true se o login estiver na lista, false caso contrário
     */
    @Override
    public boolean contem(String login) {
        return lista.contains(login);
    }

    /**
     * Retorna uma lista imutável contendo todos os logins.
     *
     * @return uma lista imutável de logins
     */
    @Override
    public List<String> listar() {
        return Collections.unmodifiableList(lista);
    }

    /**
     * Remove um login da lista, caso ele esteja presente.
     *
     * @param login o login a ser removido
     */
    @Override
    public void remover(String login) {
        lista.remove(login);
    }
}
