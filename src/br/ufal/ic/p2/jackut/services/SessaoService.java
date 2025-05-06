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
     * Obtém a instância única da classe SessaoService.
     * Implementa o padrão Singleton para garantir que apenas uma instância
     * da classe seja criada durante a execução do programa.
     *
     * @return A instância única de SessaoService.
     */
    public static synchronized SessaoService getInstance() {
        if (instance == null) {
            instance = new SessaoService();
        }
        return instance;
    }

    /**
     * Abre uma nova sessão para o usuário com o login e senha fornecidos.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O identificador único da sessão criada.
     * @throws LoginOuSenhaInvalidosException Se o login ou a senha forem inválidos.
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
     * Obtém o login do usuário associado a um identificador de sessão.
     *
     * @param id O identificador da sessão.
     * @return O login do usuário associado à sessão.
     * @throws UsuarioNaoCadastradoException Se o identificador da sessão for nulo ou vazio.
     * @throws SessaoNaoEncontradaException Se a sessão não for encontrada.
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
     * Reseta todas as sessões ativas, removendo-as e reiniciando o contador de sessões.
     */
    public void zerarSessoes() {
        sessoes.clear();
        contadorSessao = 0;
    }
}
