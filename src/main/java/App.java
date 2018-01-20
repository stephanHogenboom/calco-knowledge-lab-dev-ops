import config.ConfigScreen;
import csvInserter.CSVInsertService;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import masterclassers.CompanyOverviewScreen;
import masterclassers.JobTypesScreen;
import masterclassers.MasterClasserOverViewScreen;
import org.flywaydb.core.Flyway;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import sql.console.SQLConsoleScreen;
import util.SimpleLogConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.stream.Stream;

public class App extends Application {
    private final TextArea logArea = new TextArea();
    private Stage window;
    private final Button logButton = new Button("update logFile");
    private BorderPane layout;
    private Label sign;
    private final VBox logCompartment = new VBox();
    private Scene startScreen;
    private static final String dataBaseUrl = "jdbc:sqlite:knowledgeLab.db";
    private static final String migrationLocation = "classpath:db.migration";
    private final TabPane tabPane = new TabPane();
    private final MasterClasserOverViewScreen overViewInitObject = new MasterClasserOverViewScreen();
    private final SQLConsoleScreen sqlScreenInitObject = new SQLConsoleScreen();
    private final ConfigScreen configScreenInitObject = new ConfigScreen();
    private final CompanyOverviewScreen companyOverviewScreenInitObject = new CompanyOverviewScreen();
    private final JobTypesScreen jobTypesScreenInitObject = new JobTypesScreen();
    public static void main(String[] args) {
        setup();
        configureFlywayAndMigrateDataBase();
        CSVInsertService service = new CSVInsertService();
        service.startInsertingThread();
        launch(args);
    }

    private static void setup() {
        // very crude logging setup that use tee-logging
        // TODO the path resolving should be extracted to a seperate method and made more robust
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        String logDir = currentDir.toString().concat("/log/" + (LocalDate.now().getYear()));
        System.out.println(logDir);
        File logFile = new File(logDir);
        SimpleLogConfig config = new SimpleLogConfig(logFile);
        config.configureLogging();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        System.out.println("started!");
        Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();
        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
        primaryStage.initStyle(StageStyle.UNIFIED);
        window = primaryStage;
        window.setTitle("Calco DevOps");
        window.setMinHeight(1000);
        window.setMinWidth(1500);
        layout = new BorderPane();
        startScreen = new Scene(layout);
        startScreen.getStylesheets().addAll("index.css");
        sign = new Label("Calco Master classer tracker");
        sign.setStyle("-fx-text-fill: linear-gradient(from 25% 25% to 100% 100%, #dc143c, #32cd32);-fx-font-weight:bold; -fx-font-size: 20");

        sign.setMinWidth(400);
        sign.setFont(new Font("Arial", 30));
        sign.setTextFill(Color.valueOf("#0c4cb2"));

        logArea.setWrapText(true);
        logArea.setPrefHeight(1000);
        logArea.setPrefWidth(600);
        logArea.setStyle("-fx-background-color: #DFF2BF;-fx-text-fill: #4F8A10;-fx-font-weight:bold;");
        logButton.setOnAction(e -> updateLogging());
        logCompartment.getChildren().addAll(logArea, logButton);

        //adding of the tabs in the main screen

        Tab tab = new Tab();
        tab.setOnSelectionChanged(e -> overViewInitObject.refreshMCS());
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
        tab3.setOnSelectionChanged(e -> configScreenInitObject.setConfigSummary());
        HBox configScreen = configScreenInitObject.initConfigScreen();
        tab3.setContent(configScreen);
        tabPane.getTabs().add(tab3);

        Tab tab4 = new Tab();
        tab4.setOnSelectionChanged(e -> companyOverviewScreenInitObject.refresh());
        tab4.setText("companies");
        BorderPane companyScreen = companyOverviewScreenInitObject.initCompanyScreen();
        tab4.setContent(companyScreen);
        tabPane.getTabs().add(tab4);

        Tab tab5 = new Tab();
        tab5.setOnSelectionChanged(e -> jobTypesScreenInitObject.refresh());
        tab5.setText("jobs");
        BorderPane jobScreen = jobTypesScreenInitObject.initJobScreen();
        tab5.setContent(jobScreen);
        tabPane.getTabs().add(tab5);
        tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

        layout.setTop(sign);
        layout.setCenter(tabPane);
        layout.setLeft(logCompartment);
        startScreen.getStylesheets().add("index.css");
        window.setScene(startScreen);
        window.show();
    }

    private void updateLogging() {
        Path logDir = Paths.get(System.getProperty("user.dir") + "/log/" + LocalDate.now().getYear() +
                "/"  + LocalDate.now().getMonth().name() + "-" + LocalDate.now().getDayOfMonth());
        try {
            logArea.clear();
            Stream<String> lines = Files.lines(logDir);
            lines.forEach(e -> logArea.appendText(e + "\n"));
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
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
            System.out.println("some thing went wrong during the migration of the dataBase.");
            System.out.println("Does the base url point to your db? Or is your migration out of sync?");
            System.exit(1);
        }
    }
}