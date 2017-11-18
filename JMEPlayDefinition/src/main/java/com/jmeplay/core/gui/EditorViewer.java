package com.jmeplay.core.gui;

import java.nio.file.Path;
import java.util.List;

/**
 * Abstract class to implement varies editor views
 *
 * @author vp-byte (Vladimir Petrenko)
 */
public abstract class EditorViewer {

    /**
     * List with supported file types. Please use file extensions to
     *
     * @return
     */
    public abstract List<String> filetypes();

    public abstract EditorViewerTab view(final Path path);

}
