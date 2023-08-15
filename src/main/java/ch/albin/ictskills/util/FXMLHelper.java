package ch.albin.ictskills.util;

import ch.albin.ictskills.assets.Assets;
import ch.albin.ictskills.controller.Controller;
import ch.albin.ictskills.controller.model.ModelController;
import javafx.fxml.FXMLLoader;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

public class FXMLHelper {
    public static <T> T load(Assets asset) {
        FXMLLoader fxmlLoader = new FXMLLoader(asset.asUrl());
        T result;

        try {
            result = fxmlLoader.load();
            Controller controller = fxmlLoader.getController();

            if (controller != null){
                controller.init();
            }
        } catch (IOException | UnsupportedOperationException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    public static <T> T loadModel(Assets asset, Object o) {
        FXMLLoader fxmlLoader = new FXMLLoader(asset.asUrl());
        T result;

        try {
            result = fxmlLoader.load();
            ((ModelController)fxmlLoader.getController()).init(o);
        } catch (IOException | UnsupportedOperationException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public static <T> T loadModelWithoutController(Assets asset) {
        FXMLLoader fxmlLoader = new FXMLLoader(asset.asUrl());
        T result;

        try {
            result = fxmlLoader.load();
        } catch (IOException | UnsupportedOperationException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
    public static <T> T loadModelWithoutController(String file) {
        FXMLLoader fxmlLoader = null;
        try {
            fxmlLoader = new FXMLLoader(new File(file).toURI().toURL());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        T result;

        try {
            result = fxmlLoader.load();
        } catch (IOException | UnsupportedOperationException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}