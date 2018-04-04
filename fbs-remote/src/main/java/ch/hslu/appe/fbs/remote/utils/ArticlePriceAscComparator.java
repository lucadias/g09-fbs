package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.ArticleDTO;

import java.util.Comparator;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticlePriceAscComparator implements Comparator<ArticleDTO> {
    @Override
    public int compare(ArticleDTO o1, ArticleDTO o2) {
        return Float.compare(o1.getPrice(), o2.getPrice());
    }
}
