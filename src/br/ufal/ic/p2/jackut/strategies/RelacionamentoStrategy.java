package br.ufal.ic.p2.jackut.strategies;

/**
 * Interface que define uma estratégia para gerenciamento de relacionamentos
 */
public interface RelacionamentoStrategy {
    /**
     * Adiciona um relacionamento entre dois usuários
     * 
     * @param usuarioLogin Login do usuário que inicia o relacionamento
     * @param alvoLogin    Login do usuário alvo do relacionamento
     */
    void adicionarRelacionamento(String usuarioLogin, String alvoLogin);

    /**
     * Verifica se um relacionamento existe entre dois usuários
     * 
     * @param usuarioLogin Login do usuário
     * @param alvoLogin    Login do usuário alvo
     * @return true se o relacionamento existe, false caso contrário
     */
    boolean verificarRelacionamento(String usuarioLogin, String alvoLogin);

    /**
     * Lista os relacionamentos de um usuário
     * 
     * @param usuarioLogin Login do usuário
     * @return Lista de relacionamentos formatada
     */
    String listarRelacionamentos(String usuarioLogin);

    /**
     * Cria uma exceç?o para caso de auto-relacionamento
     * 
     * @return A exceç?o específica para este tipo de relacionamento
     */
    RuntimeException criarExcecaoAutoRelacionamento();

    /**
     * Cria uma exceç?o para caso de relacionamento já existente
     * 
     * @return A exceç?o específica para este tipo de relacionamento
     */
    RuntimeException criarExcecaoRelacionamentoExistente();
}