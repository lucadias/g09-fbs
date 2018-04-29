package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.*;
import ch.hslu.appe.fbs.data.ArticlePersistor;
import ch.hslu.appe.fbs.data.ClientPersistor;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.data.OrderPersistor;
import ch.hslu.appe.fbs.model.entities.*;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.dtos.*;
import ch.hslu.appe.fbs.remote.utils.OrderDateAscComparator;
import ch.hslu.appe.fbs.remote.utils.OrderDateDescComparator;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * OrderManager
 *
 * @author Mischa Gruber
 */
public final class OrderManager {

    private static OrderManager instance = null;

    private static Object mutex = new Object();
    private HashMap<String, String> lockpool;

    private MessageDigest sha256Digest;

    // Persistors
    private OrderPersistor orderPersistor;
    private ArticlePersistor articlePersistor;
    //private OrderStatePersistor orderStatePersistor;
    //private OrderedArticlePersistor orderedArticlePersistor;
    private EmployeePersistor employeePersistor;
    private ClientPersistor clientPersistor;

    // Converter
    private OrderConverter orderConverter;
    private ArticleConverter articleConverter;
    private OrderStateConverter orderStateConverter;
    private OrderedArticleConverter orderedArticleConverter;
    private EmployeeConverter employeeConverter;
    private ClientConverter clientConverter;

    public static OrderManager getInstance() {
        OrderManager result = instance;
        if (result == null) {
            synchronized (mutex) {
                result = instance;
                if (result == null) {
                    instance = result = new OrderManager();
                }
            }
        }
        return result;
    }

    private OrderManager() {

        this.lockpool = new HashMap<>();
        // Persistors
        this.orderPersistor = new OrderPersistor();
        this.articlePersistor = new ArticlePersistor();
        this.employeePersistor = new EmployeePersistor();
        this.clientPersistor = new ClientPersistor();
        // Converters
        this.orderConverter = new OrderConverter();
        this.articlePersistor = new ArticlePersistor();
        this.orderStateConverter = new OrderStateConverter();
        this.orderedArticleConverter = new OrderedArticleConverter();
        this.employeeConverter = new EmployeeConverter();
        this.clientConverter = new ClientConverter();

        try {
            this.sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public OrderDTO getById(final int id) {
        return convertToDTO(orderPersistor.getById(id));
    }

    private OrderDTO convertToDTO(Orders orders) {

        //==============================================
        // TODO: Remove Dummies
        // OrderState
        OrderStateDTO orderStateDTO = new OrderStateDTO(1);
        orderStateDTO.setState("TestState");
        // List<OrderedArticle> with Article
        List<OrderedArticleDTO> orderedArticleDTOList = new ArrayList<>();
        OrderedArticleDTO orderedArticleDTO = new OrderedArticleDTO(1);
        orderedArticleDTO.setAmount(10);
        orderedArticleDTO.setTotalPrice(35.50);
        Article article = articlePersistor.getById(1);
        orderedArticleDTO.setArticleDTO(articleConverter.convertToDTO(article));
        orderedArticleDTOList.add(orderedArticleDTO);
        //==============================================

        Employee employee = employeePersistor.getById(orders.getEmployeeIdEmployee());
        Client client = clientPersistor.getById(orders.getClientIdClients());

        return orderConverter.convertToDTO(orders, orderStateDTO, orderedArticleDTOList, employeeConverter.convertToDTO(employee), clientConverter.convertToDTO(client));
    }

    public List<OrderDTO> getList() {
        return getList(null);
    }

    public List<OrderDTO> getList(final String regEx) {
        // TODO: Implement regEx

        List<Orders> ordersList = orderPersistor.getAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();

        for (Orders order : ordersList) {
            orderDTOList.add(convertToDTO(order));
        }
        return orderDTOList;
    }

    public List<OrderDTO> sortList(final SortingType type) {
        return sortList(getList(), type);
    }

    public List<OrderDTO> sortList(final List<OrderDTO> orderDTOList, final SortingType type) {

        Comparator<OrderDTO> comparator;

        switch (type) {
            case ORDER_DATE_ASC:
                comparator = new OrderDateAscComparator();
                break;
            case ORDER_DATE_DESC:
                comparator = new OrderDateDescComparator();
                break;
            default:
                comparator = new OrderDateAscComparator();
                break;
        }

        Collections.sort(orderDTOList, comparator);

        return orderDTOList;
    }

    public FBSFeedback save(final OrderDTO orderDTO, final String hash) {
        FBSFeedback lockCheck = checkLock(orderDTO.getId(), hash);

        if (lockCheck == FBSFeedback.SUCCESS) {
            Orders order = orderConverter.convertToEntity(orderDTO);
            return orderPersistor.save(order);
        } else {
            return lockCheck;
        }
    }




    //TODO: lock, release, check - as interface
    public String lock(final int id) {

        synchronized (lockpool) {

            // check if article is already locked
            if (lockpool.containsKey(String.valueOf(id))) {
                return null;
            } else {
                String toHash = String.valueOf(id) + (new Date()).toString();
                String hash;
                if (sha256Digest != null) {
                    byte[] hashBytes = sha256Digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
                    hash = String.valueOf(hashBytes);
                } else {
                    hash = toHash;
                }

                lockpool.put(String.valueOf(id), hash);
                return hash;
            }

        }
    }

    public FBSFeedback release(final int id, final String hash) {

        synchronized (lockpool) {

            if (lockpool.containsKey(String.valueOf(id))) {

                if (lockpool.get(String.valueOf(id)).equals(hash)) {
                    lockpool.remove(String.valueOf(id), hash);
                    return FBSFeedback.SUCCESS;
                } else {
                    return FBSFeedback.MISMATCHING_HASH;
                }

            } else {
                return FBSFeedback.LOCK_LOST;
            }

        }
    }

    private FBSFeedback checkLock(final int id, final String hash) {
        synchronized (lockpool) {

            if (lockpool.containsKey(String.valueOf(id))) {
                if (!lockpool.get(String.valueOf(id)).equals(hash)) {
                    return FBSFeedback.MISMATCHING_HASH;
                } else {
                    return FBSFeedback.SUCCESS;
                }
            } else {
                return FBSFeedback.ORDER_NOT_LOCKED;
            }
        }
    }
}
