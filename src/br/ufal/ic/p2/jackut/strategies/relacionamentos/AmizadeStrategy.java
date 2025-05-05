package br.ufal.ic.p2.jackut.strategies.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.amizade.AmizadeJaExistenteException;
import br.ufal.ic.p2.jackut.exceptions.amizade.AmizadeParaSiMesmoException;
import br.ufal.ic.p2.jackut.exceptions.amizade.ConviteJaEnviadoException;
import br.ufal.ic.p2.jackut.exceptions.usuario.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.strategies.RelacionamentoStrategyBase;

/**
 * Estratégia para gerenciamento de amizades
 */
public class AmizadeStrategy extends RelacionamentoStrategyBase {

    @Override
    public void adicionarRelacionamento(String usuarioLogin, String amigoLogin) {
        validarAutoRelacionamento(usuarioLogin, amigoLogin);

        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        Usuario amigo = usuarioService.getUsuario(amigoLogin);

        validarInimizade(usuario, amigo);

        if (usuario.ehAmigo(amigoLogin)) {
            throw criarExcecaoRelacionamentoExistente();
        }

        if (amigo.verificarConvitePendente(usuarioLogin)) {
            usuario.aceitarAmizade(amigoLogin);
            amigo.aceitarAmizade(usuarioLogin);
        } else if (!usuario.verificarConvitePendente(amigoLogin)) {
            usuario.enviarConviteAmizade(amigoLogin);
        } else {
            throw new ConviteJaEnviadoException();
        }

        salvarAlteracoes();
    }

    @Override
    public boolean verificarRelacionamento(String usuarioLogin, String amigoLogin) {
        try {
            Usuario usuario = usuarioService.getUsuario(usuarioLogin);
            return usuario.ehAmigo(amigoLogin);
        } catch (UsuarioNaoCadastradoException e) {
            return false;
        }
    }

    @Override
    public String listarRelacionamentos(String usuarioLogin) {
        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        return formatarLista(usuario.getAmigos());
    }

    @Override
    public RuntimeException criarExcecaoAutoRelacionamento() {
        return new AmizadeParaSiMesmoException();
    }

    @Override
    public RuntimeException criarExcecaoRelacionamentoExistente() {
        return new AmizadeJaExistenteException();
    }
}