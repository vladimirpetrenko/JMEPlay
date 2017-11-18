package com.jmeplay.core.gui;

import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create center view for editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderCenter {

    private Stage stage;
    private BorderPane centerBorderPane;

    // Injected
    private EditorBuilderCenterBorderBar editorBuilderCenterBorderBar;
    private EditorBuiderBottomInfoBar buiderBottomInfoBar;
    private EditorBuilderCenterCenter editorBuilderCenterCenter;

    @Autowired
    public void setEditorBuilderCenterBorderBar(EditorBuilderCenterBorderBar editorBuilderCenterBorderBar) {
        this.editorBuilderCenterBorderBar = editorBuilderCenterBorderBar;
    }

    @Autowired
    public void setBuiderBottomInfoBar(EditorBuiderBottomInfoBar buiderBottomInfoBar) {
        this.buiderBottomInfoBar = buiderBottomInfoBar;
    }

    @Autowired
    public void setEditorBuilderCenterCenter(EditorBuilderCenterCenter editorBuilderCenterCenter) {
        this.editorBuilderCenterCenter = editorBuilderCenterCenter;
    }

    /**
     * Create center view for editor
     *
     * @param stage of editor
     * @return center view
     */
    Node view(Stage stage) {
        this.stage = stage;
        initCenterBorderPane();
        return centerBorderPane;
    }

    /**
     * Initialize center view of the editor
     */
    private void initCenterBorderPane() {
        centerBorderPane = new BorderPane();
        buiderBottomInfoBar.borderBarsVisibilityProperty().addListener(observable -> updateCenterBorderPane());
        updateCenterBorderPane();
    }

    /**
     * Update border pane an set left, right, bottom and center view
     */
    private void updateCenterBorderPane() {
        if (buiderBottomInfoBar.borderBarsVisibile()) {
            centerBorderPane.setLeft(editorBuilderCenterBorderBar.borderBarLeft());
            centerBorderPane.setRight(editorBuilderCenterBorderBar.borderBarRight());
            centerBorderPane.setBottom(editorBuilderCenterBorderBar.borderBarBottom());
        } else {
            centerBorderPane.getChildren().remove(editorBuilderCenterBorderBar.borderBarLeft());
            centerBorderPane.getChildren().remove(editorBuilderCenterBorderBar.borderBarRight());
            centerBorderPane.getChildren().remove(editorBuilderCenterBorderBar.borderBarBottom());
        }
        centerBorderPane.setCenter(editorBuilderCenterCenter.view(stage));
    }

}
