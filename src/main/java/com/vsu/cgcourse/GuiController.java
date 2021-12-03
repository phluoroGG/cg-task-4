package com.vsu.cgcourse;

import com.vsu.cgcourse.math.Matrix4f;
import com.vsu.cgcourse.math.Vector3f;
import com.vsu.cgcourse.obj_writer.ObjWriter;
import com.vsu.cgcourse.render_engine.GraphicConveyor;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.Collections;


import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.RenderEngine;

public class GuiController {

    final private float TRANSLATION = 0.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private Mesh mesh = null;

    private final Camera camera = new Camera(
            new Vector3f(1, 1, 1),
            new Vector3f(0, 0, 0),
            new Vector3f(0, 0, 0),
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            if (mesh != null) {
                RenderEngine.render(canvas.getGraphicsContext2D(), camera, mesh, (int) width, (int) height);
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void loadModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") +
                "/src/main/resources/com/vsu/cgcourse/models"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Load Model");

        File file = fileChooser.showOpenDialog(canvas.getScene().getWindow());
        if (file == null) {
            return;
        }

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String fileContent = Files.readString(fileName);
            mesh = ObjReader.read(fileContent);
            // todo: обработка ошибок
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        camera.setScale(new Vector3f(1, 1, 1));
        camera.setRotation(new Vector3f(0, 0, 0));
        camera.setTranslation(new Vector3f(0, 0, 0));
    }

    @FXML
    private void saveModel() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") +
                "/src/main/resources/com/vsu/cgcourse/models"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        Path fileName = Path.of(file.getAbsolutePath());

        try {
            String content = ObjWriter.write(mesh);
            Files.write(fileName, Collections.singleton(content));
            // todo: обработка ошибок
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    private void saveModelWithChanges() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") +
                "/src/main/resources/com/vsu/cgcourse/models"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        Path fileName = Path.of(file.getAbsolutePath());

        Matrix4f matrix = camera.getModelMatrix();
        Mesh meshToSave = mesh.copy();
        for (int i = 0; i < meshToSave.vertices.size(); i++) {
            meshToSave.vertices.set(i, GraphicConveyor.multiplyMatrix4ByVector3(matrix, meshToSave.vertices.get(i)));
        }
        try {
            String content = ObjWriter.write(meshToSave);
            Files.write(fileName, Collections.singleton(content));
            // todo: обработка ошибок
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    @FXML
    public void scaleModel() {
        Label lblX = new Label("Scale X");

        Slider sliderX = new Slider(0.1, 10, 1);
        sliderX.setShowTickMarks(true);
        sliderX.setShowTickLabels(true);
        sliderX.setBlockIncrement(0.1);
        sliderX.setMajorTickUnit(3);
        sliderX.setMinorTickCount(2);
        sliderX.setSnapToTicks(false);

        sliderX.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblX.setText("Scale X: " + String.format("%.06f", newValue.floatValue()));
            camera.setScale(new Vector3f(
                    newValue.floatValue(), camera.getScale().vector[1], camera.getScale().vector[2]));
        });

        Label lblY = new Label("Scale Y");

        Slider sliderY = new Slider(0.1, 10, 1);
        sliderY.setShowTickMarks(true);
        sliderY.setShowTickLabels(true);
        sliderY.setBlockIncrement(0.1);
        sliderY.setMajorTickUnit(3);
        sliderY.setMinorTickCount(2);
        sliderY.setSnapToTicks(false);

        sliderY.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblY.setText("Scale Y: " + String.format("%.06f", newValue.floatValue()));
            camera.setScale(new Vector3f(
                    camera.getScale().vector[0], newValue.floatValue(), camera.getScale().vector[2]));
        });

        Label lblZ = new Label("Scale Z");

        Slider sliderZ = new Slider(0.1, 10, 1);
        sliderZ.setShowTickMarks(true);
        sliderZ.setShowTickLabels(true);
        sliderZ.setBlockIncrement(0.1);
        sliderZ.setMajorTickUnit(3);
        sliderZ.setMinorTickCount(2);
        sliderZ.setSnapToTicks(false);

