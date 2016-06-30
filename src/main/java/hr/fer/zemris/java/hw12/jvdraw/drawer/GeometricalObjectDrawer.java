package hr.fer.zemris.java.hw12.jvdraw.drawer;

import hr.fer.zemris.java.hw12.jvdraw.object.GeometricalObject;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * {@code GeometricalObjectDrawer} is an abstract class which implementations
 * has to implement only one method,
 * {@linkplain #draw(Graphics, Point, Point, Color, Color)}, which defines which
 * {@code GeometricalObject} is being drawn on specified {@code Graphics}
 * object. This class providers user with current {@code GeometricalObject}
 * which was last drawn by this object.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public abstract class GeometricalObjectDrawer {

    /**
     * Last {@code GeometricalObject} drawn by this implementation of
     * {@code GeometricalObjectDrawer}.
     */
    protected GeometricalObject currentObject;

    /**
     * Draws concrete {@code GeometricalObject} specified by this
     * {@code GeometricalObjectDrawer} on specified {@code Graphics} object. It
     * draws object based on mouse starting point and mouse current point.
     * Currently drawn object can be reached by {@linkplain #getCurrentObject()}
     * method.
     * 
     * @param graphics
     *            {@code Graphics} object on which {@code GeometricalObject} is
     *            being drawn on
     * @param startPoint
     *            Mouse starting point
     * @param endPoint
     *            Mouse ending point
     * @param foregroundColor
     *            Foreground {@code Color}
     * @param backgroundColor
     *            Background {@code Color}
     */
    public abstract void draw(Graphics graphics, Point startPoint,
            Point endPoint, Color foregroundColor, Color backgroundColor);

    /**
     * Returns current {@code GeometricalObject} drawn by this
     * {@code GeometricalObjectDrawer}. If this call is made before
     * {@linkplain #draw(Graphics, Point, Point, Color, Color)} call, this
     * method will return {@code null}.
     * 
     * @return Currently drawn {@code GeometricalObject} by this
     *         {@code GeometricalObjectDrawer}
     */
    public GeometricalObject getCurrentObject() {
        return currentObject;
    }

}
