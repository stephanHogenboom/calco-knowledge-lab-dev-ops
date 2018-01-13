package elements;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox {
    public static boolean answer;
    static Stage ConfirmationWindow = new Stage();

    public static boolean display(String title, String message) {

        if (!ConfirmationWindow.getModality().equals(Modality.APPLICATION_MODAL)) {
            ConfirmationWindow.initModality(Modality.APPLICATION_MODAL);
        }

        ConfirmationWindow.setTitle(title);
        ConfirmationWindow.setMinWidth(200);

        Label label = new Label(message);

        Button confirmButton = new Button("yes");
        Button declineButton = new Button("no");


        confirmButton.setOnAction(e ->  handleAnswer(true));
        declineButton.setOnAction(e ->  handleAnswer(false));


        VBox box = new VBox(10);
        box.getChildren().addAll(label, confirmButton, declineButton);
        box.setAlignment(Pos.CENTER);

        Scene scene = new Scene(box);
        ConfirmationWindow.setScene(scene);
        ConfirmationWindow.showAndWait();
        return answer;
    }

    private static void handleAnswer(boolean answer) {
        ConfirmationBox.answer = answer;
        ConfirmationWindow.close();
    }
}
