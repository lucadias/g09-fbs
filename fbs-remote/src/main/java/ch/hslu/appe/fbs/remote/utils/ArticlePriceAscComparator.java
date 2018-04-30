package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;

import java.util.Comparator;

/**
 * This comparator compares articles ascending based on the price.
 *
 * @author Mischa Gruber
 */
public final class ArticlePriceAscComparator implements Comparator<ArticleDTO> {
    @Override
    public int compare(final ArticleDTO o1, final ArticleDTO o2) {
        return Double.compare(o1.getPrice(), o2.getPrice());
    }
}
