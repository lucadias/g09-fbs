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
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public final class ArticleService extends UnicastRemoteObject implements RemoteArticleService {


    private transient ArticleManager articleManager;

    public ArticleService() throws RemoteException {
        super();

        articleManager = ArticleManager.getInstance();
    }

    @Override
    public ArticleDTO getById(final int id) throws RemoteException {
        return articleManager.getById(id);
    }

    @Override
    public ArticleDTO getByArticleNr(final int artNr) throws RemoteException {
        return articleManager.getByArticleNr(artNr);
    }

    @Override
    public List<ArticleDTO> getList() throws RemoteException {
        return articleManager.getList();
    }

    @Override
    public FBSFeedback updateStockById(final int id, final int amount, final String hash) throws RemoteException {
        return articleManager.updateStockById(id, amount, hash);
    }

    @Override
    public List<ArticleDTO> sortList(final SortingType type) throws RemoteException {
        return articleManager.sortList(type);
    }

    @Override
    public List<ArticleDTO> sortList(final List<ArticleDTO> articleDTOs, final SortingType type) throws RemoteException {
        return articleManager.sortList(articleDTOs, type);
    }

    @Override
    public List<ArticleDTO> search(final String regEx) throws RemoteException {
        return articleManager.search(regEx);
    }

    @Override
    public FBSFeedback save(final ArticleDTO articleDTO, final String hash) throws RemoteException {
        return articleManager.save(articleDTO, hash);
    }

    @Override
    public FBSFeedback delete(final ArticleDTO articleDTO, final String hash) throws RemoteException {
        return articleManager.delete(articleDTO, hash);
    }

    @Override
    public String lock(final ArticleDTO articleDTO) throws RemoteException {
        return articleManager.lock(articleDTO.getId());
    }

    @Override
    public FBSFeedback release(final ArticleDTO articleDTO, final String hash) throws RemoteException {
        return articleManager.release(articleDTO.getId(), hash);
    }
}
