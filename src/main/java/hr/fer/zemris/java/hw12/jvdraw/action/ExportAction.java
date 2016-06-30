package hr.fer.zemris.java.hw12.jvdraw.action;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.object.Circle;
import hr.fer.zemris.java.hw12.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.object.Line;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * {@code ExportAction} defines an action for {@code JVDraw} frame which is used
 * to export current document opened in {@code JVDraw} application as image,
 * PNG, JPEG or GIF. When this action is requested user is requested to chose
 * which type of image he wants to specify and after that, current document is
 * being exported as image file with selected extension.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class ExportAction extends JVDrawAction {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new {@code ExportAction} with specified {@code JVDraw} frame and
     * its {@code DrawingModel}. By this call, actions name, description and
     * mnemonics are being set.
     * 
     * @param frame
     *            {@code JVDraw} frame to be specified
     * @param drawingModel
     *            {@code DrawingModel} of specified frame
     */
    public ExportAction(JVDraw frame, DrawingModel drawingModel) {
        super(frame, drawingModel);

        putValue(Action.NAME, "Export");
        putValue(Action.SHORT_DESCRIPTION, "Export as image");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control E"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_E);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();

        Object[] availableExtensions = { "JPG", "PNG", "GIF" };
        String extension = (String) JOptionPane.showInputDialog(frame, null,
                "Select extension", JOptionPane.PLAIN_MESSAGE, null,
                availableExtensions, "JPG");

        fc.setDialogTitle("Export file");
        if (fc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        String file = fc.getSelectedFile().toPath().toString();
        if (!(file.endsWith(".jpg") || file.endsWith(".png") || file
                .endsWith(".gif"))) {
            file += "." + extension.toLowerCase();
        }

        Path path = Paths.get(file);
        if (Files.exists(path)) {
            int rez = JOptionPane.showConfirmDialog(frame, "Selected file ("
                    + path + ") already exists. Replace file?", "Warning",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (rez != JOptionPane.YES_OPTION) {
                return;
            }
        }

        int xmax = 0;
        int xmin = Integer.MAX_VALUE;
        int ymax = 0;
        int ymin = Integer.MAX_VALUE;

        for (int index = 0; index < drawingModel.getSize(); index++) {
            GeometricalObject object = drawingModel.getObject(index);

            Point point1;
            Point point2;

            if (object instanceof Line) {
                Line line = (Line) object;

                point1 = new Point(line.getStartX(), line.getStartY());
                point2 = new Point(line.getEndX(), line.getEndY());
            } else {
                int radius;
                Point center;

                if (object instanceof Circle) {
                    Circle circle = (Circle) object;
                    radius = (int) circle.getRadius();
                    center = new Point(circle.getCenterX(), circle.getCenterY());
                } else {
                    FilledCircle circle = (FilledCircle) object;
                    radius = (int) circle.getRadius();
                    center = new Point(circle.getCenterX(), circle.getCenterY());
                }

                point1 = new Point(center.x - radius, center.y - radius);
                point2 = new Point(center.x + radius, center.y + radius);
            }

            xmax = Math.max(xmax, point1.x);
            xmax = Math.max(xmax, point2.x);
            ymax = Math.max(ymax, point1.y);
            ymax = Math.max(ymax, point2.y);
            xmin = Math.min(xmin, point1.x);
            xmin = Math.min(xmin, point2.x);
            ymin = Math.min(ymin, point1.y);
            ymin = Math.min(ymin, point2.y);
        }

        int boxWidth = xmax - xmin;
        int boxHeight = ymax - ymin;

        BufferedImage image = new BufferedImage(boxWidth, boxHeight,
                BufferedImage.TYPE_3BYTE_BGR);

        Graphics2D g2d = image.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.fillRect(0, 0, boxWidth, boxHeight);

        for (int index = 0; index < drawingModel.getSize(); index++) {
            GeometricalObject object = drawingModel.getObject(index);
            GeometricalObject objectToDraw;

            if (object instanceof Line) {
                Line line = (Line) object;

                int startX = line.getStartX() - xmin;
                int startY = line.getStartY() - ymin;
                int endX = line.getEndX() - xmin;
                int endY = line.getEndY() - ymin;

                objectToDraw = new Line(startX, startY, endX, endY,
                        line.getColor());
            } else if (object instanceof Circle) {
                Circle circle = (Circle) object;

                int centerX = circle.getCenterX() - xmin;
                int centerY = circle.getCenterY() - ymin;

                objectToDraw = new Circle(centerX, centerY, circle.getRadius(),
                        circle.getColor());
            } else {
                FilledCircle filledCircle = (FilledCircle) object;

                int centerX = filledCircle.getCenterX() - xmin;
                int centerY = filledCircle.getCenterY() - ymin;

                objectToDraw = new FilledCircle(centerX, centerY,
                        filledCircle.getRadius(),
                        filledCircle.getOutlineColor(),
                        filledCircle.getAreaColor());
            }

            objectToDraw.paintComponent(g2d);
        }

        try {
            ImageIO.write(image, extension.toLowerCase(), path.toFile());
        } catch (IOException e1) {
            JOptionPane.showMessageDialog(frame, "Error while writing file "
                    + path + ": " + e1.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(frame, "File successfully exported");
    }

}
