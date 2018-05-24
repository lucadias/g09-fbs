package ch.hslu.appe.fbs.remote.remoteServices;

import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.exception.LockCheckFailedException;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the article service.
 *
 * @author Mischa Gruber
 */
public interface RemoteArticleService extends Remote {

    /**
     * Returns an ArticleDTO object with the given id.
     * @param sessionId session id to gain access
     * @param id database id of the article
     * @return ArticleDTO with the given id
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    ArticleDTO getById(String sessionId, int id) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns an ArticleDTO list with the given article number.
     * @param sessionId session id to gain access
     * @param artNr article number of the articles
     * @return ArticleDTO list with the given article number
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<ArticleDTO> getByArticleNr(String sessionId, int artNr) throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all articles.
     * @param sessionId session id to gain access
     * @return list with all articles
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<ArticleDTO> getList(String sessionId) throws RemoteException, UserNotLoggedInException;

    /**
     * Adds an amount of of articles are in stock of a given article id.
     * @param sessionId session id to gain access
     * @param id database id of the article
     * @param amount amount of new articles in stock
     * @param hash hash lock of the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    FBSFeedback updateStockById(String sessionId, int id, int amount, String hash)
            throws RemoteException, UserNotLoggedInException;

    /**
     * Returns all articles as a sorted list.
     * @param sessionId session id to gain access
     * @param type how the list has to be sorted
     * @return sorted list of articles
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<ArticleDTO> sortList(String sessionId, SortingType type) throws RemoteException, UserNotLoggedInException;

    /**
     * Sorts a given list and returns it.
     * @param sessionId session id to gain access
     * @param articleDTOs list to sort
     * @param type how the list has to be sorted
     * @return sorted list of orders
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<ArticleDTO> sortList(String sessionId, List<ArticleDTO> articleDTOs, SortingType type)
            throws RemoteException, UserNotLoggedInException;

    /**
     * Returns a list of articles which are matching the search string.
     * @param sessionId session id to gain access
     * @param searchString search string for the search query
     * @return list of matching articles
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    List<ArticleDTO> search(String sessionId, String searchString) throws RemoteException, UserNotLoggedInException;

    /**
     * Saves the article.
     * @param sessionId session id to gain access
     * @param articleDTO article to save
     * @param hash lock hash of the article
     * @return saved article DTO on success, otherwise null
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     * @throws LockCheckFailedException is thrown if the lock check has failed
     */
    ArticleDTO save(String sessionId, ArticleDTO articleDTO, String hash)
            throws RemoteException, UserNotLoggedInException, LockCheckFailedException;

    /**
     * Deletes the article.
     * @param sessionId session id to gain access
     * @param articleDTO article to delete
     * @param hash lock hash of the article
     * @return saved article DTO on success, otherwise null
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     * @throws LockCheckFailedException is thrown if the lock check has failed
     */
    ArticleDTO delete(String sessionId, ArticleDTO articleDTO, String hash)
            throws RemoteException, UserNotLoggedInException, LockCheckFailedException;

    /**
     * Tries to gain the lock of an article.
     * @param sessionId session id to gain access
     * @param articleDTO article to gain the lock
     * @return lock hash string on success, null on failure
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    String lock(String sessionId, ArticleDTO articleDTO) throws RemoteException, UserNotLoggedInException;

    /**
     * Releases the lock of an article.
     * @param sessionId session id to gain access
     * @param articleDTO article to release the lock
     * @param hash lock hash string of the article
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws RemoteException mandatory
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    FBSFeedback release(String sessionId, ArticleDTO articleDTO, String hash)
            throws RemoteException, UserNotLoggedInException;
}
