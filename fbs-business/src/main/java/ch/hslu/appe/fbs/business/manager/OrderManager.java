package ch.hslu.appe.fbs.business.manager;

import ch.hslu.appe.fbs.business.utils.*;

import ch.hslu.appe.fbs.data.OrderPersistor;
import ch.hslu.appe.fbs.data.ArticlePersistor;
import ch.hslu.appe.fbs.data.OrderStatePersistor;
import ch.hslu.appe.fbs.data.OrderedArticlePersistor;
import ch.hslu.appe.fbs.data.EmployeePersistor;
import ch.hslu.appe.fbs.data.ClientPersistor;

import ch.hslu.appe.fbs.model.entities.Orders;
import ch.hslu.appe.fbs.model.entities.OrderState;
import ch.hslu.appe.fbs.model.entities.OrderedArticles;
import ch.hslu.appe.fbs.model.entities.Article;
import ch.hslu.appe.fbs.model.entities.Employee;
import ch.hslu.appe.fbs.model.entities.Client;

import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;

import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.SortingType;
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
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

/**
 * Manager for orders as a singleton.
 *
 * @author Mischa Gruber
 */
public final class OrderManager {

    private static OrderManager instance = null;

    private static final Object MUTEX = new Object();
    private HashMap<String, String> lockpool;

    private MessageDigest sha256Digest;

    private static final Logger LOGGER = LogManager.getLogger(OrderManager.class.getName());

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

    private CentralStock centralStock;

    /**
     * Returns the singleton instance of the OrderManager.
     * @return single instance
     */
    public static OrderManager getInstance() {
        OrderManager result = instance;
        if (result == null) {
            synchronized (MUTEX) {
                result = instance;
                if (result == null) {
                    instance = result = new OrderManager();
                }
            }
        }
        return result;
    }

    /**
     * Private constructor for the single pattern.
     */
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

        this.centralStock = CentralStock.getInstance();

