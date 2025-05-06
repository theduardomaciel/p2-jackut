package br.ufal.ic.p2.jackut.persistance;

import br.ufal.ic.p2.jackut.models.Usuario;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Database {
    private static Database instance;
    private static final String DATABASE_FILE = "jackut.dat";

    // Construtor privado para implementar o Singleton
    private Database() {}

    /**
     * Obtém a instância única da classe Database.
     * Implementa o padrão Singleton para garantir que apenas uma instância
     * da classe seja criada durante a execução do programa.
     *
     * @return A instância única de Database.
     */
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
    /**
     * Carrega os dados do arquivo de banco de dados.
     *
     * @return Um mapa contendo os dados dos usuários. Retorna um mapa vazio
     *         caso o arquivo não seja encontrado ou ocorra algum erro.
     */
    public Map<String, Usuario> carregarDados() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATABASE_FILE))) {
            return (Map<String, Usuario>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new HashMap<>();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new HashMap<>();
        }
    }

    /**
     * Salva os dados no arquivo de banco de dados.
     *
     * @param usuarios Um mapa contendo os dados dos usuários a serem salvos.
     */
    public void salvarDados(Map<String, Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
