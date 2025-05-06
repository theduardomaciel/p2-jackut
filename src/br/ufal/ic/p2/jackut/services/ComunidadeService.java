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
     * Obt�m a inst�ncia �nica da classe ComunidadeService.
     * Implementa o padr�o Singleton para garantir que apenas uma inst�ncia
     * da classe seja criada durante a execu��o do programa.
     *
     * @return A inst�ncia �nica de ComunidadeService.
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
     * @param descricao Descri��o da comunidade.
     * @param loginDono Login do usu�rio que ser� o dono da comunidade.
     * @throws ComunidadeExistenteException Se j� existir uma comunidade com o mesmo nome.
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
     * Obt�m a descri��o de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return Descri��o da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getDescricaoComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return comunidade.getDescricao();
    }

    /**
     * Obt�m o login do dono de uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @return Login do dono da comunidade.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getDonoComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return comunidade.getLoginDono();
    }

    /**
     * Obt�m os logins dos membros de uma comunidade formatados como string.
     *
     * @param nome Nome da comunidade.
     * @return Logins dos membros da comunidade formatados.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public String getMembrosComunidade(String nome) {
        Comunidade comunidade = getComunidade(nome);
        return FormatadorUtil.formatarStrings(comunidade.getMembrosLogins());
    }

    /**
     * Adiciona um membro a uma comunidade.
     *
     * @param nome Nome da comunidade.
     * @param loginUsuario Login do usu�rio a ser adicionado.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     * @throws UsuarioJaMembroException Se o usu�rio j� for membro da comunidade.
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
     * @param mensagem Conte�do da mensagem.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    public void enviarMensagem(String nome, String loginRemetente, String mensagem) {
        Comunidade comunidade = getComunidade(nome);
        comunidade.adicionarMensagem(loginRemetente, mensagem);
    }

    /**
     * L� a pr�xima mensagem destinada a um usu�rio em qualquer comunidade.
     *
     * @param login Login do usu�rio.
     * @return Conte�do da mensagem.
     * @throws NaoHaMensagensException Se n�o houver mensagens para o usu�rio.
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
     * Obt�m uma comunidade pelo nome.
     *
     * @param nome Nome da comunidade.
     * @return A comunidade correspondente.
     * @throws ComunidadeNaoExisteException Se a comunidade n�o existir.
     */
    private Comunidade getComunidade(String nome) {
        if (!comunidades.containsKey(nome)) {
            throw new ComunidadeNaoExisteException();
        }
        return comunidades.get(nome);
    }

    /**
     * Obt�m as comunidades associadas a um usu�rio.
     *
     * @param login Login do usu�rio.
     * @return String com as comunidades do usu�rio.
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
     * Remove comunidades associadas a um usu�rio.
     *
     * @param login Login do usu�rio.
     */
    public void removerComunidades(String login) {
        var comunidadesExistentes = new HashMap<>(comunidades);

        for (Comunidade comunidade : comunidadesExistentes.values()) {
            // Caso o usu�rio seja dono, exclu�mos a comunidade
            if (comunidade.getLoginDono().equals(login)) {
                comunidades.remove(comunidade.getNome());

                // Removemos os membros da comunidade
                for (Usuario membro : comunidade.getMembros()) {
                    usuarioService.removerComunidade(membro.getLogin(), comunidade.getNome());
                }
            } else {
                // Caso contr�rio, removemos o usu�rio da comunidade
                Usuario usuario = usuarioService.getUsuario(login);
                comunidade.removerMembro(usuario);
            }
        }
    }
}
