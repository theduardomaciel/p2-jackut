package br.ufal.ic.p2.jackut.services.relationshps;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.exceptions.idolo.IdoloExistenteException;
import br.ufal.ic.p2.jackut.exceptions.idolo.AutoIdolatriaException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;
import br.ufal.ic.p2.jackut.services.SessaoService;
import br.ufal.ic.p2.jackut.services.UsuarioService;

public class IdoloService extends RelationshipBaseService {
    private static IdoloService instance;

    // Construtor privado para implementar o Singleton
    private IdoloService() {
        super();
    }

    public static synchronized IdoloService getInstance() {
        if (instance == null) {
            instance = new IdoloService();
        }
        return instance;
    }

    public void adicionarIdolo(String sessaoId, String idoloLogin) {
        String faNome = sessaoService.getLoginUsuario(sessaoId);
        Usuario fa = usuarioService.getUsuario(faNome);
        Usuario idolo = usuarioService.getUsuario(idoloLogin);

        validarAutoRelacionamento(faNome, idoloLogin, new AutoIdolatriaException());
        validarInimizade(fa, idolo, new InteracaoComInimigoException(idolo.getAtributo("nome")));

        if (fa.ehFa(idoloLogin)) {
            throw new IdoloExistenteException();
        }

        fa.adicionarIdolo(idoloLogin);
        usuarioService.salvarDados();
    }

    public boolean ehFa(String login, String idoloLogin) {
        Usuario fa = usuarioService.getUsuario(login);
        return fa.ehFa(idoloLogin);
    }

    public String getFas(String login) {
        var fas = usuarioService.getTodosUsuarios().stream()
                .filter(usuario -> usuario.ehFa(login))
                .map(Usuario::getLogin)
                .toList();

        if (fas.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", fas) + "}";
    }
}