package br.ufal.ic.p2.jackut.utils;

import java.util.List;
import java.util.Collection;

/**
 * Classe utilitária para formatação de elementos
 */
public class FormatadorUtil {

    /**
     * Formata uma coleção para uma string no formato {item1,item2,...}
     * 
     * @param colecao A coleção a ser formatada
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
     * Formata uma coleção de strings para uma string no formato
     * {string1,string2,...}
     * 
     * @param colecao A coleção de strings a ser formatada
     * @return String formatada com os elementos entre chaves
     */
    public static String formatarStrings(Collection<String> colecao) {
        if (colecao == null || colecao.isEmpty()) {
            return "{}";
        }
        return "{" + String.join(",", colecao) + "}";
    }
}