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

    private static final Object MUTEX = new Object();
    private HashMap<String, String> lockpool;

    private ArticlePersistor articlePersistor;
    private MessageDigest sha256Digest;

    private ArticleConverter articleConverter;

    private static final Logger LOGGER = LogManager.getLogger(ArticleManager.class.getName());

    private SessionManager sessionManager;

    /**
     * Returns the singleton instance of the ArticleManager.
     * @return single instance
     */
    public static ArticleManager getInstance() {
        ArticleManager result = instance;
        if (result == null) {
            synchronized (MUTEX) {
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
     * @param sessionId session id to gain access
     * @param id database id of the article
     * @return article as a DTO
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public ArticleDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return articleConverter.convertToDTO(articlePersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Gets the articles by the article number as entites, converts them to a DTOs and returns them as a list.
     * @param sessionId session id to gain access
     * @param artNr article number of the article
     * @return articles as a list
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<ArticleDTO> getByArticleNr(final String sessionId, final int artNr) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return articleConverter.convertToDTO(articlePersistor.getByArticleNr(artNr));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Gets all the articles as entities, converts and returns them as a list.
     * @param sessionId session id to gain access
     * @return articles as a DTO list
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<ArticleDTO> getList(final String sessionId) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            LOGGER.info("List all Articles from Database | Employee: " +
                    sessionManager.getEmployeeIdFromSessionId(sessionId));
            List<Article> articleList = articlePersistor.getList();
            return articleConverter.convertToDTO(articleList);
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Converts the given DTO article to an entity and passes it to the persistor.
     * @param sessionId session id to gain access
     * @param articleDTO article to save as a DTO
     * @param hash lock hash string for the article
     * @return the saved article as a DTO on success, otherwise null
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     * @throws LockCheckFailedException is thrown if the lock check has failed
     */
    public ArticleDTO save(final String sessionId, final ArticleDTO articleDTO, final String hash)
            throws UserNotLoggedInException, LockCheckFailedException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS || articleDTO.getId() == -1) {
                Article article = articleConverter.convertToEntity(articleDTO);
                LOGGER.info("Save Article with id: " + article.getIdArticle() + " to Database | Employee: "
                        + sessionManager.getEmployeeIdFromSessionId(sessionId));
                return articleConverter.convertToDTO(articlePersistor.save(article));
            } else {
                throw new LockCheckFailedException();
            }
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Converts the given DTO article to an entity, sets the availability to false and passes it to the persistor.
     * @param sessionId session id to gain access
     * @param articleDTO article to delete as a DTO
     * @param hash lock hash string for the article
     * @return the saved article as a DTO on success, otherwise null
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     * @throws LockCheckFailedException is thrown if the lock check has failed
     */
    public ArticleDTO delete(final String sessionId, final ArticleDTO articleDTO, final String hash)
            throws UserNotLoggedInException, LockCheckFailedException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(articleDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS) {
                Article article = articleConverter.convertToEntity(articleDTO);
                LOGGER.info("Deleted Article with id: " + article.getIdArticle() +
                        " | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
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
     * @param sessionId session id to gain access
     * @param id database id of the article
     * @param amount amount that has to be added
     * @param hash lock hash string for the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public FBSFeedback updateStockById(final String sessionId, final int id, final int amount, final String hash)
            throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(id, hash);

            if (lockCheck == FBSFeedback.SUCCESS) {
                LOGGER.info("Update Article Stock with id: " + id + " | New Amount:" + amount +
                        " | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
                return articlePersistor.updateStockById(id, amount);
            } else {
                return lockCheck;
            }
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Returns all articles as a sorted list.
     * @param sessionId session id to gain access
     * @param type how the list has to be sorted
     * @return sorted list of articles
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<ArticleDTO> sortList(final String sessionId, final SortingType type) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return sortList(sessionId, articleConverter.convertToDTO(articlePersistor.getList()), type);
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Sorts a given list and returns it.
     * @param sessionId session id to gain access
     * @param articleDTOs list to sort
     * @param type how the list has to be sorted
     * @return sorted list of orders
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<ArticleDTO> sortList(final String sessionId, final List<ArticleDTO> articleDTOs, final SortingType type)
            throws UserNotLoggedInException {
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

    /**
     * Returns a list of articles which are matching the search string.
     * @param sessionId session id to gain access
     * @param searchString search string for the search query
     * @return list of matching articles
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<ArticleDTO> search(final String sessionId, final String searchString) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return articleConverter.convertToDTO(articlePersistor.search(searchString));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Tries to gain the lock of an article.
     * @param sessionId session id to gain access
     * @param id database id of the article to gain the lock
     * @return lock hash string on success, null on failure
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public String lock(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            synchronized (lockpool) {

                // check if article is already locked
                if (lockpool.containsKey(String.valueOf(id))) {
                    return null;
                } else {
                    String toHash = String.valueOf(id) + (new Date()).toString();
                    String hash;
                    if (sha256Digest != null) {
                        byte[] hashBytes = sha256Digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
                        StringBuffer hexString = new StringBuffer();
                        for (byte hashByte : hashBytes) {
                            String hex = Integer.toHexString(0xff & hashByte);
                            if (hex.length() == 1) {
                                hexString.append('0');
                            }
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

    /**
     * Releases the lock of an article.
     * @param sessionId session id to gain access
     * @param id database id of the article to release the lock
     * @param hash lock hash string of the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public FBSFeedback release(final String sessionId, final int id, final String hash)
            throws UserNotLoggedInException {
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

    /**
     * Checks if the given hash equals the hash for the article.
     * @param id database id of the article to check for the lock
     * @param hash lock hash string of the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
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
