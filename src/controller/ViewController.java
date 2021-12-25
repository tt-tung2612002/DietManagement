package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;

import application.Main;
import application.SumData;
import application.menuItem;
import database.AutoCompleteTextField;
import database.DatabaseManager;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableView.ResizeFeatures;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Callback;

public class ViewController implements Initializable {
	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextField amountField;

	@FXML
	private Button clearButton;

	@FXML
	private Button addButton;

	@FXML
	private Button deleteButton;

	@FXML
	private AnchorPane myAnchor;

	@FXML
	private Button saveButton;

	@FXML
	private JFXButton backButton;

	private DatabaseManager databaseManager;
	private TableView<menuItem> tableView;
	private TableView<SumData> sumView;
	private TableColumn<menuItem, String> Name;

	private TableColumn<menuItem, Double> Amt;
	private TableColumn<menuItem, Double> Calories;
	private TableColumn<menuItem, Double> Protein;
	private TableColumn<menuItem, Double> Fat;
	private TableColumn<menuItem, Double> Carbohydrates;
	private AutoCompleteTextField menuItem;

	@FXML
	public void switchToStatus(ActionEvent event) throws IOException {
		Main.getSceneManager().getStage("view").hide();

		Main.getSceneManager().activate("status");
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		tableView = new TableView<menuItem>();
		sumView = new TableView<SumData>();
		tableView.setColumnResizePolicy((param) -> true);
		try {
			Main.initializeDatabaseManager();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		databaseManager = Main.getDatabaseManager();

		tableView.setColumnResizePolicy(new Callback<TableView.ResizeFeatures, Boolean>() {
			@Override
			public Boolean call(ResizeFeatures p) {
				return true;
			}
		});
		tableView.setLayoutX(70);
		tableView.setLayoutY(110);
		tableView.setPrefSize(1150, 490);

		sumView.setFocusTraversable(false);
		sumView.setLayoutX(70);
		sumView.setLayoutY(600);
		sumView.setPrefSize(1150, 68);

		menuItem = new AutoCompleteTextField();
		menuItem.setPromptText("Menu Item");
		menuItem.setLayoutX(220);
		menuItem.setLayoutY(40);
		menuItem.setPrefSize(175, 40);
		menuItem.setLabelFloat(true);
		menuItem.setFont(Font.font("JetBrains Mono NL SemiBold", FontWeight.MEDIUM, 16));
		menuItem.setAlignment(Pos.CENTER_LEFT);

		menuItem.setEntries(databaseManager.getMenuList());
		menuItem.setFocusTraversable(false);

		menuItem.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (amountField.getText().trim().isEmpty()) {
						amountField.requestFocus();
						return;
					}
					addButton.fire();
				}
			}
		});
		amountField.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent key) {
				if (key.getCode().equals(KeyCode.ENTER)) {
					if (menuItem.getText().trim().isEmpty()) {
						menuItem.requestFocus();
						return;
					}
					addButton.fire();
				}
			}
		});

		Name = new TableColumn<menuItem, String>();
		Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
		Name.setCellFactory(tc -> {
			TableCell<menuItem, String> cell = new TableCell<menuItem, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item);
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});
		Name.setText("Name");
		Name.setPrefWidth(250);
		Name.getStyleClass().add("foo");

		Amt = new TableColumn<menuItem, Double>();
		Amt.setCellValueFactory(new PropertyValueFactory<>("Amt"));
		Amt.setCellFactory(tc -> {
			TableCell<menuItem, Double> cell = new TableCell<menuItem, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});
		Amt.setText("Amt");
		Amt.setPrefWidth(100);
		Amt.getStyleClass().add("foo");

		Calories = new TableColumn<menuItem, Double>();
		Calories.setCellValueFactory(new PropertyValueFactory<>("Calories"));
		Calories.setCellFactory(tc -> {
			TableCell<menuItem, Double> cell = new TableCell<menuItem, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});

		Calories.setText("Calories");
		Calories.setPrefWidth(200);
		Calories.getStyleClass().add("foo");

		Protein = new TableColumn<menuItem, Double>();
		Protein.setCellValueFactory(new PropertyValueFactory<>("Protein"));
		Protein.setCellFactory(tc -> {
			TableCell<menuItem, Double> cell = new TableCell<menuItem, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});

		Protein.setText("Protein");
		Protein.setPrefWidth(200);
		Protein.getStyleClass().add("foo");

		Fat = new TableColumn<menuItem, Double>();
		Fat.setCellValueFactory(new PropertyValueFactory<>("Fat"));
		Fat.setCellFactory(tc -> {
			TableCell<menuItem, Double> cell = new TableCell<menuItem, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});
		Fat.setText("Fat");
		Fat.setPrefWidth(100);
		Fat.getStyleClass().add("foo");

		Carbohydrates = new TableColumn<menuItem, Double>();
		Carbohydrates.setCellValueFactory(new PropertyValueFactory<>("Carbohydrates"));
		Carbohydrates.setCellFactory(tc -> {
			TableCell<menuItem, Double> cell = new TableCell<menuItem, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});

		Carbohydrates.setText("Carbohydrates");
		Carbohydrates.setPrefWidth(250);
		Carbohydrates.getStyleClass().add("foo");

		// columns for total table.
		TableColumn<SumData, String> Name2 = new TableColumn<SumData, String>();
		Name2.setCellValueFactory(new PropertyValueFactory<>("Text"));
		Name2.setCellFactory(tc -> {
			TableCell<SumData, String> cell = new TableCell<SumData, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item);
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("sumCol");
			return cell;
		});
		Name2.setText("Total");
		Name2.setPrefWidth(350);

		TableColumn<SumData, Double> Calories2 = new TableColumn<SumData, Double>();
		Calories2.setCellValueFactory(new PropertyValueFactory<>("Calories"));
		Calories2.setCellFactory(tc -> {
			TableCell<SumData, Double> cell = new TableCell<SumData, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});
		Calories2.setText("Calories");
		Calories2.setPrefWidth(200);

		TableColumn<SumData, Double> Protein2 = new TableColumn<SumData, Double>();
		Protein2.setCellValueFactory(new PropertyValueFactory<>("Protein"));
		Protein2.setCellFactory(tc -> {
			TableCell<SumData, Double> cell = new TableCell<SumData, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});
		Protein2.setText("Protein");
		Protein2.setPrefWidth(200);

		TableColumn<SumData, Double> Fat2 = new TableColumn<SumData, Double>();
		Fat2.setCellValueFactory(new PropertyValueFactory<>("Fat"));
		Fat2.setCellFactory(tc -> {
			TableCell<SumData, Double> cell = new TableCell<SumData, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			// maybe choose a more suitable name here...
			cell.getStyleClass().add("col");
			return cell;
		});
		Fat2.setText("Fat");
		Fat2.setPrefWidth(100);

		TableColumn<SumData, Double> Carbohydrates2 = new TableColumn<SumData, Double>();
		Carbohydrates2.setCellValueFactory(new PropertyValueFactory<>("Carbohydrates"));
		Carbohydrates2.setCellFactory(tc -> {
			TableCell<SumData, Double> cell = new TableCell<SumData, Double>() {
				@Override
				protected void updateItem(Double item, boolean empty) {
					super.updateItem(item, empty);
					setText(empty ? "" : item.toString());
				}
			};

			cell.getStyleClass().add("col");
			return cell;

		});
		Carbohydrates2.setText("Carbohydrates");
		Carbohydrates2.setPrefWidth(250);

		tableView.setTableMenuButtonVisible(true);
		sumView.setTableMenuButtonVisible(true);

		sumView.getStyleClass().add("tableview-header-hidden");
		sumView.getStyleClass().add("sumtable");
		backButton.getStyleClass().add("continue-icon");

		tableView.getColumns().addAll(Name, Amt, Calories, Protein, Fat, Carbohydrates);
		sumView.getColumns().addAll(Name2, Calories2, Protein2, Fat2, Carbohydrates2);

		myAnchor.getChildren().add(menuItem);
		myAnchor.getChildren().add(tableView);
		myAnchor.getChildren().add(sumView);
		tableView.getColumns().addListener(new ListChangeListener() {
			@Override
			public void onChanged(Change change) {
				change.next();
				if (change.wasReplaced()) {
					TableHeaderRow header = (TableHeaderRow) tableView.lookup("TableHeaderRow");
					header.setMouseTransparent(true);
				}
			}
		});
	}

	@FXML
	void clearItem(ActionEvent event) {
		tableView.getItems().clear();
		tableView.refresh();
		sumView.getItems().clear();
		sumView.refresh();
	}

	@FXML
	void addItem(ActionEvent event) {
		if (menuItem.getText().trim().isEmpty()) {
			menuItem.requestFocus();
			return;
		}
		if (amountField.getText().trim().isEmpty()) {
			amountField.requestFocus();
			return;
		}
		try {
			Double currentAmount = Double.parseDouble(amountField.getText());
			if (!(currentAmount instanceof Double))
				return;
			List<String> result = databaseManager.getServer().getMenu(menuItem.getText());
			if (result.isEmpty())
				return;
			menuItem newItem = new menuItem(result.get(0), currentAmount,
					round(currentAmount * Double.parseDouble(result.get(1)), 2),
					round(currentAmount * Double.parseDouble(result.get(2)), 2),
					round(currentAmount * Double.parseDouble(result.get(3)), 2),
					round(currentAmount * Double.parseDouble(result.get(4)), 2));
			tableView.getItems().add(newItem);
			sumView.getItems().clear();
			sumView.setItems(getSumItem());
			sumView.refresh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// get total value for each element in sumView.
		SumData sumData = sumView.getItems().get(0);
		System.out.println(sumData.getCalories());
		double calories = sumData.getCalories();
		double protein = sumData.getProtein();
		double fat = sumData.getFat();
		double carbohydrates = sumData.getCarbohydrates();
		Main.getControllerManager().getStatusController().update(calories, protein, fat, carbohydrates * 4);

	}

	@FXML
	void deleteItem(ActionEvent event) {
		tableView.getItems().removeAll(tableView.getSelectionModel().getSelectedItems());
		tableView.refresh();
		sumView.getItems().clear();
		sumView.setItems(getSumItem());
		sumView.refresh();
		SumData sumData = sumView.getItems().get(0);
		System.out.println(sumData.getCalories());
		double calories = sumData.getCalories();
		double protein = sumData.getProtein();
		double fat = sumData.getFat();
		double carbohydrates = sumData.getCarbohydrates();
		Main.getControllerManager().getStatusController().update(calories, protein, fat, carbohydrates * 4);
	}

	public ObservableList<menuItem> getMenuItem() {
		List<List<String>> list = databaseManager.getMenu();
		ObservableList<menuItem> items = FXCollections.observableArrayList();
		for (List<String> item : list)
			items.add(new menuItem(item.get(0), 1.0, Double.parseDouble(item.get(1)), Double.parseDouble(item.get(2)),
					Double.parseDouble(item.get(3)), Double.parseDouble(item.get(4))));
		return items;
	}

	public double round(double value, int places) {
		if (places < 0)
			throw new IllegalArgumentException();

		long factor = (long) Math.pow(10, places);
		value = value * factor;
		long tmp = Math.round(value);
		return (double) tmp / factor;
	}

	public ObservableList<SumData> getSumItem() {
		ObservableList<SumData> items = FXCollections.observableArrayList();
		Double caloriesValue = 0.0;
		Double proteinValue = 0.0;
		Double fatValue = 0.0;
		Double carbValue = 0.0;
		for (menuItem o : tableView.getItems()) {
			caloriesValue += Calories.getCellData(o);
			proteinValue += Protein.getCellData(o);
			fatValue += Fat.getCellData(o);
			carbValue += Carbohydrates.getCellData(o);
		}
		// round the value to 2 decimal.
		caloriesValue = round(caloriesValue, 2);
		proteinValue = round(proteinValue, 2);
		fatValue = round(fatValue, 2);
		carbValue = round(carbValue, 2);

		items.add(new SumData("Total", caloriesValue, proteinValue, fatValue, carbValue));
		return items;
	}

}
