package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.services.*;

public class Facade {
    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    private final AmizadeService amizadeService;
    private final RecadoService recadoService;

    public Facade() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
        this.amizadeService = AmizadeService.getInstance();
        this.recadoService = RecadoService.getInstance();
    }

    public void zerarSistema() {
        usuarioService.zerarSistema();
        sessaoService.zerarSessoes();
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

    public void encerrarSistema() {
        usuarioService.salvarDados();
    }
}
