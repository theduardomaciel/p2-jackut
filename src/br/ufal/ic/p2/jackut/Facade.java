package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.models.relacoes.TipoRelacionamento;
import br.ufal.ic.p2.jackut.services.ComunidadeService;
import br.ufal.ic.p2.jackut.services.RecadoService;
import br.ufal.ic.p2.jackut.services.SessaoService;
import br.ufal.ic.p2.jackut.services.UsuarioService;
import br.ufal.ic.p2.jackut.services.RelacionamentoService;

public class Facade {
    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    private final RecadoService recadoService;
    private final ComunidadeService comunidadeService;
    private final RelacionamentoService relacionamentoService;

    public Facade() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
        this.recadoService = RecadoService.getInstance();
        this.comunidadeService = ComunidadeService.getInstance();
        this.relacionamentoService = RelacionamentoService.getInstance();
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

    // Métodos de amizade usando o novo RelacionamentoService
    public void adicionarAmigo(String id, String amigoLogin) {
        relacionamentoService.adicionarRelacionamento(TipoRelacionamento.AMIZADE, id, amigoLogin);
    }

    public boolean ehAmigo(String login, String amigoLogin) {
        return relacionamentoService.verificarRelacionamento(TipoRelacionamento.AMIZADE, login, amigoLogin);
    }

    public String getAmigos(String login) {
        return relacionamentoService.listarRelacionamentos(TipoRelacionamento.AMIZADE, login);
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

    // Métodos de ídolo usando o novo RelacionamentoService
    public void adicionarIdolo(String id, String idolo) {
        relacionamentoService.adicionarRelacionamento(TipoRelacionamento.IDOLO, id, idolo);
    }

    public boolean ehFa(String login, String idolo) {
        return relacionamentoService.verificarRelacionamento(TipoRelacionamento.IDOLO, login, idolo);
    }

    public String getFas(String login) {
        return relacionamentoService.listarRelacionamentos(TipoRelacionamento.IDOLO, login);
    }

    // Métodos de paquera usando o novo RelacionamentoService
    public void adicionarPaquera(String id, String paquera) {
        relacionamentoService.adicionarRelacionamento(TipoRelacionamento.PAQUERA, id, paquera);
    }

    public boolean ehPaquera(String id, String paquera) {
        return relacionamentoService.verificarRelacionamentoPorSessao(TipoRelacionamento.PAQUERA, id, paquera);
    }

    public String getPaqueras(String id) {
        return relacionamentoService.listarRelacionamentosPorSessao(TipoRelacionamento.PAQUERA, id);
    }

    // Método de inimizade usando o novo RelacionamentoService
    public void adicionarInimigo(String id, String inimigo) {
        relacionamentoService.adicionarRelacionamento(TipoRelacionamento.INIMIZADE, id, inimigo);
    }

    public void encerrarSistema() {
        usuarioService.salvarDados();
    }

    public void removerUsuario(String id) {
        String login = sessaoService.getLoginUsuario(id);

        // Excluímos as comunidades do usuário
        comunidadeService.removerComunidades(login);

        // Excluímos os recados enviados pelo usuário
        recadoService.removerRecados(login);

        usuarioService.removerUsuario(login);
    }
}