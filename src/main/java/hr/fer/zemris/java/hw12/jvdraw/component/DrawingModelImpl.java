package hr.fer.zemris.java.hw12.jvdraw.component;

import hr.fer.zemris.java.hw12.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.object.Line;

import java.util.ArrayList;
import java.util.List;

/**
 * {@code DrawingModelImpl} is a concrete implementation of {@code DrawingModel}
 * which holds various implementations of {@code GeometricalObject}s. This
 * implementation is subject in Observer design patter where observers are
 * implementations of {@code DrawingModelListener} interface. When some change
 * occurs in this model, all observers are being notified.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class DrawingModelImpl implements DrawingModel {

    /**
     * Collection of all attached listeners, i.e. observers in this Observer
     * design pattern.
     */
    private List<DrawingModelListener> drawingModelListeners = new ArrayList<>();

    /**
     * Collection of {@code GeometricalObject}s which are being hold by this
     * {@code DrawingModel}.
     */
    private List<GeometricalObject> geometricalObjects = new ArrayList<>();

    /**
     * Index of a line which is being created when new one is drawn. This index
     * is used for defining line's name.
     */
    private int lineIndex = 1;

    /**
     * Index of a circle which is being created when new one is drawn. This
     * index is used for defining cricle's name.
     */
    private int circleIndex = 1;

    @Override
    public int getSize() {
        return geometricalObjects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        return geometricalObjects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        geometricalObjects.add(object);

        // Define index for this object
        if (object instanceof Line) {
            object.setIndex(lineIndex);
            lineIndex++;
        } else {
            object.setIndex(circleIndex);
            circleIndex++;
        }

        int index = geometricalObjects.size() - 1;

        // Notify all observers about change
        for (DrawingModelListener listener : drawingModelListeners) {
            listener.objectsAdded(this, index, index);
        }
    }

    /**
     * Indicates that {@code GeometricalObject} on specified position is being
     * changed. After this call, all attached observers are being notified about
     * this change on specified index.
     * 
     * @param index
     *            Index on which change has occurred
     */
    public void objectChanged(int index) {
        for (DrawingModelListener listener : drawingModelListeners) {
            listener.objectsChanged(this, index, index);
        }
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener listener) {
        drawingModelListeners.add(listener);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener listener) {
        drawingModelListeners.remove(listener);
    }

}
