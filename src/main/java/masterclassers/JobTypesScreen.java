package masterclassers;

import elements.AlertBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import masterclassers.model.JobType;
import masterclassers.model.MasterClasser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class JobTypesScreen {
    private ListView<JobType> jobs = new ListView();
    private ListView<MasterClasser> masterClasserWithThisJobType = new ListView<>();
    private MasterClassDAO dao = new MasterClassDAO();
    private PieChart pieChart;
    private BorderPane mainNode = new BorderPane();
    private HBox addJobBox = new HBox();
    private TextField jobInsertField = new TextField();
    private Button addJobTypeButton = new Button("add Job type");

    public BorderPane initJobScreen() {

        jobs.getItems().addAll(dao.getAllJobTypes());
        mainNode.setLeft(jobs);
        setMasterClasserForThisJob(jobs.getItems().get(0));
        jobs.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        jobs.setOnMousePressed(e -> setMasterClasserForThisJob(jobs.getSelectionModel().getSelectedItem()));
        pieChart = getMasterClasserOvertJobTypePieChart();
        mainNode.setLeft(jobs);
        mainNode.setCenter(masterClasserWithThisJobType);
        mainNode.setRight(pieChart);


        // elements that let someone add a jobtype
        addJobTypeButton.setOnAction(e -> addJobType());
        jobInsertField.setPromptText("add name of job type");
        jobInsertField.setMinWidth(250);
        addJobBox.getChildren().addAll(addJobTypeButton, jobInsertField);

        mainNode.setBottom(addJobBox);
        return mainNode;
    }

    // gets and puts all master classerws with that job type in the listView
    private void setMasterClasserForThisJob(JobType jt) {
        masterClasserWithThisJobType.getItems().clear();
        dao.getAllMasterClassers().stream()
                .filter(mc -> mc.getJobType().getName().equals(jt.getName()))
                .forEach(mc -> masterClasserWithThisJobType.getItems().add(mc));
        if (masterClasserWithThisJobType.getItems().isEmpty()) {
            masterClasserWithThisJobType.getItems().add(new MasterClasser());
        }
    }

    // creates the piechart with the latest data
    private PieChart getMasterClasserOvertJobTypePieChart() {
        HashMap<String, Integer> jobTypes = new HashMap<>();
        List<PieChart.Data> dataList = new ArrayList<>();

        dao.getAllMasterClassers()
                .forEach(employee -> jobTypes.compute(employee.getJobType().getName(), (k,v) -> v == null ? 1 : v+1));

        jobTypes.forEach((k, v) -> dataList.add(new PieChart.Data(k, v)));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(dataList);
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("jobs");
        return chart;
    }

    public void refresh() {
        pieChart = getMasterClasserOvertJobTypePieChart();
        mainNode.setRight(pieChart);
    }

    private void addJobType() {
        AlertBox.display("Error", "Not implemented yet");
        //TODO retrieve name of job from textField
        //TODO make a method in dao that inserts jobs
        //TODO invoke dao method, dont forget tot increment the id
    }
}
