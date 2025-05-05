package br.ufal.ic.p2.jackut.strategies.relacionamentos;

import br.ufal.ic.p2.jackut.exceptions.paquera.AutoPaqueraException;
import br.ufal.ic.p2.jackut.exceptions.paquera.PaqueraExistenteException;
import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.services.RecadoService;
import br.ufal.ic.p2.jackut.strategies.RelacionamentoStrategyBase;

/**
 * Estratégia para gerenciamento de paqueras
 */
public class PaqueraStrategy extends RelacionamentoStrategyBase {

    private final RecadoService recadoService;

    public PaqueraStrategy() {
        super();
        this.recadoService = RecadoService.getInstance();
    }

    @Override
    public void adicionarRelacionamento(String usuarioLogin, String paqueraLogin) {
        validarAutoRelacionamento(usuarioLogin, paqueraLogin);

        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        Usuario paquera = usuarioService.getUsuario(paqueraLogin);

        validarInimizade(usuario, paquera);

        if (usuario.ehPaquera(paqueraLogin)) {
            throw criarExcecaoRelacionamentoExistente();
        }

        usuario.adicionarPaquera(paqueraLogin);

        // Se ambos s?o paqueras um do outro, envia recado automático
        if (paquera.ehPaquera(usuarioLogin)) {
            recadoService.enviarMensagem("Jackut", usuarioLogin,
                    paquera.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
            recadoService.enviarMensagem("Jackut", paqueraLogin,
                    usuario.getAtributo("nome") + " é seu paquera - Recado do Jackut.");
        }

        salvarAlteracoes();
    }

    @Override
    public boolean verificarRelacionamento(String usuarioLogin, String paqueraLogin) {
        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        return usuario.ehPaquera(paqueraLogin);
    }

    @Override
    public String listarRelacionamentos(String usuarioLogin) {
        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        return formatarLista(usuario.getPaqueras());
    }

    @Override
    public RuntimeException criarExcecaoAutoRelacionamento() {
        return new AutoPaqueraException();
    }

    @Override
    public RuntimeException criarExcecaoRelacionamentoExistente() {
        return new PaqueraExistenteException();
    }
}