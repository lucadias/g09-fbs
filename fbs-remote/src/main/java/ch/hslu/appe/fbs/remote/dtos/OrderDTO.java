package ch.hslu.appe.fbs.remote.dtos;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

/**
 * Article DTO
 *
 * @author Mischa Gruber
 */
public final class OrderDTO implements Serializable {

    private int id;
    private Timestamp date;
    private double totalPrice;
    private OrderStateDTO orderStateDTO;
    private List<OrderedArticleDTO> orderedArticleDTOList;
    private EmployeeDTO employeeDTO;
    private ClientDTO clientDTO;

    public OrderDTO(final int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(final Timestamp date) {
        this.date = date;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(final double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public OrderStateDTO getOrderStateDTO() {
        return orderStateDTO;
    }

    public void setOrderStateDTO(final OrderStateDTO orderStateDTO) {
        this.orderStateDTO = orderStateDTO;
    }

    public List<OrderedArticleDTO> getOrderedArticleDTOList() {
        return orderedArticleDTOList;
    }

    public void setOrderedArticleDTOList(final List<OrderedArticleDTO> orderedArticleDTOList) {
        this.orderedArticleDTOList = orderedArticleDTOList;
    }

    public EmployeeDTO getEmployeeDTO() {
        return employeeDTO;
    }

    public void setEmployeeDTO(final EmployeeDTO employeeDTO) {
        this.employeeDTO = employeeDTO;
    }

    public ClientDTO getClientDTO() {
        return clientDTO;
    }

    public void setClientDTO(final ClientDTO clientDTO) {
        this.clientDTO = clientDTO;
    }
}
