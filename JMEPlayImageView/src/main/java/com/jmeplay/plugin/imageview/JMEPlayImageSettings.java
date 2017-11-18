package com.jmeplay.plugin.imageview;

import com.jmeplay.core.JMEPlayEditorResources;
import com.jmeplay.core.utils.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Settings for JMEPlayImage
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayImageSettings {

    // Injected
    private Settings settings;

    /**
     * Settings
     *
     * @param settings
     */
    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * IconSize
     *
     * @return size
     */
    public int getIconSize() {
        return settings.getOptionIntegerFromEditor(
                JMEPlayEditorResources.EDITOR_ICONSIZE, JMEPlayEditorResources.EDITOR_DEFAULT_ICONSIZE);
    }

}
