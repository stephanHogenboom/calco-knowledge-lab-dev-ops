package masterclassers;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import masterclassers.model.Address;
import masterclassers.model.MasterClasser;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;


public class OverViewScreen {
    private BorderPane layout;
    private ListView<MasterClasser> masterClasserListView = new ListView<>();
    private Button  addButton;
    private final VBox bar = new VBox();
    private final MasterClassDAO dao = new MasterClassDAO();
    private VBox mcInfo = new VBox(10);
    private Label companyHeader, jobHeader, startHeader, endHeader, addressHeader, salaryHeader, incomeHeader, profitHeader;
    private Label company, job, start, end, address, salary, income, profit;

    public BorderPane InitOverviewScreen() {
        refreshMCS();
        addButton = new Button("add master classer");
        addButton.setOnAction(e -> {
            AddMasterClasserScreen screen = new AddMasterClasserScreen();
            screen.display();
            refreshMCS();
        });

        masterClasserListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        masterClasserListView.setOnMousePressed(e -> setLabels(masterClasserListView.getSelectionModel().getSelectedItem()));


        companyHeader = new Label("company");
        companyHeader.setPrefWidth(125);
        jobHeader = new Label("job");
        jobHeader.setPrefWidth(125);
        startHeader = new Label("start date");
        startHeader.setPrefWidth(125);
        endHeader = new Label("end date");
        endHeader.setPrefWidth(125);
        addressHeader = new Label("address");
        addressHeader.setPrefWidth(125);
        salaryHeader = new Label("monthly salary");
        salaryHeader.setPrefWidth(125);
        incomeHeader = new Label("monthly income");
        incomeHeader.setPrefWidth(125);
        profitHeader = new Label("monthly profit");
        profitHeader.setPrefWidth(125);


        company = new Label();
        job = new Label();
        start = new Label();
        end = new Label();
        address = new Label();
        salary = new Label();
        income = new Label();
        profit = new Label();


        HBox companyBox = new HBox(10);
        companyBox.getChildren().addAll(companyHeader,company);
        HBox jobBox = new HBox(10);
        jobBox.getChildren().addAll( jobHeader, job);

        HBox startBox = new HBox(10);
        startBox.getChildren().addAll(startHeader, start);
        HBox endBox = new HBox(10);
        endBox.getChildren().addAll( endHeader, end);
        HBox addresBox = new HBox(10);
        addresBox.getChildren().addAll(addressHeader, address);
        HBox salaryBox = new HBox(10);
        salaryBox.getChildren().addAll(salaryHeader, salary);
        HBox incomeBox = new HBox(10);
        incomeBox.getChildren().addAll(incomeHeader, income);
        HBox profitBox = new HBox(10);
        profitBox.getChildren().addAll(profitHeader, profit);


        mcInfo.getChildren().addAll(companyBox, jobBox, startBox, endBox, addresBox, salaryBox, incomeBox, profitBox);

        bar.getChildren().addAll(addButton);
        layout = new BorderPane();
        layout.setLeft(masterClasserListView);
        layout.setCenter(mcInfo);
        layout.setBottom(bar);
        return layout;
    }

    private void refreshMCS() {
        List<MasterClasser> masterclassNames = dao.getAllMasterClassers();
        masterClasserListView.getItems().removeAll(masterClasserListView.getItems());
        masterClasserListView.getItems().addAll(masterclassNames);
    }


    private void setLabels(MasterClasser mc) {
        company.setText(mc.getCompany().getName());
        job.setText(mc.getJobType().getName());
        start.setText(mc.getStartDate().toString());
        Address addr = mc.getAddress();
        address.setText(String.format( "%s %s%s , %s %s ",  addr.getStreet(), addr.getHouseNumber(),
                addr.getExtension(), addr.getPostalCode(), addr.getCity()));

        int yearWorked = mc.getStartDate().until(LocalDate.now()).getYears();
        int monthsWorked = mc.getStartDate().until(LocalDate.now()).getMonths();
        int totalMonthsWorked = 12*yearWorked + monthsWorked;
        System.out.println(totalMonthsWorked);
        salary.setText(String.valueOf(2050 + (totalMonthsWorked>1? 50: 0) + 150 * totalMonthsWorked));
        income.setText(String.valueOf(mc.getJobType().getOid() == 12? 0 : (4000 + totalMonthsWorked * 300)));
        profit.setText(String.valueOf(Integer.parseInt(income.getText()) - Integer.parseInt(salary.getText())));
    }


}
