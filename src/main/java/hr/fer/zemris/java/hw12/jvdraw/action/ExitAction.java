package hr.fer.zemris.java.hw12.jvdraw.action;

import java.awt.event.ActionEvent;

import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModel;

import javax.swing.Action;

/**
 * {@code ExitAction} defines an action for {@code JVDraw} frame which is used
 * to exit application. When this action is requested and if current file is not
 * saved, user is being asked by new dialog if he wants to save it. Otherwise,
 * frame is being disposed.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class ExitAction extends JVDrawAction {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates new {@code ExitAction} with specified {@code JVDraw} frame and
     * its {@code DrawingModel}. By this call, actions name and description are
     * being set.
     * 
     * @param frame
     *            {@code JVDraw} frame to be specified
     * @param drawingModel
     *            {@code DrawingModel} of specified frame
     */
    public ExitAction(JVDraw frame, DrawingModel drawingModel) {
        super(frame, drawingModel);

        putValue(Action.NAME, "Exit");
        putValue(Action.SHORT_DESCRIPTION, "Exit application");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        frame.exitApplication();
    }

}
