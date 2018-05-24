package ch.hslu.appe.fbs.remote.exception;

import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Exception to indicate that at least one OrderedArticleDTO couldn't be saved.
 * Contains a list of OrderedArticleDTOs which couldn't be saved.
 *
 * @author Mischa Gruber
 */
public final class OrderedArticleNotUpdatedException extends Exception implements Serializable {

    private List<OrderedArticleDTO> notUpdatedOrderedArticleList;

    /**
     * Constructor which passes an empty ArrayList to the next constructor.
     */
    public OrderedArticleNotUpdatedException() {
        this(new ArrayList<>());
    }

    /**
     * Constructor which sets a describing string and sets the notUpdatedOrderedArticleList.
     * @param notUpdatedOrderedArticleList list that has to be set
     */
    public OrderedArticleNotUpdatedException(final List<OrderedArticleDTO> notUpdatedOrderedArticleList) {
        super("An ordered article couldn't get updated");
        this.notUpdatedOrderedArticleList = notUpdatedOrderedArticleList;
    }

    /**
     * Adds a single OrderedArticle to the not updated list.
     * @param orderedArticleDTO the dto that has to be added to the list
     */
    public void addOrderedArticleDTO(final OrderedArticleDTO orderedArticleDTO) {
        this.notUpdatedOrderedArticleList.add(orderedArticleDTO);
    }

    /**
     * Returns the list of OrderedArticleDTOs which couldn't be saved.
     * @return the list of not updated dtos
     */
    public List<OrderedArticleDTO> getNotUpdatedOrderedArticleList() {
        return notUpdatedOrderedArticleList;
    }
}
