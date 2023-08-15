package ch.albin.ictskills.assets;

import java.net.URL;

public enum Assets {

    StandartStyle("css/standartStyle.css"),
    Main("Main.fxml"),
    MANIFEST("META-INF/MANIFEST.MF"),
    ThumbsUp("img/thumbsUp.png"),
    ThumbsDown("img/thumbsDown.png"),
;
    final String filename;

    Assets(String filename){
        this.filename = filename;
    }

    public URL asUrl(){
        return Assets.class.getResource("/"+filename);
    }
}