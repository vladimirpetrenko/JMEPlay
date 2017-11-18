package com.jmeplay.core.gui;

import com.jmeplay.Resources;
import com.jmeplay.core.utils.Settings;
import javafx.beans.InvalidationListener;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * Create center view to render
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class EditorBuilderCenterCenter implements JMEEditorCenter {

    private Stage stage;
    private TabPane centerTabPane;
    private Node centerNode;

    private Node leftNode;
    private Node rightNode;
    private Node bottomNode;

    private BooleanProperty leftRemoved;
    private ChangeListener<Number> leftDividerPositionChangeListener;
    private BooleanProperty rightRemoved;
    private ChangeListener<Number> rightDividerPositionChangeListener;
    private BooleanProperty bottomRemoved;
    private ChangeListener<Number> bottomDividerPositionChangeListener;

    private SplitPane topSplitPane;
    private SplitPane splitPane;

    // Injected
    private Settings settings;
    private JMEEditor jmeEditor;

    @Autowired
    public void setSettings(Settings settings) {
        this.settings = settings;
    }

    @Autowired
    public void setJmeEditor(JMEEditor jmeEditor) {
        this.jmeEditor = jmeEditor;
    }
    
    /**
     * Initialize center view
     */
    @PostConstruct
    private void init() {
        centerTabPane = new TabPane();
        centerNode = new StackPane(centerTabPane);
        jmeEditor.setCenterTabPane(centerTabPane);
        
        leftNode = new StackPane(new Text("Left"));
        rightNode = new StackPane(new Text("Right"));
        bottomNode = new StackPane(new Text("Bottom"));

        leftRemoved = new SimpleBooleanProperty(false);
        rightRemoved = new SimpleBooleanProperty(false);
        bottomRemoved = new SimpleBooleanProperty(false);

        initTopSplitPane();
        initSplitPane();
    }

    @Override
    public TabPane centerView() {
        return centerTabPane;
    }

    /**
     * Initialize view of the top split pane
     */
    private void initTopSplitPane() {
        topSplitPane = new SplitPane();
        topSplitPane.setOrientation(Orientation.HORIZONTAL);
        topSplitPane.getItems().addAll(leftNode, centerNode, rightNode);
    }

    /**
     * Initialize view of the split pane
     */
    private void initSplitPane() {
        splitPane = new SplitPane();
        splitPane.setOrientation(Orientation.VERTICAL);
        splitPane.getItems().addAll(topSplitPane, bottomNode);
    }

    /**
     * Create center view for editor
     *
     * @return
     */
    public SplitPane view(Stage stage) {
        this.stage = stage;

        stage.widthProperty().addListener(observable -> updateDividerPositions());
        stage.heightProperty().addListener(observable -> updateDividerPositions());
        stage.maximizedProperty().addListener(observable -> updateDividerPositions());
        stage.fullScreenProperty().addListener(observable -> updateDividerPositions());

        stage.xProperty().addListener(observable -> updateDividerPositions());
        stage.yProperty().addListener(observable -> updateDividerPositions());

        stage.setOnShown(observable -> {
            updateDividerPositions();
        });

        bottomRemoved.addListener((obsBottom) -> {
            updateDividerPositions();
            addEventBottomDividerPosition();
        });

        leftRemoved.addListener(obsLeft -> {
            updateDividerPositions();
            addEventLeftDividerPosition();
            addEventRightDividerPosition();
        });

        rightRemoved.addListener(obsLeft -> {
            updateDividerPositions();
            addEventLeftDividerPosition();
            addEventRightDividerPosition();
        });

        return splitPane;
    }

    /**
     * Update divider positions
     */
    private void updateDividerPositions() {
        splitPane.setDividerPositions(settings.getOptionDouble(Resources.editorBottomDividerPosition, Resources.editorDefaultBottomDividerPosition));

        if (topSplitPane.getDividers().size() == 2) {
            topSplitPane.setDividerPositions(
                    settings.getOptionDouble(Resources.editorLeftDividerPosition, Resources.editorDefaultLeftDividerPosition),
                    settings.getOptionDouble(Resources.editorRightDividerPosition, Resources.editorDefaultRightDividerPosition)
            );
            return;
        }

        if (topSplitPane.getDividers().size() == 1) {
            if (getLeftRemoved()) {
                topSplitPane.setDividerPositions(
                        settings.getOptionDouble(Resources.editorRightDividerPosition, Resources.editorDefaultRightDividerPosition)
                );
                return;
            }

            if (getRightRemoved()) {
                topSplitPane.setDividerPositions(
                        settings.getOptionDouble(Resources.editorLeftDividerPosition, Resources.editorDefaultLeftDividerPosition)
                );
                return;
            }
        }
    }

    /**
     * Add event to save bottom divider position in settings file
     */
    private void addEventBottomDividerPosition() {
        if (splitPane.getDividers().size() > 0) {
            SplitPane.Divider divider = splitPane.getDividers().get(0);
            divider.setPosition(settings.getOptionDouble(Resources.editorBottomDividerPosition, Resources.editorDefaultBottomDividerPosition));
            if (bottomDividerPositionChangeListener != null) {
                divider.positionProperty().removeListener(bottomDividerPositionChangeListener);
            }
            bottomDividerPositionChangeListener = (observable, oldValue, newValue) -> settings.setOption(Resources.editorBottomDividerPosition, newValue.doubleValue());
            divider.positionProperty().addListener(bottomDividerPositionChangeListener);
        }
    }

    /**
     * Add event to save left divider position in settings file
     */
    private void addEventLeftDividerPosition() {
        if (getLeftRemoved()) {
            return;
        }
        SplitPane.Divider divider = topSplitPane.getDividers().get(0);
        divider.setPosition(settings.getOptionDouble(Resources.editorLeftDividerPosition, Resources.editorDefaultLeftDividerPosition));
        if (leftDividerPositionChangeListener != null) {
            divider.positionProperty().removeListener(leftDividerPositionChangeListener);
        }
        leftDividerPositionChangeListener = (observable, oldValue, newValue) -> settings.setOption(Resources.editorLeftDividerPosition, newValue.doubleValue());
        divider.positionProperty().addListener(leftDividerPositionChangeListener);
    }

    /**
     * Add event to save right divider position in settings file
     */
    private void addEventRightDividerPosition() {
        SplitPane.Divider divider = null;
        if (getLeftRemoved() && getRightRemoved()) {
            return;
        } else {
            if (getLeftRemoved()) {
                divider = topSplitPane.getDividers().get(0);
            } else {
                divider = topSplitPane.getDividers().get(1);
            }
        }
        divider.setPosition(settings.getOptionDouble(Resources.editorRightDividerPosition, Resources.editorDefaultRightDividerPosition));
        if (rightDividerPositionChangeListener != null) {
            divider.positionProperty().removeListener(rightDividerPositionChangeListener);
        }
        rightDividerPositionChangeListener = (observable, oldValue, newValue) -> settings.setOption(Resources.editorRightDividerPosition, newValue.doubleValue());
        divider.positionProperty().addListener(rightDividerPositionChangeListener);
    }

    /**
     * remove left view
     */
    public void removeLeft() {
        topSplitPane.getItems().remove(leftNode);
        leftRemoved.set(true);
    }

    /**
     * @return is left view removed
     */
    public boolean getLeftRemoved() {
        return leftRemoved.get();
    }

    /**
     * Set left view
     *
     * @param node to view
     */
    public void setLeft(Node node) {
        if (getLeftRemoved()) {
            topSplitPane.getItems().add(0, node);
        } else {
            topSplitPane.getItems().set(0, node);
        }
        leftNode = node;
        leftRemoved.set(false);
    }

    /**
     * remove right view
     */
    public void removeRight() {
        topSplitPane.getItems().remove(rightNode);
        rightRemoved.set(true);
    }

    /**
     * @return is right view removed
     */
    public boolean getRightRemoved() {
        return rightRemoved.get();
    }

    /**
     * Set right view
     *
     * @param node to view
     */
    public void setRight(Node node) {
        int index = getLeftRemoved() ? 1 : 2;
        if (getRightRemoved()) {
            topSplitPane.getItems().add(index, node);
        } else {
            topSplitPane.getItems().set(index, node);
        }
        rightNode = node;
        rightRemoved.set(false);
    }

    /**
     * remove bottom view
     */
    public void removeBottom() {
        splitPane.getItems().remove(bottomNode);
        bottomRemoved.set(true);
    }

    /**
     * @return is bottom view removed
     */
    public boolean getBottomRemoved() {
        return bottomRemoved.get();
    }

    /**
     * Set bottom view
     *
     * @param node to view
     */
    public void setBottom(Node node) {
        if (getBottomRemoved()) {
            splitPane.getItems().add(1, node);
        } else {
            splitPane.getItems().set(1, node);
        }
        bottomNode = node;
        bottomRemoved.set(false);
    }
}
