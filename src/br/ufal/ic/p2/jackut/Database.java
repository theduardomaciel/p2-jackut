package br.ufal.ic.p2.jackut;

import java.io.*;
import java.util.Map;

public class Database {
    private static final String DATA_FILE = "usuarios.dat";

    /**
     * Salva os dados dos usu�rios no arquivo especificado.
     *
     * @param usuarios O mapa contendo os dados dos usu�rios a serem salvos.
     */
    public void salvarDados(Map<String, Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            System.err.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    /**
     * Carrega os dados dos usu�rios do arquivo especificado.
     *
     * @return Um mapa contendo os dados dos usu�rios carregados, ou null se o arquivo n�o existir.
     */
    @SuppressWarnings("unchecked")
    public Map<String, Usuario> carregarDados() {
        File file = new File(DATA_FILE);
        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE))) {
            return (Map<String, Usuario>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
        }

        return null;
    }
}
