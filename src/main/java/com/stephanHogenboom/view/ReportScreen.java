package com.stephanHogenboom.view;

import com.stephanHogenboom.service.report.ReportService;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;


public class ReportScreen {
    private Button filePicker = new Button("pick report");
    private ReportService reportService = new ReportService();

    public BorderPane intReportScreen() {
        BorderPane borderPane = new BorderPane();
        FileChooser fileChooser = new FileChooser();
        filePicker.setOnAction(e -> {});

        return borderPane;
    }

}
