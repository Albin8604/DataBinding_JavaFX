package ch.albin.ictskills.controller;

import ch.albin.ictskills.assets.Assets;
import ch.albin.ictskills.model.Person;
import ch.albin.ictskills.model.viewModel.DiagrammChooserModel;
import ch.albin.ictskills.model.viewModel.PersonView;
import ch.albin.ictskills.util.ByteAStringConverter;
import ch.albin.ictskills.util.DoubleIntegerStringConverter;
import ch.albin.ictskills.util.FXMLHelper;
import ch.albin.ictskills.util.files.chooser.ChooserManager;
import ch.albin.ictskills.util.files.chooser.Extensions;
import ch.albin.ictskills.util.ui.TableManager;
import ch.albin.ictskills.util.ui.UIAlertMsg;
import ch.albin.ictskills.util.ui.UIHelper;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.ScatterChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import org.apache.commons.lang3.ArrayUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import static ch.albin.ictskills.util.Validator.*;
import static ch.albin.ictskills.util.ui.UIHelper.addRegexValidationToControl;

public class MainController extends Controller {

    public ChoiceBox<DiagrammChooserModel> diagrammChooser;
    public BorderPane chartPane;
    public TextField profilePicPathTextField;
    public Stage stage;
    public TextField pNrSearchField;
    public TextField nameSearchField;
    public TextField vornameSearchField;
    public TextField telSearchField;
    public CheckBox aktivSearchBox;
    public ToggleButton toggleBtn;
    private boolean isNewPerson = false;
    private static final ObservableList<Person> TEST_PERSON_LIST = FXCollections.observableList(PERSON_DAO.selectAll());
    private static PersonView lastSelectedPerson = null;
    public TextField pNrTextField;
    public TextField nameTextField;
    public TextField vornameTextField;
    public TextField telTextField;
    public CheckBox aktivCheckBox;
    public TableView<PersonView> personTable;

    private static final SimpleIntegerProperty AKTIV_DATA = new SimpleIntegerProperty();
    private static final SimpleIntegerProperty INAKTIV_DATA = new SimpleIntegerProperty();

    private static final ObservableList<PersonView> TABLE_DATA = FXCollections.observableArrayList(
            obs -> new Observable[]{obs.aktivProperty()}

    );

    private boolean isSearchActive = false;

    @Override
    public void init() {
        diagrammChooser.setItems(DIAGRAMM_CHOOSER_MODEL_LIST);
        diagrammChooser.getSelectionModel().selectFirst();
        changeDiagramm();

        addRegexValidationsToControls();

        AKTIV_DATA.bind(
                Bindings.size(TABLE_DATA.filtered(PersonView::getAktiv))
        );

        INAKTIV_DATA.bind(
                Bindings.size(TABLE_DATA.filtered(person -> !person.getAktiv()))
        );

        TABLE_DATA.addAll(TEST_PERSON_LIST.stream().map(person -> new PersonView(person, personTable)).toList());

        TableManager.initColumns(TABLE_DATA, personTable);

        updateTable();

        personTable.getSelectionModel().selectFirst();

        clickedOnTable();

        buildPieChart();

        setListeners();
    }

    private void addRegexValidationsToControls(){
        addRegexValidationToControl(telTextField, TEL_REGEX);
        addRegexValidationToControl(pNrTextField, getNumberRegexInRange(6, 8));
        addRegexValidationToControl(nameTextField, ONLY_LETTER_REGEX);
        addRegexValidationToControl(vornameTextField, ONLY_LETTER_REGEX);
    }
    private void setListeners(){
        pNrSearchField.textProperty().addListener(ChangeListener -> {
            if (!isSearchActive) {
                return;
            }

            personTable.setItems(
                    TABLE_DATA.filtered(
                            item ->
                                    String.valueOf(item.getpNr()).startsWith(pNrSearchField.getText().trim())
                    )
            );
        });

        nameSearchField.textProperty().addListener(ChangeListener -> {
            if (!isSearchActive) {
                return;
            }
            personTable.setItems(
                    TABLE_DATA.filtered(
                            item ->
                                    item.getName().toUpperCase().startsWith(nameSearchField.getText().toUpperCase().trim())
                    )
            );
        });

        vornameSearchField.textProperty().addListener(ChangeListener -> {
            if (!isSearchActive) {
                return;
            }
            personTable.setItems(
                    personTable.getItems().filtered(
                            item ->
                                    item.getName().toUpperCase().startsWith(vornameSearchField.getText().toUpperCase().trim())
                    )
            );
        });

        telSearchField.textProperty().addListener(ChangeListener -> {
            if (!isSearchActive) {
                return;
            }
            personTable.setItems(
                    personTable.getItems().filtered(
                            item ->
                                    item.getName().toUpperCase().startsWith(telSearchField.getText().toUpperCase().trim())
                    )
            );
        });

        aktivSearchBox.selectedProperty().addListener(ChangeListener -> {
            if (!isSearchActive) {
                return;
            }
            personTable.setItems(
                    personTable.getItems().filtered(
                            item -> aktivSearchBox.isSelected() == item.getAktiv()
                    )
            );
        });
    }

