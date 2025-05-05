package br.ufal.ic.p2.jackut.services.relacionamentos;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.SessaoService;
import br.ufal.ic.p2.jackut.services.UsuarioService;
import br.ufal.ic.p2.jackut.utils.FormatadorUtil;

/**
 * Classe base para serviços de relacionamento entre usuários
 */
public abstract class RelacionamentoBaseService {
    protected final UsuarioService usuarioService;
    protected final SessaoService sessaoService;

    protected RelacionamentoBaseService() {
        this.usuarioService = UsuarioService.getInstance();
        this.sessaoService = SessaoService.getInstance();
    }

    /**
     * Valida se o usuário está tentando criar relacionamento consigo mesmo
     * 
     * @param usuarioLogin Login do usuário principal
     * @param outroLogin   Login do outro usuário
     * @param excecaoAuto  Exceção a ser lançada em caso de auto-relacionamento
     */
    protected void validarAutoRelacionamento(String usuarioLogin, String outroLogin, RuntimeException excecaoAuto) {
        if (usuarioLogin.equals(outroLogin)) {
            throw excecaoAuto;
        }
    }

    /**
     * Valida se existe inimizade entre os usuários
     * 
     * @param usuario        O usuário principal
     * @param outro          O outro usuário
     * @param excecaoInimigo Exceção a ser lançada em caso de inimizade
     */
    protected void validarInimizade(Usuario usuario, Usuario outro, RuntimeException excecaoInimigo) {
        if (usuario.ehInimigo(outro.getLogin()) || outro.ehInimigo(usuario.getLogin())) {
            throw excecaoInimigo;
        }
    }

    /**
     * Obtém usuário a partir do login da sessão
     * 
     * @param sessaoId ID da sessão
     * @return O usuário associado ã sessão
     */
    protected Usuario getUsuarioPorSessao(String sessaoId) {
        String login = sessaoService.getLoginUsuario(sessaoId);
        return usuarioService.getUsuario(login);
    }

    /**
     * Formata uma lista de strings para o formato padrão {item1,item2,...}
     * 
     * @param lista A lista a ser formatada
     * @return String formatada
     */
    protected String formatarLista(java.util.Collection<String> lista) {
        return FormatadorUtil.formatarStrings(lista);
    }

    /**
     * Salva as alterações no banco de dados
     */
    protected void salvarAlteracoes() {
        usuarioService.salvarDados();
    }
}
