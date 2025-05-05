package br.ufal.ic.p2.jackut.services;

import java.util.HashMap;
import java.util.Map;

import br.ufal.ic.p2.jackut.models.relacoes.TipoRelacionamento;
import br.ufal.ic.p2.jackut.strategies.*;
import br.ufal.ic.p2.jackut.strategies.relacionamentos.AmizadeStrategy;
import br.ufal.ic.p2.jackut.strategies.relacionamentos.IdoloStrategy;
import br.ufal.ic.p2.jackut.strategies.relacionamentos.InimizadeStrategy;
import br.ufal.ic.p2.jackut.strategies.relacionamentos.PaqueraStrategy;

/**
 * Serviço unificado para gerenciamento de relacionamentos
 * Implementa o padr?o Strategy para flexibilizar os diferentes tipos de
 * relacionamentos
 */
public class RelacionamentoService {
    private static RelacionamentoService instance;

    private final Map<TipoRelacionamento, RelacionamentoStrategy> estrategias;
    private final SessaoService sessaoService;
    private final UsuarioService usuarioService;

    private RelacionamentoService() {
        this.estrategias = new HashMap<>();
        this.sessaoService = SessaoService.getInstance();
        this.usuarioService = UsuarioService.getInstance();

        // Registra as estratégias
        estrategias.put(TipoRelacionamento.AMIZADE, new AmizadeStrategy());
        estrategias.put(TipoRelacionamento.PAQUERA, new PaqueraStrategy());
        estrategias.put(TipoRelacionamento.IDOLO, new IdoloStrategy());
        estrategias.put(TipoRelacionamento.INIMIZADE, new InimizadeStrategy());
    }

    public static synchronized RelacionamentoService getInstance() {
        if (instance == null) {
            instance = new RelacionamentoService();
        }
        return instance;
    }

    /**
     * Adiciona um relacionamento entre usuários
     * 
     * @param tipo      Tipo de relacionamento (AMIZADE, PAQUERA, IDOLO ou
     *                  INIMIZADE)
     * @param sessaoId  ID da sess?o do usuário que inicia o relacionamento
     * @param alvoLogin Login do usuário alvo
     */
    public void adicionarRelacionamento(TipoRelacionamento tipo, String sessaoId, String alvoLogin) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);
        RelacionamentoStrategy estrategia = estrategias.get(tipo);

        estrategia.adicionarRelacionamento(usuarioLogin, alvoLogin);
    }

    /**
     * Verifica se existe um relacionamento entre dois usuários
     * 
     * @param tipo         Tipo de relacionamento a verificar
     * @param usuarioLogin Login do primeiro usuário
     * @param alvoLogin    Login do segundo usuário
     * @return true se o relacionamento existir, false caso contrário
     */
    public boolean verificarRelacionamento(TipoRelacionamento tipo, String usuarioLogin, String alvoLogin) {
        RelacionamentoStrategy estrategia = estrategias.get(tipo);
        return estrategia.verificarRelacionamento(usuarioLogin, alvoLogin);
    }

    /**
     * Verifica se existe um relacionamento entre um usuário da sess?o e outro
     * 
     * @param tipo      Tipo de relacionamento a verificar
     * @param sessaoId  ID da sess?o do usuário
     * @param alvoLogin Login do usuário alvo
     * @return true se o relacionamento existir, false caso contrário
     */
    public boolean verificarRelacionamentoPorSessao(TipoRelacionamento tipo, String sessaoId, String alvoLogin) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);
        return verificarRelacionamento(tipo, usuarioLogin, alvoLogin);
    }

    /**
     * Lista os relacionamentos de um usuário
     * 
     * @param tipo  Tipo de relacionamento a listar
     * @param login Login do usuário
     * @return Lista formatada de relacionamentos
     */
    public String listarRelacionamentos(TipoRelacionamento tipo, String login) {
        RelacionamentoStrategy estrategia = estrategias.get(tipo);
        return estrategia.listarRelacionamentos(login);
    }

    /**
     * Lista os relacionamentos de um usuário da sess?o
     * 
     * @param tipo     Tipo de relacionamento a listar
     * @param sessaoId ID da sess?o do usuário
     * @return Lista formatada de relacionamentos
     */
    public String listarRelacionamentosPorSessao(TipoRelacionamento tipo, String sessaoId) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);
        return listarRelacionamentos(tipo, usuarioLogin);
    }
}