package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.recado.RecadoNaoEncontradoException;
import br.ufal.ic.p2.jackut.exceptions.recado.RecadoParaSiMesmoException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;
import br.ufal.ic.p2.jackut.models.Usuario;

public class RecadoService {
    private static RecadoService instance;
    private final UsuarioService usuarioService;

    // Construtor privado para implementar o Singleton
    private RecadoService() {
        this.usuarioService = UsuarioService.getInstance();
    }

    // Método para obter a instância única
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

        if (usuarioDestinatario.ehInimigo(remetente)) {
            throw new InteracaoComInimigoException(usuarioDestinatario.getAtributo("nome"));
        }

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
