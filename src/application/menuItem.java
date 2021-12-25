package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class menuItem {
	private SimpleStringProperty Name;
	private SimpleDoubleProperty Amt;
	private SimpleDoubleProperty Calories;
	private SimpleDoubleProperty Protein;
	private SimpleDoubleProperty Fat;
	private SimpleDoubleProperty Carbohydrates;

	public menuItem(String name, Double amt, Double Calories, Double Protein, Double Fat, Double Carbohydrates) {
		this.Name = new SimpleStringProperty(name);
		this.Amt = new SimpleDoubleProperty(amt);
		this.Calories = new SimpleDoubleProperty(Calories);
		this.Protein = new SimpleDoubleProperty(Protein);
		this.Fat = new SimpleDoubleProperty(Fat);
		this.Carbohydrates = new SimpleDoubleProperty(Carbohydrates);
	}

	public String getName() {
		return Name.get();
	}

	public void setName(SimpleStringProperty name) {
		Name = name;
	}

	public Double getAmt() {
		return Amt.get();
	}

	public void setAmt(SimpleDoubleProperty amt) {
		Amt = amt;
	}

	public Double getCalories() {
		return Calories.get();
	}

	public void setCalories(SimpleDoubleProperty calories) {
		Calories = calories;
	}

	public Double getProtein() {
		return Protein.get();
	}

	public void setProtein(SimpleDoubleProperty protein) {
		Protein = protein;
	}

	public Double getFat() {
		return Fat.get();
	}

	public void setFat(SimpleDoubleProperty fat) {
		Fat = fat;
	}

	public Double getCarbohydrates() {
		return Carbohydrates.get();
	}

	public void setCarbohydrates(SimpleDoubleProperty carbohydrates) {
		Carbohydrates = carbohydrates;
	}
}
