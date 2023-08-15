package ch.albin.ictskills;

import ch.albin.ictskills.assets.Assets;
import ch.albin.ictskills.util.FXMLHelper;
import javafx.application.Application;
import javafx.stage.Stage;

public class Starter extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage stage) throws Exception {
        Stage primaryStage = FXMLHelper.load(Assets.Main);
        primaryStage.show();
    }
}
