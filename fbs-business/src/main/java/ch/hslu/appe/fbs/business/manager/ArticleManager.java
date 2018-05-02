package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.ArticleConverter;
import ch.hslu.appe.fbs.data.ArticlePersistor;
import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
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
import java.util.Comparator;
import java.util.Collections;
import java.util.Date;

/**
 * Manager for articles as a singleton.
 *
 * @author Mischa Gruber
 */
public final class ArticleManager {
    private static ArticleManager instance = null;

    private static Object mutex = new Object();
    private HashMap<String, String> lockpool;

    private ArticlePersistor articlePersistor;
    private MessageDigest sha256Digest;

    private ArticleConverter articleConverter;

    /**
     * Returns the singleton instance of the ArticleManager.
     * @return single instance
     */
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

    /**
     * Private constructor for the single pattern.
     */
    private ArticleManager() {

        this.articlePersistor = new ArticlePersistor();
        this.lockpool = new HashMap<>();
        this.articleConverter = new ArticleConverter();

        try {
            this.sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the article by the database id as an entity, converts it to a DTO and returns it.
     * @param id database id of the article
     * @return article as a DTO
     */
    public ArticleDTO getById(final String sessionId, final int id) {
        return articleConverter.convertToDTO(articlePersistor.getById(id));
    }

    /**
     * Gets the article by the article number as an entity, converts it to a DTO and returns it.
     * @param artNr article number of the article
     * @return article as a DTO
     */
    public ArticleDTO getByArticleNr(final String sessionId, final int artNr) {
        return articleConverter.convertToDTO(articlePersistor.getByArticleNr(artNr));
    }

    /**
     * Gets all the articles as entities, converts and returns them as a list.
     * @return articles as a DTO list
     */
    public List<ArticleDTO> getList(final String sessionId) {
        List<Article> articleList = articlePersistor.getList();
        return articleConverter.convertToDTO(articleList);
    }

    /**
     * Converts the given DTO article to an entity and passes it to the persistor.
     * @param articleDTO article to save as a DTO
     * @param hash lock hash string for the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
    public FBSFeedback save(final String sessionId, final ArticleDTO articleDTO, final String hash) {
        FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            Article article = articleConverter.convertToEntity(articleDTO);
            return articlePersistor.save(article);
        } else {
            return lockCheck;
        }
    }

    /**
     * Converts the given DTO article to an entity, sets the availability to false and passes it to the persistor.
     * @param articleDTO article to delete as a DTO
     * @param hash lock hash string for the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
    public FBSFeedback delete(final String sessionId, final ArticleDTO articleDTO, final String hash) {
        FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            Article article = articleConverter.convertToEntity(articleDTO);
            article.setAvailable(false);

            return articlePersistor.save(article);
        } else {
            return lockCheck;
        }
    }

    /**
     * Updates the amount of stock of an article by adding the given amount.
     * Amount is added and not set, so no absolute setter.
     * @param id database id of the article
     * @param amount amount that has to be added
     * @param hash lock hash string for the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
    public FBSFeedback updateStockById(final String sessionId, final int id, final int amount, final String hash) {
        FBSFeedback lockCheck = checkLock(id, hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            return articlePersistor.updateStockById(id, amount);
        } else {
            return lockCheck;
        }
    }

    public List<ArticleDTO> sortList(final String sessionId, final SortingType type) {
        return sortList(sessionId, articleConverter.convertToDTO(articlePersistor.getList()), type);
    }

    public List<ArticleDTO> sortList(final String sessionId, final List<ArticleDTO> articleDTOs, final SortingType type) {

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

    public List<ArticleDTO> search(final String sessionId, final String regEx) {
        return articleConverter.convertToDTO(articlePersistor.search(regEx));
    }

    public String lock(final String sessionId, final int id) {

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

    public FBSFeedback release(final String sessionId, final int id, final String hash) {

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
}
