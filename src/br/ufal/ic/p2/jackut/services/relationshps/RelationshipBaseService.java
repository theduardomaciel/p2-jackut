package br.ufal.ic.p2.jackut.services.relationshps;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.SessaoService;
import br.ufal.ic.p2.jackut.services.UsuarioService;

public abstract class RelationshipBaseService {
    protected final UsuarioService usuarioService;
    protected final SessaoService sessaoService;

    protected RelationshipBaseService() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
    }

    protected void validarAutoRelacionamento(String usuarioLogin, String outroLogin, RuntimeException excecaoAuto) {
        if (usuarioLogin.equals(outroLogin)) {
            throw excecaoAuto;
        }
    }

    protected void validarInimizade(Usuario usuario, Usuario outro, RuntimeException excecaoInimigo) {
        if (usuario.ehInimigo(outro.getLogin()) || outro.ehInimigo(usuario.getLogin())) {
            throw excecaoInimigo;
        }
    }
}
