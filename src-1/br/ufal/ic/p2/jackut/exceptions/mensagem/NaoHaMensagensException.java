package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.mensagem.NaoHaMensagensException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;

public class MensagemService {
    private static MensagemService instance;
    private final ComunidadeService comunidadeService;
    private final UsuarioService usuarioService;

    // Construtor privado para implementar o Singleton
    private MensagemService() {
        this.comunidadeService = ComunidadeService.getInstance();
        this.usuarioService = UsuarioService.getInstance();
    }

    // Método para obter a instância única
    public static synchronized MensagemService getInstance() {
        if (instance == null) {
            instance = new MensagemService();
        }
        return instance;
    }

    public void enviarMensagem(String loginRemetente, String nomeComunidade, String conteudo) {
        // Verifica se o usuário está na comunidade
        Usuario remetente = usuarioService.getUsuario(loginRemetente);
        Comunidade comunidade = comunidadeService.getComunidade(nomeComunidade);

        if (!comunidade.contemMembro(remetente)) {
            throw new IllegalArgumentException("Usuário não é membro da comunidade.");
        }

        // Envia a mensagem para a comunidade
        comunidade.adicionarMensagem(conteudo, loginRemetente);
    }

    public String lerMensagem(String login) {
        // Lê a mensagem de todas as comunidades do usuário
        for (Comunidade comunidade : comunidadeService.getComunidades(login)) {
            try {
                return comunidade.lerMensagem(login);
            } catch (NaoHaMensagensException ignored) {
                // Continue procurando em outras comunidades
            }
        }
        throw new NaoHaMensagensException();
    }
}