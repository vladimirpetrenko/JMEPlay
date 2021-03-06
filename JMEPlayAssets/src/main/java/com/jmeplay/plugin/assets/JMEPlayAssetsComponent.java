package com.jmeplay.plugin.assets;

import com.jmeplay.core.gui.EditorComponent;
import com.jmeplay.core.gui.Position;
import com.jmeplay.core.handler.FileHandler;
import com.jmeplay.core.utils.ImageLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;

/**
 * Create AssetEditorComponent to load and view all assets in tree view
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
public class JMEPlayAssetsComponent extends EditorComponent {

    private int iconSizeBar;
    private String rootFolder;

    private Label label;
    private StackPane component;

    private TreeView<Path> treeView;
    private TreeItem<Path> rootTreeItem;

    // Injected
    private JMEPlayAssetsSettings jmePlayAssetsSettings;
    private JMEPlayAssetsLocalization jmePlayAssetsLocalization;
    private JMEPlayAssetsImageDefinder jmePlayAssetsImageDefinder;
    private List<FileHandler<TreeView<Path>>> fileHandlers;

    /**
     * JMEPlayAssetsSettings
     *
     * @param settings
     */
    @Autowired
    public void setSettings(JMEPlayAssetsSettings settings) {
        this.jmePlayAssetsSettings = settings;
    }

    /**
     * JMEPlayAssetsLocalization
     *
     * @param localization
     */
    @Autowired
    public void setLocalization(JMEPlayAssetsLocalization localization) {
        this.jmePlayAssetsLocalization = localization;
    }

    /**
     * JMEPlayAssetsImageDefinder
     *
     * @param jmePlayAssetsImageDefinder
     */
    @Autowired
    public void setJmePlayAssetsImageDefinder(JMEPlayAssetsImageDefinder jmePlayAssetsImageDefinder) {
        this.jmePlayAssetsImageDefinder = jmePlayAssetsImageDefinder;
    }

    /**
     * All handlers for files
     *
     * @param fileHandlers
     */
    @Autowired(required = false)
    public void setFileHandlers(List<FileHandler<TreeView<Path>>> fileHandlers) {
        this.fileHandlers = fileHandlers;
    }

    @PostConstruct
    private void init() {
        initSettings();
        setPosition(Position.LEFT);
        label = new Label("Assets", ImageLoader.initImageView(this.getClass(),
                JMEPLayAssetsResources.ICONS_ASSETS_ASSETS, iconSizeBar, iconSizeBar));
        initTreeView();
        component = new StackPane(treeView);
    }

    /**
     * Initialize settings for assets component
     */
    private void initSettings() {
        iconSizeBar = jmePlayAssetsSettings.getIconSizeBar();
        rootFolder = jmePlayAssetsSettings.getRootFolder();
    }

    /**
     * {@link EditorComponent:uniqueId}
     *
     * @return uniqueId
     */
    @Override
    public String uniqueId() {
        return "73a3b67a-d279-4d5e-9b83-852570cdc2a6";
    }

    /**
     * {@link EditorComponent:name}
     *
     * @return name
     */
    @Override
    public String name() {
        return jmePlayAssetsLocalization.getString(JMEPlayAssetsLocalization.ASSETS_LOCALISATION_ASSETS);
    }

    /**
     * {@link EditorComponent:description}
     *
     * @return description
     */
    @Override
    public String description() {
        return jmePlayAssetsLocalization.getString(JMEPlayAssetsLocalization.ASSETS_LOCALIZATION_DESCRIPTION);
    }

    @Override
    public Label label() {
        return label;
    }

    @Override
    public Node component() {
        return component;
    }

    private void initTreeView() {
        if (rootFolder == null) {
            rootTreeItem = new TreeItem<>();
            treeView = new TreeView<>(rootTreeItem);
            return;
        }
        Path rootPath = Paths.get(rootFolder);
        rootTreeItem = new TreeItem<>(rootPath.getFileName());
        rootTreeItem.setExpanded(true);

        try {
            createTree(rootPath, rootTreeItem);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // sort tree structure by name
        rootTreeItem.getChildren().sort(Comparator.comparing(t -> t.getValue().getFileName().toString().toLowerCase()));

        // create components
        treeView = new TreeView<>(rootTreeItem);
        treeView.setCellFactory(param -> new JMEPlayAssetsTreeCell(fileHandlers));
    }

    /**
     * Recursively create the tree
     *
     * @param rootPath
     * @throws IOException
     */
    private void createTree(Path rootPath, TreeItem<Path> rootItem) throws IOException {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootPath)) {
            for (Path path : directoryStream) {
                TreeItem<Path> newItem = new TreeItem(path, jmePlayAssetsImageDefinder.imageByFilename(path));
                rootItem.getChildren().add(newItem);
                if (Files.isDirectory(path)) {
                    createTree(path, newItem);
                }
            }
            // sort tree structure by name
            rootItem.getChildren().sort(Comparator.comparing(t -> t.getValue().getFileName().toString().toLowerCase()));
        }
    }

}
