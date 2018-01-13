package elements;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {
    public static void display(String title, String message) {
        Stage window = new Stage();

        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(200);

        Label label = new Label(message);

        Button closeButton = new Button("close window");
        closeButton.setOnAction(e -> window.close());

        VBox box = new VBox(10);
        box.getChildren().addAll(label, closeButton);
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box);
        window.setScene(scene);
        window.showAndWait();
    }
}
