package ch.hslu.appe.fbs.business;

import ch.hslu.appe.fbs.remote.ArticleDTO;
import ch.hslu.appe.fbs.remote.FBSFeedback;

import java.util.HashMap;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticleManager {

    private HashMap<String, String> lockpool;

    public ArticleManager() {

        this.lockpool = new HashMap<>();
    }

    public ArticleDTO getById(int id) { return null; }

    public ArticleDTO getByArticleNr(int id) { return null; }

    public List<ArticleDTO> getList() { return null; }

    public List<ArticleDTO> getList(String regEx) { return null; }

    public FBSFeedback save(ArticleDTO article) { return null; }

    public FBSFeedback delete(ArticleDTO article) { return null; }

    public FBSFeedback updateStockById(int id, int amount) { return null; }

    public String lock(int id) { return null; }

    public FBSFeedback release(int id, String hash) { return null; }
}
