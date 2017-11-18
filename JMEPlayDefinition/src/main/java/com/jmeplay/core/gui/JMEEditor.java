package com.jmeplay.core.gui;

import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.control.ToolBar;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class JMEEditor {

    private final ReadOnlyObjectWrapper<Stage> stageChange = new ReadOnlyObjectWrapper<>();
    private Stage stage;
    private Scene scene;
    private ToolBar topToolBar;
    private TabPane centerTabPane;

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
        this.stageChange.set(stage);
    }

    public ReadOnlyObjectProperty<Stage> getStageChange() {
        return stageChange.getReadOnlyProperty();
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public ToolBar getTopToolBar() {
        return topToolBar;
    }

    public void setTopToolBar(ToolBar topToolBar) {
        this.topToolBar = topToolBar;
    }

    public TabPane getCenterTabPane() {
        return centerTabPane;
    }

    public void setCenterTabPane(TabPane centerTabPane) {
        this.centerTabPane = centerTabPane;
    }

}
