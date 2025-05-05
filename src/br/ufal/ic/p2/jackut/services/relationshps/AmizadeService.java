package br.ufal.ic.p2.jackut.services.relationshps;

import br.ufal.ic.p2.jackut.exceptions.amizade.AmizadeJaExistenteException;
import br.ufal.ic.p2.jackut.exceptions.amizade.AmizadeParaSiMesmoException;
import br.ufal.ic.p2.jackut.exceptions.amizade.ConviteJaEnviadoException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;
import br.ufal.ic.p2.jackut.exceptions.usuario.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.UsuarioService;

public class AmizadeService extends RelationshipBaseService {
    private static AmizadeService instance;

    // Construtor privado para implementar o Singleton
    private AmizadeService()
    {
        super();
    }

    // Método para obter a instância única da classe
    public static synchronized AmizadeService getInstance() {
        if (instance == null) {
            instance = new AmizadeService();
        }
        return instance;
    }

    public void adicionarAmigo(String login, String amigoLogin) {
        validarAutoRelacionamento(login, amigoLogin, new AmizadeParaSiMesmoException());

        Usuario usuario = usuarioService.getUsuario(login);
        Usuario amigo = usuarioService.getUsuario(amigoLogin);

        validarInimizade(usuario, amigo, new InteracaoComInimigoException(amigo.getAtributo("nome")));

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