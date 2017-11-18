package com.jmeplay.plugin.imageview;

import com.jmeplay.core.gui.EditorViewer;
import com.jmeplay.core.gui.EditorViewerTab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 * Implementation for image view in JMEPlay. Supported file types are jpg, jpeg, png, svg
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayImageViewer extends EditorViewer {

    private int iconSize;

    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private JMEPlayImageSettings jmePlayImageViewSettings;

    /**
     * Initialize JMEPlayImageViewer
     */
    @PostConstruct
    private void init() {
        initSettings();
    }

    /**
     * Initialize settings for image view tab
     */
    private void initSettings() {
        iconSize = jmePlayImageViewSettings.getIconSize();
    }

    @Override
    public List<String> filetypes() {
        return Arrays.asList(
                JMEPlayImageResources.JPEG,
                JMEPlayImageResources.JPG,
                JMEPlayImageResources.PNG,
                JMEPlayImageResources.SVG
        );
    }

    /**
     * Generate Tab to show image
     *
     * @param path
     * @return
     */
    @Override
    public EditorViewerTab view(final Path path) {
        JMEPlayImageViewerTab tab = applicationContext.getBean(JMEPlayImageViewerTab.class);
        tab.init(path);
        return tab;
    }
}
