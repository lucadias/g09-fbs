package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;

import java.util.Comparator;

/**
 * This comparator compares articles descending based on the price.
 *
 * @author Mischa Gruber
 */
public final class ArticlePriceDescComparator implements Comparator<ArticleDTO> {
    @Override
    public int compare(final ArticleDTO o1, final ArticleDTO o2) {
        return Double.compare(o2.getPrice(), o1.getPrice());
    }
}
