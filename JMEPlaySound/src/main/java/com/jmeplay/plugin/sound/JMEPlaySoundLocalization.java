package com.jmeplay.plugin.sound;

import java.util.ResourceBundle;
import org.springframework.stereotype.Component;

/**
 * Localization for JMEPlaySound
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlaySoundLocalization {

    // LOCALIZATION
    final static String PLAYSOUND_RESOURCEBUNDLE_LOCALIZATION = "JMEPlaySound";
    final static String PLAYSOUND_LOCALIZATION_PLAYSOUND = "sound";
    final static String PLAYSOUND_LOCALIZATION_DESCRIPTION = "description";
    final static String PLAYSOUND_LOCALIZATION_PLAY_TOOLTIP = "playtooltip";
    final static String PLAYSOUND_LOCALIZATION_PAUSE_TOOLTIP = "pausetooltip";
    final static String PLAYSOUND_LOCALIZATION_STOP_TOOLTIP = "stoptooltip";
    final static String PLAYSOUND_LOCALIZATION_LOOP_TOOLTIP = "looptooltip";
    final static String PLAYSOUND_LOCALIZATION_AUDIODATA = "audiodata";
    final static String PLAYSOUND_LOCALIZATION_DURATION = "duration";
    final static String PLAYSOUND_LOCALIZATION_CHANNELS = "channels";
    final static String PLAYSOUND_LOCALIZATION_BITSPERSAMPLE = "bitspersample";
    final static String PLAYSOUND_LOCALIZATION_SAMPLERATE = "samplerate";
    final static String PLAYSOUND_LOCALIZATION_DATATYPE = "datatype";
    final static String PLAYSOUND_LOCALIZATION_MONO = "mono";
    final static String PLAYSOUND_LOCALIZATION_STEREO = "stereo";
    final static String PLAYSOUND_LOCALIZATION_VOLUME = "volume";
    final static String PLAYSOUND_LOCALIZATION_PITCH = "pitch";
    final static String PLAYSOUND_LOCALIZATION_RESET = "reset";
    final static String PLAYSOUND_LOCALIZATION_OFFSET = "offset";

    private ResourceBundle bundle;

    /**
     * Gets a string for the given key from JMEPlaySoundLocalization resource bundle
     *
     * @param key
     * @return
     */
    public final String getString(String key) {
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(JMEPlaySoundLocalization.PLAYSOUND_RESOURCEBUNDLE_LOCALIZATION);
        }
        return bundle.getString(key);
    }

}
