package clientControllers;

import entities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ProductController {

    @FXML
    private Button btnAdd;

    @FXML
    private ImageView itemImage;

    @FXML
    private Label lblCode;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;
    
    private Product product;
    
    @FXML
    void clickOnAdd(ActionEvent event) {

    }

}
