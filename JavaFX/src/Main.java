import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public void start(Stage stage) throws Exception{
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/graphique/fenetre.fxml")));

        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Convertor.io");
        // Icons made by Freepik from www.flaticon.com
        stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/graphique/icon.png"))));
        stage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}