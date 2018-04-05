package ch.hslu.appe.fbs.business;

import ch.hslu.appe.fbs.data.ArticlePersistor;
import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.ArticleDTO;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.utils.ArticleNameAscComparator;
import ch.hslu.appe.fbs.remote.utils.ArticleNameDescComparator;
import ch.hslu.appe.fbs.remote.utils.ArticlePriceAscComparator;
import ch.hslu.appe.fbs.remote.utils.ArticlePriceDescComparator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class ArticleManager {
    private static ArticleManager instance = null;
    private static Object mutex = new Object();

    private ArticlePersistor articlePersistor;
    private HashMap<String, String> lockpool;
    private MessageDigest sha256Digest;

    public static ArticleManager getInstance() {
        ArticleManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new ArticleManager();
                }
            }
        }
        return result;
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

    public ArticleDTO getById(final int id) {
        return convertToDTO(articlePersistor.getById(id));
    }

    public ArticleDTO getByArticleNr(final int artNr) {
        return convertToDTO(articlePersistor.getByArticleNr(artNr));
    }

    public List<ArticleDTO> getList() {
        List<Article> articleList = articlePersistor.getList();
        return convertToDTO(articleList);
    }

    public FBSFeedback save(final ArticleDTO articleDTO, final String hash) {
        FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            Article article = convertToEntity(articleDTO);
            return articlePersistor.save(article);
        } else {
            return lockCheck;
        }
    }

    public FBSFeedback delete(final ArticleDTO articleDTO, final String hash) {
        FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            Article article = convertToEntity(articleDTO);
            article.setAvailable(false);

            return articlePersistor.save(article);
        } else {
            return lockCheck;
        }
    }

    public FBSFeedback updateStockById(final int id, final int amount, final String hash) {
        FBSFeedback lockCheck = checkLock(id, hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            return articlePersistor.updateStockById(id, amount);
        } else {
            return lockCheck;
        }
    }

    public List<ArticleDTO> sortList(final SortingType type) {
        return sortList(convertToDTO(articlePersistor.getList()), type);
    }

    public List<ArticleDTO> sortList(final List<ArticleDTO> articleDTOs, final SortingType type) {

        Comparator<ArticleDTO> comparator;

        switch (type) {
            case ARTICLE_NAME_ASC:
                comparator = new ArticleNameAscComparator();
                break;
            case ARTICLE_NAME_DESC:
                comparator = new ArticleNameDescComparator();
                break;
            case ARTICLE_PRICE_ASC:
                comparator = new ArticlePriceAscComparator();
                break;
            case ARTICLE_PRICE_DESC:
                comparator = new ArticlePriceDescComparator();
                break;
            default:
                comparator = new ArticleNameAscComparator();
                break;
        }

        Collections.sort(articleDTOs, comparator);

        return articleDTOs;
    }

    public List<ArticleDTO> search(final String regEx) {
        return convertToDTO(articlePersistor.search(regEx));
    }

    public String lock(final int id) {

        synchronized (lockpool) {

            // check if article is already locked
            if (lockpool.containsKey(String.valueOf(id))) {
                return null;
            } else {
                String toHash = String.valueOf(id) + (new Date()).toString();
                String hash = "";
                if (sha256Digest != null) {
                    byte[] hashBytes = sha256Digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
                    hash = String.valueOf(hashBytes);
                } else {
                    hash = toHash;
                }

                lockpool.put(String.valueOf(id), hash);
                return hash;
            }

        }
    }

    public FBSFeedback release(final int id, final String hash) {

        synchronized (lockpool) {

            if (lockpool.containsKey(String.valueOf(id))) {

                if (lockpool.get(String.valueOf(id)).equals(hash)) {
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

    private FBSFeedback checkLock(final int id, final String hash) {
        synchronized (lockpool) {

            if (lockpool.containsKey(String.valueOf(id))) {
                if (!lockpool.get(String.valueOf(id)).equals(hash)) {
                    return FBSFeedback.MISMATCHING_HASH;
                } else {
                    return FBSFeedback.SUCCESS;
                }
            } else {
                return FBSFeedback.ARTICLE_NOT_LOCKED;
            }
        }
    }

    private ArticleDTO convertToDTO(final Article article) {
        ArticleDTO articleDTO = new ArticleDTO(article.getIdArticle());
        articleDTO.setName(article.getName());
        articleDTO.setArticleNumber(article.getArticlenumber());
        articleDTO.setDescription(article.getDescription());
        articleDTO.setInStock(article.getInStock());
        articleDTO.setMinInStock(article.getMinInStock());
        articleDTO.setPrice(article.getPrice());
        articleDTO.setAvailable(article.getAvailable());

        return articleDTO;
    }

    private List<ArticleDTO> convertToDTO(final List<Article> articleList) {
        List<ArticleDTO> articleDTOList = new ArrayList<>();
        for (Article article : articleList) {
            articleDTOList.add(convertToDTO(article));
        }

        return articleDTOList;
    }

    private Article convertToEntity(final ArticleDTO articleDTO) {
        Article article = new Article();
        article.setIdArticle(articleDTO.getId());
        article.setName(articleDTO.getName());
        article.setArticlenumber(articleDTO.getArticleNumber());
        article.setDescription(articleDTO.getDescription());
        article.setInStock(articleDTO.getInStock());
        article.setMinInStock(articleDTO.getMinInStock());
        article.setPrice((int) articleDTO.getPrice());
        article.setAvailable(articleDTO.isAvailable());

        return article;
    }
}
