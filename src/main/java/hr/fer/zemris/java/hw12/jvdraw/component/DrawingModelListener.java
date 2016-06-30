package hr.fer.zemris.java.hw12.jvdraw.component;

/**
 * {@code DrawingModelListener} defines an interface for all observers in
 * Observer design pattern, where subject is an implementation of
 * {@code DrawingModel} interface. Implementations of this interface has to
 * implement several methods which indicate what kind of change has occurred in
 * observed {@code DrawingModel}.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public interface DrawingModelListener {

    /**
     * Indicates that some objects are added to the internal collection of
     * objects inside observed {@code DrawingModel}.
     * 
     * @param source
     *            {@code DrawingModel} where this change has occurred
     * @param index0
     *            Index on which change has occurred, inclusively
     * @param index1
     *            Index on which change ends, exclusively
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);

    /**
     * Indicates that some objects are being removed from the internal
     * collection of objects inside observed {@code DrawingModel}.
     * 
     * @param source
     *            {@code DrawingModel} where this change has occurred
     * @param index0
     *            Index on which change has occurred, inclusively
     * @param index1
     *            Index on which change ends, exclusively
     */
    public void objectsRemoved(DrawingModel source, int index0, int index1);

    /**
     * Indicates that some objects are being changed inside the internal
     * collection of objects inside observed {@code DrawingModel}.
     * 
     * @param source
     *            {@code DrawingModel} where this change has occurred
     * @param index0
     *            Index on which change has occurred, inclusively
     * @param index1
     *            Index on which change ends, exclusively
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);

}
