package br.ufal.ic.p2.jackut.services;

import br.ufal.ic.p2.jackut.exceptions.mensagem.NaoHaMensagensException;
import br.ufal.ic.p2.jackut.models.Comunidade;
import br.ufal.ic.p2.jackut.models.Usuario;

public class MensagemService {
    private static MensagemService instance;
    private final ComunidadeService comunidadeService;

    // Construtor privado para implementar o Singleton
    private MensagemService() {
        this.comunidadeService = ComunidadeService.getInstance();
    }

    // Método para obter a instância única
    public static synchronized MensagemService getInstance() {
        if (instance == null) {
            instance = new MensagemService();
        }
        return instance;
    }

    public void enviarMensagem(String nomeComunidade, String loginRemetente, String conteudo) {
        // Verifica se o usuário está na comunidade
        Usuario remetente = UsuarioService.getInstance().getUsuario(loginRemetente);
        Comunidade comunidade = comunidadeService.getComunidade(nomeComunidade);

        if (!comunidade.contemMembro(remetente)) {
            throw new RuntimeException("Usuário não é membro da comunidade.");
        }

        // Envia a mensagem para a comunidade
        comunidadeService.enviarMensagem(nomeComunidade, loginRemetente, conteudo);
    }

    public String lerMensagem(String login) {
        // Lê a mensagem de todas as comunidades que o usuário pertence
        for (Comunidade comunidade : comunidadeService.getComunidades()) {
            if (comunidade.getMembrosLogins().contains(login)) {
                try {
                    return comunidade.lerMensagem(login);
                } catch (NaoHaMensagensException ignored) {
                    // Continue procurando em outras comunidades
                }
            }
        }
        throw new NaoHaMensagensException();
    }
}