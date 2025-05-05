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

    // Método para obter a instância única
    public static synchronized UsuarioService getInstance() {
        if (instance == null) {
            instance = new UsuarioService();
        }
        return instance;
    }

    public void zerarSistema() {
        usuarios.clear();
        salvarDados();
    }

    public void criarUsuario(String login, String senha, String nome) {
        validarDadosUsuario(login, senha, nome);
        if (usuarios.containsKey(login)) {
            throw new ContaExistenteException();
        }
        usuarios.put(login, new Usuario(login, senha, nome));
        salvarDados();
    }

    private void validarDadosUsuario(String login, String senha, String nome) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException();
        }
    }

    public String getAtributoUsuario(String login, String atributo) {
        Usuario usuario = getUsuario(login); // Lança "Usuário n?o cadastrado" se o login n?o existir
        return usuario.getAtributo(atributo);
    }

    public void editarPerfil(String login, String atributo, String valor) {
        Usuario usuario = getUsuario(login);
        usuario.setAtributo(atributo, valor);
        salvarDados();
    }

    public Usuario getUsuario(String login) {
        if (!usuarios.containsKey(login) && !login.equals("Jackut")) {
            throw new UsuarioNaoCadastradoException();
        }
        return usuarios.get(login);
    }

    public String getComunidades(String login) {
        Usuario usuario = getUsuario(login);
        return FormatadorUtil.formatarStrings(usuario.getComunidades());
    }

    public void salvarDados() {
        db.salvarDados(usuarios);
    }

    public Collection<Usuario> getTodosUsuarios() {
        return Collections.unmodifiableCollection(usuarios.values());
    }

    public void removerUsuario(String login) {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException();
        }
        usuarios.remove(login);
        salvarDados();
    }

    public void removerComunidade(String login, String nome) {
        Usuario usuario = getUsuario(login);
        usuario.removerComunidade(nome);
        salvarDados();
    }

    public void removerRecados(String login, String destinatario) {
        Usuario usuario = getUsuario(login);
        usuario.removerRecados(destinatario);
        salvarDados();
    }
}
