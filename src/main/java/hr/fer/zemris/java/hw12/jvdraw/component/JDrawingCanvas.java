package hr.fer.zemris.java.hw12.jvdraw.component;

import hr.fer.zemris.java.hw12.jvdraw.drawer.GeometricalObjectDrawer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

import javax.swing.JComponent;

/**
 * {@code JDrawingCanvas} is a {@code JComponent} on which various types of
 * {@code GeometricalObject}s can be drawn by mouse clicking and dragging. First
 * click defines start point of drawn object and as mouse is moved, temporary
 * object is being showed. After second click, object is being drawn and saved
 * to the {@code DrawingModel}. This class is observer in two Observer design
 * patterns. It is being notified when some {@code Color} change occurs inside
 * {@code JColorArea} and when new {@code GeometricalObject} is being added,
 * removed or changed in {@code DrawingModel}.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class JDrawingCanvas extends JComponent implements DrawingModelListener,
        ColorChangeListener {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@code DrawingModel} which holds {@code GeometricalObject}s which are
     * being painted on this canvas.
     */
    private DrawingModel drawingModel;

    /**
     * Current {@code GeometricalObject} drawer, which is being specified by
     * user.
     */
    private GeometricalObjectDrawer drawer;

    /**
     * Indicates if first mouse click has occurred.
     */
    private boolean mouseClicked;

    /**
     * Start point where mouse click first time occurred.
     */
    private Point startPoint;

    /**
     * End point where second mouse click has occurred.
     */
    private Point endPoint;

    /**
     * Foreground color used for drawing {@code GeometricalObject} outlines.
     */
    private Color foregroundColor;

    /**
     * Background color used for filling {@code GeometricalObject}s such as
     * {@code FilledCircle}.
     */
    private Color backgroundColor;

    /**
     * Creates new {@code JDrawingCanvas} with specified {@code DrawingModel}.
     * {@code GeometricalObject} which are defined by specified model are being
     * painted on this canvas. If some change occurs, this component is being
     * repainted.
     * 
     * @param drawingModel
     *            {@code DrawingModel} which holds {@code GeometricalObject}s to
     *            be painted
     */
    public JDrawingCanvas(DrawingModel drawingModel) {
        this.drawingModel = drawingModel;
        this.drawingModel.addDrawingModelListener(this);

        this.foregroundColor = Color.RED;
        this.backgroundColor = Color.BLUE;

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent e) {
                // Check if object drawer is defined
                if (drawer != null) {
                    if (!mouseClicked) {
                        // First click occurred
                        startPoint = e.getPoint();
                    } else {
                        // Second click occurred, save object
                        drawingModel.add(drawer.getCurrentObject());
                        endPoint = null;
                    }

                    mouseClicked = !mouseClicked;
                }
            }

        });

        addMouseMotionListener(new MouseMotionAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                if (mouseClicked && drawer != null) {
                    // Define new end point
                    endPoint = e.getPoint();
                    repaint();
                }
            }

        });
    }

    /**
     * Sets specified {@code GeometricalObjectDrawer} to be current drawer for
     * this {@code JDrawingCanvas}. It is used for drawing objects when mouse is
     * moving and first mouse click has occurred.
     * 
     * @param drawer
     *            {@code GeometricalObjectDrawer} to be set as current one
     */
    public void setCurrentDrawer(GeometricalObjectDrawer drawer) {
        this.drawer = drawer;
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Insets insets = getInsets();
        Dimension size = getSize();

        // Define rectangle for painting this component
        Rectangle rectangle = new Rectangle(insets.left, insets.top, size.width
                - insets.right - insets.left, size.height - insets.top
                - insets.bottom);

        g2d.setColor(Color.WHITE);
        g2d.fillRect(rectangle.x, rectangle.y, rectangle.width,
                rectangle.height);

        int numberOfObjects = drawingModel.getSize();

        // Paint all geometric objects which are drawn on this canvas
        for (int index = 0; index < numberOfObjects; index++) {
            drawingModel.getObject(index).paintComponent(g2d);
        }

        // Pain temporary geometric object if mouse is moving and first click
        // occurred
        if (drawer != null && endPoint != null) {
            drawer.draw(g2d, startPoint, endPoint, foregroundColor,
                    backgroundColor);
        }
    }

    @Override
    public void newColorSelected(IColorProvider source, Color oldColor,
            Color newColor) {
        JColorArea colorArea = (JColorArea) source;
        int index = colorArea.getIndex();

        if (index == 0) {
            foregroundColor = newColor;
        } else if (index == 1) {
            backgroundColor = newColor;
        }
    }

}
