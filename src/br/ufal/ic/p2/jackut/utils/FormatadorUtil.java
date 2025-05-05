package br.ufal.ic.p2.jackut.utils;

import java.util.List;
import java.util.Collection;

/**
 * Classe utilit�ria para formata��o de elementos
 */
public class FormatadorUtil {

    /**
     * Formata uma cole��o para uma string no formato {item1,item2,...}
     * 
     * @param colecao A cole��o a ser formatada
     * @return String formatada com os elementos entre chaves
     */
    public static String formatarComoLista(Collection<?> colecao) {
        if (colecao == null || colecao.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", colecao.stream()
                .map(Object::toString)
                .toList()) + "}";
    }

    /**
     * Formata uma cole��o de strings para uma string no formato
     * {string1,string2,...}
     * 
     * @param colecao A cole��o de strings a ser formatada
     * @return String formatada com os elementos entre chaves
     */
    public static String formatarStrings(Collection<String> colecao) {
        if (colecao == null || colecao.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", colecao) + "}";
    }
}