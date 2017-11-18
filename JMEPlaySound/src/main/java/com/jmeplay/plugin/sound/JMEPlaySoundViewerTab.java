package com.jmeplay.plugin.sound;

import com.jme3.asset.AssetManager;
import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jme3.audio.AudioSource;
import com.jmeplay.core.gui.EditorViewerTab;
import com.jmeplay.core.gui.JMEEditor;
import com.jmeplay.core.utils.ImageLoader;
import java.nio.file.Path;
import javafx.scene.control.Tab;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * Tab to view sound in JMEPlaySound
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Scope("prototype")
public class JMEPlaySoundViewerTab extends EditorViewerTab {

    private int iconSize;
    private AssetManager assetManager;
    private AudioNode audioNode;

    @Autowired
    ApplicationContext applicationContext;
    @Autowired
    private JMEPlaySoundSettings jmePlaySoundSettings;
    @Autowired
    private JMEPlaySoundLocalization jmePlaySoundLocalization;
    @Autowired
    private JMEEditor jmeEditor;

    /**
     * Initialize MEPlaySoundViewerTab
     *
     * @param path
     * @param assetManager
     */
    public void init(final Path path, AssetManager assetManager) {
        super.init(path);
        this.assetManager = assetManager;
        initSettings();
        initAudioNode();
        initView();
        initEvents();
    }

    /**
     * AudioNode
     *
     * @return audio
     */
    public AudioNode getAudioNode() {
        return audioNode;
    }

    /**
     * Initialize settings for sound play tab
     */
    private void initSettings() {
        iconSize = jmePlaySoundSettings.getIconSize();
    }

    /**
     * Initialize audio node
     */
    private void initAudioNode() {
        String fileName = path().toString().replace(jmePlaySoundSettings.getRootFolder(), "");
        audioNode = new AudioNode(assetManager, fileName, AudioData.DataType.Buffer);
        audioNode.setPositional(false);
        audioNode.setReverbEnabled(false);
    }

    /**
     * Initialize view for JMEPlaySound
     */
    private void initView() {
        updateTopToolBar();

        JMEPlaySoundViewerAudioData jmePlaySoundViewerAudioData = applicationContext.getBean(JMEPlaySoundViewerAudioData.class);
        jmePlaySoundViewerAudioData.init(audioNode);
        setContent(jmePlaySoundViewerAudioData);

        ImageView imageImage = ImageLoader.initImageView(this.getClass(),
                JMEPlaySoundResources.ICONS__SOUND_SOUND, iconSize, iconSize);
        setGraphic(imageImage);
    }

    /**
     * Initialize events for JMEPlaySound (KEY, SELECTION, CLOSE)
     */
    private void initEvents() {
        jmeEditor.getScene().setOnKeyPressed(event -> {
            Tab selectedTab = jmeEditor.getCenterTabPane().getSelectionModel().getSelectedItem();
            if (selectedTab instanceof JMEPlaySoundViewerTab) {
                JMEPlaySoundViewerTab selSoundViewerTab = (JMEPlaySoundViewerTab) selectedTab;
                if (event.getCode() == KeyCode.P) {
                    if (selSoundViewerTab.getAudioNode().getStatus() != AudioSource.Status.Playing) {
                        selSoundViewerTab.getAudioNode().play();
                    } else {
                        selSoundViewerTab.getAudioNode().pause();
                    }
                }
                if (event.getCode() == KeyCode.S) {
                    selSoundViewerTab.getAudioNode().stop();
                }
                if (event.getCode() == KeyCode.L) {
                    jmeEditor.getTopToolBar().getItems().forEach(item -> {
                        if (item instanceof ToggleButton) {
                            ToggleButton loopButton = (ToggleButton) item;
                            loopButton.setSelected(!loopButton.isSelected());
                            selSoundViewerTab.getAudioNode().setLooping(loopButton.isSelected());
                        }
                    });
                }
            }
        });

        jmeEditor.getCenterTabPane().getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
            if (newTab instanceof JMEPlaySoundViewerTab) {
                if (oldTab instanceof JMEPlaySoundViewerTab) {
                    ((JMEPlaySoundViewerTab) oldTab).getAudioNode().stop();
                }
                if (this == newTab) {
                    updateTopToolBar();
                }
            }
        });

        setOnClosed(event -> {
            if (jmeEditor.getCenterTabPane().getTabs().isEmpty()) {
                jmeEditor.getTopToolBar().getItems().clear();
            }
            audioNode.stop();
        });
    }

    /**
     * Update tool bar for sound viewer
     */
    private void updateTopToolBar() {
        Button playButton = createPlayButton();
        Button pauseButton = createPauseButton();
        Button stopButton = createStopButton();
        Separator separator = new Separator();
        ToggleButton loopButton = createLoopButton();
        loopButton.setSelected(audioNode.isLooping());

        jmeEditor.getTopToolBar().getItems().clear();
        jmeEditor.getTopToolBar().getItems().addAll(playButton, pauseButton, stopButton, separator, loopButton);
    }

    /**
     * Create play button
     *
     * @return button
     */
    private Button createPlayButton() {
        ImageView playImage = ImageLoader.initImageView(getClass(),
                JMEPlaySoundResources.ICONS__SOUND_PLAY, iconSize, iconSize);
        Button playButton = new Button("", playImage);
        String playTooltip = jmePlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_PLAY_TOOLTIP);
        playButton.setTooltip(new Tooltip(playTooltip));
        playButton.setOnAction(event -> {
            audioNode.play();
        });
        return playButton;
    }

    /**
     * Create pause button
     *
     * @return button
     */
    private Button createPauseButton() {
        ImageView pauseImage = ImageLoader.initImageView(getClass(),
                JMEPlaySoundResources.ICONS__SOUND_PAUSE, iconSize, iconSize);
        Button pauseButton = new Button("", pauseImage);
        String pauseTooltip = jmePlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_PAUSE_TOOLTIP);
        pauseButton.setTooltip(new Tooltip(pauseTooltip));
        pauseButton.setOnAction(event -> {
            audioNode.pause();
        });
        return pauseButton;
    }

    /**
     * Create stop button
     *
     * @return button
     */
    private Button createStopButton() {
        ImageView stopImage = ImageLoader.initImageView(getClass(),
                JMEPlaySoundResources.ICONS__SOUND_STOP, iconSize, iconSize);
        Button stopButton = new Button("", stopImage);
        String stopTooltip = jmePlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_STOP_TOOLTIP);
        stopButton.setTooltip(new Tooltip(stopTooltip));
        stopButton.setOnAction(event -> {
            audioNode.stop();
        });
        return stopButton;
    }

    /**
     * Create loop button
     *
     * @return button
     */
    private ToggleButton createLoopButton() {
        ImageView loopImage = ImageLoader.initImageView(getClass(),
                JMEPlaySoundResources.ICONS__SOUND_LOOP, iconSize, iconSize);
        ToggleButton loopButton = new ToggleButton("", loopImage);
        String loopTooltip = jmePlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_LOOP_TOOLTIP);
        loopButton.setTooltip(new Tooltip(loopTooltip));
        loopButton.setOnAction(event -> {
            audioNode.setLooping(loopButton.isSelected());
        });
        return loopButton;
    }

}
