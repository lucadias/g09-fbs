package ch.hslu.appe.fbs.business.services;

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


    protected ArticleService() throws RemoteException {
        super();
    }

    @Override
    public ArticleDTO getById(int id) {
        return null;
    }

    @Override
    public ArticleDTO getByArticleNr(int artNr) {
        return null;
    }

    @Override
    public List<ArticleDTO> getList() {
        return null;
    }

    @Override
    public List<ArticleDTO> getList(String regEx) {
        return null;
    }

    @Override
    public FBSFeedback updateStockById(int id, int amount) {
        return null;
    }

    @Override
    public List<ArticleDTO> sortList(SortingType type) {
        return null;
    }

    @Override
    public List<ArticleDTO> sortList(List<ArticleDTO> articles, SortingType type) {
        return null;
    }

    @Override
    public List<ArticleDTO> search(String regEx) {
        return null;
    }

    @Override
    public FBSFeedback save(ArticleDTO article) {
        return null;
    }

    @Override
    public FBSFeedback delete(ArticleDTO article) {
        return null;
    }

    @Override
    public String lock(ArticleDTO article) {
        return null;
    }

    @Override
    public FBSFeedback release(ArticleDTO article, String hash) {
        return null;
    }
}
