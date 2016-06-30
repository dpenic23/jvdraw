package hr.fer.zemris.java.hw12.jvdraw.action;

import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModel;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.KeyStroke;

/**
 * {@code SaveAction} defines an action for {@code JVDraw} frame which is used
 * to save current document opened in {@code JVDraw} application. When this
 * action is requested and if current file is not saved, it will be saved with
 * no further communication with user, otherwise, this action does nothing.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class SaveAction extends JVDrawAction {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new {@code SaveAction} with specified {@code JVDraw} frame and
     * its {@code DrawingModel}. By this call, actions name, description and
     * mnemonics are being set.
     * 
     * @param frame
     *            {@code JVDraw} frame to be specified
     * @param drawingModel
     *            {@code DrawingModel} of specified frame
     */
    public SaveAction(JVDraw frame, DrawingModel drawingModel) {
        super(frame, drawingModel);

        putValue(Action.NAME, "Save");
        putValue(Action.SHORT_DESCRIPTION, "Save current document");
        putValue(Action.ACCELERATOR_KEY, KeyStroke.getKeyStroke("control S"));
        putValue(Action.MNEMONIC_KEY, KeyEvent.VK_S);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.writeCurrentFile();
    }

}
