package hr.fer.zemris.java.hw12.jvdraw.action;

import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.object.Circle;
import hr.fer.zemris.java.hw12.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.object.Line;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**
 * {@code OpenAction} defines an action for {@code JVDraw} frame which is used
 * to open new document and sets it as current one in {@code JVDraw}
 * application. When this action is requested file with selected file path is
 * opened and interpreted as valid line representations of
 * {@code GeometricalObject}s, otherwise appropriate message will be printed.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class OpenAction extends JVDrawAction {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new {@code OpenAction} with specified {@code JVDraw} frame and
     * its {@code DrawingModel}. By this call, actions name, description and
     * mnemonics are being set.
     * 
     * @param frame
     *            {@code JVDraw} frame to be specified
     * @param drawingModel
     *            {@code DrawingModel} of specified frame
     */
    public OpenAction(JVDraw frame, DrawingModel drawingModel) {
        super(frame, drawingModel);

        putValue(Action.NAME, "Open");
        putValue(Action.SHORT_DESCRIPTION, "Open existing document");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control O"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_O);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Open file");
        if (fc.showOpenDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path file = fc.getSelectedFile().toPath();

        if (!Files.isReadable(file)) {
            JOptionPane.showMessageDialog(frame, "Selected file " + file
                    + " is not readable.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            List<String> lines = Files.readAllLines(file);

            for (String line : lines) {
                String name = extractObjectName(line);
                String parameters = extractObjectParameters(line);
                try {
                    GeometricalObject object = createObject(name, parameters);
                    drawingModel.add(object);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frame, "Illegal line input: "
                            + ex.getMessage(), "Warning",
                            JOptionPane.WARNING_MESSAGE);
                    continue;
                }
            }

            frame.setCurrentPath(file);
        } catch (IOException e1) {
            System.out.println("IO error.");
        }
    }

    /**
     * Extracts {@code GeometricalObject} name from specified line and its
     * {@code String} representation is being returned as result.
     * 
     * @param line
     *            Line from which {@code GeometricalObject} name is being
     *            extracted
     * @return {@code GeometricalObject} name
     */
    private String extractObjectName(String line) {
        String[] elements = line.split("\\s+", 2);
        return elements[0].trim();
    }

    /**
     * Extracts {@code GeometricalObject} parameters from specified line and its
     * {@code String} representation is being returned as result. Parameters are
     * interpreted as everything after objects name to the end of current line.
     * 
     * @param line
     *            Line from which {@code GeometricalObject} parameters are being
     *            extracted
     * @return {@code GeometricalObject} name
     */
    private String extractObjectParameters(String line) {
        String[] elements = line.split("\\s+", 2);

        if (elements.length == 1) {
            return "";
        }

        return elements[1].trim();
    }

    /**
     * Creates {@code GeometricalObject} based on its name and specified
     * parameters. Created object is being returned as a result of this call. If
     * some error occurs, if object with specified name does not exits or if
     * parameters are not legal, appropriate exception will be thrown.
     * 
     * @param name
     *            {@code GeometricalObject} with specified name to be created
     * @param parameters
     *            {@code GeometricalObject} parameters to be parsed
     * @return Created {@code GeometricalObject} with specified parameters
     * @throws IllegalArgumentException
     *             If {@code GeometricalObject} with specified name does not
     *             exist
     * @throws NumberFormatException
     *             If parameters can not be interpreted as valid ones
     */
    private GeometricalObject createObject(String name, String parameters)
            throws IllegalArgumentException, NumberFormatException {
        name = name.toLowerCase();

        GeometricalObject object;

        switch (name) {
            case "line":
                object = Line.parseLine(parameters);
                break;
            case "circle":
                object = Circle.parseCircle(parameters);
                break;
            case "fcircle":
                object = FilledCircle.parseFilledCircle(parameters);
                break;
            default:
                throw new IllegalArgumentException("\"" + name
                        + "\" object does not exist");
        }

        return object;
    }

}
