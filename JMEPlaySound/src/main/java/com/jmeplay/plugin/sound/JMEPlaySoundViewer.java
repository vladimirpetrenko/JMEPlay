package com.jmeplay.plugin.sound;

import com.jme3.asset.AssetManager;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.audio.AudioContext;
import com.jme3.audio.AudioRenderer;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeSystem;
import com.jmeplay.core.gui.EditorViewer;
import com.jmeplay.core.gui.EditorViewerTab;
import com.jmeplay.core.gui.JMEEditor;
import java.net.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.springframework.context.ApplicationContext;

/**
 * Viewer for JMEPlaySound. Supported files: OGG, WAV
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlaySoundViewer extends EditorViewer {

    private URL assetCfgUrl;
    private AssetManager assetManager;
    private AppSettings appSettings;
    private AudioRenderer audioRenderer;

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private JMEPlaySoundSettings jmePlaySoundSettings;
    @Autowired
    private JMEEditor jmeEditor;

    /**
     * List of supported file types
     *
     * @return filetypes
     */
    @Override
    public List<String> filetypes() {
        return Arrays.asList(
                JMEPlaySoundResources.OGG,
                JMEPlaySoundResources.WAV
        );
    }

    /**
     * Initialize JMEPlaySoundSettings
     */
    @PostConstruct
    private void init() {
        initAppSettings();
        initAssetManager();
        initAudioRenderer();
    }

    /**
     * Initialize application settings to support JOGL and JOAL
     */
    private void initAppSettings() {
        appSettings = new AppSettings(true);
        appSettings.setRenderer(AppSettings.JOGL_OPENGL_BACKWARD_COMPATIBLE);
        appSettings.setAudioRenderer(AppSettings.JOAL);
    }

    /**
     * Initialize AssetManager to load audio file and play AudioNode
     */
    private void initAssetManager() {
        if (assetCfgUrl == null) {
            assetCfgUrl = JmeSystem.getPlatformAssetConfigURL();
        }
        if (assetManager == null) {
            assetManager = JmeSystem.newAssetManager(assetCfgUrl);
        }
        assetManager.registerLocator(jmePlaySoundSettings.getRootFolder(), FileLocator.class);
    }

    /**
     * Initialize AudioRenderer to render audio
     */
    private void initAudioRenderer() {
        audioRenderer = JmeSystem.newAudioRenderer(appSettings);
        audioRenderer.initialize();
        jmeEditor.getStageChange().addListener((o, ov, on) -> {
            on.setOnHidden((event) -> audioRenderer.cleanup());
        });
    }

    /**
     * Generate Tab to show sound
     *
     * @param path
     * @return view
     */
    @Override
    public EditorViewerTab view(final Path path) {
        AudioContext.setAudioRenderer(audioRenderer);
        JMEPlaySoundViewerTab jmePlaySoundViewerTab = applicationContext.getBean(JMEPlaySoundViewerTab.class);
        jmePlaySoundViewerTab.init(path, assetManager);
        return jmePlaySoundViewerTab;
    }

}
