package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * DTO of Order Entity.
 *
 * @author Mischa Gruber
 */
public final class OrderDTO implements Serializable {

    public static final long serialVersionUID = 1L;

    private int id;
    private Timestamp date;
    private double totalPrice;
    private OrderStateDTO orderStateDTO;
    private List<OrderedArticleDTO> orderedArticleDTOList;
    private EmployeeDTO employeeDTO;
    private ClientDTO clientDTO;

    /**
     * Constructor of the OrderDTO.
     * The id has to be given, because there is no setter method
     * for the id.
     * @param id the id to set
     */
    public OrderDTO(final int id) {
        this.id = id;
    }

    /**
     * Returns the database id of the order.
     * @return id of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the date when the order was send.
     * @return date of the order
     */
    public Timestamp getDate() {
        return date;
    }

    /**
     * Sets the date when the order was send.
     * @param date date of the order to set
     */
    public void setDate(final Timestamp date) {
        this.date = date;
    }

    /**
     * Returns the total price of the order.
     * @return total price of the order
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Sets the total price of the order.
     * @param totalPrice total price to set
     */
    public void setTotalPrice(final double totalPrice) {
        this.totalPrice = totalPrice;
    }

    /**
     * Returns the OrderState object of the order.
     * @return OrderState of the order
     */
    public OrderStateDTO getOrderStateDTO() {
        return orderStateDTO;
    }

    /**
     * Sets the OrderState object of the order.
     * @param orderStateDTO the OrderState to set
     */
    public void setOrderStateDTO(final OrderStateDTO orderStateDTO) {
        this.orderStateDTO = orderStateDTO;
    }

    /**
     * Returns the List OrderedArticleDTOList containing the specific OrderArticles of the order.
     * @return list of OrderedArticles
     */
    public List<OrderedArticleDTO> getOrderedArticleDTOList() {
        return orderedArticleDTOList;
    }

    /**
     * Sets the List OrderedArticleDTOList containing the specific OrderedArticles of the order.
     * @param orderedArticleDTOList the list to set
     */
    public void setOrderedArticleDTOList(final List<OrderedArticleDTO> orderedArticleDTOList) {
        this.orderedArticleDTOList = orderedArticleDTOList;
    }

    /**
     * Returns the EmployeeDTO object of the order.
     * @return EmployeeDTO of the order
     */
    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    /**
     * Sets the EmployeeDTO object of the order.
     * @param employeeDTO EmployeeDTO to set
     */
    public void setEmployeeDTO(final EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    /**
     * Returns the ClientDTO object of the order.
     * @return ClientDTO of the order
     */
    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    /**
     * Sets the ClientDTO object of the order.
     * @param clientDTO ClientDTO to set
     */
    public void setClientDTO(final ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;

        if(obj == null || obj.getClass()!= this.getClass())
            return false;

        OrderDTO orderDTO = (OrderDTO) obj;

        return (orderDTO.getId() == this.id);
    }

    @Override
    public int hashCode() {
        return this.id;
    }
}
