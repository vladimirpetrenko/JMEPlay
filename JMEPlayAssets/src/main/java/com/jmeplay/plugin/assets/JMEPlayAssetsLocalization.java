package com.jmeplay.plugin.assets;

import java.util.ResourceBundle;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;

/**
 * Localization for assets in JMEPLay
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsLocalization {

    // LOCALIZATION
    final static String ASSETS_RESOURCEBUNDLE_ASSETS = "JMEPlayAssets";
    final static String ASSETS_LOCALISATION_ASSETS = "assets";
    final static String ASSETS_LOCALIZATION_DESCRIPTION = "description";

    private ResourceBundle bundle;

    /**
     * Initialize JMEPlayAssets
     */
    @PostConstruct
    private void init() {
        bundle = ResourceBundle.getBundle(JMEPlayAssetsLocalization.ASSETS_RESOURCEBUNDLE_ASSETS);
    }

    /**
     * Gets a string for the given key from JMEPlayAssetsLocalization resource bundle
     *
     * @param key
     * @return
     */
    public final String getString(String key) {
        return bundle.getString(key);
    }

}
