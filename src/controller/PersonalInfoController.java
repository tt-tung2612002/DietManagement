package controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXSpinner;

import application.Main;
import database.Frequency;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class PersonalInfoController implements Initializable {

    @FXML
    private AnchorPane myAnchor;

    @FXML
    private JFXSpinner TDEEProgress;

    @FXML
    private Text TDEEValue, BMIValue, weightValue, heightValue;

    @FXML
    private JFXButton continueButton;

    public static double BMI, BMR, TDEE, totalCalories, totalProtein, totalFat, totalCarb;

    @FXML
    void switchToStatus(ActionEvent event) {
        Main.getSceneManager().getStage("info").hide();
        Main.getControllerManager().getStatusController().getTotalCarb().setText(String.format("%.0f", totalCarb));
        Main.getControllerManager().getStatusController().getTotalFat().setText(String.format("%.0f", totalFat));
        Main.getControllerManager().getStatusController().getTotalProtein()
                .setText(String.format("%.0f", totalProtein));
        Main.getControllerManager().getStatusController().getTotalCalories()
                .setText(String.format("%.0f", totalCalories));
        Main.getSceneManager().activate("status");
    }

    public void setTDEEProgress(double value) {
        TDEEProgress.setProgress(value);
    }

    public void setValue(String text1, String text2, double weight, double height, double age) {
        BMI = weight / ((height / 100) * (height / 100));
        // format BMI to 0 decimal.
        BMI = Math.round(BMI * 100.0) / 100.0;
        System.out.println("BMI Rounded: " + BMI);

        BMIValue.setText(String.format("%.1f", BMI));

        BMR = 66 + weight * 13.7 + height * 5 - age * 6.8;
        // BMRValue.setText(String.format("%.2f", BMR));

        double minutesPerDay = Double
                .parseDouble(text1);
        double daysPerWeek = Double
                .parseDouble(text2);

        double totalWorkoutHours = minutesPerDay * daysPerWeek / 60;
        Frequency frequency;
        if (totalWorkoutHours < 1) {
            frequency = Frequency.RARELY;
        } else if (totalWorkoutHours < 3) {
            frequency = Frequency.PERIODICALLY;
        } else if (totalWorkoutHours < 8) {
            frequency = Frequency.MODERATELY;
        } else {
            frequency = Frequency.FREQUENTLY;
        }

        switch (frequency) {
            case RARELY:
                TDEE = BMR * 1.2;
                break;
            case PERIODICALLY:
                TDEE = BMR * 1.375;
                break;
            case MODERATELY:
                TDEE = BMR * 1.55;
                break;
            case FREQUENTLY:
                TDEE = BMR * 1.725;
                break;
        }
        System.out.println("Frequency: " + frequency);
        TDEE = Math.round(TDEE * 100.0) / 100.0;
        TDEEValue.setText(String.format("%.0f", TDEE));
        System.out.println("TDEE: " + TDEE);

        totalCalories = (BMR + TDEE) / 2;
        totalCalories = Math.round(totalCalories * 100.0) / 100.0;

        double leanWeight = weight;
        leanWeight = Math.round(leanWeight * 100.0) / 100.0;
        totalProtein = leanWeight * 2.2;
        totalProtein = Math.round(totalProtein * 100.0) / 100.0;
        totalFat = leanWeight * 0.8;
        totalFat = Math.round(totalFat * 100.0) / 100.0;
        totalCarb = totalCalories - (totalProtein + totalFat);
        totalCarb = Math.round(totalCarb * 100.0) / 100.0;

        weightValue.setText(String.format("%.0f", weight));
        heightValue.setText(String.format("%.0f", height));
        continueButton.getStyleClass().add("continue-icon");
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

    }

}