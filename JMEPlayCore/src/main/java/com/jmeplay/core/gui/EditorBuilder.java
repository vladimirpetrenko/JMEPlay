package com.jmeplay.core.gui;

import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Create whole GUI and load all components and plugins
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilder {

    private Stage stage;
    private BorderPane borderPane;

    // Injected
    private EditorBuilderTop editorBuilderTop;
    private EditorBuilderCenter editorBuilderCenter;
    private EditorBuiderBottomInfoBar editorBuiderInfoBar;

    @Autowired
    public void setEditorBuilderTop(EditorBuilderTop editorBuilderTop) {
        this.editorBuilderTop = editorBuilderTop;
    }

    @Autowired
    public void setEditorBuilderCenter(EditorBuilderCenter editorBuilderCenter) {
        this.editorBuilderCenter = editorBuilderCenter;
    }

    @Autowired
    public void setEditorBuiderInfoBar(EditorBuiderBottomInfoBar editorBuiderInfoBar) {
        this.editorBuiderInfoBar = editorBuiderInfoBar;
    }

    /**
     * Get main view for editor
     *
     * @param stage of editor
     * @return view as scene
     */
    BorderPane view(Stage stage) {
        this.stage = stage;

        initBorderPane();
        initEditor();

        return borderPane;
    }

    /**
     * Initialize border pane with size bound on scene size
     */
    private void initBorderPane() {
        borderPane = new BorderPane();
        borderPane.prefHeightProperty().bind(stage.getScene().heightProperty());
        borderPane.prefWidthProperty().bind(stage.getScene().widthProperty());
    }

    /**
     * Initialize editor and set top, center and bottom view of the border pane
     */
    private void initEditor() {
        borderPane.setTop(editorBuilderTop.view(stage));
        borderPane.setCenter(editorBuilderCenter.view(stage));
        borderPane.setBottom(editorBuiderInfoBar.view(stage));
    }

}
