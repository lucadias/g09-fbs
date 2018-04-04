package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.ArticleDTO;

import java.util.Comparator;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticleNameAscComparator implements Comparator<ArticleDTO> {
    @Override
    public int compare(ArticleDTO o1, ArticleDTO o2) {
        return o1.getName().compareTo(o2.getName());
    }
}
