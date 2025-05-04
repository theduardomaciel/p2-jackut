package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.Usuario;

public class AmizadeService {
    private static AmizadeService instance;
    private final UsuarioService usuarioService;

    // Construtor privado para implementar o Singleton
    private AmizadeService() {
        this.usuarioService = UsuarioService.getInstance();
    }

    // Método para obter a instância única da classe
    public static synchronized AmizadeService getInstance() {
        if (instance == null) {
            instance = new AmizadeService();
        }
        return instance;
    }

    public void adicionarAmigo(String login, String amigoLogin) {
        if (login.equals(amigoLogin)) {
            throw new AmizadeParaSiMesmoException();
        }

        Usuario usuario = usuarioService.getUsuario(login);
        Usuario amigo = usuarioService.getUsuario(amigoLogin);

        if (usuario.ehAmigo(amigoLogin)) {
            throw new AmizadeJaExistenteException();
        }

        if (amigo.verificarConvitePendente(login)) {
            usuario.aceitarAmizade(amigoLogin);
            amigo.aceitarAmizade(login);
        } else if (!usuario.verificarConvitePendente(amigoLogin)) {
            usuario.enviarConviteAmizade(amigoLogin);
        } else {
            throw new ConviteJaEnviadoException();
        }

        usuarioService.salvarDados();
    }

    public boolean ehAmigo(String login, String amigoLogin) {
        try {
            Usuario usuario = usuarioService.getUsuario(login);
            return usuario.ehAmigo(amigoLogin);
        } catch (UsuarioNaoCadastradoException e) {
            return false;
        }
    }

    public String getAmigos(String login) {
        Usuario usuario = usuarioService.getUsuario(login);
        var amigos = usuario.getAmigos();
        
        if (amigos.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", amigos) + "}";
    }
}
