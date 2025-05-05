package br.ufal.ic.p2.jackut.services.relacionamentos;

import br.ufal.ic.p2.jackut.models.Usuario;
import br.ufal.ic.p2.jackut.exceptions.paquera.PaqueraExistenteException;
import br.ufal.ic.p2.jackut.exceptions.paquera.AutoPaqueraException;
import br.ufal.ic.p2.jackut.exceptions.inimizade.InteracaoComInimigoException;
import br.ufal.ic.p2.jackut.services.RecadoService;

public class PaqueraService extends RelacionamentoBaseService {
    private static PaqueraService instance;
    private final RecadoService recadoService;

    private PaqueraService() {
        super();
        this.recadoService = RecadoService.getInstance();
    }

    public static synchronized PaqueraService getInstance() {
        if (instance == null) {
            instance = new PaqueraService();
        }
        return instance;
    }

    public void adicionarPaquera(String sessaoId, String paqueraLogin) {
        String usuarioLogin = sessaoService.getLoginUsuario(sessaoId);

        validarAutoRelacionamento(usuarioLogin, paqueraLogin, new AutoPaqueraException());

        Usuario usuario = usuarioService.getUsuario(usuarioLogin);
        Usuario paquera = usuarioService.getUsuario(paqueraLogin);

        validarInimizade(usuario, paquera, new InteracaoComInimigoException(paquera.getAtributo("nome")));

        // Verifica se a paquera j� existe
        if (usuario.ehPaquera(paqueraLogin)) {
            throw new PaqueraExistenteException();
        }

        usuario.adicionarPaquera(paqueraLogin);

        // Se ambos s�o paqueras um do outro, envia recado autom�tico
        if (paquera.ehPaquera(usuarioLogin)) {
            recadoService.enviarMensagem("Jackut", usuarioLogin,
                    paquera.getAtributo("nome") + " � seu paquera - Recado do Jackut.");
            recadoService.enviarMensagem("Jackut", paqueraLogin,
                    usuario.getAtributo("nome") + " � seu paquera - Recado do Jackut.");
        }

        salvarAlteracoes();
    }

    public boolean ehPaquera(String sessaoId, String paqueraLogin) {
        Usuario usuario = getUsuarioPorSessao(sessaoId);
        return usuario.ehPaquera(paqueraLogin);
    }

    public String getPaqueras(String sessaoId) {
        Usuario usuario = getUsuarioPorSessao(sessaoId);
        return formatarLista(usuario.getPaqueras());
    }
}