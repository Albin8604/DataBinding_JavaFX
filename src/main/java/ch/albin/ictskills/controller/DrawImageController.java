package ch.albin.ictskills.controller;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import javax.imageio.ImageIO;
import javax.swing.event.ChangeListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class DrawImageController extends Controller {

    public static final File outputFile = new File("/Users/albin/Downloads/test.png");
    public Canvas canvas;
    public Stage stage;
    public Text sizeText;
    private SimpleIntegerProperty size = new SimpleIntegerProperty(5);

    @Override
    public void init() {
        StringConverter<? extends Number> stringConverter = new IntegerStringConverter();

        Bindings.bindBidirectional(sizeText.textProperty(),size,(StringConverter<Number>) stringConverter);

        size.addListener(ChangeListener -> {
            canvas.getGraphicsContext2D().setLineWidth(size.get());
        });
    }

    public void setStart(MouseEvent mouseEvent){
        double centerX = mouseEvent.getX() - size.get();
        double centerY = mouseEvent.getY() - size.get();
        canvas.getGraphicsContext2D().moveTo(centerX, centerY);
    }
    public void draw(MouseEvent mouseEvent) {
        double centerX = mouseEvent.getX() - size.get();
        double centerY = mouseEvent.getY() - size.get();
        canvas.getGraphicsContext2D().lineTo(centerX,centerY);
        canvas.getGraphicsContext2D().stroke();
    }

    public void save() {
        writeImage();
        MainController.setDrawnProfilePic(outputFile);
        stage.close();
    }

    private void writeImage(){
        WritableImage writableImage = new WritableImage((int) canvas.getWidth(), (int) canvas.getHeight());
        SnapshotParameters params = new SnapshotParameters();
        params.setFill(Color.WHITE);
        canvas.snapshot(params, writableImage);

        try {
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(writableImage, null);
            ImageIO.write(bufferedImage, "png", DrawImageController.outputFile);
            System.out.println("Image saved to " + DrawImageController.outputFile.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error saving image to " + DrawImageController.outputFile.getAbsolutePath());
        }
    }

    public void sizeUp(ActionEvent actionEvent) {
        size.set(size.get()+1);
    }

    public void sizeDown(ActionEvent actionEvent) {
        if (size.get() > 0){
            size.set(size.get()-1);
        }
    }
}
