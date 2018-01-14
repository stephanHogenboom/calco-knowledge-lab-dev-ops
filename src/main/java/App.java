import cache.ConfigCache;
import config.ConfigScreen;
import csvInserter.CSVInsertService;
import csvInserter.CsvInserts;
import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import masterclassers.CompanyOverviewScreen;
import masterclassers.MasterClassDAO;
import masterclassers.OverViewScreen;
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
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
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
    private final OverViewScreen overViewInitObject = new OverViewScreen();
    private final SQLConsoleScreen sqlScreenInitObject = new SQLConsoleScreen();
    private final ConfigScreen configScreenInitObject = new ConfigScreen();
    private final CompanyOverviewScreen companyOverviewScreenInitObject = new CompanyOverviewScreen();
    private final Button csvButton = new Button("send csv!");
    public static void main(String[] args) {
        setup();
        configureFlywayAndMigrateDataBase();
        ScheduledExecutorService service = Executors.newScheduledThreadPool(1);
        try {
            service.schedule(() -> {
                int i = 0;
                CSVInsertService insertService = new CSVInsertService();
                for (String csv : CsvInserts.insertList) {
                    try {
                        i++;
                        insertService.insertMasterClasser(csv);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    if (i == 5) {
                        ConfigCache cache = new ConfigCache();
                        MasterClassDAO dao = new MasterClassDAO();
                        try {
                            dao.executeQueryPlain("update reg_item set reg_value = ',:' where reg_name = 'delimiter'");
                            cache.resetRegistryCache();
                            i++;
                            System.out.println("sabotage done!");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        cache.resetRegistryCache();
                    }
                    try {
                        Thread.sleep(20000);
                    } catch (InterruptedException e) {
                        System.out.println(e.getMessage());
                    }

                }
            }, 3, TimeUnit.SECONDS);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        launch(args);
    }

    private static void setup() {
        System.out.println("started!");
        Path currentDir = Paths.get(System.getProperty("user.dir"));
        String logDir = currentDir.toString().concat("/log/" + (LocalDate.now().getYear()));
        System.out.println(logDir);
        File logFile = new File(logDir);
        SimpleLogConfig config = new SimpleLogConfig(logFile);
        config.configureLogging();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
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
        sign = new Label("Calco Master class tracker");
        sign.setMinWidth(400);
        sign.setFont(new Font("Arial", 30));
        sign.setTextFill(Color.valueOf("#0c4cb2"));
        csvButton.setOnAction(e -> sendCsv());

        logArea.setWrapText(true);
        logArea.setPrefHeight(1000);
        logArea.setPrefWidth(600);
        logButton.setOnAction(e -> updateLogging());
        logCompartment.getChildren().addAll(logArea, logButton);

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

        layout.setTop(sign);
        layout.setCenter(tabPane);
        layout.setLeft(logCompartment);
        startScreen.getStylesheets().add("index.css");
        window.setScene(startScreen);
        window.show();
    }

    private void updateLogging() {
        Path logDir = Paths.get(System.getProperty("user.dir")+"/log/"+LocalDate.now().getYear()+
                "/"+LocalDate.now().getMonth().name()+LocalDate.now().getDayOfMonth());
        try {
            logArea.clear();
            Stream<String> lines = Files.lines(logDir);
            lines.forEach(e -> logArea.appendText(e+"\n"));
        } catch (IOException e) {
            e.printStackTrace();
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
            System.err.println("some thing went wrong during the migration of the dataBase.");
            System.out.println("Does the base url point to your db? Or is your migration out of sync?");
            System.exit(1);
        }
    }

    private void sendCsv() {
        CSVInsertService inserter = new CSVInsertService();
        inserter.insertWithPrefab();

    }

}