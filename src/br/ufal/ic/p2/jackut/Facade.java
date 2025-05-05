package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.services.*;

public class Facade {
    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    private final AmizadeService amizadeService;
    private final RecadoService recadoService;
    private final ComunidadeService comunidadeService;

    public Facade() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
        this.amizadeService = AmizadeService.getInstance();
        this.recadoService = RecadoService.getInstance();
        this.comunidadeService = ComunidadeService.getInstance();
    }

    public void zerarSistema() {
        usuarioService.zerarSistema();
        sessaoService.zerarSessoes();
        comunidadeService.zerarComunidades();
    }

    public void criarUsuario(String login, String senha, String nome) {
        usuarioService.criarUsuario(login, senha, nome);
    }

    public String abrirSessao(String login, String senha) {
        return sessaoService.abrirSessao(login, senha);
    }

    public String getAtributoUsuario(String login, String atributo) {
        return usuarioService.getAtributoUsuario(login, atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) {
        String login = sessaoService.getLoginUsuario(id);
        usuarioService.editarPerfil(login, atributo, valor);
    }

    public void adicionarAmigo(String id, String amigoLogin) {
        String login = sessaoService.getLoginUsuario(id);
        amizadeService.adicionarAmigo(login, amigoLogin);
    }

    public boolean ehAmigo(String login, String amigoLogin) {
        return amizadeService.ehAmigo(login, amigoLogin);
    }

    public String getAmigos(String login) {
        return amizadeService.getAmigos(login);
    }

    public void enviarRecado(String id, String destinatario, String mensagem) {
        String remetente = sessaoService.getLoginUsuario(id);
        recadoService.enviarRecado(remetente, destinatario, mensagem);
    }

    public String lerRecado(String id) {
        String login = sessaoService.getLoginUsuario(id);
        return recadoService.lerRecado(login);
    }

    public void criarComunidade(String sessaoId, String nome, String descricao) {
        String loginDono = sessaoService.getLoginUsuario(sessaoId);
        comunidadeService.criarComunidade(nome, descricao, loginDono);
    }

    public String getComunidades(String login) {
        return comunidadeService.getComunidades(login);
    }

    public String getDescricaoComunidade(String nome) {
        return comunidadeService.getDescricaoComunidade(nome);
    }

    public String getDonoComunidade(String nome) {
        return comunidadeService.getDonoComunidade(nome);
    }

    public String getMembrosComunidade(String nome) {
        return comunidadeService.getMembrosComunidade(nome);
    }

    public void adicionarComunidade(String sessaoId, String nome) {
        String loginUsuario = sessaoService.getLoginUsuario(sessaoId);
        comunidadeService.adicionarMembro(nome, loginUsuario);
    }

    public void encerrarSistema() {
        usuarioService.salvarDados();
    }
}
