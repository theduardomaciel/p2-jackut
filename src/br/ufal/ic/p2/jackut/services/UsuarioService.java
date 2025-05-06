package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.usuario.ContaExistenteException;
import br.ufal.ic.p2.jackut.exceptions.usuario.LoginInvalidoException;
import br.ufal.ic.p2.jackut.exceptions.usuario.SenhaInvalidaException;
import br.ufal.ic.p2.jackut.exceptions.usuario.UsuarioNaoCadastradoException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.persistance.Database;
import br.ufal.ic.p2.jackut.utils.FormatadorUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Collections;

public class UsuarioService {
    private static UsuarioService instance;
    private final Map<String, Usuario> usuarios;
    private final Database db;

    // Construtor privado para implementar o Singleton
    private UsuarioService() {
        this.db = Database.getInstance();
        this.usuarios = new HashMap<>();
        Map<String, Usuario> dadosCarregados = db.carregarDados();
        if (dadosCarregados != null) {
            this.usuarios.putAll(dadosCarregados);
        }
    }

    /**
     * Obtém a instância única da classe UsuarioService.
     * Implementa o padrão Singleton para garantir que apenas uma instância
     * da classe seja criada durante a execução do programa.
     *
     * @return A instância única de UsuarioService.
     */
    public static synchronized UsuarioService getInstance() {
        if (instance == null) {
            instance = new UsuarioService();
        }
        return instance;
    }

    /**
     * Reseta o sistema, removendo todos os usuários e salvando os dados.
     */
    public void zerarSistema() {
        usuarios.clear();
        salvarDados();
    }

    /**
     * Cria um novo usuário no sistema.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome  O nome do usuário.
     * @throws ContaExistenteException Se o login já estiver cadastrado.
     */
    public void criarUsuario(String login, String senha, String nome) {
        validarDadosUsuario(login, senha, nome);
        if (usuarios.containsKey(login)) {
            throw new ContaExistenteException();
        }
        usuarios.put(login, new Usuario(login, senha, nome));
        salvarDados();
    }

    /**
     * Valida os dados do usuário.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome  O nome do usuário.
     * @throws LoginInvalidoException Se o login for inválido.
     * @throws SenhaInvalidaException Se a senha for inválida.
     */
    private void validarDadosUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException();
        }
    }

    /**
     * Obtém o valor de um atributo de um usuário.
     *
     * @param login     O login do usuário.
     * @param atributo  O nome do atributo a ser obtido.
     * @return O valor do atributo solicitado.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public String getAtributoUsuario(String login, String atributo) {
        Usuario usuario = getUsuario(login);
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita o perfil de um usuário, alterando o valor de um atributo.
     *
     * @param login     O login do usuário.
     * @param atributo  O nome do atributo a ser alterado.
     * @param valor     O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public void editarPerfil(String login, String atributo, String valor) {
        Usuario usuario = getUsuario(login);
        usuario.setAtributo(atributo, valor);
        salvarDados();
    }

    /**
     * Obtém um usuário pelo login.
     *
     * @param login O login do usuário.
     * @return O objeto Usuario correspondente ao login.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public Usuario getUsuario(String login) {
        if (!usuarios.containsKey(login) && !login.equals("Jackut")) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login);
    }

    /**
     * Obtém as comunidades de um usuário formatadas como uma string.
     *
     * @param login O login do usuário.
     * @return Uma string contendo as comunidades do usuário.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public String getComunidades(String login) {
        Usuario usuario = getUsuario(login);
        return FormatadorUtil.formatarStrings(usuario.getComunidades());
    }

    /**
     * Salva os dados dos usuários no banco de dados.
     */
    public void salvarDados() {
        db.salvarDados(usuarios);
    }

    /**
     * Obtém uma coleção imutável de todos os usuários cadastrados.
     *
     * @return Uma coleção de objetos Usuario.
     */
    public Collection<Usuario> getTodosUsuarios() {
        return Collections.unmodifiableCollection(usuarios.values());
    }

    /**
     * Remove um usuário do sistema.
     *
     * @param login O login do usuário a ser removido.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public void removerUsuario(String login) {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException();
        }
        usuarios.remove(login);
        salvarDados();
    }

    /**
     * Remove uma comunidade de um usuário.
     *
     * @param login O login do usuário.
     * @param nome  O nome da comunidade a ser removida.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public void removerComunidade(String login, String nome) {
        Usuario usuario = getUsuario(login);
        usuario.removerComunidade(nome);
        salvarDados();
    }

    /**
     * Remove recados enviados por um usuário a um destinatário.
     *
     * @param login        O login do usuário.
     * @param destinatario O login do destinatário dos recados.
     * @throws UsuarioNaoCadastradoException Se o usuário não estiver cadastrado.
     */
    public void removerRecados(String login, String destinatario) {
        Usuario usuario = getUsuario(login);
        usuario.removerRecados(destinatario);
        salvarDados();
    }
}
