package br.ufal.ic.p2.jackut;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Collections;

public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private Map<String, String> atributos;
    private List<String> amigos;
    private List<String> convitesEnviados;
    private Queue<String> recados;

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.atributos = new HashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
        this.recados = new LinkedList<>();
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getAtributo(String atributo) {
        if (atributo.equalsIgnoreCase("nome")) {
            return nome;
        }

        if (!atributos.containsKey(atributo.toLowerCase())) {
            throw new RuntimeException("Atributo não preenchido.");
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
        return Collections.unmodifiableList(amigos);
    }

    public void enviarConviteAmizade(String loginAmigo) {
        if (amigos.contains(loginAmigo)) {
            return;
        }

        if (convitesEnviados.contains(loginAmigo)) {
            return;
        }

        convitesEnviados.add(loginAmigo);
    }

    public void aceitarAmizade(String loginAmigo) {
        if (amigos.contains(loginAmigo)) {
            return;
        }

        amigos.add(loginAmigo);
    }

    public boolean verificarConvitePendente(String loginAmigo) {
        return convitesEnviados.contains(loginAmigo);
    }

    public boolean ehAmigo(String loginAmigo) {
        return amigos.contains(loginAmigo);
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