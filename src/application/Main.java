package application;

import java.sql.SQLException;

import controller.ControllerManager;
import controller.MenuController;
import controller.PersonalInfoController;
import controller.SceneManager;
import controller.StatusController;
import controller.ViewController;
import database.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
	public static SceneManager sceneManager;
	public static ControllerManager controllerManager;
	public static DatabaseManager databaseManager;

	@Override
	public void start(Stage primaryStage) throws Exception {
		controllerManager = new ControllerManager();
		sceneManager = new SceneManager();
		databaseManager = new DatabaseManager();

		// load menu scene
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
		Parent root = loader.load();
		Scene scene = new Scene(root);
		scene.getStylesheets().addAll(getClass().getResource("/Home.css").toExternalForm());
		Stage menuStage = new Stage(StageStyle.DECORATED);
		scene.setFill(Color.TRANSPARENT);
		menuStage.setScene(scene);
		sceneManager.addStage("menu", menuStage);
		MenuController menuController = loader.getController();
		controllerManager.addIntroController(menuController);

		// load info scene
		loader = new FXMLLoader(getClass().getResource("PersonalInformation.fxml"));
		Scene infoScene = new Scene(loader.load());
		infoScene.getStylesheets().addAll(getClass().getResource("/Home.css").toExternalForm());
		Stage infoStage = new Stage(StageStyle.DECORATED);
		infoStage.setScene(infoScene);
		PersonalInfoController personalInfoController = loader.getController();
		controllerManager.addPersonalInfoController(personalInfoController);
		sceneManager.addStage("info", infoStage);

		// load status scene
		loader = new FXMLLoader(getClass().getResource("Status.fxml"));
		root = loader.load();
		Scene statusScene = new Scene(root);
		statusScene.getStylesheets().addAll(getClass().getResource("/Home.css").toExternalForm());
		Stage statusStage = new Stage(StageStyle.DECORATED);
		statusScene.setFill(Color.TRANSPARENT);
		statusStage.setScene(statusScene);
		sceneManager.addStage("status", statusStage);
		StatusController statusController = loader.getController();
		controllerManager.addStatusController(statusController);

		// load view scene
		loader = new FXMLLoader(getClass().getResource("View.fxml"));
		Scene viewScene = new Scene(loader.load());
		viewScene.getStylesheets().addAll(getClass().getResource("/application.css").toExternalForm(),
				getClass().getResource("/Home.css").toExternalForm());
		Stage viewStage = new Stage(StageStyle.DECORATED);
		viewStage.setScene(viewScene);
		ViewController viewController = loader.getController();
		controllerManager.addViewController(viewController);
		sceneManager.addStage("view", viewStage);

		sceneManager.activate("menu");
	}

	public static ControllerManager getControllerManager() {
		return controllerManager;
	}

	public static SceneManager getSceneManager() {
		return sceneManager;
	}

	public static void initializeDatabaseManager() throws SQLException {
		databaseManager = new DatabaseManager();
	}

	public static DatabaseManager getDatabaseManager() {
		return databaseManager;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
