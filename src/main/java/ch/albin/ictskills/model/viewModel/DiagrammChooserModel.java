package ch.albin.ictskills.model.viewModel;

public class DiagrammChooserModel {
    private final String name;
    private final int value;

    public DiagrammChooserModel(String name, int value) {
        this.name = name;
        this.value = value;
    }

    /**
     * gets the value of value
     */
    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
