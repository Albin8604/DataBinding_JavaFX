package ch.albin.ictskills.controller.model;

import ch.albin.ictskills.assets.Assets;
import ch.albin.ictskills.model.Person;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import java.io.ByteArrayInputStream;
public class ProfileControlController extends ModelController{
    public Text nameText;
    public HBox starsBox;
    public ImageView pbImage;

    @Override
    public void init() {}

    @Override
    public void init(Object o) {
        Person person = (Person) o;

        nameText.setText(person.getName());

        if (person.getImage() == null){
            pbImage.setImage(new Image(Assets.UnknownPB.asUrl().toString()));
        }else {
            pbImage.setImage(new Image(new ByteArrayInputStream(person.getImage())));
        }

    }
}
