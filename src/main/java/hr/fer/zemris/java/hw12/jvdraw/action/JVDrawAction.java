package hr.fer.zemris.java.hw12.jvdraw.action;

import hr.fer.zemris.java.hw12.jvdraw.JVDraw;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModel;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

/**
 * {@code JVDraw} action extends {@code AbstractAction} and defines action which
 * can be made over {@code JVDraw} frame and its {@code DrawingModel}. Various
 * actions can be made, such as opening new document, saving it, exiting
 * application and so on. This class provides references for these objects and
 * implementations have to define only one method,
 * {@linkplain #actionPerformed(ActionEvent)}.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public abstract class JVDrawAction extends AbstractAction {

    /**
     * Default serial version ID.
     */
    private static final long serialVersionUID = 1L;

    /**
     * {@code JVDraw} frame which provides user with implementations of this
     * action.
     */
    protected JVDraw frame;

    /**
     * {@code DrawingModel} which provides {@code GeometricalObject}s.
     */
    protected DrawingModel drawingModel;

    /**
     * Creates new {@code JVDrawAction} and initializes values for
     * {@code JVDraw} frame and its {@code DrawingModel}.
     * 
     * @param frame
     *            {@code JVDraw} frame to be specified
     * @param drawingModel
     *            Frames {@code DrawingModel} to be specified
     */
    public JVDrawAction(JVDraw frame, DrawingModel drawingModel) {
        this.frame = frame;
        this.drawingModel = drawingModel;
    }

    @Override
    public abstract void actionPerformed(ActionEvent e);

}
