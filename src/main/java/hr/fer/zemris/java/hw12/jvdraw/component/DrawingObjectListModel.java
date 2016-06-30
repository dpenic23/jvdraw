package hr.fer.zemris.java.hw12.jvdraw.component;

import hr.fer.zemris.java.hw12.jvdraw.object.GeometricalObject;

import javax.swing.AbstractListModel;

/**
 * {@code DrawingObjectListModel} is an observer and subject in Observer design
 * pattern. This class is used as a bridge between {@code DrawingModel} and
 * {@code JList} which is using this {@code AbstractListModel}. When some change
 * occurs in observed model, notifications are being passed to the list
 * implementations.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class DrawingObjectListModel extends
        AbstractListModel<GeometricalObject> implements DrawingModel,
        DrawingModelListener {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@code DrawingModel} which is being adapted by this
     * {@code DrawingObjectListModel}.
     */
    private DrawingModel drawingModel;

    /**
     * Creates new {@code DrawingObjectListModel} which adapts specified
     * {@code DrawingModel} and notifies all lists about changes in this model.
     * 
     * @param drawingModel
     *            {@code DrawingModel} to be adapted by this
     *            {@code DrawingObjectListModel}
     */
    public DrawingObjectListModel(DrawingModel drawingModel) {
        this.drawingModel = drawingModel;
        this.drawingModel.addDrawingModelListener(this);
    }

    @Override
    public int getSize() {
        return drawingModel.getSize();
    }

    @Override
    public GeometricalObject getElementAt(int index) {
        return drawingModel.getObject(index);
    }

    @Override
    public GeometricalObject getObject(int index) {
        return drawingModel.getObject(index);
    }

    @Override
    public void add(GeometricalObject object) {
        drawingModel.add(object);
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener listener) {
        drawingModel.addDrawingModelListener(listener);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener listener) {
        drawingModel.removeDrawingModelListener(listener);
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fireIntervalAdded(this, index0, index1);
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fireIntervalRemoved(this, index0, index1);
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fireContentsChanged(this, index0, index1);
    }

}
