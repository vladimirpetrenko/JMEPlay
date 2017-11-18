package com.jmeplay.core;

import com.jmeplay.core.utils.Settings;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementation for assets settings
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayEditorSettings {

    // Injected
    private Settings settings;

    /**
     * Icons spacing
     *
     * @return spacing
     */
    public int getIconSpacing() {
        return settings.getOptionIntegerFromEditor(
                JMEPlayEditorResources.EDITOR_SPACING, JMEPlayEditorResources.EDITOR_DEFAULT_SPACING);
    }

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
     * Icon size for bars
     *
     * @return size
     */
    public int getIconSizeBar() {
        return settings.getOptionIntegerFromEditor(
                JMEPlayEditorResources.EDITOR_ICONSIZE_BAR, JMEPlayEditorResources.EDITOR_DEFAULT_ICONSIZE_BAR);
    }

    /**
     * Icon size
     *
     * @return size
     */
    public int getIconSize() {
        return settings.getOptionIntegerFromEditor(
                JMEPlayEditorResources.EDITOR_ICONSIZE, JMEPlayEditorResources.EDITOR_DEFAULT_ICONSIZE);
    }

    /**
     * Root folder
     *
     * @return size
     */
    public String getRootFolder() {
        // TODO change static path to selected from file -> open...
        return settings.getOptionAsStringFromEditor(JMEPlayEditorResources.EDITOR_ROOTFOLDER, "/home/vp-byte/jwGame/JWGame/assets/");
    }

    /**
     * Font for simple header
     *
     * @return font
     */
    public Font getSimpleHeaderFont() {
        return Font.font("Arial", FontWeight.BOLD, 20);
    }

}
