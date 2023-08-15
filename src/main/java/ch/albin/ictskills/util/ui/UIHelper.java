package ch.albin.ictskills.util.ui;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Control;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.util.regex.Pattern;

public class UIHelper {
    public static void createAndShowAlert(Alert.AlertType alertType, UIAlertMsg uiAlertMsg){
        createAndShowAlert(alertType,uiAlertMsg.msg);
    }
    public static void createAndShowAlert(Alert.AlertType alertType, String uiAlertMsg){
        createAndShowAlert(alertType,uiAlertMsg,null);
    }
    public static void createAndShowAlert(Alert.AlertType alertType, String uiAlertMsg, ImageView graphic){

        String title;
        String header;

        if (alertType == Alert.AlertType.ERROR){
            title = "ERROR :/";
            header = "An error occurred";
        } else if (alertType == Alert.AlertType.WARNING) {
            title = "WARNING";
            header = "A warning occurred";
        } else if (alertType == Alert.AlertType.INFORMATION) {
            title = "Information";
            header = "We have an information for you";
        } else if (alertType == Alert.AlertType.CONFIRMATION) {
            title = "Confirmation";
            header = "Here's a confirmation for you";
        }else {
            throw new RuntimeException();
        }

        alert(alertType,graphic,title,header, uiAlertMsg);
    }

    private static void alert(Alert.AlertType feedbackType, ImageView graphic, String title, String headerText, String bodyText) {
        Alert alert = new Alert(feedbackType);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(bodyText);

        if (graphic != null){
            alert.setGraphic(graphic);
        }

        alert.showAndWait();
    }

    public static void initIntegerSpinner(Spinner<Integer> spinner,int min,int max,int startWith){
        SpinnerValueFactory<Integer> spinnerValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(min, max, startWith);
        spinner.setValueFactory(spinnerValueFactory);
    }

    public static void initDoubleSpinner(Spinner<Double> spinner,double min,double max,double startWith){
        SpinnerValueFactory<Double> spinnerValueFactory = new SpinnerValueFactory.DoubleSpinnerValueFactory(min, max, startWith);
        spinner.setValueFactory(spinnerValueFactory);
    }

    public static void cleanCanvas(Canvas canvas){
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public static void cleanPane(Pane pane){
        pane.getChildren().clear();
    }

    public static Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();

        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }

        return result;
    }

    public static void addRegexValidationToControl(TextField control, String regex){
        TextFormatter<?> textFormatter = new TextFormatter<>(change -> {
            if (change.getControlNewText().matches(regex)) {
                control.getStyleClass().remove("invalid-input");
                control.setUserData(true);
                return change;
            } else {
                control.getStyleClass().add("invalid-input");
                control.setUserData(false);
                return change;
            }
        });

        control.setTextFormatter(textFormatter);
    }
}
