package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.recado.RecadoNaoEncontradoException;
import br.ufal.ic.p2.jackut.exceptions.recado.RecadoParaSiMesmoException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;
import br.ufal.ic.p2.jackut.models.Mensagem;
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

    public void enviarMensagem(String remetente, String destinatario, String mensagem) {
        if (remetente.equals(destinatario)) {
            throw new RecadoParaSiMesmoException();
        }

        Usuario usuarioDestinatario = usuarioService.getUsuario(destinatario);

        if (usuarioDestinatario.ehInimigo(remetente)) {
            throw new InteracaoComInimigoException(usuarioDestinatario.getAtributo("nome"));
        }

        usuarioDestinatario.adicionarRecado(new Mensagem(remetente, mensagem));
        usuarioService.salvarDados();
    }

    public String lerMensagem(String login) {
        Usuario usuario = usuarioService.getUsuario(login);
        String recado = usuario.lerRecado();

        if (recado == null) {
            throw new RecadoNaoEncontradoException();
        }

        usuarioService.salvarDados();
        return recado;
    }

    public void removerRecados(String login) {
        // Remove todos os recados enviados do usu�rio
        for (Usuario usuario : usuarioService.getTodosUsuarios()) {
            usuarioService.removerRecados(usuario.getLogin(), login);
        }

        usuarioService.salvarDados();
    }
}
