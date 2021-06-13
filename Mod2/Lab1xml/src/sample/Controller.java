package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    @FXML
    private TreeView treeView;
    @FXML
    private Button addCategory;
    @FXML
    private Button addItem;
    @FXML
    private Button updateCategory;
    @FXML
    private Button updateItem;
    @FXML
    private Button deleteCategory;
    @FXML
    private Button deleteItem;
    @FXML
    private Button save;
    @FXML
    private TextField codeCategory;
    @FXML
    private TextField nameCategory;
    @FXML
    private TextField id;
    @FXML
    private TextField headline;
    @FXML
    private TextField author;
    @FXML
    private Label information;

    XmlWorker xmlWorker;
    @FXML
    public void initialize() {
        try {
            xmlWorker = new XmlWorker();
            printToTreeView();

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }
    public void printToTreeView(){
        treeView.setRoot(null);
        ArrayList<Category> categories= xmlWorker.getCategories();

        TreeItem<String> root = new TreeItem<>("Agency");

        for(int i = 0; i < categories.size(); i++){
            String info;
            info = "Code: " + categories.get(i).code + " Name: " + categories.get(i).name;
            ArrayList<Item> items = categories.get(i).getItems();
            TreeItem<String> category = new TreeItem<>(info);
            for(int j = 0; j < items.size(); j++){
                String itemInfo = items.get(j).id + " " + items.get(j).headline + " - " + items.get(j).author;
                TreeItem<String> item = new TreeItem<>(itemInfo);
                category.getChildren().add(item);
            }

            root.getChildren().add(category);
        }
        treeView.setRoot(root);
    }
    @FXML
    private void addCategory(ActionEvent event) {
        String code = codeCategory.getText();
        String name = nameCategory.getText();
        Category category = new Category(Integer.parseInt(code), name);
        xmlWorker.addCategory(category);
        printToTreeView();
    }
    @FXML
    private void addItem(ActionEvent event) {
        String id = this.id.getText();
        String headline = this.headline.getText();
        String author = this.author.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected Item");
            return;
        }
        String codeCategory = selectedItem.getValue().split(" ")[1];

        xmlWorker.addItemByCategory(Integer.valueOf(codeCategory), new Item(Integer.valueOf(id), headline, author));
        printToTreeView();

    }
    @FXML
    private void updateCategory(ActionEvent event) {
        String newCode = codeCategory.getText();
        String newName = nameCategory.getText();
        TreeItem<String> selectedItem = (TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected Category");
            return;
        }

        String[] s = selectedItem.getValue().split(" ");
        String categoryCode = selectedItem.getValue().split(" ")[1];
        String categoryName = selectedItem.getValue().split(" ")[3];

        if(newCode.equals("")){
            newCode = categoryCode;
        }
        if(newName.equals("")){
            newName = categoryName;
        }
        xmlWorker.updateCategory(Integer.parseInt(categoryCode), Integer.parseInt(newCode), newName);
        printToTreeView();
    }

    @FXML
    public void deleteCategory(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected Category");
            return;
        }
        String categoryCode = selectedItem.getValue().split(" ")[1];
        xmlWorker.deleteCategory(Integer.valueOf(categoryCode));
        printToTreeView();
    }
    @FXML
    public void deleteItem(ActionEvent event){
        TreeItem<String> selectedItem =(TreeItem<String>)treeView.getSelectionModel().getSelectedItem();
        if(selectedItem == null){
            information.setText("Not selected category");
            return;
        }
        String categoryCode = selectedItem.getParent().getValue().split(" ")[1];
        String itemId = selectedItem.getValue().split(" ")[0];
        xmlWorker.deleteItem(Integer.parseInt(categoryCode), Integer.parseInt(itemId));
        printToTreeView();
    }
    @FXML
    public void save(ActionEvent event){
        xmlWorker.saveToXml();
    }
}
