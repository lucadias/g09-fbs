package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.*;
import ch.hslu.appe.fbs.data.*;
import ch.hslu.appe.fbs.model.entities.*;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
import ch.hslu.appe.fbs.remote.dtos.*;
import ch.hslu.appe.fbs.remote.exception.LockCheckFailedException;
import ch.hslu.appe.fbs.remote.exception.OrderedArticleNotUpdatedException;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.utils.OrderDateAscComparator;
import ch.hslu.appe.fbs.remote.utils.OrderDateDescComparator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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

    static final Logger logger = LogManager.getLogger(OrderManager.class.getName());


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
    private ArticleManager articleManager;

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
        this.articleManager = ArticleManager.getInstance();

        try {
            this.sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public OrderDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
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

    public List<OrderDTO> getList(final String sessionId) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return getList(sessionId, null);
        }
        throw new UserNotLoggedInException();
    }

    public List<OrderDTO> getList(final String sessionId, final String regEx) throws UserNotLoggedInException {
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

    public List<OrderDTO> sortList(final String sessionId, final SortingType type) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return sortList(sessionId, getList(sessionId), type);
        }
        throw new UserNotLoggedInException();
    }

    public List<OrderDTO> sortList(final String sessionId, final List<OrderDTO> orderDTOList, final SortingType type) throws UserNotLoggedInException {
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

    public OrderDTO save(final String sessionId, final OrderDTO orderDTO, final String hash) throws UserNotLoggedInException, LockCheckFailedException, OrderedArticleNotUpdatedException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(orderDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS || orderDTO.getId() == -1) {

                List<OrderedArticleDTO> notSavedOrderedArticleDTOs = saveOrderedArticleDTOList(sessionId, orderDTO.getOrderedArticleDTOList(), orderDTO.getId());
                Orders order = orderConverter.convertToEntity(orderDTO);
                Orders savedOrder = orderPersistor.save(order);
                logger.info("Updated Order with id: " + order.getIdOrders() + " | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
                if (notSavedOrderedArticleDTOs.size() > 0) {
                    throw new OrderedArticleNotUpdatedException(notSavedOrderedArticleDTOs);
                }

                return convertToDTO(savedOrder);
            } else {
                throw new LockCheckFailedException();
            }
        }
        throw new UserNotLoggedInException();
    }

    private List<OrderedArticleDTO> saveOrderedArticleDTOList(final String sessionId, final List<OrderedArticleDTO> orderedArticleDTOList, final int orderId) throws UserNotLoggedInException{
        List<OrderedArticleDTO> notSavedOrderedArticles = new ArrayList<>();

        if (orderedArticleDTOList == null)
            return notSavedOrderedArticles;

        List<OrderedArticles> orderedArticlesListInDatabase = orderedArticlePersistor.getByOrderId(orderId);

        if (orderedArticleDTOList.size() > 0) {

            for(OrderedArticleDTO orderedArticleDTO : orderedArticleDTOList) {

                System.out.println("Controlling OrderedArticleDTO with ID: " + orderedArticleDTO.getId());

                int amountIncreasedBy = 0;
                boolean updateOrderedArticle = true;

                if (orderedArticleDTO.getId() == -1) {
                    // New OrderedArticleDTO
                    amountIncreasedBy = orderedArticleDTO.getAmount();
                } else {
                    // Existing OrderedArticleDTO

                    OrderedArticles savedOrderedArticles = orderedArticlePersistor.getById(orderedArticleDTO.getId());
                    ArticleDTO articleDTO = articleConverter.convertToDTO(articlePersistor.getById(savedOrderedArticles.getArticleIdArticle()));
                    OrderedArticleDTO savedOrderedArticleDTO = orderedArticleConverter.convertToDTO(savedOrderedArticles, articleDTO);

                    orderedArticlesListInDatabase.remove(savedOrderedArticles);

                    // check amount differences
                    if(savedOrderedArticleDTO.getAmount() != orderedArticleDTO.getAmount()) {
                        amountIncreasedBy = orderedArticleDTO.getAmount() - savedOrderedArticleDTO.getAmount();
                    }
                }

                if (amountIncreasedBy != 0) {
                    System.out.println("increased by " + amountIncreasedBy + " from " + orderedArticleDTO.getId());
                    String lockHash = articleManager.lock(sessionId, orderedArticleDTO.getArticleDTO().getId());
                    if(lockHash != null) {

                        Article article = articlePersistor.getById(orderedArticleDTO.getArticleDTO().getId());
                        if (article.getInStock() >= amountIncreasedBy) {
                            article.setInStock(article.getInStock()-amountIncreasedBy);
                            //TODO: implement reorder if under minStock
                            articlePersistor.save(article);
                        } else {
                            //TODO: Implement getFromCentralStock
                            System.out.println("Not enough in stock");
                            updateOrderedArticle = false;
                        }

                        FBSFeedback feedbackRelease = articleManager.release(sessionId, orderedArticleDTO.getArticleDTO().getId(), lockHash);
                        if(feedbackRelease != FBSFeedback.SUCCESS) {
                            System.out.println("Release Not Success");
                            updateOrderedArticle = false;
                        }
                    } else {
                        System.out.println("Lock Hash == null");
                        updateOrderedArticle = false;
                    }
                }

                if (updateOrderedArticle) {
                    orderedArticlePersistor.save(orderedArticleConverter.convertToEntity(orderedArticleDTO, orderId));
                    logger.info("Updated orderedArticles with id: " + orderedArticleDTO.getId() + " | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
                } else {
                    notSavedOrderedArticles.add(orderedArticleDTO);
                }
            }
        }

        for(OrderedArticles deletedOrderedArticles : orderedArticlesListInDatabase) {
            boolean updateOrderedArticle = true;
            String lockHash = articleManager.lock(sessionId, deletedOrderedArticles.getArticleIdArticle());
            if (lockHash != null) {
                Article article = articlePersistor.getById(deletedOrderedArticles.getArticleIdArticle());
                article.setInStock(article.getInStock() + deletedOrderedArticles.getAmount());
                articlePersistor.save(article);
            } else {
                updateOrderedArticle = false;
            }

            FBSFeedback feedback = articleManager.release(sessionId, deletedOrderedArticles.getArticleIdArticle(), lockHash);
            if (feedback != FBSFeedback.SUCCESS)
                updateOrderedArticle = false;


            if (updateOrderedArticle) {
                orderedArticlePersistor.delete(deletedOrderedArticles);
            } else {
                ArticleDTO articleDTO = articleConverter.convertToDTO(articlePersistor.getById(deletedOrderedArticles.getArticleIdArticle()));
                notSavedOrderedArticles.add(orderedArticleConverter.convertToDTO(deletedOrderedArticles, articleDTO));
            }
        }

        return notSavedOrderedArticles;
    }

    public FBSFeedback delete(final String sessionId, final OrderDTO orderDTO, final String hash) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(orderDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS) {
                Orders order = orderConverter.convertToEntity(orderDTO);
                // Canceled
                order.setOrderStateIdOrderState(5);
                return FBSFeedback.SUCCESS;
            } else {
                return lockCheck;
            }
        }
        throw new UserNotLoggedInException();
    }

    //TODO: Orders delete mit orderstate = annul order annull funktion?

    //TODO: lock, release, check - as interface
    public String lock(final String sessionId, final int id) throws UserNotLoggedInException {
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

    public FBSFeedback release(final String sessionId, final int id, final String hash) throws UserNotLoggedInException {
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
