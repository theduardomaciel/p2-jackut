package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.mensagem.NaoHaMensagensException;

import java.io.Serializable;
import java.util.ArrayList;
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

    /**
     * Retorna o nome da comunidade.
     *
     * @return o nome da comunidade.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Retorna a descri��o da comunidade.
     *
     * @return a descri��o da comunidade.
     */
    public String getDescricao() {
        return descricao;
    }

    /**
     * Retorna o login do dono da comunidade.
     *
     * @return o login do dono da comunidade.
     */
    public String getLoginDono() {
        return dono.getLogin();
    }

    /**
     * Retorna uma lista de membros da comunidade.
     *
     * @return uma c�pia da lista de membros da comunidade.
     */
    public List<Usuario> getMembros() {
        return new ArrayList<>(membros);
    }

    /**
     * Retorna uma lista de logins dos membros da comunidade.
     *
     * @return uma lista contendo os logins dos membros.
     */
    public List<String> getMembrosLogins() {
        return membros.stream()
                .map(Usuario::getLogin)
                .toList();
    }

    /**
     * Adiciona um novo membro � comunidade.
     *
     * @param usuario o usu�rio a ser adicionado como membro.
     */
    public void adicionarMembro(Usuario usuario) {
        if (!membros.contains(usuario)) {
            membros.add(usuario);
            mensagensPorUsuario.put(usuario.getLogin(), new LinkedList<>());
            usuario.adicionarComunidade(nome);
        }
    }

    /**
     * Verifica se um usu�rio � membro da comunidade.
     *
     * @param usuario o usu�rio a ser verificado.
     * @return true se o usu�rio for membro, false caso contr�rio.
     */
    public boolean contemMembro(Usuario usuario) {
        return membros.contains(usuario);
    }

    /**
     * Adiciona uma mensagem � comunidade, enviada por um remetente.
     *
     * @param remetente o login do remetente da mensagem.
     * @param conteudo o conte�do da mensagem.
     */
    public void adicionarMensagem(String remetente, String conteudo) {
        Mensagem mensagem = new Mensagem(remetente, conteudo);
        for (Usuario membro : membros) {
            mensagensPorUsuario.get(membro.getLogin()).add(mensagem);
        }
    }

    /**
     * L� a pr�xima mensagem dispon�vel para um usu�rio.
     *
     * @param login o login do usu�rio que deseja ler a mensagem.
     * @return o conte�do da pr�xima mensagem.
     * @throws NaoHaMensagensException se n�o houver mensagens dispon�veis.
     */
    public String lerMensagem(String login) {
        Queue<Mensagem> mensagens = mensagensPorUsuario.get(login);
        if (mensagens.isEmpty()) {
            throw new NaoHaMensagensException();
        }
        return mensagens.poll().getConteudo();
    }

    /**
     * Remove um membro da comunidade.
     *
     * @param usuario o usu�rio a ser removido.
     */
    public void removerMembro(Usuario usuario) {
        if (membros.contains(usuario)) {
            membros.remove(usuario);
            mensagensPorUsuario.remove(usuario.getLogin());
            usuario.removerComunidade(nome);
        }
    }
}
