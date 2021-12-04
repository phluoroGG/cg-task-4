package com.vsu.cgcourse;

import com.vsu.cgcourse.math.Matrix4f;
import com.vsu.cgcourse.math.Vector3f;
import com.vsu.cgcourse.obj_writer.ObjWriter;
import com.vsu.cgcourse.render_engine.GraphicConveyor;
import javafx.fxml.FXML;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;


import com.vsu.cgcourse.model.Mesh;
import com.vsu.cgcourse.obj_reader.ObjReader;
import com.vsu.cgcourse.render_engine.Camera;
import com.vsu.cgcourse.render_engine.RenderEngine;

public class GuiController {

    final private float TRANSLATION = 5F;
    final private float SCALE = 1.5F;

    @FXML
    AnchorPane anchorPane;

    @FXML
    private Canvas canvas;

    private final ArrayList<Mesh> meshes = new ArrayList<>();
    private final ArrayList<Mesh> selectedMeshes = new ArrayList<>();

    private boolean isWhite = true;

    private final Camera camera = new Camera(
            new Vector3f(0, 0, 100),
            new Vector3f(0, 0, 0),
            1.0F, 1, 0.01F, 100);

    private Timeline timeline;

    @FXML
    private void initialize() {
        anchorPane.setOnScroll(scrollEvent -> {
            if (scrollEvent.getDeltaY() > 0) {
                camera.setPosition(new Vector3f(camera.getPosition().vector[0] / SCALE,
                        camera.getPosition().vector[1] / SCALE, camera.getPosition().vector[2] / SCALE));
            } else {
                camera.setPosition(new Vector3f(camera.getPosition().vector[0] * SCALE,
                        camera.getPosition().vector[1] * SCALE, camera.getPosition().vector[2] * SCALE));
            }
        });
        anchorPane.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseEvent -> {
            double x = mouseEvent.getSceneX();
            double y = mouseEvent.getSceneY();
            anchorPane.addEventHandler(MouseEvent.MOUSE_DRAGGED, mouseEvent1 ->
                    camera.movePosition(new Vector3f((float) (mouseEvent1.getSceneX() - x) / 100,
                            (float) (mouseEvent1.getSceneY() - y) / 100,
                            camera.findZ((float) ((mouseEvent1.getSceneX() - x) / 100),
                                    (float) ((mouseEvent1.getSceneY() - y) / 100)))));
        });
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);

        KeyFrame frame = new KeyFrame(Duration.millis(15), event -> {
            double width = canvas.getWidth();
            double height = canvas.getHeight();

            canvas.getGraphicsContext2D().clearRect(0, 0, width, height);
            camera.setAspectRatio((float) (width / height));

            GraphicsContext gc = canvas.getGraphicsContext2D();
            if (isWhite) {
                gc.setStroke(Color.BLACK);
            } else {
                gc.setStroke(Color.WHITE);
            }

            for (Mesh mesh : meshes) {
                if (mesh != null) {
                    RenderEngine.render(gc, camera, mesh, (int) width, (int) height);
                }
            }
        });

        timeline.getKeyFrames().add(frame);
        timeline.play();
    }

    @FXML
    private void setStyleWhite() {
        anchorPane.setStyle("-fx-background-color: #f4f4f4");
        isWhite = true;
    }

    @FXML
    private void setStyleGrey() {
        anchorPane.setStyle("-fx-background-color: #2b2b2b");
        isWhite = false;
    }

    @FXML
    private void loadModel() throws IOException {
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

        String fileContent = Files.readString(fileName);
        meshes.add(ObjReader.read(fileContent));
    }

    @FXML
    private void saveModel() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") +
                "/src/main/resources/com/vsu/cgcourse/models"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        Path fileName = Path.of(file.getAbsolutePath());

        String content = ObjWriter.write(selectedMeshes.get(0));
        Files.write(fileName, Collections.singleton(content));
    }

    @FXML
    private void saveModelWithChanges() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir") +
                "/src/main/resources/com/vsu/cgcourse/models"));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Model (*.obj)", "*.obj"));
        fileChooser.setTitle("Save Model");

        File file = fileChooser.showSaveDialog(canvas.getScene().getWindow());

        Path fileName = Path.of(file.getAbsolutePath());


        Mesh mesh = selectedMeshes.get(0).copy();
        Matrix4f matrix = mesh.getModelMatrix();
        for (int i = 0; i < mesh.vertices.size(); i++) {
            mesh.vertices.set(i, GraphicConveyor.multiplyMatrix4ByVector3(matrix, mesh.vertices.get(i)));
        }

        String content = ObjWriter.write(mesh);
        Files.write(fileName, Collections.singleton(content));
    }

    @FXML
    private void scaleModel() {
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setScale(new Vector3f(
                        newValue.floatValue(), mesh.getScale().vector[1], mesh.getScale().vector[2]));
            }
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setScale(new Vector3f(
                        mesh.getScale().vector[0], newValue.floatValue(), mesh.getScale().vector[2]));
            }
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setScale(new Vector3f(
                        mesh.getScale().vector[0], mesh.getScale().vector[1], newValue.floatValue()));
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lblX, sliderX, lblY, sliderY, lblZ, sliderZ);
        Scene scene = new Scene(root, 300, 300);
        Stage window = new Stage();
        window.setTitle("Scale");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void rotateModel() {
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setRotation(new Vector3f((float) (newValue.floatValue() * Math.PI / 180),
                        mesh.getRotation().vector[1], mesh.getRotation().vector[2]));
            }
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setRotation(new Vector3f(mesh.getRotation().vector[0],
                        (float) (newValue.floatValue() * Math.PI / 180), mesh.getRotation().vector[2]));
            }
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setRotation(new Vector3f(mesh.getRotation().vector[0],
                        mesh.getRotation().vector[1], (float) (newValue.floatValue() * Math.PI / 180)));
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lblX, sliderX, lblY, sliderY, lblZ, sliderZ);
        Scene scene = new Scene(root, 300, 300);
        Stage window = new Stage();
        window.setTitle("Rotation");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void translateModel() {
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setTranslation(new Vector3f(newValue.floatValue(),
                        mesh.getTranslation().vector[1], mesh.getTranslation().vector[2]));
            }
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setTranslation(new Vector3f(mesh.getTranslation().vector[0],
                        newValue.floatValue(), mesh.getTranslation().vector[2]));
            }
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
            for (Mesh mesh : selectedMeshes) {
                mesh.setTranslation(new Vector3f(mesh.getTranslation().vector[0],
                        mesh.getTranslation().vector[1], newValue.floatValue()));
            }
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10, lblX, sliderX, lblY, sliderY, lblZ, sliderZ);
        Scene scene = new Scene(root, 300, 300);
        Stage window = new Stage();
        window.setTitle("Translation");
        window.setScene(scene);
        window.show();
    }

    @FXML
    private void selectModel() {
        ArrayList<CheckBox> checkBoxes = new ArrayList<>();
        for (Mesh mesh : meshes) {
            CheckBox checkBox = new CheckBox(mesh.getName());
            if (selectedMeshes.contains(mesh)) {
                checkBox.setSelected(true);
            }
            checkBox.setAllowIndeterminate(false);
            checkBox.setOnAction(event -> {
                if (checkBox.isSelected()) {
                    selectedMeshes.add(mesh);
                } else {
                    selectedMeshes.remove(mesh);
                }
            });
            checkBoxes.add(checkBox);
        }
        Button button = new Button("Delete");
        button.setOnAction(event -> {
            for (Mesh mesh : selectedMeshes) {
                meshes.remove(mesh);
            }
            selectedMeshes.clear();
            button.getScene().getWindow().hide();
        });

        FlowPane root = new FlowPane(Orientation.VERTICAL, 10, 10);
        root.getChildren().addAll(checkBoxes);
        root.getChildren().add(button);
        Scene scene = new Scene(root, 250, 500);
        Stage window = new Stage();
        window.setTitle("Model Selection");
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