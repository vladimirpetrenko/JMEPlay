package com.jmeplay.plugin.console;

import com.jmeplay.core.JMEPlayConsole;
import com.jmeplay.core.gui.EditorComponent;
import com.jmeplay.core.gui.Position;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * Implementation for JMEPlayConsole
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayConsoleComponent extends EditorComponent implements JMEPlayConsole {

    private int iconSizeBar;
    private boolean writeException;
    private StringBuilder stringBuilder = new StringBuilder();
    private Label label;
    private StackPane stackPane;
    private BorderPane borderPane;

    @Autowired
    private JMEPlayConsoleSettings jmePlayConsoleSettings;
    @Autowired
    private JMEPlayConsoleLocalization jmePlayConsoleLocalization;
    @Autowired
    private JMEPlayConsoleArea jmePlayConsoleArea;
    @Autowired
    private JMEPlayConsoleToolBar jmePlayConsoleToolBar;

    /**
     * Initialize JMEPlayConsole
     */
    @PostConstruct
    private void init() {
        initSettings();
        setPosition(Position.BOTTOM);
        String consoleLabel = jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_CONSOLE);
        label = new Label(consoleLabel, ImageLoader.initImageView(this.getClass(),
                JMEPlayConsoleResources.ICONS_CONSOLE_CONSOLE, iconSizeBar, iconSizeBar));

        initStackPane();
        borderPane = new BorderPane();
        borderPane.setMinHeight(0);
        borderPane.setLeft(jmePlayConsoleToolBar);
        borderPane.setCenter(stackPane);
    }

    /**
     * Initialize stack pane
     */
    private void initStackPane() {
        stackPane = new StackPane(new VirtualizedScrollPane<>(jmePlayConsoleArea));
        stackPane.getStylesheets().add(getClass().getResource(JMEPlayConsoleResources.CSS_CONSOLE).toExternalForm());
    }

    /**
     * Initialize settings for console tool bar
     */
    private void initSettings() {
        writeException = jmePlayConsoleSettings.getWriteExceptions();
        iconSizeBar = jmePlayConsoleSettings.getIconSizeBar();
    }

    /**
     * {@link EditorComponent:uniqueId}
     *
     * @return uniqueId
     */
    @Override
    public String uniqueId() {
        return "81e5ad3a-7e83-4b90-b744-90161d7412bd";
    }

    /**
     * {@link EditorComponent:name}
     *
     * @return name
     */
    @Override
    public String name() {
        return jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_CONSOLE);
    }

    /**
     * {@link EditorComponent:description}
     *
     * @return description
     */
    @Override
    public String description() {
        return jmePlayConsoleLocalization.getString(JMEPlayConsoleLocalization.CONSOLE_LOCALIZATION_DESCRIPTION);
    }

    /**
     * {@link EditorComponent:label}
     *
     * @return label
     */
    @Override
    public Label label() {
        return label;
    }

    /**
     * {@link EditorComponent:component}
     *
     * @return component as node
     */
    @Override
    public Node component() {
        return borderPane;
    }

    /**
     * {@link JMEPlayConsole:writeText}
     */
    @Override
    public void writeMessage(MessageType messageType, String message) {
        String text = "\n[" + messageType.name() + "] : " + message;
        stringBuilder.insert(0, text);
        jmePlayConsoleArea.writeText(stringBuilder.toString());
        jmePlayConsoleToolBar.updateButtons();
    }

    /**
     * {@link JMEPlayConsole:writeException}
     */
    @Override
    public void writeException(Exception exception) {
        if (writeException) {
            writeMessage(MessageType.ERROR, stackTraceToString(exception));
        }
    }

    /**
     * Clear text buffer
     */
    void clear() {
        stringBuilder = new StringBuilder();
    }

    /**
     * Convert exception stack trace to string
     *
     * @param exception to get stack trace
     * @return stack trace as string
     */
    private String stackTraceToString(Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        return sw.toString();
    }

}
