package ch.hslu.appe.fbs.client;

/**
 *
 * @author salzm
 */
import static ch.hslu.appe.fbs.client.JavaFXViewController.ARTICLE_SERVICE_NAME;
import static ch.hslu.appe.fbs.client.JavaFXViewController.REGISTRY_PORT;
import ch.hslu.appe.fbs.remote.ArticleDTO;
import ch.hslu.appe.fbs.remote.RemoteArticleService;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
 
public class ArticleDetailView {
    private ArticleDTO articleDTO;
    
    public ArticleDetailView () {
        try {
            final String url = "rmi://localhost:" + String.valueOf(REGISTRY_PORT) + "/" + ARTICLE_SERVICE_NAME;
            final RemoteArticleService articleService = (RemoteArticleService) Naming.lookup(url);
            ArticleDTO currentArticle = articleService.getById(1);
            this.articleDTO = currentArticle;
        } catch (NotBoundException | MalformedURLException |
            RemoteException e) {
            this.articleDTO = new ArticleDTO(Integer.MAX_VALUE);
            this.articleDTO.setDescription("Error in RMI: "+e);
            System.out.println("Error in RMI: "+e);
        }
    }
    
    public HBox addHBoxTop() {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");

        Button backButton = new Button("Zurück");
        backButton.setPrefSize(100, 20);

        Button editButton = new Button("Bearbeiten");
        editButton.setPrefSize(100, 20);
        
        Button updateStockButton = new Button("Lager updaten");
        updateStockButton.setPrefSize(100, 20);
        
        hbox.getChildren().addAll(backButton, editButton, updateStockButton);

        return hbox;
    }
    
    public GridPane addGridPaneCenter() {
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(0, 10, 0, 10));
        
        TextField articleName = new TextField();
        articleName.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        //ToDo: remove this check if is handled on lower level
        String name = this.articleDTO.getName() != null ? this.articleDTO.getName() : "";
        articleName.setText(name);
        grid.add(articleName, 1, 0);
        
        TextField articleDescription = new TextField();
        articleDescription.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        //ToDo: remove this check if is handled on lower level
        String description = this.articleDTO.getDescription()!= null ? this.articleDTO.getDescription(): "";
        articleDescription.setText(description);
        grid.add(articleDescription, 1, 1, 1, 6);
        
        Text articleNumber = new Text("Artikelnummer");
        articleNumber.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(articleNumber, 2, 1);
        
        TextField articleNumberValue = new TextField();
        articleNumberValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        articleNumberValue.setText(Integer.toString(this.articleDTO.getArticleNumber()));
        grid.add(articleNumberValue, 2, 2);
        
        Text articleStock = new Text("Lager");
        articleStock.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(articleStock, 2, 3);
        
        TextField articleStockValue = new TextField();
        articleStockValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        articleStockValue.setText(Integer.toString(this.articleDTO.getInStock()));
        grid.add(articleStockValue, 2, 4);
        
        Text articleMinStock = new Text("Min-Bestellung");
        articleMinStock.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        grid.add(articleMinStock, 2, 5);
        
        TextField articleMinStockValue = new TextField();
        articleMinStockValue.setFont(Font.font("Arial", FontWeight.NORMAL, 18));
        articleMinStockValue.setText(Integer.toString(this.articleDTO.getMinInStock()));
        grid.add(articleMinStockValue, 2, 6);
        
        return grid;
    }    
}
