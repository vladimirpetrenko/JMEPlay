package com.jmeplay.plugin.assets;

import com.jmeplay.core.JMEPlayEditorSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation for assets settings
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsSettings {

    // Injected
    private JMEPlayEditorSettings jmePlayEditorSettings;

    /**
     * JMEPlayEditorSettings
     *
     * @param jmePlayEditorSettings
     */
    @Autowired
    public void setJMEPlayEditorSettings(JMEPlayEditorSettings jmePlayEditorSettings) {
        this.jmePlayEditorSettings = jmePlayEditorSettings;
    }

    /**
     * Icon size for bars
     *
     * @return size
     */
    public int getIconSizeBar() {
        return jmePlayEditorSettings.getIconSizeBar();
    }

    /**
     * Icon size
     *
     * @return size
     */
    public int getIconSize() {
        return jmePlayEditorSettings.getIconSize();
    }

    /**
     * Root folder
     *
     * @return size
     */
    public String getRootFolder() {
        return jmePlayEditorSettings.getRootFolder();
    }

}
