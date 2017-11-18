package com.jmeplay.plugin.imageview;

import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

/**
 * Localization for JMEPlayImage
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayImageLocalization {

    // LOCALIZATION
    final static String IMAGEVIEW_RESOURCEBUNDLE_LOCALIZATION = "JMEPlayImageView";
    final static String IMAGEVIEW_LOCALIZATION_IMAGEVIEW = "imageview";
    final static String IMAGEVIEW_LOCALIZATION_DESCRIPTION = "description";

    private ResourceBundle bundle;

    /**
     * Gets a string for the given key from JMEPlayImageLocalization resource bundle
     *
     * @param key
     * @return
     */
    public final String getString(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(JMEPlayImageLocalization.IMAGEVIEW_RESOURCEBUNDLE_LOCALIZATION);
        }
        return bundle.getString(key);
    }

}
