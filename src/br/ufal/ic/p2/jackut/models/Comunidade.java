package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.mensagem.NaoHaMensagensException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;
import java.util.LinkedList;

public class Comunidade implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nome;
    private String descricao;
    private Usuario dono;
    private List<Usuario> membros;
    private final Map<String, Queue<Mensagem>> mensagensPorUsuario;

    public Comunidade(String nome, String descricao, Usuario dono) {
        this.nome = nome;
        this.descricao = descricao;
        this.dono = dono;
        this.membros = new ArrayList<>();
        this.membros.add(dono);
        this.mensagensPorUsuario = new HashMap<>();
        this.mensagensPorUsuario.put(dono.getLogin(), new LinkedList<>());
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
            mensagensPorUsuario.put(usuario.getLogin(), new LinkedList<>());
            usuario.adicionarComunidade(nome);
        }
    }

    public boolean contemMembro(Usuario usuario) {
        return membros.contains(usuario);
    }

    public void adicionarMensagem(String conteudo, String remetente) {
        Mensagem mensagem = new Mensagem(conteudo, remetente);
        for (Usuario membro : membros) {
            mensagensPorUsuario.get(membro.getLogin()).add(mensagem);
        }
    }

    public String lerMensagem(String login) {
        Queue<Mensagem> mensagens = mensagensPorUsuario.get(login);
        if (mensagens.isEmpty()) {
            throw new NaoHaMensagensException();
        }
        return mensagens.poll().getConteudo();
    }
}
