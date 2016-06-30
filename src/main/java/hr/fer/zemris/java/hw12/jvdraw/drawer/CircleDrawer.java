package hr.fer.zemris.java.hw12.jvdraw.drawer;

import hr.fer.zemris.java.hw12.jvdraw.object.Circle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

/**
 * {@code CircleDrawer} is a concrete implementation of
 * {@code GeometricalObjectDrawer} which draws instances of {@code Circle}
 * {@code GeometricalObject}s on specified {@code Graphics} object. Starting
 * point is defined by first mouse click, and second point defines radius of
 * drawn circle. Outline of drawn {@code Circle} is defined by foreground
 * {@code Color}.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public class CircleDrawer extends GeometricalObjectDrawer {

    @Override
    public void draw(Graphics graphics, Point startPoint, Point endPoint,
            Color foregroundColor, Color backgroundColor) {
        int radius = (int) startPoint.distance(endPoint);
        currentObject = new Circle(startPoint.x, startPoint.y, radius,
                foregroundColor);

        currentObject.paintComponent(graphics);
    }

}
