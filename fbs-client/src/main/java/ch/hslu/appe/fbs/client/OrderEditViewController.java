package ch.hslu.appe.fbs.client;

import static ch.hslu.appe.fbs.client.Client.REGISTRY_PORT;
import static ch.hslu.appe.fbs.client.Client.SESSION;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ARTICLE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.CLIENT_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.EMPLOYEE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDERSTATE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.ORDER_SERVICE_NAME;
import ch.hslu.appe.fbs.remote.FBSFeedback;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteArticleService;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderService;
import ch.hslu.appe.fbs.remote.dtos.ArticleDTO;
import ch.hslu.appe.fbs.remote.dtos.ClientDTO;
import ch.hslu.appe.fbs.remote.dtos.EmployeeDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderStateDTO;
import ch.hslu.appe.fbs.remote.dtos.OrderedArticleDTO;
import ch.hslu.appe.fbs.remote.exception.LockCheckFailedException;
import ch.hslu.appe.fbs.remote.exception.OrderedArticleNotUpdatedException;
import ch.hslu.appe.fbs.remote.exception.UserNotLoggedInException;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteClientService;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteEmployeeService;
import ch.hslu.appe.fbs.remote.remoteServices.RemoteOrderStateService;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 * This class is responsible for the orderEditView and defines the methods for that view
 * @author joel salzmann
 */
public class OrderEditViewController implements Initializable {
    private RemoteOrderService orderService = null;
    private RemoteArticleService articleService = null;
    private RemoteEmployeeService employeeService = null;
    private RemoteOrderStateService orderStateService = null;
    private RemoteClientService clientService = null;
    private List<OrderStateDTO> orderStateList;
    private List<ClientDTO> clientList;
    private List<EmployeeDTO> employeeList;
    private List<OrderedArticleDTO> orderedArticleList;
    private List<ArticleDTO> allArticleList;
    private List<ArticleDTO> searchArticleList;
    private ObservableList<OrderStateDTO> stateObservableList;
    private ObservableList<ClientDTO> clientObservableList;
    private ObservableList<EmployeeDTO> employeeObservableList;
    private OrderDTO orderDTO;
    private int orderId;
    private String hash;
    
    @FXML
    private Label orderNumber;
    
    @FXML
    private Label orderDate;
    
    @FXML
    private ChoiceBox employeeChoice;
    
    @FXML
    private ChoiceBox clientChoice;
    
    @FXML
    private ChoiceBox stateChoice;
    
    @FXML
    private GridPane orderedArticleGrid;
    
    @FXML
    private GridPane allArticleGrid;
    
    @FXML
    private GridPane searchArticleGrid;

    @FXML
    private TextField searchInput;
    
