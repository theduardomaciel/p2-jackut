package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Usuario implements Serializable {
    // serialVersionUID � uma vers�o �nica para identificar a classe
    // durante a serializa��o e desserializa��o
    // Isso � importante para garantir que a vers�o da classe que
    // est� sendo lida � compat�vel com a vers�o gravada (acredita em mim)
    // Se a vers�o n�o for compat�vel, pode ocorrer uma InvalidClassException.
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private Map<String, String> atributos;
    private List<String> amigos;
    private Queue<String> recados;

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.atributos = new HashMap<>();
        this.amigos = new ArrayList<>();
        this.recados = new LinkedList<>();
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getAtributo(String atributo) {
        if (atributo.equalsIgnoreCase("nome")) {
            return nome;
        }

        if (!atributos.containsKey(atributo.toLowerCase())) {
            throw new RuntimeException("Atributo n�o preenchido.");
        }

        return atributos.get(atributo.toLowerCase());
    }

    public void setAtributo(String atributo, String valor) {
        if (atributo.equalsIgnoreCase("nome")) {
            this.nome = valor;
        } else {
            atributos.put(atributo.toLowerCase(), valor);
        }
    }

    public List<String> getAmigos() {
        return amigos;
    }

    public void adicionarAmigo(String amigo) {
        if (!amigos.contains(amigo)) {
            amigos.add(amigo);
        }
    }

    public boolean ehAmigo(String amigo) {
        return amigos.contains(amigo);
    }

    public void adicionarRecado(String recado) {
        recados.add(recado);
    }

    public String lerRecado() {
        if (recados.isEmpty()) {
            return null;
        }
        return recados.poll();
    }
}