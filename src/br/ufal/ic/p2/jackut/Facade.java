package br.ufal.ic.p2.jackut;

import easyaccept.EasyAccept;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
        if (atributo.equalsIgnoreCase("nome")) {
            return usuario.getNome();
        }
        throw new RuntimeException("Atributo não encontrado.");
    }

    public void editarPerfil(String id, String atributo, String valor) {
        if (!sessoes.containsKey(id)) {
            throw new RuntimeException("Sessão inválida.");
        }

        String login = sessoes.get(id);
        Usuario usuario = usuarios.get(login);

        switch (atributo.toLowerCase()) {
            case "nome":
                usuario.setNome(valor);
                break;
            default:
                throw new RuntimeException("Atributo não encontrado.");
        }
        salvarDados();
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