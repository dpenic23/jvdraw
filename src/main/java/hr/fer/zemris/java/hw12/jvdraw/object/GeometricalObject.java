package hr.fer.zemris.java.hw12.jvdraw.object;

import java.awt.Graphics;

import javax.swing.JComponent;

/**
 * {@code GeometricalObject} class extends {@code JComponent} class in order to
 * represent geometric objects which can be treated as component which can be
 * painted and drawn by its {@linkplain #paintComponent(Graphics)} method. This
 * class defines another property, which represents serial number of each
 * component. In that way, for example, every {@code Line} implementation can be
 * distinguished from another implementations. Main purpose is to define
 * meaningful name for each component.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public abstract class GeometricalObject extends JComponent {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Serial number index by which each component derived from this class is
     * distinguished.
     */
    protected int index;

    /**
     * Sets specified index as current one. For more informations about this
     * property, look at the class documentation.
     * 
     * @param index
     *            Index to be set as current one
     * @throws IndexOutOfBoundsException
     *             If index is less than 1
     */
    public void setIndex(int index) throws IndexOutOfBoundsException {
        // Check if index is 0 or negative
        if (index < 1) {
            throw new IndexOutOfBoundsException(
                    "Index can not be less than 1: " + index);
        }

        this.index = index;
    }

    /**
     * Returns {@code String} representation of this {@code GeometricalObject}
     * by previously defined format: {@code "NAME value1 value2 ..."}.
     * 
     * @return {@code String} representation of this {@code GeometricalObject}
     *         with its name and parameter values
     */
    public abstract String asText();

    @Override
    public abstract void paintComponent(Graphics g);

}
