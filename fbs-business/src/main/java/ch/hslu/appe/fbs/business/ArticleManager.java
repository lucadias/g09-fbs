package ch.hslu.appe.fbs.business;

import ch.hslu.appe.fbs.data.ArticlePersistor;
import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.ArticleDTO;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class ArticleManager {
    //TODO: implement search, sortList


    private static ArticleManager instance = null;

    private ArticlePersistor articlePersistor;
    private HashMap<String, String> lockpool;
    private MessageDigest sha256Digest;

    public static ArticleManager getInstance() {
        if (instance == null) {
            instance = new ArticleManager();
        }
        return instance;
    }

    private ArticleManager() {

        this.articlePersistor = new ArticlePersistor();
        this.lockpool = new HashMap<>();

        try {
            this.sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public ArticleDTO getById(int id) {
        return convertToDTO(articlePersistor.getById(id));
    }

    public ArticleDTO getByArticleNr(int artNr) {
        return convertToDTO(articlePersistor.getByArticleNr(artNr));
    }

    public List<ArticleDTO> getList() {
        return getList("");
    }

    public List<ArticleDTO> getList(String regEx) {
        // TODO: implement regular expression
        List<Article> articleList = articlePersistor.getList();
        return convertToDTO(articleList);
    }

    public FBSFeedback save(ArticleDTO articleDTO, String hash) {
        FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            Article article = convertToEntity(articleDTO);
            return articlePersistor.save(article);
        } else {
            return lockCheck;
        }
    }

    public FBSFeedback delete(ArticleDTO articleDTO, String hash) {
        FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            Article article = convertToEntity(articleDTO);
            article.setAvailable(false);

            return articlePersistor.save(article);
        } else {
            return lockCheck;
        }
    }

    public FBSFeedback updateStockById(int id, int amount, String hash) {
        FBSFeedback lockCheck = checkLock(id, hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            return articlePersistor.updateStockById(id, amount);
        } else {
            return lockCheck;
        }
    }

    public List<ArticleDTO> sortList(SortingType type) {
        return convertToDTO(articlePersistor.getList());
    }

    public List<ArticleDTO> sortList(List<ArticleDTO> articleDTOs, SortingType type) {
        return articleDTOs;
    }

    public List<ArticleDTO> search(String regEx) {
        return convertToDTO(articlePersistor.getList());
    }

    public String lock(int id) {

        synchronized (lockpool) {

            // check if article is already locked
            if (lockpool.containsKey(String.valueOf(id))) {
                return null;
            } else {
                String toHash = String.valueOf(id) + (new Date()).toString();
                String hash = "";
                if(sha256Digest != null) {
                    hash = (sha256Digest.digest(toHash.getBytes())).toString();
                } else {
                    hash = toHash;
                }

                lockpool.put(String.valueOf(id), hash);
                return hash;
            }

        }
    }

    public FBSFeedback release(int id, String hash) {

        synchronized (lockpool) {

            if (lockpool.containsKey(String.valueOf(id))) {

                if (lockpool.get(String.valueOf(id)) == hash) {
                    lockpool.remove(String.valueOf(id), hash);
                    return FBSFeedback.SUCCESS;
                } else {
                    return FBSFeedback.MISMATCHING_HASH;
                }

            } else {
                return FBSFeedback.LOCK_LOST;
            }

        }
    }

    private FBSFeedback checkLock(int id, String hash) {
        synchronized (lockpool) {

            if (lockpool.containsKey(String.valueOf(id))) {
                if (lockpool.get(String.valueOf(id)) != hash) {
                    return FBSFeedback.MISMATCHING_HASH;
                } else {
                    return FBSFeedback.SUCCESS;
                }
            } else {
                return FBSFeedback.ARTICLE_NOT_LOCKED;
            }
        }
    }

    private ArticleDTO convertToDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO(article.getId());
        articleDTO.setName(article.getName());
        articleDTO.setArticleNumber(article.getArticleNumber());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setInStock(article.getInStock());
        articleDTO.setMinInStock(article.getMinInStock());
        articleDTO.setPrice(article.getPrice());
        articleDTO.setAvailable(article.isAvailable());

        return articleDTO;
    }

    private List<ArticleDTO> convertToDTO(List<Article> articleList) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOList.add(convertToDTO(article));
        }

        return articleDTOList;
    }

    private Article convertToEntity(ArticleDTO articleDTO) {
        Article article = new Article(articleDTO.getId());
        article.setName(articleDTO.getName());
        article.setArticleNumber(articleDTO.getArticleNumber());
        article.setDescription(articleDTO.getDescription());
        article.setInStock(articleDTO.getInStock());
        article.setMinInStock(articleDTO.getMinInStock());
        article.setPrice(articleDTO.getPrice());
        article.setAvailable(articleDTO.isAvailable());

        return article;
    }
}
