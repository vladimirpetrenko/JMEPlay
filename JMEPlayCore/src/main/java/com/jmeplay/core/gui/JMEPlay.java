package com.jmeplay.core.gui;

import com.jmeplay.Resources;
import com.jmeplay.core.utils.Settings;
import de.codecentric.centerdevice.javafxsvg.SvgImageLoaderFactory;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Start JMEPlay editor
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@SpringBootApplication
@ComponentScan({"com.jmeplay.core", "com.jmeplay.plugin"})
public class JMEPlay extends Application {

    // Spring context
    private ConfigurableApplicationContext appContext;
    private Scene scene;
    private Group root;
    private Stage stage;
    
    @Value("${application.name}")
    private String applicationName;

    @Value("${jme.version}")
    private String jmeVersion;

    // Injected
    private Settings settings;
    private EditorBuilder editorBuilder;
    private JMEEditor jmeEditor;

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Autowired
    public void setEditorBuilder(EditorBuilder editorBuilder) {
        this.editorBuilder = editorBuilder;
    }

    @Autowired
    public void setJmeEditor(JMEEditor jmeEditor) {
        this.jmeEditor = jmeEditor;
    }
    
    /**
     * Main point to start whole Application
     *
     * @param args for start configs
     */
    public static void main(String[] args) {
        // Activate SVG support for javafx
        SvgImageLoaderFactory.install();

        Application.launch(args);
    }

    /**
     * Initialize spring context
     *
     * @throws Exception if JavaFX run fail
     */
    @Override
    public void init() throws Exception {
        appContext = SpringApplication.run(this.getClass());
        appContext.getAutowireCapableBeanFactory().autowireBean(this);
    }

    /**
     * Start as JavaFX application
     *
     * @param stage to view content
     * @throws Exception if JavaFX run fail
     */
    @Override
    public void start(Stage stage) throws Exception {
        this.stage = stage;
        jmeEditor.setStage(stage);
        root = new Group();
        initScene();
        initStage();
    }

    /**
     * Create scene for stage
     */
    private void initScene() {
        scene = new Scene(root);
        jmeEditor.setScene(scene);
        scene.getStylesheets().add(getClass().getResource(Resources.editorCss).toExternalForm());
    }

    /**
     * Load and set full content of a stage
     */
    private void initStage() {
        initStageTitle();
        initStageMinHeight();
        initStageHeight();
        initStageMinWidth();
        initStageWidth();
        initStageMaximized();

        stage.setScene(this.scene);
        root.getChildren().addAll(this.editorBuilder.view(stage));
        stage.show();

        initStageY();
        initStageX();
    }

    /**
     * Title of the stage
     */
    private void initStageTitle() {
        stage.setTitle(applicationName + " (" + jmeVersion + ")");
    }

    /**
     * Min height of the stage
     */
    private void initStageMinHeight() {
        Double stageMinHeight = settings.getOptionDouble(Resources.editorStageMinHeight, Resources.editorDefaultStageMinHeight);
        stage.setMinHeight(stageMinHeight);
    }

    /**
     * Height of the stage
     */
    private void initStageHeight() {
        Double stageHeight = settings.getOptionDouble(Resources.editorStageHeight, Resources.editorDefaultStageMinHeight);
        stage.setHeight(stageHeight);
        stage.heightProperty().addListener((observable, oldValue, newValue) -> settings.setOption(Resources.editorStageHeight, newValue.doubleValue()));
    }

    /**
     * Min width of the stage
     */
    private void initStageMinWidth() {
        Double stageMinWidth = settings.getOptionDouble(Resources.editorStageMinWidth, Resources.editorDefaultStageMinWidth);
        stage.setMinWidth(stageMinWidth);
    }

    /**
     * Width of the stage
     */
    private void initStageWidth() {
        Double stageWidth = settings.getOptionDouble(Resources.editorStageWidth, Resources.editorDefaultStageMinWidth);
        stage.setWidth(stageWidth);
        stage.widthProperty().addListener((observable, oldValue, newValue) -> settings.setOption(Resources.editorStageWidth, newValue.doubleValue()));
    }

    /**
     * Stage maximized or not
     */
    private void initStageMaximized() {
        stage.setMaximized(settings.getOptionAsBoolean(Resources.editorMaximized, Resources.editorDefaultMaximized));
        stage.maximizedProperty().addListener((observable, oldValue, newValue) -> settings.setOption(Resources.editorMaximized, newValue));
    }

    /**
     * Stage y position
     */
    private void initStageY() {
        Double stageY = settings.getOptionDouble(Resources.editorStageY, Resources.editorDefaultStageY);
        if (stageY != null) {
            stage.setY(stageY);
        }
        stage.yProperty().addListener((observable, oldValue, newValue) -> {
            if (!stage.isMaximized()) {
                settings.setOption(Resources.editorStageY, newValue.doubleValue());
            }
        });
    }

    /**
     * Stage x position
     */
    private void initStageX() {
        Double stageX = settings.getOptionDouble(Resources.editorStageX, Resources.editorDefaultStageX);
        if (stageX != null) {
            double screensWidth = Screen.getScreens().stream().mapToDouble(screen -> screen.getBounds().getWidth()).sum();
            double stageXWidth = stageX + stage.getMinWidth();
            if (stageXWidth <= screensWidth) {
                stage.setX(stageX);
            }
        }
        stage.xProperty().addListener((observable, oldValue, newValue) ->
        {
            if (!stage.isMaximized()) {
                settings.setOption(Resources.editorStageX, newValue.doubleValue());
            }
        });
    }

    /**
     * Stop spring context
     *
     * @throws Exception if stopping fail
     */
    @Override
    public void stop() throws Exception {
        appContext.stop();
    }
}
