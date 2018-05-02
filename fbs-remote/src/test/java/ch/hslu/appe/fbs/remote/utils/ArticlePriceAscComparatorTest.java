package ch.hslu.appe.fbs.remote.utils;

import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * JUnit Test of ArticlePriceAscComparator.
 *
 * @author Mischa Gruber
 */
public class ArticlePriceAscComparatorTest {

    @Test
    public void testSorting() {
        List<ArticleDTO> articleDTOs = new ArrayList<>();
        ArticleDTO article1 = new ArticleDTO(1);
        article1.setPrice(new Double(10));
        ArticleDTO article2 = new ArticleDTO(2);
        article2.setPrice(new Double(5));
        articleDTOs.add(article1);
        articleDTOs.add(article2);

        Comparator<ArticleDTO> comparator = new ArticlePriceAscComparator();
        Collections.sort(articleDTOs, comparator);

        assertEquals(2, articleDTOs.get(0).getId());
        assertEquals(1, articleDTOs.get(1).getId());
    }
}
