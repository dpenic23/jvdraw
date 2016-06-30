package hr.fer.zemris.java.hw12.jvdraw.component;

import java.awt.Color;

/**
 * {@code IColorProvider} interface defines an subject in Observer design
 * pattern, where observers are implementations of {@code ColorChangeListener}
 * interface. Implementation of this interface has {@code Color} property which
 * can be change. Then this happens, subject has to on some way notify all
 * observers that color change has occurred.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public interface IColorProvider {

    /**
     * Returns current {@code Color} which is stored in this
     * {@code IColorProvider}.
     * 
     * @return Current {@code Color} by this {@code IColorProvider}
     */
    Color getCurrentColor();

}