    private void buildPieChart() {
        final PieChart pieChart = new PieChart();
        final PieChart.Data aktivPieChartData = new PieChart.Data("Aktiv", 0);
        final PieChart.Data inaktivPieChartData = new PieChart.Data("Inaktiv", 0);

        aktivPieChartData.pieValueProperty().bind(AKTIV_DATA);
        inaktivPieChartData.pieValueProperty().bind(INAKTIV_DATA);

        pieChart.setLabelsVisible(true);
        pieChart.getData().addAll(aktivPieChartData, inaktivPieChartData);

        chartPane.setCenter(pieChart);
    }

    private void buildBarChart() {
        final BarChart<String, Number> barChart = new BarChart<>(createXAxis(), createYAxis());

        barChart.getData().addAll(createAktivInaktivSeries());

        chartPane.setCenter(barChart);
    }

    private void buildScatterChart() {
        final ScatterChart<String, Number> scatterChart = new ScatterChart<>(createXAxis(), createYAxis());

        scatterChart.getData().addAll(createAktivInaktivSeries());

        chartPane.setCenter(scatterChart);
    }

    private List<XYChart.Series<String, Number>> createAktivInaktivSeries() {
        return List.of(
                createAktivSeries(),
                createInaktivSeries()
        );
    }

    private XYChart.Data<String, Number> createAktivXYData() {
        final XYChart.Data<String, Number> aktivXYData = new XYChart.Data<>("Aktiv", AKTIV_DATA.get());
        aktivXYData.YValueProperty().bind(AKTIV_DATA);
        return aktivXYData;
    }

    private XYChart.Data<String, Number> createInaktivXYData() {
        final XYChart.Data<String, Number> inaktivXYData = new XYChart.Data<>("Inaktiv", INAKTIV_DATA.get());
        inaktivXYData.YValueProperty().bind(INAKTIV_DATA);
        return inaktivXYData;
    }

    private XYChart.Series<String, Number> createAktivSeries() {
        final XYChart.Series<String, Number> aktivSeries = new XYChart.Series<>();

        aktivSeries.setName("Aktiv");
        aktivSeries.getData().add(createAktivXYData());

        return aktivSeries;
    }

    private XYChart.Series<String, Number> createInaktivSeries() {
        final XYChart.Series<String, Number> inaktivSeries = new XYChart.Series<>();

        inaktivSeries.setName("Inaktiv");
        inaktivSeries.getData().add(createInaktivXYData());

        return inaktivSeries;
    }

    private CategoryAxis createXAxis() {
        final CategoryAxis xAxis = new CategoryAxis();
        return xAxis;
    }

    private NumberAxis createYAxis() {
        final NumberAxis yAxis = new NumberAxis();
        StringConverter<? extends Number> stringConverter = new DoubleIntegerStringConverter();

        yAxis.setTickLabelFormatter((StringConverter<Number>) stringConverter);
        yAxis.setLabel("Anzahl");

        return yAxis;
    }

    private void updateTable() {
        final ObservableList<PersonView> PERSON_VIEW_LIST = FXCollections.observableArrayList(
                TEST_PERSON_LIST.stream().map(person -> new PersonView(person, personTable)).toList()
        );

        personTable.getItems().clear();
        personTable.getItems().addAll(PERSON_VIEW_LIST);
    }

