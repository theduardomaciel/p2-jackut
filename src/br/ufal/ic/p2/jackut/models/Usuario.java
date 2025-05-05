package br.ufal.ic.p2.jackut.models;

import br.ufal.ic.p2.jackut.exceptions.AtributoNaoPreenchidoException;

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
    private List<String> comunidades;

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.atributos = new HashMap<>();
        this.amigos = new ArrayList<>();
        this.convitesEnviados = new ArrayList<>();
        this.recados = new LinkedList<>();
        this.comunidades = new ArrayList<>();
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    /**
     * Obt?m o valor de um atributo do usu?rio.
     *
     * @param atributo O nome do atributo.
     * @return O valor do atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo n?o estiver preenchido.
     */
    public String getAtributo(String atributo) {
        if (atributo.equalsIgnoreCase("nome")) {
            return nome;
        }

        if (!atributos.containsKey(atributo.toLowerCase())) {
            throw new AtributoNaoPreenchidoException();
        }

        return atributos.get(atributo.toLowerCase());
    }

    /**
     * Define o valor de um atributo do usu?rio.
     *
     * @param atributo O nome do atributo.
     * @param valor O novo valor do atributo.
     */
    public void setAtributo(String atributo, String valor) {
        if (atributo.equalsIgnoreCase("nome")) {
            this.nome = valor;
        } else {
            atributos.put(atributo.toLowerCase(), valor);
        }
    }

    /**
     * Obt?m a lista de amigos do usu?rio.
     *
     * @return A lista de amigos.
     */
    public List<String> getAmigos() {
        return Collections.unmodifiableList(amigos);
    }

    /**
     * Envia um convite de amizade para outro usu?rio.
     *
     * @param loginAmigo O login do amigo.
     */
    public void enviarConviteAmizade(String loginAmigo) {
        if (amigos.contains(loginAmigo)) {
            return;
        }

        if (convitesEnviados.contains(loginAmigo)) {
            return;
        }

        convitesEnviados.add(loginAmigo);
    }

    /**
     * Aceita um convite de amizade de outro usu?rio.
     *
     * @param loginAmigo O login do amigo.
     */
    public void aceitarAmizade(String loginAmigo) {
        if (amigos.contains(loginAmigo)) {
            return;
        }

        amigos.add(loginAmigo);
    }

    /**
     * Verifica se h? um convite de amizade pendente de outro usu?rio.
     *
     * @param loginAmigo O login do amigo.
     * @return true se houver um convite pendente, false caso contr?rio.
     */
    public boolean verificarConvitePendente(String loginAmigo) {
        return convitesEnviados.contains(loginAmigo);
    }

    /**
     * Verifica se outro usu?rio ? amigo.
     *
     * @param loginAmigo O login do amigo.
     * @return true se forem amigos, false caso contr?rio.
     */
    public boolean ehAmigo(String loginAmigo) {
        return amigos.contains(loginAmigo);
    }

    /**
     * Adiciona um recado para o usu?rio.
     *
     * @param recado A mensagem do recado.
     */
    public void adicionarRecado(String recado) {
        recados.add(recado);
    }

    /**
     * L? o pr?ximo recado do usu?rio.
     *
     * @return A mensagem do recado.
     */
    public String lerRecado() {
        if (recados.isEmpty()) {
            return null;
        }
        return recados.poll();
    }

    public void adicionarComunidade(String nomeComunidade) {
        if (!comunidades.contains(nomeComunidade)) {
            comunidades.add(nomeComunidade);
        }
    }

    public List<String> getComunidades() {
        return Collections.unmodifiableList(comunidades);
    }
}
