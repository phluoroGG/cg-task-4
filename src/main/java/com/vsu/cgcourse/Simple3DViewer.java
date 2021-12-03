package com.vsu.cgcourse;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.Objects;

public class Simple3DViewer extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        Locale.setDefault(Locale.ROOT);
        Thread.setDefaultUncaughtExceptionHandler(Simple3DViewer::showError);
        AnchorPane viewport = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("fxml/gui.fxml")));

        Scene scene = new Scene(viewport);
        stage.setMinWidth(1600);
        stage.setMinHeight(900);
        viewport.prefWidthProperty().bind(scene.widthProperty());
        viewport.prefHeightProperty().bind(scene.heightProperty());

        stage.setTitle("Simple3DViewer");
        stage.setScene(scene);
        stage.setOnHidden(e -> Platform.exit());
        stage.show();
    }

    private static void showError(Thread t, Throwable e) {
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in " + t);

        }
    }

    private static void showErrorDialog(Throwable e) {
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Simple3DViewer.class.getResource("fxml/Error.fxml")));
        try {
            Parent root = loader.load();
            ((ErrorController) loader.getController()).setErrorText(e.getCause().getCause().getMessage());
            dialog.setScene(new Scene(root, 500, 100));
            dialog.setTitle("Error");
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}