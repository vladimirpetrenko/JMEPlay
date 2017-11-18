package com.jmeplay.plugin.imageview;

import java.nio.file.Path;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * ImageViewer for JMEPlayImage
 *
 * @author vp-byte (Vladimir Petrenko)
 */
@Component
@Scope("prototype")
public class JMEPlayImageViewerScrollPane extends ScrollPane {

    private Path path;

    public void init(Path path) {
        this.path = path;
        initContent();
    }

    private void initContent() {

        Region zoomTarget = initZoomTarget();
        Group group = new Group(zoomTarget);

        // stackpane for centering the content, in case the ScrollPane viewport
        // is larget than zoomTarget
        StackPane content = new StackPane(group);
        group.layoutBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            // keep it at least as large as the content
            content.setMinWidth(newBounds.getWidth());
            content.setMinHeight(newBounds.getHeight());
        });

        setContent(content);
        setPannable(true);
        centerNodeInScrollPane();
        viewportBoundsProperty().addListener((observable, oldBounds, newBounds) -> {
            // use vieport size, if not too small for zoomTarget
            content.setPrefSize(newBounds.getWidth(), newBounds.getHeight());
        });

        content.setOnScroll(evt -> {
            if (evt.isControlDown()) {
                evt.consume();

                final double zoomFactor = evt.getDeltaY() > 0 ? 1.2 : 1 / 1.2;

                Bounds groupBounds = group.getLayoutBounds();
                final Bounds viewportBounds = getViewportBounds();

                // calculate pixel offsets from [0, 1] range
                double valX = getHvalue() * (groupBounds.getWidth() - viewportBounds.getWidth());
                double valY = getVvalue() * (groupBounds.getHeight() - viewportBounds.getHeight());

                // convert content coordinates to zoomTarget coordinates
                Point2D posInZoomTarget = zoomTarget.parentToLocal(group.parentToLocal(new Point2D(evt.getX(), evt.getY())));

                // calculate adjustment of scroll position (pixels)
                Point2D adjustment = zoomTarget.getLocalToParentTransform().deltaTransform(posInZoomTarget.multiply(zoomFactor - 1));

                // do the resizing
                zoomTarget.setScaleX(zoomFactor * zoomTarget.getScaleX());
                zoomTarget.setScaleY(zoomFactor * zoomTarget.getScaleY());

                // refresh ScrollPane scroll positions & content bounds
                layout();

                // convert back to [0, 1] range
                // (too large/small values are automatically corrected by ScrollPane)
                groupBounds = group.getLayoutBounds();
                setHvalue((valX + adjustment.getX()) / (groupBounds.getWidth() - viewportBounds.getWidth()));
                setVvalue((valY + adjustment.getY()) / (groupBounds.getHeight() - viewportBounds.getHeight()));
            }
        });
    }

    private Region initZoomTarget() {
        ImageView imageView = new ImageView("file:" + path.toString());

        Region zoomTarget = new StackPane(imageView);
        zoomTarget.setPrefSize(imageView.getFitWidth(), imageView.getFitHeight());
        zoomTarget.setOnDragDetected(evt -> {
            Node target = (Node) evt.getTarget();
            while (target != zoomTarget && target != null) {
                target = target.getParent();
            }
            if (target != null) {
                target.startFullDrag();
            }
        });
        return zoomTarget;
    }

    public void centerNodeInScrollPane() {
        double w = getContent().getBoundsInLocal().getWidth();
        double h = getContent().getBoundsInLocal().getHeight();
        double y = (getContent().getBoundsInParent().getMaxY() + getContent().getBoundsInParent().getMinY()) / 2.0;
        double x = (getContent().getBoundsInParent().getMaxX() + getContent().getBoundsInParent().getMinX()) / 2.0;
        double vB = getViewportBounds().getHeight();
        double hB = getViewportBounds().getWidth();
        setVvalue(getVmax() * ((y - 0.5 * vB) / (h - vB)));
        setHvalue(getHmax() * ((x - 0.5 * hB) / (w - hB)));
    }

}
