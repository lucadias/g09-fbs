package ch.hslu.appe.fbs.remote;

import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 * Remote interface for the service object
 *
 * @author Mischa Gruber
 */
public interface RemoteArticleService extends Remote {

    ArticleDTO getById(int id) throws RemoteException;

    ArticleDTO getByArticleNr(int artNr) throws RemoteException;

    List<ArticleDTO> getList() throws RemoteException;

    FBSFeedback updateStockById(int id, int amount, String hash) throws RemoteException;

    List<ArticleDTO> sortList(SortingType type) throws RemoteException;

    List<ArticleDTO> sortList(List<ArticleDTO> articleDTOs, SortingType type) throws RemoteException;

    List<ArticleDTO> search(String regEx) throws RemoteException;

    FBSFeedback save(ArticleDTO articleDTO, String hash) throws RemoteException;

    FBSFeedback delete(ArticleDTO articleDTO, String hash) throws RemoteException;

    String lock(ArticleDTO articleDTO) throws RemoteException;

    FBSFeedback release(ArticleDTO articleDTO, String hash) throws RemoteException;
}
