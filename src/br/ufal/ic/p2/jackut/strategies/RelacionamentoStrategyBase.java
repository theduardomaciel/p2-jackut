package br.ufal.ic.p2.jackut.strategies;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.UsuarioService;
import br.ufal.ic.p2.jackut.utils.FormatadorUtil;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;

/**
 * Implementa��o base para as estrat�gias de relacionamento
 */
public abstract class RelacionamentoStrategyBase implements RelacionamentoStrategy {

    protected final UsuarioService usuarioService;

    protected RelacionamentoStrategyBase() {
        this.usuarioService = UsuarioService.getInstance();
    }

    /**
     * Formata uma lista de strings para o formato padr�o
     * 
     * @param lista Lista de strings a ser formatada
     * @return Lista formatada em {item1,item2,...}
     */
    protected String formatarLista(java.util.Collection<String> lista) {
        return FormatadorUtil.formatarStrings(lista);
    }

    /**
     * Valida se existe inimizade entre os usu�rios
     * 
     * @param usuario Usu�rio principal
     * @param alvo    Usu�rio alvo
     */
    protected void validarInimizade(Usuario usuario, Usuario alvo) {
        if (usuario.ehInimigo(alvo.getLogin()) || alvo.ehInimigo(usuario.getLogin())) {
            throw new InteracaoComInimigoException(alvo.getAtributo("nome"));
        }
    }

    /**
     * Valida se o usu�rio est� tentando criar um relacionamento consigo mesmo
     * 
     * @param usuarioLogin Login do usu�rio principal
     * @param alvoLogin    Login do usu�rio alvo
     */
    protected void validarAutoRelacionamento(String usuarioLogin, String alvoLogin) {
        if (usuarioLogin.equals(alvoLogin)) {
            throw criarExcecaoAutoRelacionamento();
        }
    }

    /**
     * Salva as altera��es no banco de dados
     */
    protected void salvarAlteracoes() {
        usuarioService.salvarDados();
    }
}