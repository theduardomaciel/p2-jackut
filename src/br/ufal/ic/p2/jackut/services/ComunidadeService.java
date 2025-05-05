package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.comunidade.ComunidadeExistenteException;
import br.ufal.ic.p2.jackut.exceptions.comunidade.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.exceptions.comunidade.UsuarioJaMembroException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import java.util.HashMap;
import java.util.Map;

public class ComunidadeService {
    private static ComunidadeService instance;
    private final Map<String, Comunidade> comunidades;
    private final UsuarioService usuarioService;

    private ComunidadeService() {
        this.comunidades = new HashMap<>();
        this.usuarioService = UsuarioService.getInstance();
    }

    public static synchronized ComunidadeService getInstance() {
        if (instance == null) {
            instance = new ComunidadeService();
        }
        return instance;
    }

    public void criarComunidade(String nome, String descricao, String loginDono) {
        if (comunidades.containsKey(nome)) {
            throw new ComunidadeExistenteException();
        }

        // Verifica se o dono existe
        Usuario dono = usuarioService.getUsuario(loginDono);

        // Cria a comunidade
        Comunidade comunidade = new Comunidade(nome, descricao, dono);
        comunidades.put(nome, comunidade);

        // Salva os dados
        usuarioService.salvarDados();
    }

    public String getDescricaoComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return comunidade.getDescricao();
    }

    public String getDonoComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return comunidade.getLoginDono();
    }

    public String getMembrosComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return "{" + String.join(",", comunidade.getMembrosLogins()) + "}";
    }

    public void adicionarMembro(String nome, String loginUsuario) {
        Comunidade comunidade = getComunidade(nome);
        Usuario usuario = usuarioService.getUsuario(loginUsuario);
        
        if (comunidade.contemMembro(usuario)) {
            throw new UsuarioJaMembroException();
        }

        comunidade.adicionarMembro(usuario);
        usuarioService.salvarDados();
    }

    private Comunidade getComunidade(String nome) {
        if (!comunidades.containsKey(nome)) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidades.get(nome);
    }

    public String getComunidades(String login) {
        return usuarioService.getComunidades(login);
    }

    public void zerarComunidades() {
        comunidades.clear();
    }
}
