package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.services.*;
import br.ufal.ic.p2.jackut.services.mensagem.RecadoService;
import br.ufal.ic.p2.jackut.services.relacionamentos.AmizadeService;
import br.ufal.ic.p2.jackut.services.relacionamentos.IdoloService;
import br.ufal.ic.p2.jackut.services.relacionamentos.InimizadeService;
import br.ufal.ic.p2.jackut.services.relacionamentos.PaqueraService;

public class Facade {
    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    private final AmizadeService amizadeService;
    private final RecadoService recadoService;
    private final ComunidadeService comunidadeService;
    private final IdoloService idoloService;
    private final PaqueraService paqueraService;
    private final InimizadeService inimizadeService;

    public Facade() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
        this.amizadeService = AmizadeService.getInstance();
        this.recadoService = RecadoService.getInstance();
        this.comunidadeService = ComunidadeService.getInstance();
        this.idoloService = IdoloService.getInstance();
        this.paqueraService = PaqueraService.getInstance();
        this.inimizadeService = InimizadeService.getInstance();
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
        recadoService.enviarMensagem(remetente, destinatario, mensagem);
    }

    public String lerRecado(String id) {
        String login = sessaoService.getLoginUsuario(id);
        return recadoService.lerMensagem(login);
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

    public void enviarMensagem(String id, String nomeComunidade, String mensagem) {
        String loginRemetente = sessaoService.getLoginUsuario(id);
        comunidadeService.enviarMensagem(nomeComunidade, loginRemetente, mensagem);
    }

    public String lerMensagem(String id) {
        String login = sessaoService.getLoginUsuario(id);
        return comunidadeService.lerMensagem(login);
    }

    public void adicionarIdolo(String id, String idolo) {
        idoloService.adicionarIdolo(id, idolo);
    }

    public boolean ehFa(String login, String idolo) {
        return idoloService.ehFa(login, idolo);
    }

    public String getFas(String login) {
        return idoloService.getFas(login);
    }

    public void adicionarPaquera(String id, String paquera) {
        paqueraService.adicionarPaquera(id, paquera);
    }

    public boolean ehPaquera(String id, String paquera) {
        return paqueraService.ehPaquera(id, paquera);
    }

    public String getPaqueras(String id) {
        return paqueraService.getPaqueras(id);
    }

    public void adicionarInimigo(String id, String inimigo) {
        inimizadeService.adicionarInimigo(id, inimigo);
    }

    public void encerrarSistema() {
        usuarioService.salvarDados();
    }
}