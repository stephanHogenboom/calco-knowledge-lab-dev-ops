import config.ConfigScreen;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.stage.StageStyle;
import masterclassers.OverViewScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.flywaydb.core.Flyway;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import sql.console.SQLConsoleScreen;

public class App extends Application {
    private Stage window;
    private BorderPane layout;
    private Label sign;
    private Button logs;
    private VBox buttons;
    private Scene startScreen;
    private static final String dataBaseUrl = "jdbc:sqlite:knowledgeLab.db";
    private static final String migrationLocation = "classpath:db.migration";
    private TabPane tabPane = new TabPane();
    private OverViewScreen overViewInitObject = new OverViewScreen();
    private SQLConsoleScreen sqlScreenInitObject = new SQLConsoleScreen();
    private ConfigScreen configScreenInitObject = new ConfigScreen();

    public static void main(String[] args) {
        System.out.println("started!");
        configureFlywayAndMigrateDataBase();
        launch(args);
        System.out.println(System.getProperty("user.dir"));
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.initStyle(StageStyle.UNIFIED);
        window = primaryStage;
        window.setTitle("Calco DevOps");
        window.setMinHeight(1000);
        window.setMinWidth(1500);
        layout = new BorderPane();
        startScreen = new Scene(layout);
        startScreen.getStylesheets().addAll("index.css");
        sign = new Label("Calco Master class tracker");
        sign.setMinWidth(400);
        sign.setFont(new Font("Arial", 30));
        sign.setTextFill(Color.valueOf("#0c4cb2"));


        logs = new Button("Logs");


        buttons = new VBox();
        buttons.getChildren().addAll(logs);


        Tab tab = new Tab();
        tab.setText("master classers");
        BorderPane masterClassScreen = overViewInitObject.InitOverviewScreen();
        tab.setContent(masterClassScreen);
        tabPane.getTabs().add(tab);

        Tab tab2 = new Tab();
        tab2.setText("sql console");
        VBox sqlscreen = sqlScreenInitObject.initSqlCOnsoleScreen();
        tab2.setContent(sqlscreen);
        tabPane.getTabs().add(tab2);

        Tab tab3 = new Tab();
        tab3.setText("configurations");
        HBox configScreen = configScreenInitObject.initConfigScreen();
        tab3.setContent(configScreen);
        tabPane.getTabs().add(tab3);

        layout.setTop(sign);
        layout.setLeft(buttons);
        layout.setCenter(tabPane);
        startScreen.getStylesheets().add("index.css");
        window.setScene(startScreen);
        window.show();
    }

    private static void configureFlywayAndMigrateDataBase() {
        try {
            Flyway flyway = new Flyway();
            SQLiteConfig config = new SQLiteConfig();
            config.setJournalMode(SQLiteConfig.JournalMode.WAL);
            config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(dataBaseUrl);
            flyway.setLocations(migrationLocation);
            flyway.setDataSource(dataSource);
            flyway.migrate();
        } catch (Exception e) {
            System.err.println("some thing went wrong during the migration of the dataBase.");
            System.out.println("Does the base url point to your db? Or is your migration out of sync?");
            System.exit(1);
        }
    }

}