        try {
            this.sha256Digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns an OrderDTO object with the given id.
     * @param sessionId session id to gain access
     * @param id database id of the order
     * @return OrderDTO with the given id
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public OrderDTO getById(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return convertToDTO(orderPersistor.getById(id));
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Converts a order entity to an dto, without additional dto that have to be passed.
     * @param orders the order to be converted
     * @return the converted order
     */
    private OrderDTO convertToDTO(final Orders orders) {

        if (orders == null) {
            return null;
        }

        OrderState orderState = orderStatePersistor.getById(orders.getOrderStateIdOrderState());
        OrderStateDTO orderStateDTO = null;
        if (orderState != null) {
            orderStateDTO = orderStateConverter.convertToDTO(orderState);
        }

        List<OrderedArticles> orderedArticlesList = orderedArticlePersistor.getByOrderId(orders.getIdOrders());
        List<OrderedArticleDTO> orderedArticleDTOList = new ArrayList<>();
        if (orderedArticlesList != null) {
            for (OrderedArticles orderedArticles : orderedArticlesList) {
                Article article = articlePersistor.getById(orderedArticles.getArticleIdArticle());
                ArticleDTO articleDTO = null;
                if (article != null) {
                    articleDTO = articleConverter.convertToDTO(article);
                }

                OrderedArticleDTO orderedArticleDTO = orderedArticleConverter.convertToDTO(orderedArticles, articleDTO);
                orderedArticleDTOList.add(orderedArticleDTO);
            }
        }

        Employee employee = employeePersistor.getById(orders.getEmployeeIdEmployee());
        EmployeeDTO employeeDTO = null;
        if (employee != null) {
            employeeDTO = employeeConverter.convertToDTO(employee);
        }

        ClientDTO clientDTO = null;
        Integer clientId = orders.getClientIdClients();
        if (clientId != null) {
            Client client = clientPersistor.getById(clientId);
            if (client != null) {
                clientDTO = clientConverter.convertToDTO(client);
            }

        }

        return orderConverter.convertToDTO(orders, orderStateDTO, orderedArticleDTOList, employeeDTO, clientDTO);
    }

    /**
     * Returns all orders.
     * @param sessionId session id to gain access
     * @return list with all orders
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<OrderDTO> getList(final String sessionId) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return getList(sessionId, null);
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Returns a list of orders which are matching the search string.
     * @param sessionId session id to gain access
     * @param searchString search string for the search query
     * @return list of matching orders
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<OrderDTO> getList(final String sessionId, final String searchString) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            // TODO: Implement searchString
            List<Orders> ordersList = orderPersistor.getAll();
            List<OrderDTO> orderDTOList = new ArrayList<>();

            for (Orders order : ordersList) {
                orderDTOList.add(convertToDTO(order));
            }
            return orderDTOList;
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Returns all orders of a client by his database id.
     * @param sessionId session id to gain access
     * @param id database id of the client
     * @return list with all orders of the client
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<OrderDTO> getListByClientId(final String sessionId, final int id) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            List<Orders> ordersList = orderPersistor.getListByClientId(id);
            List<OrderDTO> orderDTOList = new ArrayList<>();
            for (Orders order : ordersList) {
                orderDTOList.add(convertToDTO(order));
            }
            return orderDTOList;
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Returns all orders as a sorted list.
     * @param sessionId session id to gain access
     * @param type how the list has to be sorted
     * @return sorted list of orders
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<OrderDTO> sortList(final String sessionId, final SortingType type) throws UserNotLoggedInException {
        if (sessionManager.getIsLoggedIn(sessionId)) {
            return sortList(sessionId, getList(sessionId), type);
        }
        throw new UserNotLoggedInException();
    }

    /**
     * Sorts a given list and returns it.
     * @param sessionId session id to gain access
     * @param orderDTOList list to sort
     * @param type how the list has to be sorted
     * @return sorted list of orders$
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public List<OrderDTO> sortList(final String sessionId, final List<OrderDTO> orderDTOList, final SortingType type)
            throws UserNotLoggedInException {
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

    /**
     * Saves the order and returns the saved order.
     * @param sessionId session id to gain access
     * @param orderDTO order to save
     * @param hash lock hash of the order
     * @return saved order dto
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     * @throws LockCheckFailedException is thrown if the lock check has failed
     * @throws OrderedArticleNotUpdatedException is thrown if any OrderedArticle couldn't get updated
     */
    public OrderDTO save(final String sessionId, final OrderDTO orderDTO, final String hash)
            throws UserNotLoggedInException, LockCheckFailedException, OrderedArticleNotUpdatedException {


        if (sessionManager.getIsLoggedIn(sessionId)) {
            FBSFeedback lockCheck = checkLock(orderDTO.getId(), hash);

            if (lockCheck == FBSFeedback.SUCCESS || orderDTO.getId() == -1) {
                /*System.out.println("sleep for 10 seconds");
                try {
                    TimeUnit.SECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sleep done");*/

                List<OrderedArticleDTO> notSavedOrderedArticleDTOs =
                        saveOrderedArticleDTOList(sessionId, orderDTO.getOrderedArticleDTOList(), orderDTO.getId());
                Orders order = orderConverter.convertToEntity(orderDTO);
                Orders savedOrder = orderPersistor.save(order);
                LOGGER.info("Updated Order with id: " + order.getIdOrders() + " | Employee: "
                        + sessionManager.getEmployeeIdFromSessionId(sessionId));
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

    /**
     * Saves a list of OrderedArticleDTOs and returns a list of dtos, which couldn't be saved.
     * @param sessionId session id to gain access
     * @param orderedArticleDTOList the list of OrderedArticle to be saved
     * @param orderId the id of the order, which contains the OrderedArticles
     * @return a list with those dtos, which couldn't be saved
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    private List<OrderedArticleDTO> saveOrderedArticleDTOList(final String sessionId,
                                                              final List<OrderedArticleDTO> orderedArticleDTOList,
                                                              final int orderId) throws UserNotLoggedInException {
        List<OrderedArticleDTO> notSavedOrderedArticles = new ArrayList<>();

        if (orderedArticleDTOList == null) {
            return notSavedOrderedArticles;
        }

        List<OrderedArticles> orderedArticlesListInDatabase = orderedArticlePersistor.getByOrderId(orderId);

        if (orderedArticleDTOList.size() > 0) {

            for (OrderedArticleDTO orderedArticleDTO : orderedArticleDTOList) {

                System.out.println("Controlling OrderedArticleDTO with ID: " + orderedArticleDTO.getId());

                int amountIncreasedBy = 0;
                boolean updateOrderedArticle = true;

                if (orderedArticleDTO.getId() == -1) {
                    // New OrderedArticleDTO
                    amountIncreasedBy = orderedArticleDTO.getAmount();
                } else {
                    // Existing OrderedArticleDTO

                    OrderedArticles savedOrderedArticles = orderedArticlePersistor.getById(orderedArticleDTO.getId());
                    ArticleDTO articleDTO = articleConverter.convertToDTO(
                            articlePersistor.getById(savedOrderedArticles.getArticleIdArticle()));
                    OrderedArticleDTO savedOrderedArticleDTO = orderedArticleConverter.convertToDTO(
                            savedOrderedArticles, articleDTO);

                    orderedArticlesListInDatabase.remove(savedOrderedArticles);

                    // check amount differences
                    if (savedOrderedArticleDTO.getAmount() != orderedArticleDTO.getAmount()) {
                        amountIncreasedBy = orderedArticleDTO.getAmount() - savedOrderedArticleDTO.getAmount();
                    }
                }

                if (amountIncreasedBy != 0) {
                    System.out.println("increased by " + amountIncreasedBy + " from " + orderedArticleDTO.getId());
                    String lockHash = articleManager.lock(sessionId, orderedArticleDTO.getArticleDTO().getId());
                    if (lockHash != null) {

                        Article article = articlePersistor.getById(orderedArticleDTO.getArticleDTO().getId());
                        if (article.getInStock() >= amountIncreasedBy) {
                            article.setInStock(article.getInStock() - amountIncreasedBy);

                            if (article.getInStock() < article.getMinInStock()) {
                                int amountOfReorder = article.getMinInStock() - article.getInStock();
                                if (centralStock.getCentralStock(article.getArticlenumber()) > amountOfReorder) {
                                    long amountOrdered = centralStock.orderItem(article.getArticlenumber(), amountOfReorder);
                                    System.out.println("Central Stock: reordered " + amountOfReorder + " amount.");
                                    //TODO: create new reorder
                                }
                            }
                            articlePersistor.save(article);
                        } else {
                            int centralInStock = centralStock.getCentralStock(article.getArticlenumber());
                            System.out.println("Central Stock: has " + centralInStock + " amount.");
                            if (centralStock.getCentralStock(article.getArticlenumber()) >= amountIncreasedBy) {
                                long amountOrdered = centralStock.orderItem(article.getArticlenumber(), amountIncreasedBy);

                                if (amountOrdered > 0) {
                                    System.out.println("Central Stock: ordered from central stock " + amountIncreasedBy
                                            + " amount.");
                                } else {
                                    System.out.println("Not enough in central stock");
                                    updateOrderedArticle = false;
                                }
                                //TODO: create new reorder
                            } else {
                                System.out.println("Not enough in central stock");
                                updateOrderedArticle = false;
                            }
                        }

                        FBSFeedback feedbackRelease =
                                articleManager.release(sessionId, orderedArticleDTO.getArticleDTO().getId(), lockHash);
                        if (feedbackRelease != FBSFeedback.SUCCESS) {
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
                    LOGGER.info("Updated orderedArticles with id: " + orderedArticleDTO.getId()
                            + " | Employee: " + sessionManager.getEmployeeIdFromSessionId(sessionId));
                } else {
                    notSavedOrderedArticles.add(orderedArticleDTO);
                }
            }
        }

        for (OrderedArticles deletedOrderedArticles : orderedArticlesListInDatabase) {
            boolean updateOrderedArticle = true;
            String lockHash = articleManager.lock(sessionId, deletedOrderedArticles.getArticleIdArticle());
            if (lockHash != null) {
                Article article = articlePersistor.getById(deletedOrderedArticles.getArticleIdArticle());
                article.setInStock(article.getInStock() + deletedOrderedArticles.getAmount());
                articlePersistor.save(article);
            } else {
                updateOrderedArticle = false;
            }

            FBSFeedback feedback =
                    articleManager.release(sessionId, deletedOrderedArticles.getArticleIdArticle(), lockHash);
            if (feedback != FBSFeedback.SUCCESS) {
                updateOrderedArticle = false;
            }


            if (updateOrderedArticle) {
                orderedArticlePersistor.delete(deletedOrderedArticles);
            } else {
                ArticleDTO articleDTO = articleConverter.convertToDTO(
                        articlePersistor.getById(deletedOrderedArticles.getArticleIdArticle()));
                notSavedOrderedArticles.add(orderedArticleConverter.convertToDTO(deletedOrderedArticles, articleDTO));
            }
        }

        return notSavedOrderedArticles;
    }

    /**
     * Deletes the order.
     * @param sessionId session id to gain access
     * @param orderDTO order to delete
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public FBSFeedback delete(final String sessionId, final OrderDTO orderDTO, final String hash)
            throws UserNotLoggedInException {
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

    /**
     * Tries to gain the lock of an order.
     * @param sessionId session id to gain access
     * @param id database id of the order to gain the lock
     * @return lock hash string on success, null on failure
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public String lock(final String sessionId, final int id) throws UserNotLoggedInException {
        //TODO: lock, release, check - as interface
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
                        for (byte hashByte : hashBytes) {
                            String hex = Integer.toHexString(0xff & hashByte);
                            if (hex.length() == 1){
                                hexString.append('0');
                            }
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

    /**
     * Releases the lock of an order.
     * @param sessionId session id to gain access
     * @param id database id of the order to release the lock
     * @param hash lock hash of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     * @throws UserNotLoggedInException is thrown if the sessionId is invalid
     */
    public FBSFeedback release(final String sessionId, final int id, final String hash)
            throws UserNotLoggedInException {
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

    /**
     * Checks if the given hash equals the hash for the order.
     * @param id database id of the order to check for the lock
     * @param hash lock hash string of the order
     * @return FBSFeedback.SUCCESS on success, otherwise a specific feedback
     */
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
