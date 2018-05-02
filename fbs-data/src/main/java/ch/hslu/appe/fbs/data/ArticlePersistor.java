package ch.hslu.appe.fbs.data;

import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


//Hotfix


/**
 * JavaDoc
 *
 * @author Pascal Stalder
 */
public class ArticlePersistor {


    private final EntityManager entitymanager = Util.entityManager;


    public Article getById(int id) {
        Util.transactionBegin();

        Article article = entitymanager.find(Article.class, id);

        Util.transactionCommit();

        return article;
    }

    public List<Article> getList(String regex){


        String query = "";
        try {
            int regexint = Integer.parseInt(regex);
            query = "SELECT c FROM Article c WHERE c.articlenumber LIKE :regexint";
        }catch (NumberFormatException nfe) {
            query = "SELECT c FROM Article c WHERE c.name LIKE :regex OR c.description LIKE :regex";
        } finally {
            return this.entitymanager.createQuery(query)
                    .setParameter("regex", regex)
                    .getResultList();
        }
}

    public Article getByArticleNr(int artNr) { return this.getById(artNr);}

    public FBSFeedback save(Article article) {
        return Util.save(article);
    }

    public FBSFeedback updateStockById(int id, int amount) { return FBSFeedback.SUCCESS; }

    public List<Article> getList() {
        Util.transactionBegin();
        List<Article> list = this.entitymanager.createQuery("Select a From Article a").getResultList();
        Util.transactionCommit();
        return list;
    }

    public List<Article> search(String regEx) {
        List<Article> result = new ArrayList<>();
        for (Article article:this.getList()){
            if(article.getName().matches(regEx)) {
                result.add(article);
            }
        }
        return result;
    }


}