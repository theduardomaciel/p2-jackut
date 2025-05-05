package br.ufal.ic.p2.jackut.services.relacionamentos;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InimigoExistenteException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.AutoInimizadeException;

public class InimizadeService extends RelacionamentoBaseService {
    private static InimizadeService instance;

    private InimizadeService() {
        super();
    }

    public static synchronized InimizadeService getInstance() {
        if (instance == null) {
            instance = new InimizadeService();
        }
        return instance;
    }

    public void adicionarInimigo(String sessaoId, String inimigoLogin) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);

        validarAutoRelacionamento(usuarioLogin, inimigoLogin, new AutoInimizadeException());

        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        Usuario inimigo = usuarioService.getUsuario(inimigoLogin);

        validarInimizade(usuario, inimigo, new InimigoExistenteException());

        usuario.adicionarInimigo(inimigoLogin);
        usuarioService.salvarDados();
    }
}