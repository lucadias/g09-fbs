package ch.hslu.appe.fbs.remote.exception;

import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaDoc
 *
 * @author Mischa Gruber
 */
public class OrderedArticleNotUpdatedException extends Exception implements Serializable{

    List<OrderedArticleDTO> notUpdatedOrderedArticleList;

    public OrderedArticleNotUpdatedException() {
        this(new ArrayList<>());
    }

    public OrderedArticleNotUpdatedException(List<OrderedArticleDTO> notUpdatedOrderedArticleList) {
        super("An ordered article couldn't get updated");
        this.notUpdatedOrderedArticleList = notUpdatedOrderedArticleList;
    }

    public void addOrderedArticleDTO(OrderedArticleDTO orderedArticleDTO) {
        this.notUpdatedOrderedArticleList.add(orderedArticleDTO);
    }

    public List<OrderedArticleDTO> getNotUpdatedOrderedArticleList() {
        return notUpdatedOrderedArticleList;
    }
}
