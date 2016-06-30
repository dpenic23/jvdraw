package hr.fer.zemris.java.hw12.jvdraw.component;

import java.awt.Color;

/**
 * {@code ColorChangeListener} defines an interface for observers in Observer
 * design pattern where subject is an implementation of {@code IColorProvider}.
 * When some change in this provider occurs,
 * {@linkplain #newColorSelected(IColorProvider, Color, Color)} method is
 * expected to be called with informations about this change.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public interface ColorChangeListener {

    /**
     * Method called by {@code IColorProvider} when some change occurs.
     * Implementations of {@code ColorChangeListener} decide what to do by this
     * information.
     * 
     * @param source
     *            {@code IColorProvider} from which change is occurred
     * @param oldColor
     *            Color before change occurred
     * @param newColor
     *            Color after change occurred
     */
    void newColorSelected(IColorProvider source, Color oldColor, Color newColor);

}
