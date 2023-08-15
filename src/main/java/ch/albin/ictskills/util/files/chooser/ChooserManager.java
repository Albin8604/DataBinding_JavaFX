package ch.albin.ictskills.util.files.chooser;

import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class ChooserManager {
    public static File getChoosedSaveFile(Stage stage, Extensions... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                Arrays.stream(extensions)
                        .map(Extensions::getExtensionFilter
                        ).toList()
        );

        File file = fileChooser.showSaveDialog(stage);

        if (file == null) {
            throw new RuntimeException("File not saved");
        }

        return file;
    }

    public static File getChoosedOpenFile(Stage stage, Extensions... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                Arrays.stream(extensions)
                        .map(Extensions::getExtensionFilter
                        ).toList()
        );

        File file = fileChooser.showOpenDialog(stage);

        if (file == null) {
            throw new RuntimeException("File could not be opened");
        }

        return file;
    }
    public static File getChoosedOpenFolder(Stage stage) {
        DirectoryChooser directoryChooser = new DirectoryChooser();

        File file = directoryChooser.showDialog(stage);

        if (file == null) {
            throw new RuntimeException("File could not be opened");
        }

        return file;
    }
    public static List<File> getChoosedOpenFiles(Stage stage, Extensions... extensions) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                Arrays.stream(extensions)
                        .map(Extensions::getExtensionFilter
                        ).toList()
        );

        List<File> file = fileChooser.showOpenMultipleDialog(stage);

        if (file == null) {
            throw new RuntimeException("Files could not be opened");
        }

        return file;
    }
}
