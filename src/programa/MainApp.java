package programa;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        // "/" reiskia resources aplanko sakni
        FXMLLoader loader = new FXMLLoader(MainApp.class.getResource("/login-view.fxml"));
        Scene scene = new Scene(loader.load(), 400, 300);
        stage.setTitle("Augintinių priežiūros sistema");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
