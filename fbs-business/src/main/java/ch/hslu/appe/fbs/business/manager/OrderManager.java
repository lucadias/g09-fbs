package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.*;
import ch.hslu.appe.fbs.data.*;
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
    private OrderStatePersistor orderStatePersistor;
    private OrderedArticlePersistor orderedArticlePersistor;
    private EmployeePersistor employeePersistor;
    private ClientPersistor clientPersistor;

    // Converter
    private OrderConverter orderConverter;
    private ArticleConverter articleConverter;
    private OrderStateConverter orderStateConverter;
    private OrderedArticleConverter orderedArticleConverter;
    private EmployeeConverter employeeConverter;
    private ClientConverter clientConverter;

    private SessionManager sessionManager;

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
        this.orderStatePersistor = new OrderStatePersistor();
        this.orderedArticlePersistor = new OrderedArticlePersistor();
        this.employeePersistor = new EmployeePersistor();
        this.clientPersistor = new ClientPersistor();
        // Converters
        this.orderConverter = new OrderConverter();
        this.articleConverter = new ArticleConverter();
        this.orderStateConverter = new OrderStateConverter();
        this.orderedArticleConverter = new OrderedArticleConverter();
        this.employeeConverter = new EmployeeConverter();
        this.clientConverter = new ClientConverter();

        this.sessionManager = SessionManager.getInstance();

        try {
            this.sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public OrderDTO getById(final String sessionId, final int id) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return convertToDTO(orderPersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    private OrderDTO convertToDTO(Orders orders) {

        if (orders == null)
            return null;

        OrderState orderState = orderStatePersistor.getById(orders.getOrderStateIdOrderState());
        OrderStateDTO orderStateDTO = null;
        if (orderState != null)
            orderStateDTO = orderStateConverter.convertToDTO(orderState);

        List<OrderedArticles> orderedArticlesList = orderedArticlePersistor.getByOrderId(orders.getIdOrders());
        List<OrderedArticleDTO> orderedArticleDTOList = new ArrayList<>();
        if (orderedArticlesList != null) {
            for(OrderedArticles orderedArticles : orderedArticlesList) {
                Article article = articlePersistor.getById(orderedArticles.getArticleIdArticle());
                ArticleDTO articleDTO = null;
                if (article != null)
                    articleDTO = articleConverter.convertToDTO(article);

                OrderedArticleDTO orderedArticleDTO = orderedArticleConverter.convertToDTO(orderedArticles, articleDTO);
                orderedArticleDTOList.add(orderedArticleDTO);
            }
        }

        Employee employee = employeePersistor.getById(orders.getEmployeeIdEmployee());
        EmployeeDTO employeeDTO = null;
        if (employee != null)
            employeeDTO = employeeConverter.convertToDTO(employee);

        Client client = clientPersistor.getById(orders.getClientIdClients());
        ClientDTO clientDTO = null;
        if (client != null)
            clientDTO = clientConverter.convertToDTO(client);

        return orderConverter.convertToDTO(orders, orderStateDTO, orderedArticleDTOList, employeeDTO, clientDTO);
    }

    public List<OrderDTO> getList(final String sessionId) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return getList(sessionId, null);
        }
        throw new UserNotLoggedInException();
    }

    public List<OrderDTO> getList(final String sessionId, final String regEx) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            // TODO: Implement regEx

            List<Orders> ordersList = orderPersistor.getAll();
            List<OrderDTO> orderDTOList = new ArrayList<>();

            for (Orders order : ordersList) {
                orderDTOList.add(convertToDTO(order));
            }
            return orderDTOList;
        }
        throw new UserNotLoggedInException();
    }

    public List<OrderDTO> sortList(final String sessionId, final SortingType type) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return sortList(sessionId, getList(sessionId), type);
        }
        throw new UserNotLoggedInException();
    }

    public List<OrderDTO> sortList(final String sessionId, final List<OrderDTO> orderDTOList, final SortingType type) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
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
        throw new UserNotLoggedInException();
    }

    public FBSFeedback save(final String sessionId, final OrderDTO orderDTO, final String hash) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(orderDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS) {
                Orders order = orderConverter.convertToEntity(orderDTO);
                return orderPersistor.save(order);
            } else {
                return lockCheck;
            }
        }
        throw new UserNotLoggedInException();
    }

    public FBSFeedback delete(final String sessionId, final OrderDTO orderDTO, final String hash) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(orderDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS) {
                Orders order = orderConverter.convertToEntity(orderDTO);
                // Canceled
                order.setOrderStateIdOrderState(5);
                return orderPersistor.save(order);
            } else {
                return lockCheck;
            }
        }
        throw new UserNotLoggedInException();
    }

    //TODO: Orders delete mit orderstate = annul order annull funktion?

    //TODO: lock, release, check - as interface
    public String lock(final String sessionId, final int id) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            synchronized (lockpool) {

                // check if article is already locked
                if (lockpool.containsKey(String.valueOf(id))) {
                    return null;
                } else {
                    String toHash = String.valueOf(id) + (new Date()).toString();
                    String hash;
                    if (sha256Digest != null) {
                        byte[] hashBytes = sha256Digest.digest(toHash.getBytes(StandardCharsets.UTF_8));
                        StringBuffer hexString = new StringBuffer();
                        for (int i = 0; i < hashBytes.length; i++) {
                            String hex = Integer.toHexString(0xff & hashBytes[i]);
                            if(hex.length() == 1) hexString.append('0');
                            hexString.append(hex);
                        }
                        hash = hexString.toString();
                    } else {
                        hash = toHash;
                    }

                    lockpool.put(String.valueOf(id), hash);
                    return hash;
                }

            }
        }
        throw new UserNotLoggedInException();
    }

    public FBSFeedback release(final String sessionId, final int id, final String hash) {
        if (sessionManager.getIsLoggedIn(sessionId)) {
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
        throw new UserNotLoggedInException();
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
