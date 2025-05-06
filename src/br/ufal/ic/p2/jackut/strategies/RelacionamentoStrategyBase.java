package br.ufal.ic.p2.jackut.strategies;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.UsuarioService;
import br.ufal.ic.p2.jackut.utils.FormatadorUtil;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;

/**
 * Implementação base para as estratégias de relacionamento
 */
public abstract class RelacionamentoStrategyBase implements RelacionamentoStrategy {

    protected final UsuarioService usuarioService;

    protected RelacionamentoStrategyBase() {
        this.usuarioService = UsuarioService.getInstance();
    }

    /**
     * Formata uma lista de strings para o formato padrão
     * 
     * @param lista Lista de strings a ser formatada
     * @return Lista formatada em {item1,item2,...}
     */
    protected String formatarLista(java.util.Collection<String> lista) {
        return FormatadorUtil.formatarStrings(lista);
    }

    /**
     * Valida se existe inimizade entre os usuários
     * 
     * @param usuario Usuário principal
     * @param alvo    Usuário alvo
     */
    protected void validarInimizade(Usuario usuario, Usuario alvo) {
        if (usuario.ehInimigo(alvo.getLogin()) || alvo.ehInimigo(usuario.getLogin())) {
            throw new InteracaoComInimigoException(alvo.getAtributo("nome"));
        }
    }

    /**
     * Valida se o usuário está tentando criar um relacionamento consigo mesmo
     * 
     * @param usuarioLogin Login do usuário principal
     * @param alvoLogin    Login do usuário alvo
     */
    protected void validarAutoRelacionamento(String usuarioLogin, String alvoLogin) {
        if (usuarioLogin.equals(alvoLogin)) {
            throw criarExcecaoAutoRelacionamento();
        }
    }

    /**
     * Salva as alterações no banco de dados
     */
    protected void salvarAlteracoes() {
        usuarioService.salvarDados();
    }
}