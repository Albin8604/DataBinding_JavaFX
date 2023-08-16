package ch.albin.ictskills.assets;

import java.net.URL;

public enum Assets {

    StandartStyle("/css/standartStyle.css"),
    ProfileControl("/profileControl.fxml"),
    Main("/Main.fxml"),
    MANIFEST("/META-INF/MANIFEST.MF"),
    ThumbsUp("/img/thumbsUp.png"),
    UnknownPB("/img/unknownPB.png"),
    ThumbsDown("/img/thumbsDown.png"),
    DrawImage("/DrawImage.fxml"),
;
    final String filename;

    Assets(String filename){
        this.filename = filename;
    }

    public URL asUrl(){
        return Assets.class.getResource(filename);
    }
}