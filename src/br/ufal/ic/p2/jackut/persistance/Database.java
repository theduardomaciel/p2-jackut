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

    // Método para obter a instância única
    public static synchronized Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    @SuppressWarnings("unchecked")
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

    public void salvarDados(Map<String, Usuario> usuarios) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATABASE_FILE))) {
            oos.writeObject(usuarios);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
