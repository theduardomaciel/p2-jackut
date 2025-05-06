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

    /**
     * Obtém a instância única da classe RecadoService.
     * Implementa o padrão Singleton para garantir que apenas uma instância
     * da classe seja criada durante a execução do programa.
     *
     * @return A instância única de RecadoService.
     */
    public static synchronized RecadoService getInstance() {
        if (instance == null) {
            instance = new RecadoService();
        }
        return instance;
    }

    /**
     * Envia uma mensagem de um usuário para outro.
     *
     * @param remetente    O login do usuário que está enviando a mensagem.
     * @param destinatario O login do usuário que receberá a mensagem.
     * @param mensagem     O conteúdo da mensagem a ser enviada.
     * @throws RecadoParaSiMesmoException       Se o remetente tentar enviar uma mensagem para si mesmo.
     * @throws InteracaoComInimigoException     Se o destinatário for inimigo do remetente.
     */
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

    /**
     * Lê a mensagem mais recente de um usuário.
     *
     * @param login O login do usuário que deseja ler a mensagem.
     * @return A mensagem mais recente do usuário.
     * @throws RecadoNaoEncontradoException Se não houver mensagens disponíveis para o usuário.
     */
    public String lerMensagem(String login) {
        Usuario usuario = usuarioService.getUsuario(login);
        String recado = usuario.lerRecado();

        if (recado == null) {
            throw new RecadoNaoEncontradoException();
        }

        usuarioService.salvarDados();
        return recado;
    }

    /**
     * Remove todos os recados enviados por um usuário específico.
     *
     * @param login O login do usuário cujos recados enviados serão removidos.
     */
    public void removerRecados(String login) {
        // Remove todos os recados enviados do usuário
        for (Usuario usuario : usuarioService.getTodosUsuarios()) {
            usuarioService.removerRecados(usuario.getLogin(), login);
        }

        usuarioService.salvarDados();
    }
}
