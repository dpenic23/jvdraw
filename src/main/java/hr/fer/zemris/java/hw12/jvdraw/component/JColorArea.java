package hr.fer.zemris.java.hw12.jvdraw.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JColorChooser;
import javax.swing.JComponent;
import javax.swing.border.BevelBorder;

/**
 * {@code JColorArea} is a {@code JComponent} implementation which shows which
 * {@code Color} is currently selected. This component can be pressed, when new
 * color chooser opens and asks user to define new {@code Color} for this
 * component. This class is also subject in Observer design pattern, where
 * observers are being notified when color change occurs.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class JColorArea extends JComponent implements IColorProvider {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Index used to distinguish various instances of this {@code JColorArea}
     * class.
     */
    private static int colorAreaIndex = 0;

    /**
     * Index for this instance of {@code JColorArea} class. It represents its
     * serial number.
     */
    private int index;

    /**
     * Collection of {@code ColorChangeListener}s, observers which are being
     * notified when color change occurs.
     */
    private List<ColorChangeListener> colorChangeListeners;

    /**
     * Current {@code Color} which is selected by this component. When selected
     * color changes, observers are being notified.
     */
    private Color selectedColor;

    /**
     * Creates new {@code JColorArea} with initial {@code Color} set as
     * specified one.
     * 
     * @param selectedColor
     *            {@code Color} to be set as initial one
     */
    public JColorArea(Color selectedColor) {
        this.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));

        // Initialize properties
        this.index = colorAreaIndex++;
        this.colorChangeListeners = new ArrayList<>();
        this.selectedColor = selectedColor;

        // Attach listener when mouse is clicked
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Color oldColor = JColorArea.this.selectedColor;
                Color newColor = JColorChooser.showDialog(JColorArea.this,
                        "Select a color", oldColor);

                // Check if color is changed
                if (newColor != null && !newColor.equals(oldColor)) {
                    JColorArea.this.selectedColor = newColor;
                    repaint();

                    // Notify observers about change
                    for (ColorChangeListener listener : colorChangeListeners) {
                        listener.newColorSelected(JColorArea.this, oldColor,
                                newColor);
                    }
                }
            }
        });
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(15, 15);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Insets insets = getInsets();
        Dimension size = getSize();

        // Define rectangle allowed for painting
        Rectangle rectangle = new Rectangle(insets.left, insets.top, size.width
                - insets.right - insets.left, size.height - insets.top
                - insets.bottom);

        g.setColor(selectedColor);
        g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    }

    @Override
    public Color getCurrentColor() {
        return selectedColor;
    }

    /**
     * Return index which represents serial number of this implementation of
     * {@code JColorArea}.
     * 
     * @return Index of this instance of {@code JColorArea}
     */
    public int getIndex() {
        return index;
    }

    /**
     * Adds specified {@code ColorChangeListener} as attached on this
     * {@code IColorProvider}. After this call, this listener will be when some
     * change occurs.
     * 
     * @param listener
     *            {@code ColorChangeListener} to be attached on this object
     */
    public void addColorChangeListener(ColorChangeListener listener) {
        if (!colorChangeListeners.contains(listener)) {
            colorChangeListeners.add(listener);
        }
    }

    /**
     * Removes specified {@code ColorChangeListener} and detaches it from this
     * object. After this call, this listener will no longer be notified about
     * further {@code Color} changes.
     * 
     * @param listener
     *            {@code ColorChangeListener} to be detached from this object
     */
    public void removeColorChangeListener(ColorChangeListener listener) {
        colorChangeListeners.remove(listener);
    }

}
