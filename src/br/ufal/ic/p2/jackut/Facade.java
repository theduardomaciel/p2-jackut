package br.ufal.ic.p2.jackut;

import br.ufal.ic.p2.jackut.exceptions.*;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Facade {
    private static final Database db = new Database();

    private Map<String, Usuario> usuarios;
    private Map<String, String> sessoes;
    private int contadorSessao;

    public Facade() {
        usuarios = new HashMap<>();
        sessoes = new HashMap<>();
        contadorSessao = 0;

        // Carrega os dados do banco de dados
        Map<String, Usuario> dadosCarregados = db.carregarDados();
        if (dadosCarregados != null) {
            usuarios = dadosCarregados;
        }
    }

    /**
     * Reseta o sistema, limpando todos os dados de usu�rios e sess�es.
     */
    public void zerarSistema() {
        usuarios.clear();
        sessoes.clear();
        contadorSessao = 0;

        // Salva os dados zerados no banco de dados
        db.salvarDados(usuarios);
    }

    /**
     * Cria um novo usu�rio no sistema.
     *
     * @param login O login do novo usu�rio.
     * @param senha A senha do novo usu�rio.
     * @param nome O nome do novo usu�rio.
     * @throws AcaoProibidaException Se o login j� estiver em uso.
     */
    public void criarUsuario(String login, String senha, String nome) {
        validarLogin(login);
        validarSenha(senha);

        if (usuarios.containsKey(login)) {
            throw new AcaoProibidaException("Conta com esse nome j� existe.");
        }

        Usuario novoUsuario = new Usuario(login, senha, nome);
        usuarios.put(login, novoUsuario);

        db.salvarDados(usuarios);
    }

    private void validarLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new LoginInvalidoException();
        }
    }

    private void validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new SenhaInvalidaException();
        }
    }

    /**
     * Abre uma sess�o para um usu�rio existente.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return O ID da sess�o criada.
     * @throws LoginInvalidoException Se o login ou a senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() ||
                senha == null || senha.trim().isEmpty() ||
                !usuarios.containsKey(login) ||
                !usuarios.get(login).getSenha().equals(senha)) {
            throw new LoginInvalidoException();
        }

        String idSessao = "sessao" + (++contadorSessao);
        sessoes.put(idSessao, login);
        return idSessao;
    }

    /**
     * Obt�m um atributo de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @param atributo O nome do atributo.
     * @return O valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public String getAtributoUsuario(String login, String atributo) {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = usuarios.get(login);
        return usuario.getAtributo(atributo);
    }

    /**
     * Edita o perfil de um usu�rio.
     *
     * @param id O ID da sess�o do usu�rio.
     * @param atributo O nome do atributo a ser editado.
     * @param valor O novo valor do atributo.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public void editarPerfil(String id, String atributo, String valor) {
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        usuario.setAtributo(atributo, valor);
        db.salvarDados(usuarios);
    }

    /**
     * Adiciona um amigo para um usu�rio.
     *
     * @param id O ID da sess�o do usu�rio.
     * @param amigoLogin O login do amigo a ser adicionado.
     * @throws UsuarioNaoCadastradoException Se o usu�rio ou o amigo n�o estiverem cadastrados.
     * @throws AcaoProibidaException Se o usu�rio ou o amigo n�o estiverem cadastrados, ou se j� forem amigos.
     */
    public void adicionarAmigo(String id, String amigoLogin) {
        // Verificamos se o ID da sess�o � v�lido
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        String login = sessoes.get(id);

        if (login.equals(amigoLogin)) {
            throw new AcaoProibidaException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
        }

        // Verificamos se o amigo existe
        if (!usuarios.containsKey(amigoLogin)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = usuarios.get(login);
        Usuario amigo = usuarios.get(amigoLogin);

        if (usuario.ehAmigo(amigoLogin)) {
            throw new AcaoProibidaException("Usu�rio j� est� adicionado como amigo.");
        }

        // Verificamos se o outro usu�rio j� enviou convite
        if (amigo.verificarConvitePendente(login)) {
            // Caso sim, aceitamos a amizade em ambas as dire��es
            usuario.aceitarAmizade(amigoLogin);
            amigo.aceitarAmizade(login);
            db.salvarDados(usuarios);
            return;
        }

        // Verificamos se j� enviou convite antes
        if (usuario.verificarConvitePendente(amigoLogin)) {
            throw new AcaoProibidaException("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
        }

        // Enviamos o convite
        usuario.enviarConviteAmizade(amigoLogin);
        db.salvarDados(usuarios);
    }

    /**
     * Verifica se dois usu�rios s�o amigos.
     *
     * @param login O login do usu�rio.
     * @param amigoLogin O login do amigo.
     * @return true se forem amigos, false caso contr�rio.
     */
    public boolean ehAmigo(String login, String amigoLogin) {
        if (!usuarios.containsKey(login) || !usuarios.containsKey(amigoLogin)) {
            return false;
        }

        Usuario usuario = usuarios.get(login);
        return usuario.ehAmigo(amigoLogin);
    }

    /**
     * Obt�m a lista de amigos de um usu�rio.
     *
     * @param login O login do usu�rio.
     * @return A lista de amigos no formato de string.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado.
     */
    public String getAmigos(String login) {
        if (!usuarios.containsKey(login)) {
            throw new UsuarioNaoCadastradoException();
        }

        Usuario usuario = usuarios.get(login);
        List<String> amigos = usuario.getAmigos();

        if (amigos.isEmpty()) {
            return "{}";
        }

        return "{" + String.join(",", amigos) + "}";
    }

    /**
     * Envia um recado para um usu�rio.
     *
     * @param id O ID da sess�o do remetente.
     * @param destinatario O login do destinat�rio.
     * @param mensagem A mensagem do recado.
     * @throws UsuarioNaoCadastradoException Se o destinat�rio n�o estiver cadastrado.
     * @throws AcaoProibidaException Se o usu�rio tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String mensagem) {
        // Verificamos se o ID da sess�o � v�lido
        if (!sessoes.containsKey(id)) {
            throw new UsuarioNaoCadastradoException();
        }

        // Obtemos o login do remetente
        String loginRemetente = sessoes.get(id);

        // Verificamos se o destinat�rio existe
        if (!usuarios.containsKey(destinatario)) {
            throw new UsuarioNaoCadastradoException();
        }

        // Verificamos se o usu�rio est� tentando enviar um recado para si mesmo
        if (loginRemetente.equals(destinatario)) {
            throw new AcaoProibidaException("Usu�rio n�o pode enviar recado para si mesmo.");
        }

        // Em seguida, enviamos o recado ao destinat�rio
        Usuario usuarioDestinatario = usuarios.get(destinatario);
        usuarioDestinatario.adicionarRecado(mensagem);

        // E salvamos os dados atualizados
        db.salvarDados(usuarios);
    }

    /**
     * L� o pr�ximo recado de um usu�rio.
     *
     * @param id O ID da sess�o do usu�rio.
     * @return A mensagem do recado.
     * @throws UsuarioNaoCadastradoException Se o usu�rio n�o estiver cadastrado
     * @throws RecursoNaoEncontradoException Se n�o houver recados.
     */
    public String lerRecado(String id) {
        // Verificamos se o ID da sess�o � v�lido
        if (!sessoes.containsKey(id)) {
            // Caso n�o, o usu�rio n�o est� cadastrado
            throw new UsuarioNaoCadastradoException();
        }

        // Obtemos o login do usu�rio
        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);

        // Lemos o pr�ximo recado da fila
        String recado = usuario.lerRecado();

        // Se n�o houver recados, lan�amos uma exce��o
        if (recado == null) {
            throw new RecursoNaoEncontradoException("N�o h� recados.");
        }

        // Salvamos os dados atualizados ap�s a leitura do recado
        db.salvarDados(usuarios);

        return recado;
    }

    public void encerrarSistema() {
        db.salvarDados(usuarios);
    }
}