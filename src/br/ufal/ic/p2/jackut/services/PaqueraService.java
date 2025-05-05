package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.exceptions.paquera.PaqueraExistenteException;
import br.ufal.ic.p2.jackut.exceptions.paquera.AutoPaqueraException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;

public class PaqueraService {
    private static PaqueraService instance;
    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;
    private final RecadoService recadoService;

    private PaqueraService() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
        this.recadoService = RecadoService.getInstance();
    }

    public static synchronized PaqueraService getInstance() {
        if (instance == null) {
            instance = new PaqueraService();
        }
        return instance;
    }

    public void adicionarPaquera(String sessaoId, String paqueraLogin) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);

        if (usuarioLogin.equals(paqueraLogin)) {
            throw new AutoPaqueraException();
        }

        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        Usuario paquera = usuarioService.getUsuario(paqueraLogin);

        // Verifica se o usuário e a paquera são inimigos
        if (paquera.ehInimigo(usuarioLogin) || usuario.ehInimigo(paqueraLogin)) {
            throw new InteracaoComInimigoException(paquera.getAtributo("nome"));
        }

        // Verifica se a paquera já existe
        if (usuario.ehPaquera(paqueraLogin)) {
            throw new PaqueraExistenteException();
        }

        usuario.adicionarPaquera(paqueraLogin);

        // Se ambos são paqueras um do outro, envia recado automático
        if (paquera.ehPaquera(usuarioLogin)) {
            recadoService.enviarRecado("Jackut", usuarioLogin,
                    paquera.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
            recadoService.enviarRecado("Jackut", paqueraLogin,
                    usuario.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
        }

        usuarioService.salvarDados();
    }

    public boolean ehPaquera(String sessaoId, String paqueraLogin) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);
        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        return usuario.ehPaquera(paqueraLogin);
    }

    public String getPaqueras(String sessaoId) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);
        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        var paqueras = usuario.getPaqueras();
        if (paqueras.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", paqueras) + "}";
    }
}