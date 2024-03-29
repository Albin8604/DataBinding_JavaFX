package ch.albin.ictskills.model.viewModel;

import ch.albin.ictskills.assets.Assets;
import ch.albin.ictskills.model.Person;
import ch.albin.ictskills.util.FXMLHelper;
import ch.albin.ictskills.util.ui.annotation.TableIgnore;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class PersonView {
    private Person person;
    private SimpleIntegerProperty pNr;
    private SimpleStringProperty name;
    private SimpleStringProperty vorname;
    private SimpleStringProperty tel;
    private BooleanProperty aktiv;
    private SimpleObjectProperty<BorderPane> profile;

    @TableIgnore
    private SimpleObjectProperty<Byte[]> image;

    public PersonView(Person person, TableView<PersonView> tableView) {
        this.person = person;

        pNr = new SimpleIntegerProperty(person.getPNr() == null ? 0 : person.getPNr());
        name = new SimpleStringProperty(person.getName());
        vorname = new SimpleStringProperty(person.getVorname());
        tel = new SimpleStringProperty(person.getTel());

        TableColumn<PersonView, Boolean> tableColumn = (TableColumn<PersonView, Boolean>) tableView.getColumns().stream().filter(item -> item.getText().equalsIgnoreCase("Aktiv")).toList().get(0);

        tableColumn.setCellFactory(col -> {
            TableCell<PersonView, Boolean> cell = new TableCell<>();
            cell.itemProperty().addListener((obs, old, newVal) -> {
                if (newVal != null) {
                    Node centreBox = getImageView(newVal);
                    cell.graphicProperty().bind(Bindings.when(cell.emptyProperty()).then((Node) null).otherwise(centreBox));
                }
            });
            return cell;
        });

        aktiv = new SimpleBooleanProperty(person.getAktiv());
        image= new SimpleObjectProperty<>(ArrayUtils.toObject(person.getImage()));
        profile= new SimpleObjectProperty<>(FXMLHelper.loadModel(Assets.ProfileControl,getPerson()));

        image.addListener(
                ChangeListener -> {
                    getPerson().setImage(ArrayUtils.toPrimitive(image.getValue()));
                    profile.set(FXMLHelper.loadModel(Assets.ProfileControl,getPerson()));
                }
        );
    }

    public PersonView() {
    }

    private ImageView getImageView(boolean bool) {
        ImageView imageView = new ImageView();
        imageView.setFitHeight(25);
        imageView.setFitWidth(25);
        try {
            imageView.setImage(getImage(bool));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return imageView;
    }

    private Image getImage(boolean aktiv) throws IOException {
        if (aktiv) {
            return new Image(Files.newInputStream(Path.of(Assets.ThumbsUp.asUrl().getPath())));
        } else {
            return new Image(Files.newInputStream(Path.of(Assets.ThumbsDown.asUrl().getPath())));
        }
    }

    public int getpNr() {
        return pNr.get();
    }

    public SimpleIntegerProperty pNrProperty() {
        return pNr;
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public String getVorname() {
        return vorname.get();
    }

    public SimpleStringProperty vornameProperty() {
        return vorname;
    }

    public String getTel() {
        return tel.get();
    }

    public SimpleStringProperty telProperty() {
        return tel;
    }

    public void setpNr(int pNr) {
        this.pNr.set(pNr);
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public void setVorname(String vorname) {
        this.vorname.set(vorname);
    }

    public void setTel(String tel) {
        this.tel.set(tel);
    }

    /**
     * gets the value of person
     */
    public Person getPerson() {
        return person;
    }

    public boolean getAktiv() {
        return aktiv.get();
    }

    public BooleanProperty aktivProperty() {
        return aktiv;
    }

    public void setAktiv(boolean aktiv) {
        this.aktiv.set(aktiv);
    }

    public boolean isAktiv() {
        return aktiv.get();
    }

    public BorderPane getProfile() {
        return profile.get();
    }

    public SimpleObjectProperty<BorderPane> profileProperty() {
        return profile;
    }

    public void setProfile(BorderPane profile) {
        this.profile.set(profile);
    }

    /**
     * sets the value of person
     *
     * @return PersonViewTemp
     */
    public PersonView setPerson(Person person) {
        this.person = person;
        return this;
    }

    public Byte[] getImage() {
        return image.get();
    }

    public SimpleObjectProperty<Byte[]> imageProperty() {
        return image;
    }

    public void setImage(Byte[] image) {
        this.image.set(image);
    }

    @Override
    public String toString() {
        return "PersonView{" +
                "person=" + person +
                ", pNr=" + pNr +
                ", name=" + name +
                ", vorname=" + vorname +
                ", tel=" + tel +
                ", aktiv=" + aktiv +
                '}';
    }
}
