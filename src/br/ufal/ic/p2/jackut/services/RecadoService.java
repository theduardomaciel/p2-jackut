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
     * Obt�m a inst�ncia �nica da classe RecadoService.
     * Implementa o padr�o Singleton para garantir que apenas uma inst�ncia
     * da classe seja criada durante a execu��o do programa.
     *
     * @return A inst�ncia �nica de RecadoService.
     */
    public static synchronized RecadoService getInstance() {
        if (instance == null) {
            instance = new RecadoService();
        }
        return instance;
    }

    /**
     * Envia uma mensagem de um usu�rio para outro.
     *
     * @param remetente    O login do usu�rio que est� enviando a mensagem.
     * @param destinatario O login do usu�rio que receber� a mensagem.
     * @param mensagem     O conte�do da mensagem a ser enviada.
     * @throws RecadoParaSiMesmoException       Se o remetente tentar enviar uma mensagem para si mesmo.
     * @throws InteracaoComInimigoException     Se o destinat�rio for inimigo do remetente.
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
     * L� a mensagem mais recente de um usu�rio.
     *
     * @param login O login do usu�rio que deseja ler a mensagem.
     * @return A mensagem mais recente do usu�rio.
     * @throws RecadoNaoEncontradoException Se n�o houver mensagens dispon�veis para o usu�rio.
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
     * Remove todos os recados enviados por um usu�rio espec�fico.
     *
     * @param login O login do usu�rio cujos recados enviados ser�o removidos.
     */
    public void removerRecados(String login) {
        // Remove todos os recados enviados do usu�rio
        for (Usuario usuario : usuarioService.getTodosUsuarios()) {
            usuarioService.removerRecados(usuario.getLogin(), login);
        }

        usuarioService.salvarDados();
    }
}
