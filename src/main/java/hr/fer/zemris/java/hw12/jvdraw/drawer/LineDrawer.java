package hr.fer.zemris.java.hw12.jvdraw.drawer;

import hr.fer.zemris.java.hw12.jvdraw.object.Line;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * {@code LineDrawer} is a concrete implementation of
 * {@code GeometricalObjectDrawer} which draws instances of {@code Line}
 * {@code GeometricalObject}s on specified {@code Graphics} object. Starting
 * point is defined by first mouse click, and second point defines {@code Line}
 * ending point. Outline of drawn {@code Line} is defined by foreground
 * {@code Color}.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public class LineDrawer extends GeometricalObjectDrawer {

    @Override
    public void draw(Graphics graphics, Point startPoint, Point endPoint,
            Color foregroundColor, Color backgroundColor) {
        currentObject = new Line(startPoint.x, startPoint.y, endPoint.x,
                endPoint.y, foregroundColor);
        currentObject.paintComponent(graphics);
    }

}
