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

    // Método para obter a instância única
    public static synchronized SessaoService getInstance() {
        if (instance == null) {
            instance = new SessaoService();
        }
        return instance;
    }

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

    public String getLoginUsuario(String id) {
        if (id == null || id.trim().isEmpty()) {
            throw new UsuarioNaoCadastradoException();
        }
        if (!sessoes.containsKey(id)) {
            throw new SessaoNaoEncontradaException();
        }
        return sessoes.get(id);
    }

    public void zerarSessoes() {
        sessoes.clear();
        contadorSessao = 0;
    }
}
