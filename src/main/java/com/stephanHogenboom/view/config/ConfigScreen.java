package com.stephanHogenboom.view.config;

import com.stephanHogenboom.cache.ConfigCache;
import com.stephanHogenboom.elements.AlertBox;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.Map;

public class ConfigScreen {
    private HBox layout;
    private VBox bar = new VBox();
    private VBox configSummary = new VBox();
    private Button resetConfig;
    private ConfigCache configCache = new ConfigCache();


    public HBox initConfigScreen() {
        layout = new HBox(5);

        resetConfig = new Button("Reset configurations cache");
        resetConfig.setOnAction(e -> {
            configCache.resetRegistryCache();
            setConfigSummary();
            AlertBox.display("succes", "Config reset!");

        });

        bar.getChildren().addAll(resetConfig);
        setConfigSummary();


        return layout;
    }

    public void setConfigSummary() {
        VBox vBox = new VBox();
        for (Map.Entry entry : configCache.getConfigCache().entrySet()) {
            HBox hBox = new HBox(10);
            Label key = new Label(entry.getKey().toString());
            key.setMinWidth(175);
            Label value = new Label(entry.getValue().toString());
            value.setMinWidth(175);
            hBox.getChildren().addAll(key, value);
            vBox.getChildren().add(hBox);

        }
        layout.getChildren().removeAll(configSummary, bar);
        configSummary = vBox;
        layout.getChildren().addAll(configSummary, bar);
    }
}
