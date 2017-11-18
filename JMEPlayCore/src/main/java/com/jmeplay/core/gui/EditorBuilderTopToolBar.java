package com.jmeplay.core.gui;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import javafx.geometry.Orientation;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Create tool bar for the top view of editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderTopToolBar implements JMEEditorToolBar {

    private ToolBar toolBar;

    // Injected
    private JMEEditor jmeEditor;

    @Autowired
    public void setJmeEditor(JMEEditor jmeEditor) {
        this.jmeEditor = jmeEditor;
    }

    @PostConstruct
    private void init() {
        toolBar = new ToolBar(new Button("New"), new Button("Open"), new Button("Save"),
                new Separator(Orientation.VERTICAL), new Button("Clean"), new Button("Compile"), new Button("Run"),
                new Separator(Orientation.VERTICAL), new Button("Debug"), new Button("Profile"));
        jmeEditor.setTopToolBar(toolBar);
    }

    public ToolBar getToolBar() {
        return toolBar;
    }

}
