package com.jmeplay;

/**
 * All resources for JMEPlay
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class Resources {
    private final static String baseEditor = "/com/jmeplay/";
    private final static String cssBase = baseEditor + "css/";
    private final static String iconsBase = baseEditor + "icons/";

    // CSS
    public final static String editorCss = cssBase + "editor.css";

    // ICONS
    public final static String editorIconInfoBarDisable = iconsBase + "disable.svg";
    public final static String editorIconsInfoBarEnable = iconsBase + "enable.svg";

    // KEYS FOR SETTINGS
    public final static String editorStageX = "editorStageX";
    public final static String editorStageY = "editorStageY";
    public final static String editorStageMinWidth = "editorStageMinWidth";
    public final static String editorStageWidth = "editorStageWidth";
    public final static String editorStageMinHeight = "editorStageMinHeight";
    public final static String editorStageHeight = "editorStageHeight";
    public final static String editorMaximized = "editorMaximized";

    public final static String editorIconsSize = "editorIconsSize";
    public final static String editorBorderBarsVisible = "editorBorderBarsVisible";
    public final static String editorLeftVisible = "editorLeftVisible";
    public final static String editorRightVisible = "editorRightVisible";
    public final static String editorBottomVisible = "editorBottomVisible";
    public final static String editorLeftDividerPosition = "editorLeftDividerPosition";
    public final static String editorRightDividerPosition = "editorRightDividerPosition";
    public final static String editorBottomDividerPosition = "editorBottomDividerPosition";


    // DEFAULT VALUES
    public final static Double editorDefaultStageX = null;
    public final static Double editorDefaultStageY = null;
    public final static Double editorDefaultStageMinWidth = 800.0;
    public final static Double editorDefaultStageMinHeight = 600.0;
    public final static Boolean editorDefaultMaximized = true;

    public final static Integer editorDefaultIconsSize = 32;
    public final static Boolean editorDefaultBorderBarsVisible = true;
    public final static Boolean editorDefaultLeftVisible = true;
    public final static Boolean editorDefaultRightVisible = true;
    public final static Boolean editorDefaultBottomVisible = true;
    public final static Double editorDefaultLeftDividerPosition = 0.2;
    public final static Double editorDefaultRightDividerPosition = 0.8;
    public final static Double editorDefaultBottomDividerPosition = 0.8;
}
