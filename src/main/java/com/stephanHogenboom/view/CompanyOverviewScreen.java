package com.stephanHogenboom.view;

import com.stephanHogenboom.elements.AlertBox;
import com.stephanHogenboom.acces.MasterClassDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import com.stephanHogenboom.model.Company;
import com.stephanHogenboom.model.MasterClasser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class CompanyOverviewScreen {
    private BorderPane companyScreen = new BorderPane();
    private final ListView<Company> companies = new ListView();
    private final MasterClassDAO dao = new MasterClassDAO();
    private List<MasterClasser> mcs = dao.getAllMasterClassers();
    private final ListView<MasterClasser> masterClasserInThisCompany = new ListView<>();
    private final HBox addCompanyBox = new HBox(5);
    private final Button addCompany = new Button("addCompany");
    private final TextField companyNameTextField = new TextField();
    private  PieChart masterClasserOvertCompanies;

    public BorderPane initCompanyScreen() {

        companies.getItems().addAll(dao.getAllCompanies());
        companies.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        companies.setOnMouseClicked(e-> setMasterClassersView(companies.getSelectionModel().getSelectedItem()));

        //TODO insert method in masterclassDAO or other dao to add companies
        addCompany.setOnAction(e -> AlertBox.display("error", "not yet implemented!"));

        companyNameTextField.setMinWidth(250);
        companyNameTextField.setPromptText("enter company name");
        addCompanyBox.getChildren().addAll(addCompany, companyNameTextField);
        companyScreen.setLeft(companies);
        companyScreen.setCenter(masterClasserInThisCompany);
        companyScreen.setBottom(addCompanyBox);
        masterClasserOvertCompanies = getMasterClasserOvertCompaniesPieCharet();
        masterClasserInThisCompany.getItems().add(new MasterClasser());

        companyScreen.setRight(masterClasserOvertCompanies);
        return companyScreen;
    }

    private void setMasterClassersView(Company company) {
        if (company == null) {
            return;
        }
        List<MasterClasser> list = dao
                .getAllMasterClassers()
                .stream()
                .filter(mc -> mc.getCompany().getName().equals(company.getName()))
                .collect(Collectors.toList());
        masterClasserInThisCompany.getItems().clear();
        if (list.isEmpty()) {
            masterClasserInThisCompany.getItems().add(new MasterClasser());
        }
        masterClasserInThisCompany.getItems().addAll(list);
    }

    private void insertCompany(Company company) {
        // get name from textfield
        // write insertQuery in dao class
        // invoke dao -> database
        // dont forget to autoincrement oid
    }

    public void refresh() {
        mcs = dao.getAllMasterClassers();
        masterClasserOvertCompanies = getMasterClasserOvertCompaniesPieCharet();
        companyScreen.setRight(masterClasserOvertCompanies);
    }

    private PieChart getMasterClasserOvertCompaniesPieCharet() {
        HashMap<String, Integer> companies = new HashMap<>();
        List<PieChart.Data> dataList = new ArrayList<>();

        mcs.forEach(employee -> companies.compute(employee.getCompany().getName(), (k,v) -> v == null ? 1 : v+1));

        companies.forEach((k, v) -> dataList.add(new PieChart.Data(k, v)));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(dataList);
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Companies");
        return chart;
    }
}
