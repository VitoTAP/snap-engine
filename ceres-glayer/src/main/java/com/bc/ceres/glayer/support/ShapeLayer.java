package com.bc.ceres.glayer.support;

import com.bc.ceres.glayer.Layer;
import com.bc.ceres.grender.Rendering;
import com.bc.ceres.grender.Viewport;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// todo - use style here

/**
 * A shape layer is used to draw {@link Shape}s.
 *
 * @author Norman Fomferra
 */
public class ShapeLayer extends Layer {

    private final List<Shape> shapeList;
    private final AffineTransform shapeToModelTransform;
    private final AffineTransform modelToShapeTransform;

    public ShapeLayer(Shape[] shapes) {
        this.shapeList = new ArrayList<Shape>(Arrays.asList(shapes));
        this.shapeToModelTransform =
                this.modelToShapeTransform = new AffineTransform();
    }

    public ShapeLayer(Shape[] shapes, AffineTransform shapeToModelTransform) {
        this.shapeList = new ArrayList<Shape>(Arrays.asList(shapes));
        this.shapeToModelTransform = (AffineTransform) shapeToModelTransform.clone();
        try {
            this.modelToShapeTransform = shapeToModelTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            throw new IllegalArgumentException("imageToModelTransform", e);
        }
    }

    public List<Shape> getShapeList() {
        return new ArrayList<Shape>(shapeList);
    }

    public void setShapeList(List<Shape> list) {
        shapeList.clear();
        shapeList.addAll(list);
    }

    public AffineTransform getShapeToModelTransform() {
        return (AffineTransform) shapeToModelTransform.clone();
    }

    public AffineTransform getModelToShapeTransform() {
        return (AffineTransform) modelToShapeTransform.clone();
    }

    @Override
    protected Rectangle2D getLayerModelBounds() {
        Rectangle2D shapeBounds = null;
        for (Shape shape : shapeList) {
            if (shapeBounds == null) {
                shapeBounds = shape.getBounds2D();
            } else {
                shapeBounds.add(shape.getBounds2D());
            }
        }
        return shapeBounds != null ? shapeToModelTransform.createTransformedShape(shapeBounds).getBounds2D() : null;
    }

    @Override
    protected void renderLayer(Rendering rendering) {
        final Graphics2D g = rendering.getGraphics();
        final Viewport vp = rendering.getViewport();
        final AffineTransform transformSave = g.getTransform();
        try {
            final AffineTransform transform = new AffineTransform();
            transform.concatenate(vp.getModelToViewTransform());
            transform.concatenate(shapeToModelTransform);
            g.setTransform(transform);
            for (Shape shape : shapeList) {
                g.setPaint(Color.WHITE);
                g.fill(shape);
                g.setPaint(Color.BLACK);
                g.draw(shape);
            }
        } finally {
            g.setTransform(transformSave);
        }
    }
}