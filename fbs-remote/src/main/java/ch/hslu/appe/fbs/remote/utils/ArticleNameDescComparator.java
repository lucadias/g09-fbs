package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;

import java.util.Comparator;

/**
 * This comparator compares articles desscending based on the name.
 *
 * @author Mischa Gruber
 */
public final class ArticleNameDescComparator implements Comparator<ArticleDTO> {
    @Override
    public int compare(final ArticleDTO o1, final ArticleDTO o2) {
        return o2.getName().compareTo(o1.getName());
    }
}