    public void clickedOnTable() {
        PersonView personView = personTable.getSelectionModel().getSelectedItem();
        if (personView == null) {
            return;
        }

        if (lastSelectedPerson != null) {
            Bindings.unbindBidirectional(pNrTextField.textProperty(), lastSelectedPerson.pNrProperty());
            Bindings.unbindBidirectional(nameTextField.textProperty(), lastSelectedPerson.nameProperty());
            Bindings.unbindBidirectional(vornameTextField.textProperty(), lastSelectedPerson.vornameProperty());
            Bindings.unbindBidirectional(telTextField.textProperty(), lastSelectedPerson.telProperty());
            Bindings.unbindBidirectional(aktivCheckBox.selectedProperty(), lastSelectedPerson.aktivProperty());
            Bindings.unbindBidirectional(profilePicPathTextField.textProperty(), lastSelectedPerson.imageProperty());
        }

        StringConverter<? extends Number> stringConverter = new IntegerStringConverter();
        StringConverter<? extends Byte[]> stringConverterForBorderPane = new ByteAStringConverter();

        Bindings.bindBidirectional(pNrTextField.textProperty(), personView.pNrProperty(), ((StringConverter<Number>) stringConverter));
        Bindings.bindBidirectional(nameTextField.textProperty(), personView.nameProperty());
        Bindings.bindBidirectional(vornameTextField.textProperty(), personView.vornameProperty());
        Bindings.bindBidirectional(telTextField.textProperty(), personView.telProperty());
        Bindings.bindBidirectional(aktivCheckBox.selectedProperty(), personView.aktivProperty());
        Bindings.bindBidirectional(profilePicPathTextField.textProperty(), personView.imageProperty(), (StringConverter<Byte[]>) stringConverterForBorderPane);

        lastSelectedPerson = personView;
    }

    private boolean isInputValid() {
        return (boolean) pNrTextField.getUserData() &&
                (boolean) nameTextField.getUserData() &&
                (boolean) vornameTextField.getUserData() &&
                (boolean) telTextField.getUserData();
    }

    public void save() {
        PersonView selectedItem = personTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            return;
        }

        if (!isInputValid()) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.INVALID_INPUT);
            return;
        }

        try {
            if (isNewPerson) {
                PERSON_DAO.addOne(new Person(selectedItem));
                isNewPerson = false;
                return;
            }

            PERSON_DAO.update(new Person(selectedItem));

            personTable.getSelectionModel().selectFirst();
            clickedOnTable();

            UIHelper.createAndShowAlert(Alert.AlertType.INFORMATION, UIAlertMsg.PERSON_SAVED);
        } catch (Exception e) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, e.getMessage());
        }

    }

    public void add() {
        TEST_PERSON_LIST.add(new Person());

        updateTable();

        personTable.getSelectionModel().selectLast();

        clickedOnTable();

    }

    public void delete() {
        PersonView selectedItem = personTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            return;
        }

        personTable.getItems().remove(selectedItem);
        PERSON_DAO.deleteOne(selectedItem.getPerson());

        UIHelper.createAndShowAlert(Alert.AlertType.INFORMATION, UIAlertMsg.PERSON_DELETED);
    }

    public void changeDiagramm() {
        DiagrammChooserModel selectedItem = diagrammChooser.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            return;
        }

        switch (selectedItem.getValue()) {
            case 1 -> {
                buildPieChart();
            }
            case 2 -> {
                buildBarChart();
            }
            case 3 -> {
                buildScatterChart();
            }
        }
    }

    public void chooseProfilePic() {
        File choosenFile = ChooserManager.getChoosedOpenFile(stage, Extensions.JPEG, Extensions.JPG, Extensions.PNG);

        if (choosenFile == null) {
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.FILE_MISSING);
            return;
        }

        profilePicPathTextField.textProperty().set(choosenFile.getAbsolutePath());
    }

    public void drawProfilePic() {
        Stage drawStage = FXMLHelper.load(Assets.DrawImage);
        drawStage.initModality(Modality.APPLICATION_MODAL);
        drawStage.show();
    }

    public static void setDrawnProfilePic(File pic) {
        try {
            lastSelectedPerson.setImage(ArrayUtils.toObject(Files.readAllBytes(pic.toPath())));
            pic.delete();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearAllSearchFields(){
        pNrSearchField.setText(null);
        nameSearchField.setText(null);
        vornameSearchField.setText(null);
        telSearchField.setText(null);
        aktivSearchBox.setSelected(false);
    }
    public void searchToggle() {
        isSearchActive = toggleBtn.isSelected();

        if (isSearchActive) {
            toggleBtn.setText("On");
            toggleBtn.setStyle("-fx-background-color: #1d9d0d");
        }else {
            toggleBtn.setText("Off");
            toggleBtn.setStyle("-fx-background-color: #cb0508");

            clearAllSearchFields();
            personTable.setItems(TABLE_DATA);
        }
    }
}
