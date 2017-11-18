package com.jmeplay.core.gui;

import com.jmeplay.Resources;
import com.jmeplay.core.BottomInfoMessage;
import com.jmeplay.core.utils.ImageLoader;
import com.jmeplay.core.utils.Settings;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.ReadOnlyBooleanWrapper;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create info bar for editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuiderBottomInfoBar implements BottomInfoMessage {

    private int size;
    private Stage stage;

    private ReadOnlyBooleanWrapper borderBarsVisibility;
    private Label modeSwitcher;
    private Label infoLabel;
    private HBox bottomBox;

    // Injected
    private Settings settings;

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    /**
     * Initialize info bar of the editor
     */
    @PostConstruct
    private void init() {
        size = settings.getOptionInteger(Resources.editorIconsSize, Resources.editorDefaultIconsSize);
        initBorderBarsVisibility();
        initModeSwitcher();
        initInfoLabel();
        initBottomBox();
    }

    /**
     * Initialize visibility of border bars
     */
    private void initBorderBarsVisibility() {
        borderBarsVisibility = new ReadOnlyBooleanWrapper(settings.getOptionAsBoolean(Resources.editorBorderBarsVisible, Resources.editorDefaultBorderBarsVisible));
        borderBarsVisibility.addListener((observable, oldValue, newValue) -> {
            settings.setOption(Resources.editorBorderBarsVisible, newValue);
            updateModeSwitcherImage();
        });
    }

    /**
     * Update image of mode switcher
     */
    private void updateModeSwitcherImage() {
        modeSwitcher.setGraphic(modeSwitcherImage());
    }

    /**
     * @return actual image depends on border bars visibility mode
     */
    private ImageView modeSwitcherImage() {
        String imagePath = Resources.editorIconInfoBarDisable;
        if (!borderBarsVisibility.get()) {
            imagePath = Resources.editorIconsInfoBarEnable;
        }
        return ImageLoader.initImageView(this.getClass(), imagePath, size - 4, size - 4);
    }

    /**
     * Initialize switcher between two modes visible and hidden border bars
     */
    private void initModeSwitcher() {
        modeSwitcher = new Label();
        modeSwitcher.setGraphic(modeSwitcherImage());
        modeSwitcher.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> borderBarsVisibility.set(!borderBarsVisibility.get()));
    }

    /**
     * Initialize info label
     */
    private void initInfoLabel() {
        infoLabel = new Label();
        infoLabel.getStyleClass().add("infobar-infolabel");
        writeInfoMessage("Info message");
    }

    /**
     * Initialize bottom view with mode switcher and info label
     */
    private void initBottomBox() {
        bottomBox = new HBox();
        bottomBox.getStyleClass().add("infobar-bottom-box");
        bottomBox.setMinHeight(size);
        bottomBox.setMaxHeight(size);
        bottomBox.setAlignment(Pos.CENTER_LEFT);
        bottomBox.getChildren().addAll(modeSwitcher, infoLabel);
    }

    /**
     * Get bottom view for editor
     *
     * @param stage of editor
     * @return HBox
     */
    HBox view(Stage stage) {
        this.stage = stage;
        return bottomBox;
    }

    /**
     * Are border bars visible
     *
     * @return view mode
     */
    boolean borderBarsVisibile() {
        return borderBarsVisibility.get();
    }

    /**
     * @return property for border bars visibility
     */
    ReadOnlyBooleanProperty borderBarsVisibilityProperty() {
        return borderBarsVisibility.getReadOnlyProperty();
    }

    /**
     * Set message to bottom info bar
     */
    @Override
    public void writeInfoMessage(String message) {
        this.infoLabel.setText(message);
    }

    /**
     * Get message to bottom info bar
     */
    @Override
    public String infoMessage() {
        return infoLabel.getText();
    }
}
