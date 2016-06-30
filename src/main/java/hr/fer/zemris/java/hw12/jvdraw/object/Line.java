package hr.fer.zemris.java.hw12.jvdraw.object;

import java.awt.Color;
import java.awt.Graphics;

/**
 * {@code Line} class extends {@code GeometricalObject} class and represents
 * single two-dimensional line which can be painted and drawn by its
 * {@linkplain #paintComponent(Graphics)} method. {@code Line} is defined by its
 * start point, end point and its {@code Color} which is being used when this
 * {@code JComponent} is being painted.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public class Line extends GeometricalObject {

    /** Default serial version ID. */
    private static final long serialVersionUID = 1L;

    /** Line's start point x-coordinate. */
    private int startX;
    /** Line's start point y-coordinate. */
    private int startY;

    /** Line's end point x-coordinate. */
    private int endX;
    /** Line's end point y-coordinate. */
    private int endY;

    /** Color to be used when this line is being painted. */
    private Color color;

    /**
     * Creates new {@code Line} with specified parameters, its start point, end
     * point and {@code Color} used when this line is being painted. Created
     * {@code Line} represents single line in two-dimensional Cartesian system.
     * 
     * @param startX
     *            Start point x-coordinate
     * @param startY
     *            Start point y-coordinate
     * @param endX
     *            End point x-coordinate
     * @param endY
     *            End point y-coordinate
     * @param color
     *            Color of this line
     */
    public Line(int startX, int startY, int endX, int endY, Color color) {
        this.startX = startX;
        this.startY = startY;

        this.endX = endX;
        this.endY = endY;

        this.color = color;
    }

    /**
     * Returns start point x-coordinate of this {@code Line}.
     * 
     * @return Start point x-coordinate
     */
    public int getStartX() {
        return startX;
    }

    /**
     * Sets new start point x-coordinate for this {@code Line}.
     * 
     * @param startX
     *            Start point x-coordinate to be set
     */
    public void setStartX(int startX) {
        this.startX = startX;
    }

    /**
     * Returns start point y-coordinate of this {@code Line}.
     * 
     * @return Start point y-coordinate
     */
    public int getStartY() {
        return startY;
    }

    /**
     * Sets new start point y-coordinate for this {@code Line}.
     * 
     * @param startY
     *            Start point y-coordinate to be set
     */
    public void setStartY(int startY) {
        this.startY = startY;
    }

    /**
     * Returns end point x-coordinate of this {@code Line}.
     * 
     * @return End point x-coordinate
     */
    public int getEndX() {
        return endX;
    }

    /**
     * Sets new end point x-coordinate for this {@code Line}.
     * 
     * @param endX
     *            End point x-coordinate to be set
     */
    public void setEndX(int endX) {
        this.endX = endX;
    }

    /**
     * Returns end point y-coordinate of this {@code Line}.
     * 
     * @return End point y-coordinate
     */
    public int getEndY() {
        return endY;
    }

    /**
     * Sets new end point y-coordinate for this {@code Line}.
     * 
     * @param endY
     *            End point y-coordinate to be set
     */
    public void setEndY(int endY) {
        this.endY = endY;
    }

    /**
     * Returns {@code Color} of this {@code Line}.
     * 
     * @return {@code Color} of this {@code Line}
     */
    public Color getColor() {
        return color;
    }

    /**
     * Sets specified {@code Color} as current one for this {@code Line}.
     * 
     * @param color
     *            {@code Color} to be set as current
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Parses specified {@code String} and interprets it as {@code Line} with
     * given parameters. Given text has to be in format:
     * {@code sX sY eX eY r g b}, otherwise appropriate exception will be
     * thrown.
     * 
     * @param parameters
     *            Text to be interpreted as {@code Line} parameters
     * @return {@code Line} created based on specified parameters
     * @throws NumberFormatException
     *             If parameters can not be interpreted as {@code Line}
     *             parameters
     */
    public static Line parseLine(String parameters)
            throws NumberFormatException {
        String[] params = parameters.split("\\s+");

        if (params.length != 7) {
            throw new NumberFormatException(
                    "Invalid number of line parameters, expected 7: "
                            + params.length);
        }

        int startX = Integer.parseInt(params[0]);
        int startY = Integer.parseInt(params[1]);
        int endX = Integer.parseInt(params[2]);
        int endY = Integer.parseInt(params[3]);
        int colorGreen = Integer.parseInt(params[4]);
        int colorBlue = Integer.parseInt(params[5]);
        int colorRed = Integer.parseInt(params[6]);

        return new Line(startX, startY, endX, endY, new Color(colorRed,
                colorGreen, colorBlue));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(color);
        g.drawLine(startX, startY, endX, endY);
    }

    @Override
    public String asText() {
        return String.format("LINE %d %d %d %d %d %d %d", startX, startY, endX,
                endY, color.getRed(), color.getGreen(), color.getBlue());
    }

    @Override
    public String toString() {
        return "Line " + index;
    }

}
