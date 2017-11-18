/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jmeplay.core.gui;

import java.nio.file.Path;
import javafx.scene.control.Tab;

/**
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public class EditorViewerTab extends Tab {

    private Path path;

    public void init(final Path path) {
        this.path = path;
        this.setText(getTitle());
    }

    public String getTitle() {
        return path.getFileName().toString();
    }

    public Path path() {
        return path;
    }

}
