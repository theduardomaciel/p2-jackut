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

    public void zerarSistema() {
        usuarios.clear();
        sessoes.clear();
        contadorSessao = 0;
        salvarDados();
    }

    public void criarUsuario(String login, String senha, String nome) {
        validarLogin(login);
        validarSenha(senha);

        if (usuarios.containsKey(login)) {
            throw new RuntimeException("Conta com esse nome já existe.");
        }

        Usuario novoUsuario = new Usuario(login, senha, nome);
        usuarios.put(login, novoUsuario);
        salvarDados();
    }

    private void validarLogin(String login) {
        if (login == null || login.trim().isEmpty()) {
            throw new RuntimeException("Login inválido.");
        }
    }

    private void validarSenha(String senha) {
        if (senha == null || senha.trim().isEmpty()) {
            throw new RuntimeException("Senha inválida.");
        }
    }

    public String abrirSessao(String login, String senha) {
        if (login == null || login.trim().isEmpty() ||
                senha == null || senha.trim().isEmpty() ||
                !usuarios.containsKey(login) ||
                !usuarios.get(login).getSenha().equals(senha)) {
            throw new RuntimeException("Login ou senha inválidos.");
        }

        String idSessao = "sessao" + (++contadorSessao);
        sessoes.put(idSessao, login);
        return idSessao;
    }

    public String getAtributoUsuario(String login, String atributo) {
        if (!usuarios.containsKey(login)) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        Usuario usuario = usuarios.get(login);
        return usuario.getAtributo(atributo);
    }

    public void editarPerfil(String id, String atributo, String valor) {
        if (!sessoes.containsKey(id)) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);
        usuario.setAtributo(atributo, valor);
        salvarDados();
    }

    public void adicionarAmigo(String id, String amigoLogin) {
        if (!sessoes.containsKey(id)) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        String login = sessoes.get(id);

        if (login.equals(amigoLogin)) {
            throw new RuntimeException("Usuário não pode adicionar a si mesmo como amigo.");
        }

        if (!usuarios.containsKey(amigoLogin)) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        Usuario usuario = usuarios.get(login);
        Usuario amigo = usuarios.get(amigoLogin);

        if (usuario.ehAmigo(amigoLogin)) {
            throw new RuntimeException("Usuário já está adicionado como amigo.");
        }

        // Verificamos se o outro usuário já enviou convite
        if (amigo.verificarConvitePendente(login)) {
            // Caso sim, aceitamos a amizade em ambas as direções
            usuario.aceitarAmizade(amigoLogin);
            amigo.aceitarAmizade(login);
            salvarDados();
            return;
        }

        // Verificamos se já enviou convite antes
        if (usuario.verificarConvitePendente(amigoLogin)) {
            throw new RuntimeException("Usuário já está adicionado como amigo, esperando aceitação do convite.");
        }

        // Enviamos o convite
        usuario.enviarConviteAmizade(amigoLogin);
        salvarDados();
    }

    public boolean ehAmigo(String login, String amigoLogin) {
        if (!usuarios.containsKey(login) || !usuarios.containsKey(amigoLogin)) {
            return false;
        }

        Usuario usuario = usuarios.get(login);
        return usuario.ehAmigo(amigoLogin);
    }

    public String getAmigos(String login) {
        if (!usuarios.containsKey(login)) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        Usuario usuario = usuarios.get(login);
        List<String> amigos = usuario.getAmigos();

        if (amigos.isEmpty()) {
            return "{}";
        }

        return "{" + String.join(",", amigos) + "}";
    }

    public void enviarRecado(String id, String destinatario, String mensagem) {
        // Verificamos se o ID da sessão é válido
        if (!sessoes.containsKey(id)) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        // Obtemos o login do remetente
        String loginRemetente = sessoes.get(id);

        // Verificamos se o destinatário existe
        if (!usuarios.containsKey(destinatario)) {
            throw new RuntimeException("Usuário não cadastrado.");
        }

        // Verificamos se o usuário está tentando enviar um recado para si mesmo
        if (loginRemetente.equals(destinatario)) {
            throw new RuntimeException("Usuário não pode enviar recado para si mesmo.");
        }

        // Em seguida, enviamos o recado ao destinatário
        Usuario usuarioDestinatario = usuarios.get(destinatario);
        usuarioDestinatario.adicionarRecado(mensagem);

        // E salvamos os dados atualizados
        salvarDados();
    }

    public String lerRecado(String id) {
        // Verificamos se o ID da sessão é válido
        if (!sessoes.containsKey(id)) {
            // Caso não, o usuário não está cadastrado
            throw new RuntimeException("Usuário não cadastrado.");
        }

        // Obtemos o login do usuário
        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);

        // Lemos o próximo recado da fila
        String recado = usuario.lerRecado();

        // Se não houver recados, lançamos uma exceção
        if (recado == null) {
            throw new RuntimeException("Não há recados.");
        }

        // Salvamos os dados atualizados após a leitura do recado
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