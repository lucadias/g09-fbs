package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.ArticleDTO;

import java.util.Comparator;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticlePriceDescComparator implements Comparator<ArticleDTO> {
    @Override
    public int compare(ArticleDTO o1, ArticleDTO o2) {
        return Float.compare(o2.getPrice(), o1.getPrice());
    }
}
