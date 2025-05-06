package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.comunidade.ComunidadeExistenteException;
import br.ufal.ic.p2.jackut.exceptions.comunidade.ComunidadeNaoExisteException;
import br.ufal.ic.p2.jackut.exceptions.comunidade.UsuarioJaMembroException;
import br.ufal.ic.p2.jackut.exceptions.mensagem.NaoHaMensagensException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.utils.FormatadorUtil;
import java.util.HashMap;
import java.util.Map;

public class ComunidadeService {
    private static ComunidadeService instance;
    private final Map<String, Comunidade> comunidades;
    private final UsuarioService usuarioService;

    private ComunidadeService() {
        this.comunidades = new HashMap<>();
        this.usuarioService = UsuarioService.getInstance();
    }

    /**
     * Obtém a instância única da classe ComunidadeService.
     * Implementa o padrão Singleton para garantir que apenas uma instância
     * da classe seja criada durante a execução do programa.
     *
     * @return A instância única de ComunidadeService.
     */
    public static synchronized ComunidadeService getInstance() {
        if (instance == null) {
            instance = new ComunidadeService();
        }
        return instance;
    }

    /**
     * Cria uma nova comunidade.
     *
     * @param nome Nome da comunidade.
     * @param descricao Descrição da comunidade.
     * @param loginDono Login do usuário que será o dono da comunidade.
     * @throws ComunidadeExistenteException Se já existir uma comunidade com o mesmo nome.
     */
    public void criarComunidade(String nome, String descricao, String loginDono) {
        if (comunidades.containsKey(nome)) {
            throw new ComunidadeExistenteException();
        }

        // Verifica se o dono existe
        Usuario dono = usuarioService.getUsuario(loginDono);

        // Cria a comunidade
        Comunidade comunidade = new Comunidade(nome, descricao, dono);
        comunidades.put(nome, comunidade);

        // Salva os dados
        usuarioService.salvarDados();
    }

    /**
     * Obtém a descrição de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return Descrição da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getDescricaoComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return comunidade.getDescricao();
    }

    /**
     * Obtém o login do dono de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return Login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getDonoComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return comunidade.getLoginDono();
    }

    /**
     * Obtém os logins dos membros de uma comunidade formatados como string.
     *
     * @param nome Nome da comunidade.
     * @return Logins dos membros da comunidade formatados.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public String getMembrosComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return FormatadorUtil.formatarStrings(comunidade.getMembrosLogins());
    }

    /**
     * Adiciona um membro a uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @param loginUsuario Login do usuário a ser adicionado.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     * @throws UsuarioJaMembroException Se o usuário já for membro da comunidade.
     */
    public void adicionarMembro(String nome, String loginUsuario) {
        Comunidade comunidade = getComunidade(nome);
        Usuario usuario = usuarioService.getUsuario(loginUsuario);

        if (comunidade.contemMembro(usuario)) {
            throw new UsuarioJaMembroException();
        }

        comunidade.adicionarMembro(usuario);
        usuarioService.salvarDados();
    }

    /**
     * Envia uma mensagem para uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @param loginRemetente Login do remetente da mensagem.
     * @param mensagem Conteúdo da mensagem.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    public void enviarMensagem(String nome, String loginRemetente, String mensagem) {
        Comunidade comunidade = getComunidade(nome);
        comunidade.adicionarMensagem(loginRemetente, mensagem);
    }

    /**
     * Lê a próxima mensagem destinada a um usuário em qualquer comunidade.
     *
     * @param login Login do usuário.
     * @return Conteúdo da mensagem.
     * @throws NaoHaMensagensException Se não houver mensagens para o usuário.
     */
    public String lerMensagem(String login) {
        // Procura a mensagem em todas as comunidades
        for (Comunidade comunidade : comunidades.values()) {
            if (comunidade.getMembrosLogins().contains(login)) {
                try {
                    return comunidade.lerMensagem(login);
                } catch (NaoHaMensagensException ignored) {
                    // Continue procurando em outras comunidades
                }
            }
        }
        throw new NaoHaMensagensException();
    }

    /**
     * Obtém uma comunidade pelo nome.
     *
     * @param nome Nome da comunidade.
     * @return A comunidade correspondente.
     * @throws ComunidadeNaoExisteException Se a comunidade não existir.
     */
    private Comunidade getComunidade(String nome) {
        if (!comunidades.containsKey(nome)) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidades.get(nome);
    }

    /**
     * Obtém as comunidades associadas a um usuário.
     *
     * @param login Login do usuário.
     * @return String com as comunidades do usuário.
     */
    public String getComunidades(String login) {
        return usuarioService.getComunidades(login);
    }

    /**
     * Remove todas as comunidades.
     */
    public void zerarComunidades() {
        comunidades.clear();
    }

    /**
     * Remove comunidades associadas a um usuário.
     *
     * @param login Login do usuário.
     */
    public void removerComunidades(String login) {
        var comunidadesExistentes = new HashMap<>(comunidades);

        for (Comunidade comunidade : comunidadesExistentes.values()) {
            // Caso o usuário seja dono, excluímos a comunidade
            if (comunidade.getLoginDono().equals(login)) {
                comunidades.remove(comunidade.getNome());

                // Removemos os membros da comunidade
                for (Usuario membro : comunidade.getMembros()) {
                    usuarioService.removerComunidade(membro.getLogin(), comunidade.getNome());
                }
            } else {
                // Caso contrário, removemos o usuário da comunidade
                Usuario usuario = usuarioService.getUsuario(login);
                comunidade.removerMembro(usuario);
            }
        }
    }
}
