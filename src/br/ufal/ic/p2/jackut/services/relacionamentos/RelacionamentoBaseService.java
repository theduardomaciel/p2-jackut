package br.ufal.ic.p2.jackut.services.relacionamentos;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.SessaoService;
import br.ufal.ic.p2.jackut.services.UsuarioService;
import br.ufal.ic.p2.jackut.utils.FormatadorUtil;

/**
 * Classe base para servi�os de relacionamento entre usu�rios
 */
public abstract class RelacionamentoBaseService {
    protected final UsuarioService usuarioService;
    protected final SessaoService sessaoService;

    protected RelacionamentoBaseService() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
    }

    /**
     * Valida se o usu�rio est� tentando criar relacionamento consigo mesmo
     * 
     * @param usuarioLogin Login do usu�rio principal
     * @param outroLogin   Login do outro usu�rio
     * @param excecaoAuto  Exce��o a ser lan�ada em caso de auto-relacionamento
     */
    protected void validarAutoRelacionamento(String usuarioLogin, String outroLogin, RuntimeException excecaoAuto) {
        if (usuarioLogin.equals(outroLogin)) {
            throw excecaoAuto;
        }
    }

    /**
     * Valida se existe inimizade entre os usu�rios
     * 
     * @param usuario        O usu�rio principal
     * @param outro          O outro usu�rio
     * @param excecaoInimigo Exce��o a ser lan�ada em caso de inimizade
     */
    protected void validarInimizade(Usuario usuario, Usuario outro, RuntimeException excecaoInimigo) {
        if (usuario.ehInimigo(outro.getLogin()) || outro.ehInimigo(usuario.getLogin())) {
            throw excecaoInimigo;
        }
    }

    /**
     * Obt�m usu�rio a partir do login da sess�o
     * 
     * @param sessaoId ID da sess�o
     * @return O usu�rio associado � sess�o
     */
    protected Usuario getUsuarioPorSessao(String sessaoId) {
        String login = sessaoService.getLoginUsuario(sessaoId);
        return usuarioService.getUsuario(login);
    }

    /**
     * Formata uma lista de strings para o formato padr�o {item1,item2,...}
     * 
     * @param lista A lista a ser formatada
     * @return String formatada
     */
    protected String formatarLista(java.util.Collection<String> lista) {
        return FormatadorUtil.formatarStrings(lista);
    }

    /**
     * Salva as altera��es no banco de dados
     */
    protected void salvarAlteracoes() {
        usuarioService.salvarDados();
    }
}
