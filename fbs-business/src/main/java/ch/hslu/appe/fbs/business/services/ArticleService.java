package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.manager.ArticleManager;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.RemoteArticleService;
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
    public ArticleDTO getById(final String sessionId, final int id) throws RemoteException {
        return articleManager.getById(sessionId, id);
    }

    @Override
    public ArticleDTO getByArticleNr(final String sessionId, final int artNr) throws RemoteException {
        return articleManager.getByArticleNr(sessionId, artNr);
    }

    @Override
    public List<ArticleDTO> getList(final String sessionId) throws RemoteException {
        return articleManager.getList(sessionId);
    }

    @Override
    public FBSFeedback updateStockById(final String sessionId, final int id, final int amount, final String hash) throws RemoteException {
        return articleManager.updateStockById(sessionId, id, amount, hash);
    }

    @Override
    public List<ArticleDTO> sortList(final String sessionId, final SortingType type) throws RemoteException {
        return articleManager.sortList(sessionId, type);
    }

    @Override
    public List<ArticleDTO> sortList(final String sessionId, final List<ArticleDTO> articleDTOs, final SortingType type) throws RemoteException {
        return articleManager.sortList(sessionId, articleDTOs, type);
    }

    @Override
    public List<ArticleDTO> search(final String sessionId, final String regEx) throws RemoteException {
        return articleManager.search(sessionId, regEx);
    }

    @Override
    public FBSFeedback save(final String sessionId, final ArticleDTO articleDTO, final String hash) throws RemoteException {
        return articleManager.save(sessionId, articleDTO, hash);
    }

    @Override
    public FBSFeedback delete(final String sessionId, final ArticleDTO articleDTO, final String hash) throws RemoteException {
        return articleManager.delete(sessionId, articleDTO, hash);
    }

    @Override
    public String lock(final String sessionId, final ArticleDTO articleDTO) throws RemoteException {
        return articleManager.lock(sessionId, articleDTO.getId());
    }

    @Override
    public FBSFeedback release(final String sessionId, final ArticleDTO articleDTO, final String hash) throws RemoteException {
        return articleManager.release(sessionId, articleDTO.getId(), hash);
    }
}