    /**
     * This method is called when the GUI-Button "zurück" is pressed.
     * This method navigates to the Detail or the ListView depending if its
     * a new or an existing order.
     * @param event An ActionEvent given by JavaFx
     */
    @FXML
    public void back(ActionEvent event) {
        if(this.orderId != -1) {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/OrderDetailView.fxml"));
                Parent orderDetail = (Parent) loader.load();
                OrderDetailViewController orderDetailViewController = (OrderDetailViewController) loader.getController();
                orderDetailViewController.setId(this.orderId);
                JavaFXViewController.getInstance().setView(orderDetail);
                JavaFXViewController.getInstance().repaint();
            } catch (IOException e) {
                System.out.println("Error loading fxml: ");
                e.printStackTrace();
            }
        } else {
            try {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/fxml/OrderListView.fxml"));
                Parent orders = (Parent) loader.load();
                OrderListViewController orderListViewController = (OrderListViewController) loader.getController();
                JavaFXViewController.getInstance().setView(orders);
                JavaFXViewController.getInstance().repaint();
            } catch (IOException e) {
                System.out.println("Error loading fxml: ");
                e.printStackTrace();
            }
        }
    }
    
    /**
     * This method is not Clean Code compatibel xD
     * This method is called when the GUI-Button "speichern" is pressed.
     * It takes the choices(state, employee, client) from the GUI, sets them in the orderDTO
     * and saves the DTO. Then it navigates to the DetailView
     * @param event An ActionEvent given by JavaFx
     */
    @FXML
    public void save(ActionEvent event) {
        OrderStateDTO state = (OrderStateDTO)this.stateChoice.getValue();
        this.orderDTO.setOrderStateDTO(state);
        EmployeeDTO employee = (EmployeeDTO)this.employeeChoice.getValue();
        this.orderDTO.setEmployeeDTO(employee);
        ClientDTO client = (ClientDTO)this.clientChoice.getValue();
        this.orderDTO.setClientDTO(client);
        this.saveDTO();
        try {  
            FBSFeedback feedback = this.orderService.release(SESSION, this.orderDTO, hash);
        } catch (RemoteException e) {
            System.out.println("Error in RMI: ");
            e.printStackTrace();
        } catch (UserNotLoggedInException ex) {
            System.out.println("User is not logged in");
        }
        this.hash = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderDetailView.fxml"));
            Parent orderDetail = (Parent) loader.load();
            OrderDetailViewController orderDetailViewController = (OrderDetailViewController) loader.getController();
            orderDetailViewController.setId(this.orderId);
            JavaFXViewController.getInstance().setView(orderDetail);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }
    
    @FXML
    public void delete(ActionEvent event) {
        String hash = "";
        try {
            hash = this.getHash();
            FBSFeedback feedback = this.orderService.delete(SESSION, this.orderDTO, hash);
            System.out.println(feedback);
        } catch(RemoteException e) {
            System.out.println("Error in RMI:");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        } finally {
            try {
                FBSFeedback feedback = this.orderService.release(SESSION, this.orderDTO, hash);
                this.hash = null;
            } catch(RemoteException e) {
                System.out.println("Error in RMI:");
                e.printStackTrace();
            } catch(UserNotLoggedInException e) {
                System.out.println("User is not logged in");
            }
        }
    }
    
    /**
     * This method is called when the GUI-Button "suchen" in the "Artikel suchen" tab is pressed
     * It calls the search method of the articleService and then print a list of them
     * @param event An ActionEvent given by JavaFx
     */
    @FXML
    public void searchArticle(ActionEvent event) {
        try {
            String searchString = this.searchInput.getText();
            this.searchArticleList = this.articleService.search(SESSION, searchString);
            this.fillSearchedArticles();
        } catch(RemoteException e) {
            System.out.println("Error in search: ");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
    }
    
    /**
     * Initializes the controller class.
     * This method is called, when the OrderEditView Fxml file is loaded.
     * This method initializes the needed services
     * - orderService
     * - articleService
     * - employeeService
     * - orderStateService
     * - clientService
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            final String urlStringOrder = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDER_SERVICE_NAME;
            this.orderService = (RemoteOrderService) Naming.lookup(urlStringOrder);
            final String urlStringArticle = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            this.articleService = (RemoteArticleService) Naming.lookup(urlStringArticle);
            final String urlStringEmployee = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + EMPLOYEE_SERVICE_NAME;
            this.employeeService = (RemoteEmployeeService) Naming.lookup(urlStringEmployee);
            final String urlStringOrderState = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ORDERSTATE_SERVICE_NAME;
            this.orderStateService = (RemoteOrderStateService) Naming.lookup(urlStringOrderState);
            final String urlStringClient = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + CLIENT_SERVICE_NAME;
            this.clientService = (RemoteClientService) Naming.lookup(urlStringClient);
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: ");
            e.printStackTrace();
        }
    }
    /**
     * This method is used to set the orderDTO given by id.
     * Then it fills the ui components with the orderDTO's values
     * @param id the id of the orderDTO
     */
    public boolean setId(int id) {
        this.orderId = id;
        try {
            if(this.orderId != -1) {
                OrderDTO currentOrder = orderService.getById(SESSION, this.orderId);
                this.orderDTO = currentOrder;
                this.hash = this.getHash();
                 if(this.hash == null) {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        loader.setLocation(getClass().getResource("/fxml/OrderDetailView.fxml"));
                        Parent orderDetail = (Parent) loader.load();
                        OrderDetailViewController orderDetailViewController = (OrderDetailViewController) loader.getController();
                        orderDetailViewController.setId(this.orderId);
                        JavaFXViewController.getInstance().setView(orderDetail);
                        JavaFXViewController.getInstance().repaint();
                    } catch (IOException e) {
                        System.out.println("Error loading fxml: ");
                        e.printStackTrace();
                    } finally {
                        return false;
                    }
                }
                String date = String.valueOf(this.orderDTO.getDate());
                this.orderDate.setText(date);
                String number = String.valueOf(this.orderDTO.getId());
                this.orderNumber.setText(number);
                //ToDo: Check Nullpointers, maybe work with optionals
                this.orderedArticleList = this.orderDTO.getOrderedArticleDTOList();
            } else {
                this.orderDTO = new OrderDTO(-1);
                this.orderedArticleList = new ArrayList<>();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                this.orderDTO.setDate(timestamp);
                EmployeeDTO employeeDTO = this.employeeService.getByUsername(SESSION, Client.username);
                this.orderDTO.setEmployeeDTO(employeeDTO);
                OrderStateDTO orderState = new OrderStateDTO(1);
                this.orderDTO.setOrderStateDTO(orderState);
                if(employeeDTO == null || orderState == null) {
                    System.out.println("something was null");
                }
                this.saveDTO();
            }
            try {  
                FBSFeedback feedback = this.orderService.release(SESSION, this.orderDTO, hash);
            } catch (RemoteException e) {
                System.out.println("Error in RMI: ");
                e.printStackTrace();
            } catch (UserNotLoggedInException ex) {
                System.out.println("User is not logged in");
            }
            this.hash = null;
            this.fillStateChoice();
            this.fillClientChoice();
            this.fillEmployeeChoice();
            this.fillOrderedArticles();
            this.fillAllArticles();
        } catch(RemoteException e) {
            this.orderDTO = new OrderDTO(Integer.MAX_VALUE);
            System.out.println("Error in RMI: ");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
        return true;
    }
    
    /**
     * This method fills the ui component StateChoice with the possible values
     */
    private void fillStateChoice() {
        try {
            this.orderStateList = this.orderStateService.getList(SESSION);
            this.stateObservableList = FXCollections.observableArrayList(this.orderStateList);
            this.stateChoice.setItems(this.stateObservableList);
            this.selectStateChoice();
        } catch(RemoteException e) {
            System.out.println("Error in getting orderStates:");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
    }
    
    /**
     * This method sets the stateChoice depending on the orderDTO
     */
    private void selectStateChoice() {
        if(this.stateObservableList.contains(this.orderDTO.getOrderStateDTO())) {
            this.stateChoice.setValue(this.orderDTO.getOrderStateDTO());
        }
    }
    
    /**
     * This method fills the ui component ClientChoice with the possible values
     */
    private void fillClientChoice() {
        try {
            this.clientList = this.clientService.getList(SESSION);
            this.clientObservableList = FXCollections.observableArrayList(this.clientList);
            this.clientChoice.setItems(this.clientObservableList);
            this.selectClientChoice();
        } catch(RemoteException e) {
            System.out.println("Error in getting orderStates:");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
    }
    
    /**
     * This method sets the clientChoice depending on the orderDTO
     */
    private void selectClientChoice() {
        if(clientList.contains(this.orderDTO.getClientDTO())) {
            this.clientChoice.setValue(this.orderDTO.getClientDTO());
        }
    }
    
    /**
     * This method fills the ui component EmployeeChoice with the possible values
     */
    private void fillEmployeeChoice() {
        try {
            this.employeeList = this.employeeService.getList(SESSION);
            this.employeeObservableList = FXCollections.observableArrayList(this.employeeList);
            this.employeeChoice.setItems(this.employeeObservableList);
            this.selectEmployeeChoice();
        } catch(RemoteException e) {
            System.out.println("Error in getting orderStates:");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
    }
    
    /**
     * This method sets the employeeChoice depending on the orderDTO
     */
    private void selectEmployeeChoice() {
        if(this.employeeObservableList.contains(this.orderDTO.getEmployeeDTO())) {
            this.employeeChoice.setValue(this.orderDTO.getEmployeeDTO());
        }
    }
    
    /**
     * This method fills the grid of orderedArticles depending on the orderedArticleList of the orderDTO
     */
    private void fillOrderedArticles() {
        int i = 1;
        for(OrderedArticleDTO orderedArticle:this.orderedArticleList) {
            ArticleDTO article = orderedArticle.getArticleDTO();
            Text articleName = new Text(article.getName());
            int amount = orderedArticle.getAmount();
            Text articleAmount = new Text(String.valueOf(amount));
            double price = article.getPrice();
            Text articlePrice = new Text(String.valueOf(price));
            Button removeFromOrderButton = new Button();
            removeFromOrderButton.setText("entfernen");
            removeFromOrderButton.setPrefWidth(100);
            removeFromOrderButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    removeFromOrder(orderedArticle);
                }
            });
            this.orderedArticleGrid.add(articleName, 0, i);
            this.orderedArticleGrid.add(articleAmount, 1, i);
            this.orderedArticleGrid.add(articlePrice, 2, i);
            this.orderedArticleGrid.add(removeFromOrderButton, 3, i);
            i++;
            System.out.println("article in order:"+article.getName());
        }
    }
    
    /**
     * This method fills the grid, where all available articles are listet
     */
    private void fillAllArticles() {
        int i = 1;
        try {
            this.allArticleList = this.articleService.getList(SESSION);
            for(ArticleDTO article:this.allArticleList) {
                Text articleName = new Text(article.getName());
                TextField articleAmount = new TextField();
                double price = article.getPrice();
                Text articlePrice = new Text(String.valueOf(price));
                Button addToOrderButton = new Button();
                addToOrderButton.setText("hinzufügen");
                addToOrderButton.setPrefWidth(100);
                addToOrderButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override public void handle(ActionEvent e) {
                        int amount = Integer.valueOf(articleAmount.getText());
                        addToOrder(article, amount);
                    }
                });
                this.allArticleGrid.add(articleName, 0, i);
                this.allArticleGrid.add(articleAmount, 1, i);
                this.allArticleGrid.add(articlePrice, 2, i);
                this.allArticleGrid.add(addToOrderButton, 3, i);
                i++;
            }
        } catch(RemoteException e) {
            System.out.println("Error in RMI:");
            e.printStackTrace();
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        }
    }
    
    /**
     * This method fills the grid of searched articles depending on the searchArticleList
     */
    private void fillSearchedArticles() {
        int i = 1;
        for(ArticleDTO article:this.searchArticleList) {
            Text articleName = new Text(article.getName());
            System.out.println("found: "+articleName);
            TextField articleAmount = new TextField();
            double price = article.getPrice();
            Text articlePrice = new Text(String.valueOf(price));
            Button addToOrderButton = new Button();
            addToOrderButton.setText("hinzufügen");
            addToOrderButton.setPrefWidth(100);
            addToOrderButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    int amount = Integer.valueOf(articleAmount.getText());
                    addToOrder(article, amount);
                }
            });
            this.searchArticleGrid.add(articleName, 0, i);
            this.searchArticleGrid.add(articleAmount, 1, i);
            this.searchArticleGrid.add(articlePrice, 2, i);
            this.searchArticleGrid.add(addToOrderButton, 3, i);
            i++;
        }
    }
    
    /**
     * This method adds an articleDTO to the order
     * @param article An articleDTO which should be added to the order
     * @param amount The amount of the articleDTO which should be added to the order
     */
    private void addToOrder(ArticleDTO article, int amount) {
        OrderedArticleDTO orderedArticle = new OrderedArticleDTO(-1);
        orderedArticle.setAmount(amount);
        orderedArticle.setArticleDTO(article);
        this.orderedArticleList.add(orderedArticle);
        this.orderDTO.setOrderedArticleDTOList(this.orderedArticleList);
        this.saveDTO();
        try {  
            FBSFeedback feedback = this.orderService.release(SESSION, this.orderDTO, hash);
            this.hash = null;
        } catch (RemoteException e) {
            System.out.println("Error in RMI: ");
            e.printStackTrace();
        } catch (UserNotLoggedInException ex) {
            System.out.println("User is not logged in");
        }
        this.refresh();
    }
    
    /**
     * This method removes an articleDTO from an order
     * @param article An articleDTO which should be removed from the order
     */
    private void removeFromOrder(OrderedArticleDTO article) {
        this.orderedArticleList.remove(article);
        this.orderDTO.setOrderedArticleDTOList(this.orderedArticleList);
        this.saveDTO();
        try {  
            FBSFeedback feedback = this.orderService.release(SESSION, this.orderDTO, hash);
            this.hash = null;
        } catch (RemoteException e) {
            System.out.println("Error in RMI: ");
            e.printStackTrace();
        } catch (UserNotLoggedInException ex) {
            System.out.println("User is not logged in");
        }
        this.refresh();
    }
    
    /**
     * This method saves the orderDTO
     */
    private void saveDTO() {
        String hash = "";
        try {
            if(this.orderId != -1) {
                hash = this.getHash();
                this.orderDTO = this.orderService.save(SESSION, this.orderDTO, hash);
            } else {
                this.orderDTO = this.orderService.save(SESSION, this.orderDTO, "");
                this.orderId = this.orderDTO.getId();
                System.out.println("saved existing DTO: "+String.valueOf(this.orderDTO.getId()));
            }
        } catch(RemoteException e){
            System.out.println("Error in RMI:");
            e.printStackTrace();
        } catch(LockCheckFailedException e){
            System.out.println("Failed to get Access to order");
        } catch(UserNotLoggedInException e) {
            System.out.println("User is not logged in");
        } catch(OrderedArticleNotUpdatedException e) {
            System.out.println("Article not updated");
            System.out.println(e.getNotUpdatedOrderedArticleList());
        } 
    }
    
    /**
     * This method reloads the orderEditView depending on the orderId
     */
    private void refresh() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/fxml/OrderEditView.fxml"));
            Parent orderEdit = (Parent) loader.load();
            OrderEditViewController orderEditViewController = (OrderEditViewController) loader.getController();
            orderEditViewController.setId(this.orderId);
            JavaFXViewController.getInstance().setView(orderEdit);
            JavaFXViewController.getInstance().repaint();
        } catch (IOException e) {
            System.out.println("Error loading fxml: ");
            e.printStackTrace();
        }
    }
    
    private String getHash() throws RemoteException, UserNotLoggedInException {
        if(this.hash == null) {
            System.out.println(this.orderDTO);
            String newHash = this.orderService.lock(SESSION, this.orderDTO);
            this.hash = newHash;
            return newHash;
        } else {
            return this.hash;
        }
    }
}
