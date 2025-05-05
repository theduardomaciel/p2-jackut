package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InimigoExistenteException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.AutoInimizadeException;

public class InimizadeService {
    private static InimizadeService instance;
    private final UsuarioService usuarioService;
    private final SessaoService sessaoService;

    private InimizadeService() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
    }

    public static synchronized InimizadeService getInstance() {
        if (instance == null) {
            instance = new InimizadeService();
        }
        return instance;
    }

    public void adicionarInimigo(String sessaoId, String inimigoLogin) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);

        if (usuarioLogin.equals(inimigoLogin)) {
            throw new AutoInimizadeException();
        }

        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        Usuario inimigo = usuarioService.getUsuario(inimigoLogin);

        if (usuario.ehInimigo(inimigoLogin) || inimigo.ehInimigo(usuarioLogin)) {
            throw new InimigoExistenteException();
        }

        usuario.adicionarInimigo(inimigoLogin);
        usuarioService.salvarDados();
    }

    public boolean ehInimigo(String login, String inimigoLogin) {
        Usuario usuario = usuarioService.getUsuario(login);
        return usuario.ehInimigo(inimigoLogin);
    }
}