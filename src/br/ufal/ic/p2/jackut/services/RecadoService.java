package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.*;
import br.ufal.ic.p2.jackut.models.Usuario;

public class RecadoService {
    private static RecadoService instance;
    private final UsuarioService usuarioService;

    // Construtor privado para implementar o Singleton
    private RecadoService() {
        this.usuarioService = UsuarioService.getInstance();
    }

    // M�todo para obter a inst�ncia �nica
    public static synchronized RecadoService getInstance() {
        if (instance == null) {
            instance = new RecadoService();
        }
        return instance;
    }

    public void enviarRecado(String remetente, String destinatario, String mensagem) {
        if (remetente.equals(destinatario)) {
            throw new RecadoParaSiMesmoException();
        }

        Usuario usuarioDestinatario = usuarioService.getUsuario(destinatario);
        usuarioDestinatario.adicionarRecado(mensagem);
        usuarioService.salvarDados();
    }

    public String lerRecado(String login) {
        Usuario usuario = usuarioService.getUsuario(login);
        String recado = usuario.lerRecado();
        
        if (recado == null) {
            throw new RecadoNaoEncontradoException();
        }

        usuarioService.salvarDados();
        return recado;
    }
}
