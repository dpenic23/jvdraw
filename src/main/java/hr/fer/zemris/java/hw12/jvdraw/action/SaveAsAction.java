package hr.fer.zemris.java.hw12.jvdraw.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.nio.file.Path;

import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModel;

import javax.swing.Action;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

/**
 * {@code SaveAsAction} defines an action for {@code JVDraw} frame which is used
 * to save current document opened in {@code JVDraw} application. When this
 * action is requested and if current file is not saved, user will be asked by
 * appropriate dialog if he wants to save it, otherwise this method does
 * nothing.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class SaveAsAction extends JVDrawAction {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new {@code SaveAsAction} with specified {@code JVDraw} frame and
     * its {@code DrawingModel}. By this call, actions name, description and
     * mnemonics are being set.
     * 
     * @param frame
     *            {@code JVDraw} frame to be specified
     * @param drawingModel
     *            {@code DrawingModel} of specified frame
     */
    public SaveAsAction(JVDraw frame, DrawingModel drawingModel) {
        super(frame, drawingModel);

        putValue(Action.NAME, "Save As...");
        putValue(Action.SHORT_DESCRIPTION, "Save current document as...");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control A"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_A);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Save As document");

        if (fc.showSaveDialog(frame) != JFileChooser.APPROVE_OPTION) {
            return;
        }

        Path path = fc.getSelectedFile().toPath();
        frame.setCurrentPath(path);
        frame.writeCurrentFile();

    }

}
