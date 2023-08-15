package ch.albin.ictskills.controller;

import ch.albin.ictskills.model.Person;
import ch.albin.ictskills.model.viewModel.PersonView;
import ch.albin.ictskills.util.ui.TableManager;
import ch.albin.ictskills.util.ui.UIAlertMsg;
import ch.albin.ictskills.util.ui.UIHelper;
import javafx.beans.Observable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import static ch.albin.ictskills.util.Validator.*;
import static ch.albin.ictskills.util.ui.UIHelper.addRegexValidationToControl;

public class MainController extends Controller {

    public PieChart pieChart;
    private boolean isNewPerson = false;
    private static final ObservableList<Person> TEST_PERSON_LIST = FXCollections.observableList(PERSON_DAO.selectAll());
    private PersonView lastSelectedPerson = null;
    public TextField pNrTextField;
    public TextField nameTextField;
    public TextField vornameTextField;
    public TextField telTextField;
    public CheckBox aktivCheckBox;
    public TableView<PersonView> personTable;
    private static final PieChart.Data AKTIV_DATA = new PieChart.Data("Aktiv",0);
    private static final PieChart.Data INAKTIV_DATA = new PieChart.Data("Inaktiv",0);
    @Override
    public void init() {

        addRegexValidationToControl(telTextField,TEL_REGEX);
        addRegexValidationToControl(pNrTextField,getNumberRegexInRange(6,8));
        addRegexValidationToControl(nameTextField,ONLY_LETTER_REGEX);
        addRegexValidationToControl(vornameTextField,ONLY_LETTER_REGEX);

        ObservableList<PersonView> orc = FXCollections.observableArrayList(
                obs -> new Observable[] {obs.aktivProperty()}
        );

        AKTIV_DATA.pieValueProperty().bind(
                Bindings.size(orc.filtered(PersonView::getAktiv))
        );

        INAKTIV_DATA.pieValueProperty().bind(
                Bindings.size(orc.filtered(person -> !person.getAktiv()))
        );

        orc.addAll(TEST_PERSON_LIST.stream().map(person -> new PersonView(person, personTable,this)).toList());

        TableManager.initColumns(orc,personTable);

        updateTable();

        personTable.getSelectionModel().selectFirst();
        clickedOnTable();

        pieChart.setLabelsVisible(true);
        pieChart.getData().addAll(AKTIV_DATA, INAKTIV_DATA);
    }
    private void updateTable(){
        final ObservableList<PersonView> PERSON_VIEW_LIST = FXCollections.observableArrayList(
                TEST_PERSON_LIST.stream().map(person -> new PersonView(person, personTable,this)).toList()
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
        }

        StringConverter<? extends Number> stringConverter = new IntegerStringConverter();

        Bindings.bindBidirectional(pNrTextField.textProperty(), personView.pNrProperty(), ((StringConverter<Number>) stringConverter));
        Bindings.bindBidirectional(nameTextField.textProperty(), personView.nameProperty());
        Bindings.bindBidirectional(vornameTextField.textProperty(), personView.vornameProperty());
        Bindings.bindBidirectional(telTextField.textProperty(), personView.telProperty());
        Bindings.bindBidirectional(aktivCheckBox.selectedProperty(), personView.aktivProperty());

        lastSelectedPerson = personView;
    }

    private boolean isInputValid(){
        return (boolean)pNrTextField.getUserData() && (boolean)nameTextField.getUserData() && (boolean)vornameTextField.getUserData() && (boolean)telTextField.getUserData();
    }

    public void save() {
        PersonView selectedItem = personTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null){
            return;
        }

        if (!isInputValid()){
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR, UIAlertMsg.INVALID_INPUT);
            return;
        }

        try {
            if (isNewPerson){
                PERSON_DAO.addOne(new Person(selectedItem));
                isNewPerson = false;
                return;
            }

            PERSON_DAO.update(new Person(selectedItem));

            personTable.getSelectionModel().selectFirst();
            clickedOnTable();

            UIHelper.createAndShowAlert(Alert.AlertType.INFORMATION,UIAlertMsg.PERSON_SAVED);
        }catch (Exception e){
            UIHelper.createAndShowAlert(Alert.AlertType.ERROR,e.getMessage());
        }

    }

    public void add() {
        TEST_PERSON_LIST.add(new Person());

        updateTable();

        personTable.getSelectionModel().selectLast();

        clickedOnTable();

    }

    public void delete(ActionEvent actionEvent) {
        PersonView selectedItem = personTable.getSelectionModel().getSelectedItem();

        if (selectedItem == null){
            return;
        }

        personTable.getItems().remove(selectedItem);
        PERSON_DAO.deleteOne(selectedItem.getPerson());

        UIHelper.createAndShowAlert(Alert.AlertType.INFORMATION,UIAlertMsg.PERSON_DELETED);

    }
}
