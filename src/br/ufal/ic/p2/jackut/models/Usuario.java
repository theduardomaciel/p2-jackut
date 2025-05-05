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

    private AmizadeRelacao amizades;
    private Queue<Mensagem> recados;

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
     * Obtém o valor de um atributo do usuário.
     *
     * @param atributo O nome do atributo.
     * @return O valor do atributo.
     * @throws AtributoNaoPreenchidoException Se o atributo não estiver preenchido.
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
     * Define o valor de um atributo do usuário.
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

    public List<String> getAmigos() {
        return amizades.listar();
    }

    public void enviarConviteAmizade(String loginAmigo) {
        amizades.enviarConvite(loginAmigo);
    }

    public void aceitarAmizade(String loginAmigo) {
        if (!amizades.contem(loginAmigo)) {
            amizades.adicionar(loginAmigo);
        }
    }

    public boolean verificarConvitePendente(String loginAmigo) {
        return amizades.temConvitePendente(loginAmigo);
    }

    public boolean ehAmigo(String loginAmigo) {
        return amizades.contem(loginAmigo);
    }

    // Relacionamentos

    public void adicionarComunidade(String nomeComunidade) {
        comunidades.adicionar(nomeComunidade);
    }

    public List<String> getComunidades() {
        return comunidades.listar();
    }

    public boolean ehFa(String idolo) {
        return idolos.contem(idolo);
    }

    public void adicionarIdolo(String idolo) {
        idolos.adicionar(idolo);
    }

    public boolean ehPaquera(String paquera) {
        return paqueras.contem(paquera);
    }

    public void adicionarPaquera(String paquera) {
        paqueras.adicionar(paquera);
    }

    public List<String> getPaqueras() {
        return paqueras.listar();
    }

    public void adicionarInimigo(String inimigo) {
        inimigos.adicionar(inimigo);
    }

    public boolean ehInimigo(String inimigo) {
        return inimigos.contem(inimigo);
    }

    public void removerComunidade(String nomeComunidade) {
        comunidades.remover(nomeComunidade);
    }

    // Recados

    /**
     * Adiciona um recado para o usuário.
     *
     * @param recado A mensagem do recado.
     */
    public void adicionarRecado(Mensagem recado) {
        recados.add(recado);
    }

    /**
     * Lê o próximo recado do usuário.
     *
     * @return A mensagem do recado.
     */
    public String lerRecado() {
        if (recados.isEmpty()) {
            return null;
        }
        return recados.poll().getConteudo();
    }

    public void removerRecados(String login) {
        recados.removeIf(recado -> recado.getRemetente().equals(login));
    }
}
