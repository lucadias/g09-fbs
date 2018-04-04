package ch.hslu.appe.fbs.business.services;

import ch.hslu.appe.fbs.business.ArticleManager;
import ch.hslu.appe.fbs.remote.ArticleDTO;
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
public class ArticleService extends UnicastRemoteObject implements RemoteArticleService{


    private ArticleManager articleManager;

    public ArticleService() throws RemoteException {
        super();

        articleManager = ArticleManager.getInstance();
    }

    @Override
    public ArticleDTO getById(int id) throws RemoteException {
        return articleManager.getById(id);
    }

    @Override
    public ArticleDTO getByArticleNr(int artNr) throws RemoteException {
        return articleManager.getByArticleNr(artNr);
    }

    @Override
    public List<ArticleDTO> getList() throws RemoteException {
        return articleManager.getList();
    }

    @Override
    public List<ArticleDTO> getList(String regEx) throws RemoteException {
        return articleManager.getList(regEx);
    }

    @Override
    public FBSFeedback updateStockById(int id, int amount, String hash) throws RemoteException {
        return articleManager.updateStockById(id, amount, hash);
    }

    @Override
    public List<ArticleDTO> sortList(SortingType type) throws RemoteException {
        return articleManager.sortList(type);
    }

    @Override
    public List<ArticleDTO> sortList(List<ArticleDTO> articleDTOs, SortingType type) throws RemoteException {
        return articleManager.sortList(articleDTOs, type);
    }

    @Override
    public List<ArticleDTO> search(String regEx) throws RemoteException {
        return articleManager.search(regEx);
    }

    @Override
    public FBSFeedback save(ArticleDTO articleDTO, String hash) throws RemoteException {
        return articleManager.save(articleDTO, hash);
    }

    @Override
    public FBSFeedback delete(ArticleDTO articleDTO, String hash) throws RemoteException {
        return articleManager.delete(articleDTO, hash);
    }

    @Override
    public String lock(ArticleDTO articleDTO) throws RemoteException {
        return articleManager.lock(articleDTO.getId());
    }

    @Override
    public FBSFeedback release(ArticleDTO articleDTO, String hash) throws RemoteException {
        return articleManager.release(articleDTO.getId(), hash);
    }
}
