package br.ufal.ic.p2.jackut.strategies.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.inimizade.AutoInimizadeException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InimigoExistenteException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.strategies.RelacionamentoStrategyBase;

/**
 * Estratégia para gerenciamento de inimizades
 */
public class InimizadeStrategy extends RelacionamentoStrategyBase {

    @Override
    public void adicionarRelacionamento(String usuarioLogin, String inimigoLogin) {
        validarAutoRelacionamento(usuarioLogin, inimigoLogin);

        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        usuarioService.getUsuario(inimigoLogin); // Verificamos se o inimigo existe

        if (usuario.ehInimigo(inimigoLogin)) {
            throw criarExcecaoRelacionamentoExistente();
        }

        usuario.adicionarInimigo(inimigoLogin);
        salvarAlteracoes();
    }

    @Override
    public boolean verificarRelacionamento(String usuarioLogin, String inimigoLogin) {
        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        return usuario.ehInimigo(inimigoLogin);
    }

    @Override
    public String listarRelacionamentos(String usuarioLogin) {
        // Retornamos lista vazia como comportamento padrão
        return "{}";
    }

    @Override
    public RuntimeException criarExcecaoAutoRelacionamento() {
        return new AutoInimizadeException();
    }

    @Override
    public RuntimeException criarExcecaoRelacionamentoExistente() {
        return new InimigoExistenteException();
    }
}