        sliderZ.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblZ.setText("Scale Z: " + String.format("%.06f", newValue.floatValue()));
            camera.setScale(new Vector3f(
                    camera.getScale().vector[0], camera.getScale().vector[1], newValue.floatValue()));
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lblX, sliderX, lblY, sliderY, lblZ, sliderZ);
        Scene scene = new Scene(root, 300, 300);
        Stage window = new Stage();
        window.setTitle("Scale");
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void rotateModel() {
        Label lblX = new Label("Rotation X");

        Slider sliderX = new Slider(0, 360, 0);
        sliderX.setShowTickMarks(true);
        sliderX.setShowTickLabels(true);
        sliderX.setBlockIncrement(10);
        sliderX.setMajorTickUnit(90);
        sliderX.setMinorTickCount(2);
        sliderX.setSnapToTicks(false);

        sliderX.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblX.setText("Rotation X: " + String.format("%d", newValue.intValue()));
            camera.setRotation(new Vector3f((float) (newValue.floatValue() * Math.PI / 180),
                    camera.getRotation().vector[1], camera.getRotation().vector[2]));
        });

        Label lblY = new Label("Rotation Y");

        Slider sliderY = new Slider(0, 360, 0);
        sliderY.setShowTickMarks(true);
        sliderY.setShowTickLabels(true);
        sliderY.setBlockIncrement(10);
        sliderY.setMajorTickUnit(90);
        sliderY.setMinorTickCount(2);
        sliderY.setSnapToTicks(false);

        sliderY.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblY.setText("Rotation Y: " + String.format("%d", newValue.intValue()));
            camera.setRotation(new Vector3f(camera.getRotation().vector[0],
                    (float) (newValue.floatValue() * Math.PI / 180), camera.getRotation().vector[2]));
        });

        Label lblZ = new Label("Rotation Z");

        Slider sliderZ = new Slider(0, 360, 0);
        sliderZ.setShowTickMarks(true);
        sliderZ.setShowTickLabels(true);
        sliderZ.setBlockIncrement(10);
        sliderZ.setMajorTickUnit(90);
        sliderZ.setMinorTickCount(2);
        sliderZ.setSnapToTicks(false);

        sliderZ.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblZ.setText("Rotation Z: " + String.format("%d", newValue.intValue()));
            camera.setRotation(new Vector3f(camera.getRotation().vector[0],
                    camera.getRotation().vector[1], (float) (newValue.floatValue() * Math.PI / 180)));
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lblX, sliderX, lblY, sliderY, lblZ, sliderZ);
        Scene scene = new Scene(root, 300, 300);
        Stage window = new Stage();
        window.setTitle("Rotation");
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void translateModel() {
        Label lblX = new Label("Translation X");

        Slider sliderX = new Slider(-100, 100, 0);
        sliderX.setShowTickMarks(true);
        sliderX.setShowTickLabels(true);
        sliderX.setBlockIncrement(5);
        sliderX.setMajorTickUnit(20);
        sliderX.setMinorTickCount(1);
        sliderX.setSnapToTicks(false);

        sliderX.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblX.setText("Translation X: " + String.format("%d", newValue.intValue()));
            camera.setTranslation(new Vector3f(newValue.floatValue(),
                    camera.getTranslation().vector[1], camera.getTranslation().vector[2]));
        });

        Label lblY = new Label("Translation Y");

        Slider sliderY = new Slider(-100, 100, 0);
        sliderY.setShowTickMarks(true);
        sliderY.setShowTickLabels(true);
        sliderY.setBlockIncrement(5);
        sliderY.setMajorTickUnit(20);
        sliderY.setMinorTickCount(1);
        sliderY.setSnapToTicks(false);

        sliderY.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblY.setText("Translation Y: " + String.format("%d", newValue.intValue()));
            camera.setTranslation(new Vector3f(camera.getTranslation().vector[0],
                    newValue.floatValue(), camera.getTranslation().vector[2]));
        });

        Label lblZ = new Label("Translation Z");

        Slider sliderZ = new Slider(-100, 100, 0);
        sliderZ.setShowTickMarks(true);
        sliderZ.setShowTickLabels(true);
        sliderZ.setBlockIncrement(5);
        sliderZ.setMajorTickUnit(20);
        sliderZ.setMinorTickCount(1);
        sliderZ.setSnapToTicks(false);

        sliderZ.valueProperty().addListener((changed, oldValue, newValue) -> {
            lblZ.setText("Translation Z: " + String.format("%d", newValue.intValue()));
            camera.setTranslation(new Vector3f(camera.getTranslation().vector[0],
                    camera.getTranslation().vector[1], newValue.floatValue()));
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lblX, sliderX, lblY, sliderY, lblZ, sliderZ);
        Scene scene = new Scene(root, 300, 300);
        Stage window = new Stage();
        window.setTitle("Translation");
        window.setScene(scene);
        window.show();
    }

    @FXML
    public void handleCameraForward() {
        camera.movePosition(new Vector3f(0, 0, -TRANSLATION));
    }

    @FXML
    public void handleCameraBackward() {
        camera.movePosition(new Vector3f(0, 0, TRANSLATION));
    }

    @FXML
    public void handleCameraLeft() {
        camera.movePosition(new Vector3f(TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraRight() {
        camera.movePosition(new Vector3f(-TRANSLATION, 0, 0));
    }

    @FXML
    public void handleCameraUp() {
        camera.movePosition(new Vector3f(0, TRANSLATION, 0));
    }

    @FXML
    public void handleCameraDown() {
        camera.movePosition(new Vector3f(0, -TRANSLATION, 0));
    }
}