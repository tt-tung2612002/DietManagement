package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXToggleNode;

import application.Main;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;

public class MenuController implements Initializable {

	@FXML
	private AnchorPane myAnchor;

	@FXML
	private JFXSlider weightSlider, heightSlider, ageSlider;

	@FXML
	private JFXToggleNode weightMinus, weightPlus, heightMinus, heightPlus, ageMinus, agePlus;

	@FXML
	private JFXButton continueButton;

	@FXML
	private JFXToggleNode maleButton;

	@FXML
	private JFXToggleNode femaleButton;

	@FXML
	private JFXTextField minutesPerDay, daysPerWeek;

	public JFXTextField getMinutesPerDay() {
		return minutesPerDay;
	}

	public JFXTextField getDaysPerWeek() {
		return daysPerWeek;
	}

	@FXML
	public void switchToInfo(ActionEvent event) throws IOException {
		System.out.println(minutesPerDay.getText());
		Main.getSceneManager().getStage("menu").hide();
		Main.getSceneManager().activate("info");
		Main.getControllerManager().getPersonalInfoController().setValue(minutesPerDay.getText(), daysPerWeek.getText(),
				weightSlider.getValue(), heightSlider.getValue(), ageSlider.getValue());
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		weightMinus.setOnMouseClicked(e -> {
			weightSlider.decrement();
		});
		weightPlus.setOnMouseClicked(e -> {
			weightSlider.increment();
		});
		heightMinus.setOnMouseClicked(e -> {
			heightSlider.decrement();
		});
		heightPlus.setOnMouseClicked(e -> {
			heightSlider.increment();
		});
		ageMinus.setOnMouseClicked(e -> {
			ageSlider.decrement();
		});
		agePlus.setOnMouseClicked(e -> {
			ageSlider.increment();
		});

		heightSlider.getStyleClass().add("slider");
		ageSlider.getStyleClass().add("slider");
		weightSlider.getStyleClass().add("slider");
		maleButton.getStyleClass().add("toggle-icon");
		continueButton.getStyleClass().add("continue-icon");
		femaleButton.getStyleClass().add("toggle-icon");
		weightMinus.getStyleClass().add("util-icon");
		weightPlus.getStyleClass().add("util-icon");
		heightMinus.getStyleClass().add("util-icon");
		heightPlus.getStyleClass().add("util-icon");
		ageMinus.getStyleClass().add("util-icon");
		agePlus.getStyleClass().add("util-icon");

	}

	public class IntroView extends Thread {
		@Override
		public void run() {
			try {
				Thread.sleep(5000);
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						Main.getSceneManager().activate("view");
						myAnchor.getScene().getWindow().hide();
					}
				});
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
