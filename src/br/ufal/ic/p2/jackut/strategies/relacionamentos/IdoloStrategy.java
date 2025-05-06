package br.ufal.ic.p2.jackut.strategies.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.idolo.AutoIdolatriaException;
import br.ufal.ic.p2.jackut.exceptions.idolo.IdoloExistenteException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.strategies.RelacionamentoStrategyBase;

/**
 * Estratégia para gerenciamento da relação ídolo-fã
 */
public class IdoloStrategy extends RelacionamentoStrategyBase {

    @Override
    public void adicionarRelacionamento(String usuarioLogin, String idoloLogin) {
        validarAutoRelacionamento(usuarioLogin, idoloLogin);

        Usuario fa = usuarioService.getUsuario(usuarioLogin);
        Usuario idolo = usuarioService.getUsuario(idoloLogin);

        validarInimizade(fa, idolo);

        if (fa.ehFa(idoloLogin)) {
            throw criarExcecaoRelacionamentoExistente();
        }

        fa.adicionarIdolo(idoloLogin);
        salvarAlteracoes();
    }

    @Override
    public boolean verificarRelacionamento(String usuarioLogin, String idoloLogin) {
        Usuario fa = usuarioService.getUsuario(usuarioLogin);
        return fa.ehFa(idoloLogin);
    }

    @Override
    public String listarRelacionamentos(String usuarioLogin) {
        // Lista os fãs de um usuário
        var fas = usuarioService.getTodosUsuarios().stream()
                .filter(usuario -> usuario.ehFa(usuarioLogin))
                .map(Usuario::getLogin)
                .toList();

        return formatarLista(fas);
    }

    @Override
    public RuntimeException criarExcecaoAutoRelacionamento() {
        return new AutoIdolatriaException();
    }

    @Override
    public RuntimeException criarExcecaoRelacionamentoExistente() {
        return new IdoloExistenteException();
    }
}