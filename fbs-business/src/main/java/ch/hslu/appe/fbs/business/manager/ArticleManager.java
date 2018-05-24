package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.ArticleConverter;
import ch.hslu.appe.fbs.remote.exception.LockCheckFailedException;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.data.ArticlePersistor;
import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.utils.ArticleNameAscComparator;
import ch.hslu.appe.fbs.remote.utils.ArticleNameDescComparator;
import ch.hslu.appe.fbs.remote.utils.ArticlePriceAscComparator;
import ch.hslu.appe.fbs.remote.utils.ArticlePriceDescComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.rmi.RemoteException;
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

    static final Logger logger = LogManager.getLogger(ArticleManager.class.getName());

    private SessionManager sessionManager;


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
        this.sessionManager = SessionManager.getInstance();

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
    public ArticleDTO getById(final String sessionId, final int id) throws UserNotLoggedInException{
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return articleConverter.convertToDTO(articlePersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Gets the articles by the article number as entites, converts them to a DTOs and returns them as a list.
     * @param artNr article number of the article
     * @return articles as a list
     */
    public List<ArticleDTO> getByArticleNr(final String sessionId, final int artNr) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return articleConverter.convertToDTO(articlePersistor.getByArticleNr(artNr));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Gets all the articles as entities, converts and returns them as a list.
     * @return articles as a DTO list
     */
    public List<ArticleDTO> getList(final String sessionId) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            logger.info("List all Articles from Database | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
            List<Article> articleList = articlePersistor.getList();
            return articleConverter.convertToDTO(articleList);
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Converts the given DTO article to an entity and passes it to the persistor.
     * @param articleDTO article to save as a DTO
     * @param hash lock hash string for the article
     * @return the saved article as a DTO on success, otherwise null
     */
    public ArticleDTO save(final String sessionId, final ArticleDTO articleDTO, final String hash) throws UserNotLoggedInException, LockCheckFailedException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS || articleDTO.getId() == -1) {
                Article article = articleConverter.convertToEntity(articleDTO);
                logger.info("Save Article with id: " +article.getIdArticle()+ " to Database | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
                return articleConverter.convertToDTO(articlePersistor.save(article));
            } else {
                throw new LockCheckFailedException();
            }
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Converts the given DTO article to an entity, sets the availability to false and passes it to the persistor.
     * @param articleDTO article to delete as a DTO
     * @param hash lock hash string for the article
     * @return the saved article as a DTO on success, otherwise null
     */
    public ArticleDTO delete(final String sessionId, final ArticleDTO articleDTO, final String hash) throws UserNotLoggedInException, LockCheckFailedException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS) {
                Article article = articleConverter.convertToEntity(articleDTO);
                logger.info("Deleted Article with id: " +article.getIdArticle()+ " | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
                article.setAvailable(false);

                return articleConverter.convertToDTO(articlePersistor.save(article));
            } else {
                throw new LockCheckFailedException();
            }
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Updates the amount of stock of an article by adding the given amount.
     * Amount is added and not set, so no absolute setter.
     * @param id database id of the article
     * @param amount amount that has to be added
     * @param hash lock hash string for the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
    public FBSFeedback updateStockById(final String sessionId, final int id, final int amount, final String hash) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(id, hash);

            if (lockCheck == FBSFeedback.SUCCESS) {
                logger.info("Update Article Stock with id: " +id+ " | New Amount:" +amount+" | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
                return articlePersistor.updateStockById(id, amount);
            } else {
                return lockCheck;
            }
        }
        throw new UserNotLoggedInException();
    }

    public List<ArticleDTO> sortList(final String sessionId, final SortingType type) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return sortList(sessionId, articleConverter.convertToDTO(articlePersistor.getList()), type);
        }
        throw new UserNotLoggedInException();
    }

    public List<ArticleDTO> sortList(final String sessionId, final List<ArticleDTO> articleDTOs, final SortingType type) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
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
        throw new UserNotLoggedInException();
    }

    public List<ArticleDTO> search(final String sessionId, final String regEx) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return articleConverter.convertToDTO(articlePersistor.search(regEx));
        }
        throw new UserNotLoggedInException();
    }

    public String lock(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            synchronized (lockpool) {

                // check if article is already locked
                if (lockpool.containsKey(String.valueOf(id))) {
                    return null;
                } else {
                    String toHash = String.valueOf(id) + (new Date()).toString();
                    String hash = "";
                    if (sha256Digest != null) {
                        byte[] hashBytes = sha256Digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
                        StringBuffer hexString = new StringBuffer();
                        for (int i = 0; i < hashBytes.length; i++) {
                            String hex = Integer.toHexString(0xff & hashBytes[i]);
                            if(hex.length() == 1) hexString.append('0');
                            hexString.append(hex);
                        }
                        hash = hexString.toString();
                    } else {
                        hash = toHash;
                    }

                    lockpool.put(String.valueOf(id), hash);
                    return hash;
                }

            }
        }
        throw new UserNotLoggedInException();
    }

    public FBSFeedback release(final String sessionId, final int id, final String hash) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            synchronized (lockpool) {

                if (lockpool.containsKey(String.valueOf(id))) {

                    System.out.println("lockpool hash: " + lockpool.get(String.valueOf(id)));
                    System.out.println("passed hash: " + hash);
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
        throw new UserNotLoggedInException();
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
