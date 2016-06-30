package hr.fer.zemris.java.hw12.jvdraw.drawer;

import hr.fer.zemris.java.hw12.jvdraw.object.FilledCircle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * {@code FilledCircleDrawer} is a concrete implementation of
 * {@code GeometricalObjectDrawer} which draws instances of {@code FilledCircle}
 * {@code GeometricalObject}s on specified {@code Graphics} object. Starting
 * point is defined by first mouse click, and second point defines radius of
 * drawn {@code FilledCircle}. Its area is painted by specified background
 * {@code Color} and outline by specified foreground {@code Color}.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public class FilledCircleDrawer extends GeometricalObjectDrawer {

    @Override
    public void draw(Graphics graphics, Point startPoint, Point endPoint,
            Color foregroundColor, Color backgroundColor) {
        int radius = (int) startPoint.distance(endPoint);
        currentObject = new FilledCircle(startPoint.x, startPoint.y, radius,
                foregroundColor, backgroundColor);

        currentObject.paintComponent(graphics);
    }

}
