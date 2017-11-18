package com.jmeplay.plugin.sound;

import com.jme3.audio.AudioData;
import com.jme3.audio.AudioNode;
import com.jmeplay.core.utils.ImageLoader;
import java.util.concurrent.TimeUnit;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.util.converter.NumberStringConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * AudioData viewer for JMEPlaySound
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Scope("prototype")
public class JMEPlaySoundViewerAudioData extends HBox {

    private int iconSize;
    AudioNode audioNode;
    AudioData audioData;
    GridPane gridPane;

    @Autowired
    JMEPlaySoundSettings jmePlaySoundSettings;
    @Autowired
    JMEPlaySoundLocalization jMEPlaySoundLocalization;

    /**
     * Initialize complete JMEPlaySoundViewerAudioData
     *
     * @param audioNode
     */
    public void init(AudioNode audioNode) {
        this.audioNode = audioNode;
        audioData = audioNode.getAudioData();
        initSettings();
        initGridPane();
        initTitle();
        initDuration();
        initChannels();
        initBitsPerSample();
        initSampleRate();
        initDataType();
        initVolumeControl();
        initPitchControl();
        initOffesetControl();
    }

    /**
     * Initialize settings for AudioData viewer
     */
    private void initSettings() {
        iconSize = jmePlaySoundSettings.getIconSize();
    }

    /**
     * Initialize GridPane
     */
    private void initGridPane() {
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        getChildren().add(gridPane);
    }

    /**
     * Initialize title
     */
    private void initTitle() {
        Text title = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_AUDIODATA));
        title.setFont(jmePlaySoundSettings.getSimpleHeaderFont());
        gridPane.add(title, 0, 0);
    }

    /**
     * Initialize duration
     */
    private void initDuration() {
        Text durationLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_DURATION));
        gridPane.add(durationLabel, 0, 1);
        TextField durationData = new TextField(formatDurationText());
        durationData.setEditable(false);
        gridPane.add(durationData, 1, 1);
    }

    /**
     * Format duration text
     */
    private String formatDurationText() {
        double millisec = audioData.getDuration() * 1000.0;
        long milliseconds = (long) millisec;
        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) - (TimeUnit.MILLISECONDS.toHours(milliseconds) * 60);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) - (TimeUnit.MILLISECONDS.toMinutes(milliseconds) * 60);
        long millis = milliseconds - (TimeUnit.MILLISECONDS.toSeconds(milliseconds) * 1000);
        return String.format("%02d:%02d:%02d:%03d", hours, minutes, seconds, millis);
    }

    /**
     * Initialize channels
     */
    private void initChannels() {
        Text channelsLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_CHANNELS));
        gridPane.add(channelsLabel, 0, 2);
        TextField channelsData = new TextField(formatChannelsText());
        channelsData.setEditable(false);
        gridPane.add(channelsData, 1, 2);
    }

    /**
     * Format channels text
     */
    private String formatChannelsText() {
        String channelsText = audioData.getChannels() + " (";
        if (audioData.getChannels() == 1) {
            channelsText += jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_MONO);
        } else {
            channelsText += jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_STEREO);
        }
        channelsText += ")";
        return channelsText;
    }

    /**
     * Initialize BitsPerSample
     */
    private void initBitsPerSample() {
        Text bitsPerSampleLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_BITSPERSAMPLE));
        gridPane.add(bitsPerSampleLabel, 0, 3);
        TextField bitsPerSampleData = new TextField("" + audioData.getBitsPerSample());
        bitsPerSampleData.setEditable(false);
        gridPane.add(bitsPerSampleData, 1, 3);
    }

    /**
     * Initialize SampleRate
     */
    private void initSampleRate() {
        Text sampleRateLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_SAMPLERATE));
        gridPane.add(sampleRateLabel, 0, 4);
        TextField sampleRateData = new TextField("" + audioData.getSampleRate());
        sampleRateData.setEditable(false);
        gridPane.add(sampleRateData, 1, 4);
    }

    /**
     * Initialize DataType
     */
    private void initDataType() {
        Text dataTypeLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_DATATYPE));
        gridPane.add(dataTypeLabel, 0, 5);
        TextField dataTypeData = new TextField("" + audioData.getDataType());
        dataTypeData.setEditable(false);
        gridPane.add(dataTypeData, 1, 5);
    }

    /**
     * Initialize volume control
     */
    private void initVolumeControl() {
        Text volumeLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_VOLUME));
        gridPane.add(volumeLabel, 2, 1);
        Slider volumeSlider = new Slider(0, 1.0, audioNode.getVolume());
        volumeSlider.valueProperty().addListener((ov, oldV, newV) -> {
            audioNode.setVolume(newV.floatValue());
        });
        gridPane.add(volumeSlider, 3, 1, 2, 1);
    }

    /**
     * Initialize pitch control
     */
    private void initPitchControl() {
        Text pitchLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_PITCH));
        gridPane.add(pitchLabel, 2, 2);
        Slider pitchSlider = new Slider(0.5, 2.0, audioNode.getPitch());
        pitchSlider.valueProperty().addListener((ov, oldP, newP) -> {
            audioNode.setPitch(newP.floatValue());
        });
        gridPane.add(pitchSlider, 3, 2, 2, 1);

        ImageView minusImage = ImageLoader.initImageView(getClass(),
                JMEPlaySoundResources.ICONS__SOUND_MINUS, iconSize, iconSize);
        Button minus = new Button("", minusImage);
        minus.setOnAction(event -> {
            if (pitchSlider.getValue() > 0.5) {
                pitchSlider.setValue(pitchSlider.getValue() - 0.1);
            }
        });
        gridPane.add(minus, 5, 2);

        ImageView plusImage = ImageLoader.initImageView(getClass(),
                JMEPlaySoundResources.ICONS__SOUND_PLUS, iconSize, iconSize);
        Button plus = new Button("", plusImage);
        plus.setOnAction(event -> {
            if (pitchSlider.getValue() < 2.0) {
                pitchSlider.setValue(pitchSlider.getValue() + 0.1);
            }
        });
        gridPane.add(plus, 6, 2);

        Button reset = new Button(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_RESET));
        reset.setOnAction(event -> {
            pitchSlider.setValue(1);
        });
        gridPane.add(reset, 7, 2);
    }

    /**
     * Initialize offset control
     */
    private void initOffesetControl() {
        Text offsetLabel = new Text(jMEPlaySoundLocalization.getString(JMEPlaySoundLocalization.PLAYSOUND_LOCALIZATION_OFFSET));
        gridPane.add(offsetLabel, 2, 3);
        Slider offsetSlider = new Slider(0.0, audioData.getDuration(), 0.0);
        offsetSlider.valueProperty().addListener((ov, oldTO, newTO) -> {
            audioNode.setTimeOffset(newTO.floatValue());
        });
        gridPane.add(offsetSlider, 3, 3, 2, 1);
        Text offsetViewLabel = new Text();
        offsetViewLabel.textProperty().bindBidirectional(offsetSlider.valueProperty(), new NumberStringConverter());
        gridPane.add(offsetViewLabel, 5, 3, 2, 1);
    }
}
