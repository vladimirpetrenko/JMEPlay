/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmeplay.plugin.imageview;

import com.jmeplay.core.gui.EditorViewerTab;
import com.jmeplay.core.gui.JMEEditor;
import com.jmeplay.core.utils.ImageLoader;
import java.nio.file.Path;
import javafx.scene.image.ImageView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Tab to view images in JMEPlayImage
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Scope("prototype")
public class JMEPlayImageViewerTab extends EditorViewerTab {

    private int iconSize;

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private JMEPlayImageSettings jmePlayImageSettings;
    @Autowired
    private JMEPlayImageLocalization jmePlayImageLocalization;
    @Autowired
    private JMEEditor jmeEditor;

    /**
     * Initialize JMEPlayImageViewerTab
     *
     * @param path
     */
    @Override
    public void init(final Path path) {
        super.init(path);
        initSettings();
        initView();
        initEvents();
    }

    /**
     * Initialize settings for image view tab
     */
    private void initSettings() {
        iconSize = jmePlayImageSettings.getIconSize();
    }

    /**
     * Initialize view for JMEPlayImage
     */
    private void initView() {
        updateTopToolBar();

        JMEPlayImageViewerScrollPane jMEPlayImageScrollPane = applicationContext.getBean(JMEPlayImageViewerScrollPane.class);
        jMEPlayImageScrollPane.init(path());
        setContent(jMEPlayImageScrollPane);

        ImageView imageImage = ImageLoader.initImageView(this.getClass(),
                JMEPlayImageResources.ICONS_IMAGEVIE_IMAGEVIEW, iconSize, iconSize);
        setGraphic(imageImage);
    }

    /**
     * Initialize events for JMEPlayImave
     */
    private void initEvents() {
        jmeEditor.getCenterTabPane().getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (newTab instanceof JMEPlayImageViewerTab) {
                if (this == newTab) {
                    updateTopToolBar();
                }
            }
        });

        setOnClosed(event -> {
            if (jmeEditor.getCenterTabPane().getTabs().isEmpty()) {
                jmeEditor.getTopToolBar().getItems().clear();
            }
        });
    }

    /**
     * Update tool bar for sound viewer
     */
    private void updateTopToolBar() {
        jmeEditor.getTopToolBar().getItems().clear();
    }
}
