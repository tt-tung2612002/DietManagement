package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import database.DatabaseManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class NewViewController implements Initializable {

    @FXML
    private AnchorPane myAnchor;

    @FXML
    private JFXListView<String> menuListView;

    @FXML
    private ScrollPane menuScrollPane;

    @FXML
    private JFXTextField textField;

    private ObservableList<String> menuItems = FXCollections.observableArrayList();

    public DatabaseManager databaseManager = Main.getDatabaseManager();

    @FXML
    public void initialize(URL location, ResourceBundle resources) {

        menuItems.addAll(databaseManager.getMenuList());

        // AutoCompleteTextField textField = new AutoCompleteTextField();
        // List<String> menuList = databaseManager.getMenuList();

        FilteredList<String> filteredData = new FilteredList<>(menuItems, data -> true);
        menuListView.setItems(filteredData);

        // hide scrollbar in menuScrollPane
        menuScrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(data -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseSearch = newValue.toLowerCase();
                return data.contains(lowerCaseSearch);
            });
        });
        textField.setPromptText("Search Menu...");
        textField.setLayoutX(121);
        textField.setLayoutY(112);
        textField.setPrefSize(390, 34);
        textField.setFont(Font.font("JetBrains Mono NL SemiBold", FontWeight.MEDIUM, 16));
        textField.setFocusTraversable(false);
        textField.setUnFocusColor(Color.WHITE);
        ChangeListener<String> listListener = new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable,
                    String oldValue, String newValue) {
                String searched = menuListView.getSelectionModel().getSelectedItem();
                if (searched == null) {
                    return;
                }
            }

        };
        menuListView.getSelectionModel().selectedItemProperty().addListener(listListener);
        menuListView.getStyleClass().add("list-view");

        // myAnchor.getChildren().add(textField);
    }
}