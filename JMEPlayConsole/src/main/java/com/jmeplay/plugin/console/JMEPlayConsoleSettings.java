package com.jmeplay.plugin.console;

import com.jmeplay.core.JMEPlayEditorSettings;
import com.jmeplay.core.utils.Settings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Setting for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleSettings {

    // KEYS FOR SETTINGS
    final static String CONSOLE_WRITE_EXCEPTIONS = "consoleWriteExceptions";

    // DEFAULT VALUES
    final static boolean CONSOLE_DEFAULT_WRITE_EXCEPTIONS = false;

    @Autowired
    private Settings settings;
    @Autowired
    private JMEPlayEditorSettings jmePlayEditorSettings;

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
     * Icons spacing
     *
     * @return spacing
     */
    public int getIconSpacing() {
        return jmePlayEditorSettings.getIconSpacing();
    }

    /**
     * Write exceptions
     *
     * @return value
     */
    public boolean getWriteExceptions() {
        return settings.getOptionAsBoolean(
                JMEPlayConsoleSettings.CONSOLE_WRITE_EXCEPTIONS, JMEPlayConsoleSettings.CONSOLE_DEFAULT_WRITE_EXCEPTIONS);
    }

}
