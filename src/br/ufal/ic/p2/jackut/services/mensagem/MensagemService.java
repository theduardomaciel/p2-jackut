package br.ufal.ic.p2.jackut.services.mensagem;

import br.ufal.ic.p2.jackut.services.ComunidadeService;

public class MensagemService extends MensagemBaseService {
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

    public void enviarMensagem(String nomeComunidade, String loginRemetente, String mensagem) {
        // Verifica se a comunidade existe
        comunidadeService.enviarMensagem(nomeComunidade, loginRemetente, mensagem);
    }

    public String lerMensagem(String login) {
        // Lê a mensagem da comunidade à qual o usuário pertence
        return comunidadeService.lerMensagem(login);
    }
}