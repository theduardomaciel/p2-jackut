package br.ufal.ic.p2.jackut.models.relacionamentos;

import java.util.ArrayList;
import java.util.List;

public class AmizadeRelacionamento extends ListaRelacionamento {
    private final List<String> convitesEnviados;

    public AmizadeRelacionamento() {
        super();
        this.convitesEnviados = new ArrayList<>();
    }

    public void enviarConvite(String login) {
        if (!contem(login) && !convitesEnviados.contains(login)) {
            convitesEnviados.add(login);
        }
    }

    public boolean temConvitePendente(String login) {
        return convitesEnviados.contains(login);
    }
}
