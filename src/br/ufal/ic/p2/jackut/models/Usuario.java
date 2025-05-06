package br.ufal.ic.p2.jackut.models;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import br.ufal.ic.p2.jackut.exceptions.usuario.AtributoNaoPreenchidoException;
import br.ufal.ic.p2.jackut.models.relacoes.*;

public class Usuario implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String login;
    private String senha;
    private String nome;
    private Map<String, String> atributos;

    private Queue<Mensagem> recados;

    private AmizadeRelacao amizades;
    private ListaRelacao comunidades;
    private ListaRelacao idolos;
    private ListaRelacao paqueras;
    private ListaRelacao inimigos;

    public Usuario(String login, String senha, String nome) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.atributos = new HashMap<>();
        this.amizades = new AmizadeRelacao();
        this.recados = new LinkedList<>();
        this.comunidades = new ListaRelacao();
        this.idolos = new ListaRelacao();
        this.paqueras = new ListaRelacao();
        this.inimigos = new ListaRelacao();
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    /**
     * Obt�m o valor de um atributo do usu�rio.
     *
     * @param atributo O nome do atributo.
     * @return O valor do atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo n�o estiver preenchido.
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
     * Define o valor de um atributo do usu�rio.
     *
     * @param atributo O nome do atributo.
     * @param valor    O novo valor do atributo.
     */
    public void setAtributo(String atributo, String valor) {
        if (atributo.equalsIgnoreCase("nome")) {
            this.nome = valor;
        } else {
            atributos.put(atributo.toLowerCase(), valor);
        }
    }

    // Amizades

    /**
     * Obt�m a lista de amigos do usu�rio.
     *
     * @return Uma lista contendo os logins dos amigos.
     */
    public List<String> getAmigos() {
        return amizades.listar();
    }

    /**
     * Envia um convite de amizade para outro usu�rio.
     *
     * @param loginAmigo O login do usu�rio para quem o convite ser� enviado.
     */
    public void enviarConviteAmizade(String loginAmigo) {
        amizades.enviarConvite(loginAmigo);
    }

    /**
     * Aceita um convite de amizade de outro usu�rio.
     *
     * @param loginAmigo O login do usu�rio cujo convite ser� aceito.
     */
    public void aceitarAmizade(String loginAmigo) {
        if (!amizades.contem(loginAmigo)) {
            amizades.adicionar(loginAmigo);
        }
    }

    /**
     * Verifica se h� um convite de amizade pendente de um usu�rio.
     *
     * @param loginAmigo O login do usu�rio a ser verificado.
     * @return {@code true} se houver um convite pendente, caso contr�rio {@code false}.
     */
    public boolean verificarConvitePendente(String loginAmigo) {
        return amizades.temConvitePendente(loginAmigo);
    }

    /**
     * Verifica se um usu�rio � amigo.
     *
     * @param loginAmigo O login do usu�rio a ser verificado.
     * @return {@code true} se o usu�rio for amigo, caso contr�rio {@code false}.
     */
    public boolean ehAmigo(String loginAmigo) {
        return amizades.contem(loginAmigo);
    }

    /**
     * Adiciona uma comunidade ao perfil do usu�rio.
     *
     * @param nomeComunidade O nome da comunidade a ser adicionada.
     */
    public void adicionarComunidade(String nomeComunidade) {
        comunidades.adicionar(nomeComunidade);
    }

    /**
     * Obt�m a lista de comunidades do usu�rio.
     *
     * @return Uma lista contendo os nomes das comunidades.
     */
    public List<String> getComunidades() {
        return comunidades.listar();
    }

    /**
     * Verifica se o usu�rio � f� de um �dolo.
     *
     * @param idolo O nome do �dolo a ser verificado.
     * @return {@code true} se o usu�rio for f�, caso contr�rio {@code false}.
     */
    public boolean ehFa(String idolo) {
        return idolos.contem(idolo);
    }

    /**
     * Adiciona um �dolo ao perfil do usu�rio.
     *
     * @param idolo O nome do �dolo a ser adicionado.
     */
    public void adicionarIdolo(String idolo) {
        idolos.adicionar(idolo);
    }

    /**
     * Verifica se o usu�rio tem um paquera.
     *
     * @param paquera O login do paquera a ser verificado.
     * @return {@code true} se o usu�rio tiver o paquera, caso contr�rio {@code false}.
     */
    public boolean ehPaquera(String paquera) {
        return paqueras.contem(paquera);
    }

    /**
     * Adiciona um paquera ao perfil do usu�rio.
     *
     * @param paquera O login do paquera a ser adicionado.
     */
    public void adicionarPaquera(String paquera) {
        paqueras.adicionar(paquera);
    }

    /**
     * Obt�m a lista de paqueras do usu�rio.
     *
     * @return Uma lista contendo os logins dos paqueras.
     */
    public List<String> getPaqueras() {
        return paqueras.listar();
    }

    /**
     * Adiciona um inimigo ao perfil do usu�rio.
     *
     * @param inimigo O login do inimigo a ser adicionado.
     */
    public void adicionarInimigo(String inimigo) {
        inimigos.adicionar(inimigo);
    }

    /**
     * Verifica se o usu�rio tem um inimigo.
     *
     * @param inimigo O login do inimigo a ser verificado.
     * @return {@code true} se o usu�rio tiver o inimigo, caso contr�rio {@code false}.
     */
    public boolean ehInimigo(String inimigo) {
        return inimigos.contem(inimigo);
    }

    /**
     * Remove uma comunidade do perfil do usu�rio.
     *
     * @param nomeComunidade O nome da comunidade a ser removida.
     */
    public void removerComunidade(String nomeComunidade) {
        comunidades.remover(nomeComunidade);
    }

    // Recados

    /**
     * Adiciona um recado para o usu�rio.
     *
     * @param recado A mensagem do recado.
     */
    public void adicionarRecado(Mensagem recado) {
        recados.add(recado);
    }

    /**
     * L� o pr�ximo recado do usu�rio.
     *
     * @return A mensagem do recado.
     */
    public String lerRecado() {
        if (recados.isEmpty()) {
            return null;
        }
        return recados.poll().getConteudo();
    }

    /**
     * Remove todos os recados enviados por um usu�rio espec�fico.
     *
     * @param login O login do remetente cujos recados ser�o removidos.
     */
    public void removerRecados(String login) {
        recados.removeIf(recado -> recado.getRemetente().equals(login));
    }
