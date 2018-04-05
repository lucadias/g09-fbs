package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.ArticleDTO;

import java.util.Comparator;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class ArticlePriceAscComparator implements Comparator<ArticleDTO> {
    @Override
    public int compare(final ArticleDTO o1, final ArticleDTO o2) {
        return Float.compare(o1.getPrice(), o2.getPrice());
    }
}
