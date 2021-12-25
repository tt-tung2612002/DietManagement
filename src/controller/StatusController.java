package controller;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;

import application.Main;
import database.DatabaseManager;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class StatusController implements Initializable {

    private double currentCalories = 0, currentProtein = 0, currentCarbs = 0, currentFat = 0;

    @FXML
    private AnchorPane myAnchor;

    @FXML
    private JFXDatePicker myDatePicker;

    @FXML
    private JFXSpinner statusSpinner, backgroundSpinner;
    @FXML
    private Text burnedCalories, consumedCalories, totalCaloriesToBurn, currentCarbDisplay,
            totalCarbDisplay, currentProteinDisplay, totalProteinDisplay, currentFatDisplay,
            totalFatDisplay, date;

    @FXML
    private PieChart dailyChart;

    @FXML
    private JFXButton nodeButton;

    @FXML
    private JFXButton exercisesButton;

    @FXML
    private JFXButton dishesButton;

    @FXML
    private JFXButton continueButton;

    @FXML
    private JFXProgressBar carbProgress, proteinProgress, fatProgress;

    private DatabaseManager databaseManager;

    @FXML
    public void switchToView(ActionEvent event) throws IOException {
        Main.getSceneManager().getStage("status").hide();
        Main.getSceneManager().activate("view");
    }

    @FXML
    public void switchToInfo(ActionEvent event) throws IOException {
        Main.getSceneManager().getStage("status").hide();
        Main.getSceneManager().activate("info");
    }

    public Text getTotalCalories() {
        return totalCaloriesToBurn;
    }

    public Text getTotalCarb() {
        return totalCarbDisplay;
    }

    public Text getCurrentCarb() {
        return currentCarbDisplay;
    }

    public Text getTotalProtein() {
        return totalProteinDisplay;
    }

    public Text getTotalFat() {
        return totalFatDisplay;
    }

    public void update(double calories, double protein, double fat, double carbs) {
        currentCalories = calories + 0.01;
        currentProtein = protein + 0.01;
        currentCarbs = carbs + 0.01;
        currentFat = fat + 0.01;
        currentCarbDisplay.setText(String.format("%.0f", currentCarbs));
        currentProteinDisplay.setText(String.format("%.0f", currentProtein));
        currentFatDisplay.setText(String.format("%.0f", currentFat));
        consumedCalories.setText(String.format("%.0f", currentCalories));
        totalCaloriesToBurn.setText(String.format("%.0f", PersonalInfoController.totalCalories - currentCalories));
        double statusProgress = currentCalories / PersonalInfoController.totalCalories;
        System.out.println(statusProgress);
        double carbPercent = currentCarbs / PersonalInfoController.totalCarb;
        double proteinPercent = currentProtein / PersonalInfoController.totalProtein;
        double fatPercent = currentFat / PersonalInfoController.totalFat;
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(statusSpinner.progressProperty(), 0),
                        new KeyValue(carbProgress.progressProperty(), 0),
                        new KeyValue(proteinProgress.progressProperty(), 0),
                        new KeyValue(fatProgress.progressProperty(), 0)),
                new KeyFrame(Duration.seconds(0.25), new KeyValue(statusSpinner.progressProperty(), statusProgress / 4),
                        new KeyValue(carbProgress.progressProperty(), carbPercent / 4),
                        new KeyValue(proteinProgress.progressProperty(), proteinPercent / 4),
                        new KeyValue(fatProgress.progressProperty(), fatPercent / 4)),
                new KeyFrame(Duration.seconds(0.5), new KeyValue(statusSpinner.progressProperty(), statusProgress / 2),
                        new KeyValue(carbProgress.progressProperty(), carbPercent / 2),
                        new KeyValue(proteinProgress.progressProperty(), proteinPercent / 2),
                        new KeyValue(fatProgress.progressProperty(), fatPercent / 2)),
                new KeyFrame(Duration.seconds(1), new KeyValue(statusSpinner.progressProperty(), statusProgress),
                        new KeyValue(carbProgress.progressProperty(), carbPercent),
                        new KeyValue(proteinProgress.progressProperty(), proteinPercent),
                        new KeyValue(fatProgress.progressProperty(), fatPercent)));
        timeline.setCycleCount(1);
        timeline.play();

        double total = currentCarbs / 4
                + currentFat
                + currentProtein;

        double carbsPercent = (currentCarbs) / 4 / total * 100;
        fatPercent = (currentFat) / total * 100;
        proteinPercent = 100 - carbsPercent - fatPercent;

        updateProtein(proteinPercent);
        updateCarbs(carbsPercent);
        updateFat(fatPercent);
        Date date = Date.valueOf(myDatePicker.getValue());
        try {
            databaseManager.updateHistory(date, calories, protein, fat, carbs);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize(URL arg0, ResourceBundle arg1) {

        databaseManager = Main.getDatabaseManager();
        // set value to current date.
        myDatePicker.setValue(LocalDate.now());
        myDatePicker.getStyleClass().add("date-picker");
        // add change listener to date picker.
        myDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            // compare date of newValue to today.
            // get date from date picker in sql format.
            String selectedDate = myDatePicker.getValue().toString();
            List<String> tracked = databaseManager.getHistory(selectedDate);
            // parse tracked to double type.
            if (tracked.size() > 0) {
                double calories = Double.parseDouble(tracked.get(0));
                double protein = Double.parseDouble(tracked.get(1));
                double fat = Double.parseDouble(tracked.get(2));
                double carbs = Double.parseDouble(tracked.get(3));
                update(calories, protein, fat, carbs);
                System.out.println("tracked");
            } else {
                update(0, 0, 0, 0);
            }

            if (newValue.isBefore(LocalDate.now())) {
                // get the difference of dates.
                long days = newValue.until(LocalDate.now(), java.time.temporal.ChronoUnit.DAYS);
                System.out.println("Days: " + days);
                // set date to "Yesterday" if difference is 1, else set to "X days ago"
                if (days == 1) {
                    date.setText("Yesterday");
                } else {
                    date.setText(days + " days ago");
                }
            } else if (!newValue.isAfter(LocalDate.now())) {
                date.setText("Today");

            } else {
                // get the difference of dates.
                long days = newValue.until(LocalDate.now(), java.time.temporal.ChronoUnit.DAYS);
                System.out.println("Days: " + days);
                // set date to "Yesterday" if difference is 1, else set to "X days ago"
                if (days == -1) {
                    date.setText("Tomorrow");
                } else {
                    date.setText(Math.abs(days) + " days from now");
                }
            }
        });

        statusSpinner.getStyleClass().add("percentage");
        backgroundSpinner.getStyleClass().add("background-spinner");
        // add data to dailyChart
        PieChart.Data carbs = new PieChart.Data("Carbohydates", 50);
        PieChart.Data fats = new PieChart.Data("Fats", 10);
        PieChart.Data proteins = new PieChart.Data("Proteins", 40);

        dailyChart.getData().add(carbs);
        dailyChart.getData().add(fats);
        dailyChart.getData().add(proteins);
        dailyChart.setStartAngle(20);

        // change dailyChart font to JetBrains Mono NL SemiBold;
        dailyChart.setStyle("-fx-font-family: 'JetBrains Mono NL SemiBold'; -fx-font-size: 18px;");

        final Label caption = new Label("");
        caption.setTextFill(Color.WHITE);
        caption.setStyle("-fx-font-family: 'JetBrains Mono NL SemiBold'; -fx-font-size: 20px;");
        for (final PieChart.Data data : dailyChart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_PRESSED,
                    e -> {
                        double total = 0;
                        for (PieChart.Data d : dailyChart.getData()) {
                            total += d.getPieValue();
                        }
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        String text = String.format("%.1f%%", 100 * data.getPieValue() / total);
                        caption.setText(text);
                    });
        }
        carbs.getNode().setStyle("-fx-pie-color:#f54c36;");
        fats.getNode().setStyle("-fx-pie-color: #f2f556;");
        proteins.getNode().setStyle("-fx-pie-color: #19c2a9;");
        nodeButton.getStyleClass().add("node-button");
        continueButton.getStyleClass().add("continue-icon");
        exercisesButton.getStyleClass().add("subnode-button");
        dishesButton.getStyleClass().add("subnode-button");
        myAnchor.getChildren().add(caption);
    }

    public void updateProgressBar(double value) {
        statusSpinner.setProgress(value);
    }

    public void updateProtein(double value) {
        // update calories in dailyChart.
        dailyChart.getData().get(2).setPieValue(value);
    }

    public void updateFat(double value) {
        dailyChart.getData().get(1).setPieValue(value);
    }

    public void updateCarbs(double value) {
        dailyChart.getData().get(0).setPieValue(value);
    }
}
