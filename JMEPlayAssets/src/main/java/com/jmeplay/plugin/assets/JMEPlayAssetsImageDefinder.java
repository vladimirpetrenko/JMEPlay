package com.jmeplay.plugin.assets;

import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.PathResolver;
import javafx.scene.image.ImageView;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Defines image from filename
 *
 * @author vp-byte(Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsImageDefinder {

    private int iconSize = 24;

    // Injected
    private JMEPlayAssetsSettings settings;

    /**
     * JMEPlayAssetsSettings
     *
     * @param settings
     */
    @Autowired
    public void setSettings(JMEPlayAssetsSettings settings) {
        this.settings = settings;
    }

    /**
     * Initialize JMEPlayAssetsImageDefinder
     */
    @PostConstruct
    private void init() {
        iconSize = settings.getIconSize();
    }

    public ImageView imageByFilename(Path path) {
        if (Files.isDirectory(path)) {
            return ImageLoader.initImageView(this.getClass(), JMEPLayAssetsResources.ICONS_ASSETS_FOLDER, iconSize, iconSize);
        } else {
            String fileExtension = PathResolver.resolveExtension(path);
            if (fileExtension != null) {
                switch (fileExtension.toLowerCase()) {
                    case "j3o":
                        return ImageLoader.initImageView(this.getClass(), JMEPLayAssetsResources.ICONS_ASSETS_MONKEY, iconSize, iconSize);
                    case "png":
                    case "jpg":
                    case "jpeg":
                        return ImageLoader.initImageView(this.getClass(), JMEPLayAssetsResources.ICONS_ASSETS_IMAGE, iconSize, iconSize);
                    case "fnt":
                        return ImageLoader.initImageView(this.getClass(), JMEPLayAssetsResources.ICONS_ASSETS_FONT, iconSize, iconSize);
                    case "j3m":
                        return ImageLoader.initImageView(this.getClass(), JMEPLayAssetsResources.ICONS_ASSETS_MATERIAL, iconSize, iconSize);
                    case "ogg":
                    case "wav":
                        return ImageLoader.initImageView(this.getClass(), JMEPLayAssetsResources.ICONS_ASSETS_MUSIC, iconSize, iconSize);
                }
            }
        }
        return ImageLoader.initImageView(this.getClass(), JMEPLayAssetsResources.ICONS_ASSETS_FILE, iconSize, iconSize);
    }

}
