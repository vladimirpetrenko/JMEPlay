package com.jmeplay.plugin.sound;

import com.jmeplay.core.JMEPlayEditorSettings;
import javafx.scene.text.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Setting for JMEPlaySound
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlaySoundSettings {

    @Autowired
    private JMEPlayEditorSettings jmePlayEditorSettings;

    /**
     * IconSize
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

    /**
     * Font for simple header
     *
     * @return font
     */
    public Font getSimpleHeaderFont() {
        return jmePlayEditorSettings.getSimpleHeaderFont();
    }

}
