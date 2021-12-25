package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class SumData {

	private final SimpleStringProperty text;
	private final SimpleDoubleProperty calories;
	private final SimpleDoubleProperty protein;
	private final SimpleDoubleProperty fat;
	private final SimpleDoubleProperty carbohydrates;

	public SumData(String text, double value1, double value2, double value3, double value4) {
		this.text = new SimpleStringProperty(text);
		this.calories = new SimpleDoubleProperty(value1);
		this.protein = new SimpleDoubleProperty(value2);
		this.fat = new SimpleDoubleProperty(value3);
		this.carbohydrates = new SimpleDoubleProperty(value4);
	}

	public void switchToStatus() {
		Main.getSceneManager().getStage("view").hide();
		Main.getSceneManager().activate("status");
	}

	public String getText() {
		return text.get();
	}

	public void setText(String text) {
		this.text.set(text);
	}

	public double getCalories() {
		return calories.get();
	}

	public void setCalories(double value1) {
		calories.set(value1);
	}

	public double getProtein() {
		return protein.get();
	}

	public void setProtein(double value2) {
		protein.set(value2);
	}

	public double getFat() {
		return fat.get();
	}

	public void setFat(double value3) {
		fat.set(value3);
	}

	public double getCarbohydrates() {
		return carbohydrates.get();
	}

	public void setCarbohydrates(double value4) {
		carbohydrates.set(value4);
	}

}
