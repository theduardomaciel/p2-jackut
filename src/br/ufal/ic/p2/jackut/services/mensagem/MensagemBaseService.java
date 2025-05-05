package br.ufal.ic.p2.jackut.services.mensagem;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.SessaoService;
import br.ufal.ic.p2.jackut.services.UsuarioService;

public abstract class MensagemBaseService {
    protected MensagemBaseService() {}

    public abstract void enviarMensagem(String remetente, String destinatario, String mensagem);
    public abstract String lerMensagem(String login);
}
