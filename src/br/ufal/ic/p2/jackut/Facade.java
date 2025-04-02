package br.ufal.ic.p2.jackut;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

public class Facade {
    private static final String DATA_FILE = "usuarios.dat";
    private Map<String, Usuario> usuarios;
    private Map<String, String> sessoes;
    private int contadorSessao;

    public Facade() {
        usuarios = new HashMap<>();
        sessoes = new HashMap<>();
        contadorSessao = 0;
        carregarDados();
    }

    /**
     * Reseta o sistema, limpando todos os dados de usu�rios e sess�es.
     */
    public void zerarSistema() {
        usuarios.clear();
        sessoes.clear();
        contadorSessao = 0;
        salvarDados();
    }

    /**
     * Cria um novo usu�rio no sistema.
     *
     * @param login O login do novo usu�rio.
     * @param senha A senha do novo usu�rio.
     * @param nome O nome do novo usu�rio.
     * @throws RuntimeException Se o login j� estiver em uso.
     */
    public void criarUsuario(String login, String senha, String nome) {
        validarLogin(login);
        validarSenha(senha);

        if (usuarios.containsKey(login)) {
            throw new RuntimeException("Conta com esse nome j� existe.");
        }

        Usuario novoUsuario = new Usuario(login, senha, nome);
        usuarios.put(login, novoUsuario);
        salvarDados();
    }

    private void validarLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new RuntimeException("Login inv�lido.");
        }
    }

    private void validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new RuntimeException("Senha inv�lida.");
        }
    }

    /**
     * Abre uma sess�o para um usu�rio existente.
     *
     * @param login O login do usu�rio.
     * @param senha A senha do usu�rio.
     * @return O ID da sess�o criada.
     * @throws RuntimeException Se o login ou a senha forem inv�lidos.
     */
    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() ||
                senha == null || senha.trim().isEmpty() ||
                !usuarios.containsKey(login) ||
                !usuarios.get(login).getSenha().equals(senha)) {
            throw new RuntimeException("Login ou senha inv�lidos.");
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
     * @throws RuntimeException Se o usu�rio n�o estiver cadastrado.
     */
    public String getAtributoUsuario(String login, String atributo) {
        if (!usuarios.containsKey(login)) {
            throw new RuntimeException("Usu�rio n�o cadastrado.");
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
     * @throws RuntimeException Se o usu�rio n�o estiver cadastrado.
     */
    public void editarPerfil(String id, String atributo, String valor) {
        if (!sessoes.containsKey(id)) {
            throw new RuntimeException("Usu�rio n�o cadastrado.");
        }

        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        usuario.setAtributo(atributo, valor);
        salvarDados();
    }

    /**
     * Adiciona um amigo para um usu�rio.
     *
     * @param id O ID da sess�o do usu�rio.
     * @param amigoLogin O login do amigo a ser adicionado.
     * @throws RuntimeException Se o usu�rio ou o amigo n�o estiverem cadastrados, ou se j� forem amigos.
     */
    public void adicionarAmigo(String id, String amigoLogin) {
        if (!sessoes.containsKey(id)) {
            throw new RuntimeException("Usu�rio n�o cadastrado.");
        }

        String login = sessoes.get(id);

        if (login.equals(amigoLogin)) {
            throw new RuntimeException("Usu�rio n�o pode adicionar a si mesmo como amigo.");
        }

        if (!usuarios.containsKey(amigoLogin)) {
            throw new RuntimeException("Usu�rio n�o cadastrado.");
        }

        Usuario usuario = usuarios.get(login);
        Usuario amigo = usuarios.get(amigoLogin);

        if (usuario.ehAmigo(amigoLogin)) {
            throw new RuntimeException("Usu�rio j� est� adicionado como amigo.");
        }

        // Verificamos se o outro usu�rio j� enviou convite
        if (amigo.verificarConvitePendente(login)) {
            // Caso sim, aceitamos a amizade em ambas as dire��es
            usuario.aceitarAmizade(amigoLogin);
            amigo.aceitarAmizade(login);
            salvarDados();
            return;
        }

        // Verificamos se j� enviou convite antes
        if (usuario.verificarConvitePendente(amigoLogin)) {
            throw new RuntimeException("Usu�rio j� est� adicionado como amigo, esperando aceita��o do convite.");
        }

        // Enviamos o convite
        usuario.enviarConviteAmizade(amigoLogin);
        salvarDados();
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
     * @throws RuntimeException Se o usu�rio n�o estiver cadastrado.
     */
    public String getAmigos(String login) {
        if (!usuarios.containsKey(login)) {
            throw new RuntimeException("Usu�rio n�o cadastrado.");
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
     * @throws RuntimeException Se o remetente ou o destinat�rio n�o estiverem cadastrados, ou se o remetente tentar enviar um recado para si mesmo.
     */
    public void enviarRecado(String id, String destinatario, String mensagem) {
        // Verificamos se o ID da sess�o � v�lido
        if (!sessoes.containsKey(id)) {
            throw new RuntimeException("Usu�rio n�o cadastrado.");
        }

        // Obtemos o login do remetente
        String loginRemetente = sessoes.get(id);

        // Verificamos se o destinat�rio existe
        if (!usuarios.containsKey(destinatario)) {
            throw new RuntimeException("Usu�rio n�o cadastrado.");
        }

        // Verificamos se o usu�rio est� tentando enviar um recado para si mesmo
        if (loginRemetente.equals(destinatario)) {
            throw new RuntimeException("Usu�rio n�o pode enviar recado para si mesmo.");
        }

        // Em seguida, enviamos o recado ao destinat�rio
        Usuario usuarioDestinatario = usuarios.get(destinatario);
        usuarioDestinatario.adicionarRecado(mensagem);

        // E salvamos os dados atualizados
        salvarDados();
    }

    /**
     * L� o pr�ximo recado de um usu�rio.
     *
     * @param id O ID da sess�o do usu�rio.
     * @return A mensagem do recado.
     * @throws RuntimeException Se o usu�rio n�o estiver cadastrado ou se n�o houver recados.
     */
    public String lerRecado(String id) {
        // Verificamos se o ID da sess�o � v�lido
        if (!sessoes.containsKey(id)) {
            // Caso n�o, o usu�rio n�o est� cadastrado
            throw new RuntimeException("Usu�rio n�o cadastrado.");
        }

        // Obtemos o login do usu�rio
        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);

        // Lemos o pr�ximo recado da fila
        String recado = usuario.lerRecado();

        // Se n�o houver recados, lan�amos uma exce��o
        if (recado == null) {
            throw new RuntimeException("N�o h� recados.");
        }

        // Salvamos os dados atualizados ap�s a leitura do recado
        salvarDados();

        return recado;
    }

    public void encerrarSistema() {
        salvarDados();
    }

    private void salvarDados() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    private void carregarDados() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            usuarios = (Map<String, Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }
    }
}