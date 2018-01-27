package com.stephanHogenboom.view;

import com.stephanHogenboom.elements.AlertBox;
import com.stephanHogenboom.service.report.ReportService;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.time.LocalDate;


public class ReportScreen {
    private Button createReportButton = new Button("generate report");
    private ReportService reportService = new ReportService();

    public BorderPane intReportScreen() {
        BorderPane borderPane = new BorderPane();
        createReportButton.setOnAction(e -> createReport());
        borderPane.setBottom(createReportButton);
        return borderPane;
    }

    private void createReport() {
        System.out.println(String.format("requested report for %s", LocalDate.now()));
        reportService.createMasterClasserReport();
    }


}
