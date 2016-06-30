package hr.fer.zemris.java.hw12.jvdraw.object;

import java.awt.Color;
import java.awt.Graphics;

/**
 * {@code FilledCircle} class extends {@code GeometricalObject} class and
 * represents single two-dimensional filled circle which can be painted and
 * drawn by its {@linkplain #paintComponent(Graphics)} method.
 * {@code FilledCircle} is defined by its center point, radius, outline
 * {@code Color} and its area {@code Color} which are being used when this
 * {@code JComponent} is being painted.
 * 
 * @author Domagoj Penic
 * @version 3.6.2015.
 *
 */
public class FilledCircle extends GeometricalObject {

    /** Default serial version ID. */
    private static final long serialVersionUID = 1L;

    /** Center point x-coordinate. */
    private int centerX;
    /** Center point y-coordinate. */
    private int centerY;
    /** Circle radius. */
    private int radius;

    /** Outline color for painting circle border. */
    private Color outlineColor;
    /** Outline color for filling circle area. */
    private Color areaColor;

    /**
     * Creates new {@code FilledCircle} with specified parameters, its center
     * point, radius and colors, outline and area color used when this component
     * is being painted.
     * 
     * @param centerX
     *            Center point x-coordinate
     * @param centerY
     *            Center point y-coordinate
     * @param radius
     *            Circle radius
     * @param outlineColor
     *            Outline color for painting border
     * @param areaColor
     *            Area color for filling area
     */
    public FilledCircle(int centerX, int centerY, int radius,
            Color outlineColor, Color areaColor) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;

        this.outlineColor = outlineColor;
        this.areaColor = areaColor;
    }

    /**
     * Returns center point x-coordinate of this {@code FilledCircle}.
     * 
     * @return Center point x-coordinate
     */
    public int getCenterX() {
        return centerX;
    }

    /**
     * Returns center point y-coordinate of this {@code FilledCircle}.
     * 
     * @return Center point y-coordinate
     */
    public int getCenterY() {
        return centerY;
    }

    /**
     * Returns radius of this {@code FilledCircle}.
     * 
     * @return This {@code FilledCircle} radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * Returns outline {@code FilledColor} of this {@code FilledCircle}.
     * 
     * @return This {@code FilledCircle} outline {@code FilledColor}
     */
    public Color getOutlineColor() {
        return outlineColor;
    }

    /**
     * Returns area {@code FilledColor} of this {@code FilledCircle}.
     * 
     * @return This {@code FilledCircle} area {@code FilledColor}
     */
    public Color getAreaColor() {
        return areaColor;
    }

    /**
     * Sets center point x-coordinate on specified value for this
     * {@code FilledCircle} .
     * 
     * @param centerX
     *            Center point x-coordinate to be set
     */
    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    /**
     * Sets center point y-coordinate on specified value for this
     * {@code FilledCircle} .
     * 
     * @param centerY
     *            Center point y-coordinate to be set
     */
    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    /**
     * Sets radius for this {@code FilledCircle}.
     * 
     * @param radius
     *            Radius of this {@code FilledCircle} to be set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }

    /**
     * Sets specified outline {@code Color} for this {@code FilledCircle}.
     * 
     * @param outlineColor
     *            Outline {@code Color} to be set
     */
    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    /**
     * Sets specified area {@code Color} for this {@code FilledCircle}.
     * 
     * @param areaColor
     *            Area {@code Color} to be set
     */
    public void setAreaColor(Color areaColor) {
        this.areaColor = areaColor;
    }

    /**
     * Parses specified {@code String} and interprets it as {@code FilledCircle}
     * with given parameters. Given text has to be in format:
     * {@code cX cY radius oR oG oB aR aG aB}, otherwise appropriate exception
     * will be thrown.
     * 
     * @param parameters
     *            Text to be interpreted as {@code FilledCircle} parameters
     * @return {@code FilledCircle} created based on specified parameters
     * @throws NumberFormatException
     *             If parameters can not be interpreted as {@code FilledCircle}
     *             parameters
     */
    public static FilledCircle parseFilledCircle(String parameters)
            throws NumberFormatException {
        String[] params = parameters.split("\\s+");

        if (params.length != 9) {
            throw new NumberFormatException(
                    "Invalid number of arguments, expected 9: " + params.length);
        }

        int centerX = Integer.parseInt(params[0]);
        int centerY = Integer.parseInt(params[1]);
        int radius = Integer.parseInt(params[2]);
        int outlineRed = Integer.parseInt(params[3]);
        int outlineGreen = Integer.parseInt(params[4]);
        int outlineBlue = Integer.parseInt(params[5]);
        int areaRed = Integer.parseInt(params[6]);
        int areaGreen = Integer.parseInt(params[7]);
        int areaBlue = Integer.parseInt(params[8]);

        return new FilledCircle(centerX, centerY, radius, new Color(outlineRed,
                outlineGreen, outlineBlue), new Color(areaRed, areaGreen,
                areaBlue));
    }

    @Override
    public void paintComponent(Graphics g) {
        g.setColor(areaColor);
        g.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

        g.setColor(outlineColor);
        g.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }

    @Override
    public String asText() {
        return String.format("FCIRCLE %d %d %d %d %d %d %d %d %d", centerX,
                centerY, radius, outlineColor.getRed(),
                outlineColor.getGreen(), outlineColor.getBlue(),
                areaColor.getRed(), areaColor.getGreen(), areaColor.getBlue());
    }

    @Override
    public String toString() {
        return "Circle " + index;
    }

}
