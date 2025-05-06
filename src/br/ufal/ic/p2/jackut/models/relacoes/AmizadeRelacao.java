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
     * O convite só será enviado se o login não estiver na lista de relações
     * e se ainda não houver um convite pendente para o mesmo login.
     *
     * @param login O login do usuário para quem o convite será enviado.
     */
    public void enviarConvite(String login) {
        if (!contem(login) && !convitesEnviados.contains(login)) {
            convitesEnviados.add(login);
        }
    }

    /**
     * Verifica se há um convite de amizade pendente para o login especificado.
     *
     * @param login O login do usuário a ser verificado.
     * @return true se houver um convite pendente, false caso contrário.
     */
    public boolean temConvitePendente(String login) {
        return convitesEnviados.contains(login);
    }
}
