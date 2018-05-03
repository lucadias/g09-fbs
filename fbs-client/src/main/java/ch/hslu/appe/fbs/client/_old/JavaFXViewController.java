package ch.hslu.appe.fbs.client._old;

import java.util.ArrayList;
import javafx.scene.Node;

/**
 * JavaDoc
 */
public final class JavaFXViewController {
    public static final String ARTICLE_SERVICE_NAME = "ArticleService";
    public static final String LOGIN_SERVICE_NAME = "LoginService";
    public static final String ORDER_SERVICE_NAME = "OrderService";
    private FBSView currentView;
//    private ArrayList<RepaintRequestListener> repaintRequestListeners;
    

    public JavaFXViewController() {
        
    }
    
    public void start() {
//        //create loginView
//        //ToDo: Implement logged in check
//        LoginView loginView = new LoginView();
////        loginView.addLoginButtonListener(this);
//        this.setView(loginView);
        
//        ArticleDetailView adv = new ArticleDetailView();
//        this.setView(adv);
        
//        ArticleEditView aev = new ArticleEditView();
//        this.setView(aev);
        
        OrderDetailView odv = new OrderDetailView();
        this.setView(odv);

//        ArticleListView alv = new ArticleListView();
//        this.setView(alv);
    }
    
//    public void addRepaintRequestListener(RepaintRequestListener listener) {
//        this.repaintRequestListeners.add(listener);
//    }
    
    public void setView(FBSView view) {
        this.currentView = view;
    }
    
    public Node getTopPane() {
        return null;
    }
    
    public Node getCenterPane() {
        return this.currentView.getCenterPane();
    }
    
    public Node getRightPane() {
        
        return null;
    }
    
    public Node getLeftPane() {
        
        return null;
    }
    
    public Node getBottomPane() {
        return this.currentView.getBottomPane();
    }

}