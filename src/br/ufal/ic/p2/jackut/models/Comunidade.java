package br.ufal.ic.p2.jackut.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Comunidade implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;
    private Usuario dono;
    private List<Usuario> membros;

    public Comunidade(String nome, String descricao, Usuario dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new ArrayList<>();
        this.membros.add(dono);
        dono.adicionarComunidade(nome);
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getLoginDono() {
        return dono.getLogin();
    }

    public List<String> getMembrosLogins() {
        return membros.stream()
                     .map(Usuario::getLogin)
                     .toList();
    }

    public void adicionarMembro(Usuario usuario) {
        if (!membros.contains(usuario)) {
            membros.add(usuario);
            usuario.adicionarComunidade(nome);
        }
    }

    public boolean contemMembro(Usuario usuario) {
        return membros.contains(usuario);
    }
}
