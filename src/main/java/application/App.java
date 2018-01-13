package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class App extends Application {
    private Stage window;
    private BorderPane layout;
    private Label sign;
    private Button config, sqlConsole, overView, logs;
    private VBox buttons;


    public static void main(String[] args) {
        System.out.println("started!");
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Calco DevOps");
        window.setMinHeight(800);
        window.setMinWidth(1000);
        sign = new Label("Calco Ops");


        config = new Button("Configure Application");
        sqlConsole = new Button("Database Console");
        logs = new Button("Logs");
        overView = new Button("employee overview");


        buttons = new VBox();
        buttons.getChildren().addAll(config, sqlConsole, logs, overView);
        layout = new BorderPane();
        layout.setTop(sign);
        layout.setLeft(buttons);
        Scene startScreen = new Scene(layout);
        window.setScene(startScreen);
        window.show();
    }
}
