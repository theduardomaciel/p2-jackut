package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.sessao.SessaoNaoEncontradaException;
import br.ufal.ic.p2.jackut.exceptions.usuario.LoginOuSenhaInvalidosException;
import br.ufal.ic.p2.jackut.exceptions.usuario.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;

import java.util.HashMap;
import java.util.Map;

public class SessaoService {
    private static SessaoService instance;
    private final Map<String, String> sessoes;
    private int contadorSessao;
    private final UsuarioService usuarioService;

    // Construtor privado para implementar o Singleton
    private SessaoService() {
        this.sessoes = new HashMap<>();
        this.contadorSessao = 0;
        this.usuarioService = UsuarioService.getInstance();
    }

    /**
     * Obt�m a inst�ncia �nica da classe SessaoService.
     * Implementa o padr�o Singleton para garantir que apenas uma inst�ncia
     * da classe seja criada durante a execu��o do programa.
     *
     * @return A inst�ncia �nica de SessaoService.
     */
    public static synchronized SessaoService getInstance() {
        if (instance == null) {
            instance = new SessaoService();
        }
        return instance;
    }

    /**
     * Abre uma nova sess�o para o usu�rio com o login e senha fornecidos.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return O identificador �nico da sess�o criada.
     * @throws LoginOuSenhaInvalidosException Se o login ou a senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            throw new LoginOuSenhaInvalidosException();
        }

        try {
            Usuario usuario = usuarioService.getUsuario(login);
            if (!usuario.getSenha().equals(senha)) {
                throw new LoginOuSenhaInvalidosException();
            }

            String idSessao = "sessao" + (++contadorSessao);
            sessoes.put(idSessao, login);
            return idSessao;
        } catch (UsuarioNaoCadastradoException e) {
            throw new LoginOuSenhaInvalidosException();
        }
    }

    /**
     * Obt�m o login do usu�rio associado a um identificador de sess�o.
     *
     * @param id O identificador da sess�o.
     * @return O login do usu�rio associado � sess�o.
     * @throws UsuarioNaoCadastradoException Se o identificador da sess�o for nulo ou vazio.
     * @throws SessaoNaoEncontradaException Se a sess�o n�o for encontrada.
     */
    public String getLoginUsuario(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new UsuarioNaoCadastradoException();
        }
        if (!sessoes.containsKey(id)) {
            throw new SessaoNaoEncontradaException();
        }
        return sessoes.get(id);
    }

    /**
     * Reseta todas as sess�es ativas, removendo-as e reiniciando o contador de sess�es.
     */
    public void zerarSessoes() {
        sessoes.clear();
        contadorSessao = 0;
    }
}
