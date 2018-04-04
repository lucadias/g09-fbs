package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticlePersistor {

    public ArticlePersistor() {

    }

    public Article getById(int id) { return new Article(id); }

    public Article getByArticleNr(int artNr) { return new Article(0);}

    public FBSFeedback save(Article article) { return FBSFeedback.SUCCESS; }

    public FBSFeedback updateStockById(int id, int amount) { return FBSFeedback.SUCCESS; }

    public List<Article> getList() {
        List<Article> list = new ArrayList<>();
        list.add(new Article(0));
        list.add(new Article(1));
        return list;
    }
}
