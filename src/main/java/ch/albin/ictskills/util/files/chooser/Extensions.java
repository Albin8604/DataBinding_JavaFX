package ch.albin.ictskills.util.files.chooser;

import javafx.stage.FileChooser;

public enum Extensions {
    CSV,
    JSON,
    PNG,
    JPG,
    BMP,
    JPEG;

    public FileChooser.ExtensionFilter getExtensionFilter(){
        return new FileChooser.ExtensionFilter(this.name()+" File","*."+this.name().toLowerCase());
    }
}
