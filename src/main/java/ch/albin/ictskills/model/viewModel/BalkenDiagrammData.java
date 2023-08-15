package ch.albin.ictskills.model.viewModel;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class BalkenDiagrammData {
    private final SimpleStringProperty category;
    private final SimpleDoubleProperty value;

    public BalkenDiagrammData(String category, double value) {
        this.category = new SimpleStringProperty(category);
        this.value = new SimpleDoubleProperty(value);
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public double getValue() {
        return value.get();
    }

    public void setValue(double value) {
        this.value.set(value);
    }
}
