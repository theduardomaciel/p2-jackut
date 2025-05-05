// filepath: c:\Users\Eduardo\Documents\UFAL\Projetos\p2-jackut\src\br\ufal\ic\p2\jackut\services\MensagemService.java
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
        Usuario usuario = usuarioService.getUsuario(loginRemetente);
        if (!usuario.getComunidades().contains(nomeComunidade)) {
            throw new IllegalArgumentException("Usuário não é membro da comunidade.");
        }

        // Envia a mensagem para a comunidade
        comunidadeService.enviarMensagem(nomeComunidade, loginRemetente, conteudo);
    }

    public String lerMensagem(String login) {
        // Lê a mensagem da primeira comunidade que o usuário pertence
        return comunidadeService.lerMensagem(login);
    }
}