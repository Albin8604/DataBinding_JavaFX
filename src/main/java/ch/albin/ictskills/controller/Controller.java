package ch.albin.ictskills.controller;


import ch.albin.ictskills.db.DAO;
import ch.albin.ictskills.model.Person;
import ch.albin.ictskills.model.viewModel.DiagrammChooserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;

public abstract class Controller {
    protected static final DAO<Person> PERSON_DAO = new DAO<>(Person.class);
    protected static final ObservableList<DiagrammChooserModel> DIAGRAMM_CHOOSER_MODEL_LIST = FXCollections.observableArrayList(
            new DiagrammChooserModel("Kreisdiagramm",1),
            new DiagrammChooserModel("Balkendiagramm",2),
            new DiagrammChooserModel("Scatterchart",3)
    );

    protected Image drawnImage = null;
    public abstract void init();
}