package com.stephanHogenboom.view.console;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import com.stephanHogenboom.acces.MasterClassDAO;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.List;
import java.util.stream.Collectors;

public class SQLConsoleScreen {
    private VBox layout;
    private HBox bar = new HBox();
    private Label queryResponse = new Label();
    private Button executeButton;
    private MasterClassDAO dao = new MasterClassDAO();
    private TextField queryField;
    private ComboBox<String> tablesNames = new ComboBox<>();


    public VBox initSqlCOnsoleScreen() {
        layout = new VBox(5);
        queryField = new TextField("insert query here");
        queryResponse.setPadding(new Insets(10,0,100,0));

        executeButton = new Button("execute");
        executeButton.setOnAction(e -> executeQuery());
        tablesNames.setPromptText("Table names");
        tablesNames.getItems().addAll(getTableNames());
        tablesNames.setOnAction(e -> queryField.setText(String.format("SELECT * FROM %s", tablesNames.getSelectionModel().getSelectedItem())));
        bar.getChildren().addAll(tablesNames, executeButton);

        layout.getChildren().addAll(queryField, bar, queryResponse);
        layout.setBorder(new Border(new BorderStroke(Color.BLACK,
                BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));

       return layout;
    }

    private List<String> getTableNames() {
        return dao.getDataBases().stream()
                .filter(e -> !e.contains("flyway"))
                .collect(Collectors.toList());
    }

    private void executeQuery() {
        String query = queryField.getText().toLowerCase();
        if (query.contains("drop") || query.contains("truncate") || query.contains("delete")) {
            queryField.setText("you are only allowed to execute select and update statements!");
            return;
        }
        if (query.startsWith("SELECT") || query.startsWith("select")) {
            try {
                ResultSet rs = dao.executeQueryPlain(query);
                StringBuilder bldr = new StringBuilder();
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = 0;

                columnCount = metaData.getColumnCount();

                while (rs.next()) {
                    for (int i = 1; i <= columnCount; i++) {
                        bldr.append(metaData.getColumnName(i) + " : ");
                        bldr.append(rs.getString(i) + ", ");
                    }
                    bldr.deleteCharAt(bldr.length()-2);
                    bldr.append(" \n");
                }

                queryResponse.setText(bldr.toString());
            } catch (Exception e) {
                queryResponse.setText("invalid query!!!!");
            }
        } else if (query.contains("update") ||
                query.contains("UPDATE") ||
                query.contains("INSERT") ||
                query.contains("insert")) {
            try {
                dao.executeQueryPlain(query);
                queryResponse.setText( "insertion or update succesFull!");
            } catch (Exception e) {
                queryResponse.setText("invalid query!!!!");
            }

        }

    }
}
