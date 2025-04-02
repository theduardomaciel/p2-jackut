package br.ufal.ic.p2.jackut;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Usuario implements Serializable {
    private String login;
    private String senha;
    private String nome;
    private List<String> amigos;
    private Queue<String> recados;

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
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