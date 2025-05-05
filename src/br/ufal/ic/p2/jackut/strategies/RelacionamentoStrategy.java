package br.ufal.ic.p2.jackut.strategies;

/**
 * Interface que define uma estrat�gia para gerenciamento de relacionamentos
 */
public interface RelacionamentoStrategy {
    /**
     * Adiciona um relacionamento entre dois usu�rios
     * 
     * @param usuarioLogin Login do usu�rio que inicia o relacionamento
     * @param alvoLogin    Login do usu�rio alvo do relacionamento
     */
    void adicionarRelacionamento(String usuarioLogin, String alvoLogin);

    /**
     * Verifica se um relacionamento existe entre dois usu�rios
     * 
     * @param usuarioLogin Login do usu�rio
     * @param alvoLogin    Login do usu�rio alvo
     * @return true se o relacionamento existe, false caso contr�rio
     */
    boolean verificarRelacionamento(String usuarioLogin, String alvoLogin);

    /**
     * Lista os relacionamentos de um usu�rio
     * 
     * @param usuarioLogin Login do usu�rio
     * @return Lista de relacionamentos formatada
     */
    String listarRelacionamentos(String usuarioLogin);

    /**
     * Cria uma exce�?o para caso de auto-relacionamento
     * 
     * @return A exce�?o espec�fica para este tipo de relacionamento
     */
    RuntimeException criarExcecaoAutoRelacionamento();

    /**
     * Cria uma exce�?o para caso de relacionamento j� existente
     * 
     * @return A exce�?o espec�fica para este tipo de relacionamento
     */
    RuntimeException criarExcecaoRelacionamentoExistente();
}