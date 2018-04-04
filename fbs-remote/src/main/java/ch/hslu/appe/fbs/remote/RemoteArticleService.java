package ch.hslu.appe.fbs.remote;

import java.rmi.Remote;
import java.util.List;

/**
 * Remote interface for the service object
 *
 * @author Mischa Gruber
 */
public interface RemoteArticleService extends Remote{

    ArticleDTO getById(int id);

    ArticleDTO getByArticleNr(int artNr);

    List<ArticleDTO> getList();

    List<ArticleDTO> getList(String regEx);

    FBSFeedback updateStockById(int id, int amount);

    List<ArticleDTO> sortList(SortingType type);

    List<ArticleDTO> sortList(List<ArticleDTO> articles, SortingType type);

    List<ArticleDTO> search(String regEx);

    FBSFeedback save(ArticleDTO article);

    FBSFeedback delete(ArticleDTO article);

    String lock(ArticleDTO article);

    FBSFeedback release(ArticleDTO article, String hash);
}
