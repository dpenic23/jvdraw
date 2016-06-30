package hr.fer.zemris.java.hw12.jvdraw.object;

import java.awt.Color;
import java.awt.Graphics;

/**
 * {@code Circle} class extends {@code GeometricalObject} class and represents
 * single two-dimensional circle which can be painted and drawn by its
 * {@linkplain #paintComponent(Graphics)} method. {@code Circle} is defined by
 * its center point, radius and {@code Color} which is being used when this
 * {@code JComponent} is being painted.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public class Circle extends GeometricalObject {

    /** Default serial version ID. */
    private static final long serialVersionUID = 1L;

    /** Center point x-coordinate. */
    private int centerX;
    /** Center point y-coordinate. */
    private int centerY;
    /** Circle radius. */
    private int radius;

    /** Color used for painting this circle. */
    private Color color;

    /**
     * Creates new {@code Circle} with specified parameters, its center point,
     * radius and colors used when this component is being painted.
     * 
     * @param centerX
     *            Center point x-coordinate
     * @param centerY
     *            Center point y-coordinate
     * @param radius
     *            Circle radius
     * @param color
     *            Color for painting this circle
     */
    public Circle(int centerX, int centerY, int radius, Color color) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;

        this.color = color;
    }

    /**
     * Returns center point x-coordinate of this {@code Circle}.
     * 
     * @return Center point x-coordinate
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * Returns center point y-coordinate of this {@code Circle}.
     * 
     * @return Center point y-coordinate
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * Returns radius of this {@code Circle}.
     * 
     * @return This {@code Circle} radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Returns currently selected {@code Color} for this {@code Circle}.
     * 
     * @return {@code Color} of this {@code Circle}
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets center point x-coordinate on specified value for this {@code Circle}
     * .
     * 
     * 
     * @param centerX
     *            Center point x-coordinate to be set
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * Sets center point y-coordinate on specified value for this {@code Circle}
     * .
     * 
     * 
     * @param centerY
     *            Center point y-coordinate to be set
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    /**
     * Sets radius for this {@code Circle}.
     * 
     * @param radius
     *            Radius of this {@code Circle} to be set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Sets current {@code Color} for this {@code Circle}.
     * 
     * @param color
     *            {@code Color} of this {@code Circle} to be set
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Parses specified {@code String} and interprets it as {@code Circle} with
     * given parameters. Given text has to be in format:
     * {@code cX cY radius r g b}, otherwise appropriate exception will be
     * thrown.
     * 
     * @param parameters
     *            Text to be interpreted as {@code Circle} parameters
     * @return {@code Circle} created based on specified parameters
     * @throws NumberFormatException
     *             If parameters can not be interpreted as {@code Circle}
     *             parameters
     */
    public static Circle parseCircle(String parameters)
            throws NumberFormatException {
        String[] params = parameters.split("\\s+");

        if (params.length != 6) {
            throw new NumberFormatException(
                    "Invalid number of parameters, expected 6: "
                            + params.length);
        }

        int centerX = Integer.parseInt(params[0]);
        int centerY = Integer.parseInt(params[1]);
        int radius = Integer.parseInt(params[2]);
        int colorRed = Integer.parseInt(params[3]);
        int colorGreen = Integer.parseInt(params[4]);
        int colorBlue = Integer.parseInt(params[5]);

        return new Circle(centerX, centerY, radius, new Color(colorRed,
                colorGreen, colorBlue));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }

    @Override
    public String asText() {
        return String.format("CIRCLE %d %d %d %d %d %d", centerX, centerY,
                radius, color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public String toString() {
        return "Circle " + index;
    }

}
