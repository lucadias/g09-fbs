package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.ArticleManager;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.exception.LockCheckFailedException;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteArticleService;
import ch.hslu.appe.fbs.remote.SortingType;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

/**
 * Server implementation of the RemoteArticleService interface.
 *
 * @author Mischa Gruber
 */
public final class ArticleService extends UnicastRemoteObject implements RemoteArticleService {

    public static final long serialVersionUID = 1L;
    private transient ArticleManager articleManager;

    /**
     * Constructor of the ArticleService.
     * @throws RemoteException mandatory
     */
    public ArticleService() throws RemoteException {
        super();
        articleManager = ArticleManager.getInstance();
    }

    @Override
    public ArticleDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        return articleManager.getById(sessionId, id);
    }

    @Override
    public List<ArticleDTO> getByArticleNr(final String sessionId, final int artNr) throws UserNotLoggedInException {
        return articleManager.getByArticleNr(sessionId, artNr);
    }

    @Override
    public List<ArticleDTO> getList(final String sessionId) throws UserNotLoggedInException {
        return articleManager.getList(sessionId);
    }

    @Override
    public FBSFeedback updateStockById(final String sessionId, final int id, final int amount, final String hash)
            throws UserNotLoggedInException {
        return articleManager.updateStockById(sessionId, id, amount, hash);
    }

    @Override
    public List<ArticleDTO> sortList(final String sessionId, final SortingType type) throws UserNotLoggedInException {
        return articleManager.sortList(sessionId, type);
    }

    @Override
    public List<ArticleDTO> sortList(final String sessionId, final List<ArticleDTO> articleDTOs, final SortingType type)
            throws UserNotLoggedInException {
        return articleManager.sortList(sessionId, articleDTOs, type);
    }

    @Override
    public List<ArticleDTO> search(final String sessionId, final String regEx) throws UserNotLoggedInException {
        return articleManager.search(sessionId, regEx);
    }

    @Override
    public ArticleDTO save(final String sessionId, final ArticleDTO articleDTO, final String hash)
            throws UserNotLoggedInException, LockCheckFailedException {
        return articleManager.save(sessionId, articleDTO, hash);
    }

    @Override
    public ArticleDTO delete(final String sessionId, final ArticleDTO articleDTO, final String hash)
            throws UserNotLoggedInException, LockCheckFailedException {
        return articleManager.delete(sessionId, articleDTO, hash);
    }

    @Override
    public String lock(final String sessionId, final ArticleDTO articleDTO) throws UserNotLoggedInException {
        return articleManager.lock(sessionId, articleDTO.getId());
    }

    @Override
    public FBSFeedback release(final String sessionId, final ArticleDTO articleDTO, final String hash)
            throws UserNotLoggedInException {
        return articleManager.release(sessionId, articleDTO.getId(), hash);
    }
}
