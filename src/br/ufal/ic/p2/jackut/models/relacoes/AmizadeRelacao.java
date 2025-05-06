package br.ufal.ic.p2.jackut.models.relacoes;

import java.util.ArrayList;
import java.util.List;

public class AmizadeRelacao extends ListaRelacao {
    private final List<String> convitesEnviados;

    public AmizadeRelacao() {
        super();
        this.convitesEnviados = new ArrayList<>();
    }

    /**
     * Envia um convite de amizade para o login especificado.
     * O convite s� ser� enviado se o login n�o estiver na lista de rela��es
     * e se ainda n�o houver um convite pendente para o mesmo login.
     *
     * @param login O login do usu�rio para quem o convite ser� enviado.
     */
    public void enviarConvite(String login) {
        if (!contem(login) && !convitesEnviados.contains(login)) {
            convitesEnviados.add(login);
        }
    }

    /**
     * Verifica se h� um convite de amizade pendente para o login especificado.
     *
     * @param login O login do usu�rio a ser verificado.
     * @return true se houver um convite pendente, false caso contr�rio.
     */
    public boolean temConvitePendente(String login) {
        return convitesEnviados.contains(login);
    }
}
