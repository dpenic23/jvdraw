package hr.fer.zemris.java.hw12.jvdraw.component;

import hr.fer.zemris.java.hw12.jvdraw.object.GeometricalObject;

/**
 * {@code DrawingModel} defines an interface for subjects in Observer design
 * pattern where concrete observers are implementations of
 * {@code DrawingModelListener}. Concrete subjects have to implement several
 * method implementations which are used for retrieving
 * {@code GeometricalObject}s which are stored in this model, for adding
 * {@code GeometricalObject}s and for adding and removing attached observers.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public interface DrawingModel {

    /**
     * Returns size of this {@code DrawingModel}, i.e. number of
     * {@code GeometricalObject}s stored in internal collection of objects
     * inside this model.
     * 
     * @return Number of {@code GeometricalObject}s
     */
    int getSize();

    /**
     * Returns {@code GeometricalObject}s which is stored on specified position
     * in this {@code DrawingModel}. Objects are being stored such that objects
     * which are added first are located on lower positions. First
     * {@code GeometricalObject} is on position 0.
     * 
     * @param index
     *            Requested index
     * @return {@code GeometricalObject} on specified position
     */
    GeometricalObject getObject(int index);

    /**
     * Adds new {@code GeometricalObject} in internal collection of objects
     * inside this {@code DrawingModel}.
     * 
     * @param object
     *            {@code GeometricalObject} to be added
     */
    void add(GeometricalObject object);

    /**
     * Adds new {@code DrawingModelListener}, i.e. observer in this Observer
     * design pattern, to be attached to this subject, {@code DrawingModel}.
     * When some change occurs, all observers will be notified.
     * 
     * @param listener
     *            {@code DrawingModelListener} to be attached on this subject
     */
    void addDrawingModelListener(DrawingModelListener listener);

    /**
     * Removes specified {@code DrawingModelListener} and detaches it from this
     * subject, {@code DrawingModel}.
     * 
     * @param listener
     *            {@code DrawingModelListener} to be detached from this subject
     */
    void removeDrawingModelListener(DrawingModelListener listener);

}
