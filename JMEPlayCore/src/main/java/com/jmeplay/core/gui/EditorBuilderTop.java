package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javafx.scene.Node;
import javafx.scene.layout.VBox;

/**
 * Create top view of editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderTop {

    private Stage stage;
    private VBox top;

    // Injected
    private EditorBuilderTopMenuBar editorBuilderTopMenuBar;
    private EditorBuilderTopToolBar editorBuilderTopToolBar;

    @Autowired
    public void setEditorBuilderTopMenuBar(EditorBuilderTopMenuBar editorBuilderTopMenuBar) {
        this.editorBuilderTopMenuBar = editorBuilderTopMenuBar;
    }

    @Autowired
    public void setEditorBuilderTopToolBar(EditorBuilderTopToolBar editorBuilderTopToolBar) {
        this.editorBuilderTopToolBar = editorBuilderTopToolBar;
    }

    /**
     * @return top view
     */
    Node view(Stage stage) {
        this.stage = stage;
        initTop();
        return top;
    }

    /**
     * Initialize top view of editor
     */
    private void initTop() {
        top = new VBox();
        top.getChildren().add(editorBuilderTopMenuBar.menuBar());
        top.getChildren().add(editorBuilderTopToolBar.getToolBar());
    }

}
