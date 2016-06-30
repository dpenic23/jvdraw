package hr.fer.zemris.java.hw12.jvdraw;

import hr.fer.zemris.java.hw12.jvdraw.action.ExitAction;
import hr.fer.zemris.java.hw12.jvdraw.action.ExportAction;
import hr.fer.zemris.java.hw12.jvdraw.action.OpenAction;
import hr.fer.zemris.java.hw12.jvdraw.action.SaveAction;
import hr.fer.zemris.java.hw12.jvdraw.action.SaveAsAction;
import hr.fer.zemris.java.hw12.jvdraw.component.ColorChangeListener;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModel;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModelImpl;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingModelListener;
import hr.fer.zemris.java.hw12.jvdraw.component.DrawingObjectListModel;
import hr.fer.zemris.java.hw12.jvdraw.component.JColorArea;
import hr.fer.zemris.java.hw12.jvdraw.component.JDrawingCanvas;
import hr.fer.zemris.java.hw12.jvdraw.drawer.CircleDrawer;
import hr.fer.zemris.java.hw12.jvdraw.drawer.FilledCircleDrawer;
import hr.fer.zemris.java.hw12.jvdraw.drawer.LineDrawer;
import hr.fer.zemris.java.hw12.jvdraw.object.Circle;
import hr.fer.zemris.java.hw12.jvdraw.object.FilledCircle;
import hr.fer.zemris.java.hw12.jvdraw.object.GeometricalObject;
import hr.fer.zemris.java.hw12.jvdraw.object.Line;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.border.BevelBorder;

/**
 * {@code JVDraw} is a command line application which, when started, opens new
 * {@code JFrame} and provides user with component on which various
 * {@code GeometricalObject}s can be drawn by mouse clicking and dragging. One
 * one side, list of all currently painted objects is showed and tool-bar with
 * various options. Tool-bar provides foreground and background color, such as
 * objects which can be drawn on central canvas. There are circle, filled circle
 * and line. Result of painting can be saved as text document or it can be
 * exported as JPG, GIF or PNG image.
 * 
 * @author Domagoj Penic
 * @version 4.6.2015.
 *
 */
public class JVDraw extends JFrame implements DrawingModelListener {

    /**
     * Default serial version ID.
     * */
    private static final long serialVersionUID = 1L;

    /**
     * {@code DrawingModel} which holds object which are being painted and
     * showed on list.
     */
    private DrawingModelImpl drawingModel;

    /**
     * Main panel of this {@code JFrame} where all components are being stored,
     * such as canvas and list.
     */
    private JPanel mainPanel;

    /**
     * Instance of {@code JDrawingCanvas} on which object are being painted.
     */
    private JDrawingCanvas canvas;

    /**
     * {@code JColorArea} which defines foreground color for canvas.
     */
    private JColorArea foregroundColorArea;

    /**
     * {@code JColorArea} which defines background color for canvas.
     */
    private JColorArea backgroundColorArea;

    /**
     * Text which shows foreground and background color which are currently
     * selected.
     */
    private JLabel colorLabel;

    /**
     * Indicates if current file is saved or changed.
     */
    private boolean fileSaved;

    /**
     * Current {@code Path} of current file which is being painted.
     */
    private Path currentPath;

    /**
     * Creates new {@code JVDraw} frame and initializes all its GUI components
     * and defined actions. It also attaches all necessary listeners defined by
     * Observer design pattern.
     */
    public JVDraw() {
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(800, 550);
        setLocation(50, 50);
        setTitle("JVDraw");

        fileSaved = true;
        currentPath = null;

        initGUI();
    }

    /**
     * Initializes GUI components of this {@code JVDraw} frame. It creates
     * central canvas component, it adds list which shows painted
     * {@code GeometricalObject}s and creates menus and tool-bar.
     */
    private void initGUI() {
        mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        getContentPane().add(mainPanel);

        // When window is closed, check if file is saved
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitApplication();
            }
        });

        // Create new drawing model for this file
        drawingModel = new DrawingModelImpl();
        drawingModel.addDrawingModelListener(this);

        canvas = new JDrawingCanvas(drawingModel);
        canvas.setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY,
                Color.LIGHT_GRAY));

        JList<GeometricalObject> list = new JList<GeometricalObject>(
                new DrawingObjectListModel(drawingModel));
        list.setBackground(new Color(204, 204, 204));

        // When some list component is pressed, open new dialog
        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = list.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        openDialogForObject(index);
                    }
                }
            }
        });

        JScrollPane scrollPaneList = new JScrollPane(list);
        scrollPaneList.setPreferredSize(new Dimension(80, 0));
        scrollPaneList.setBorder(BorderFactory.createEtchedBorder(
                Color.LIGHT_GRAY, Color.LIGHT_GRAY));

        mainPanel.add(canvas, BorderLayout.CENTER);
        mainPanel.add(scrollPaneList, BorderLayout.LINE_END);

        createMenus();
        createToolBar();
        createStatusBar();
    }

    /**
     * Creates menus for this {@code JVDraw} frame and adds it to the main
     * panel. Only file menu is provided which holds several actions, such as
     * opening and saving current document.
     */
    private void createMenus() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        menuBar.add(fileMenu);

        fileMenu.add(new JMenuItem(new OpenAction(this, drawingModel)));
        fileMenu.addSeparator();

        fileMenu.add(new JMenuItem(new SaveAction(this, drawingModel)));
        fileMenu.add(new JMenuItem(new SaveAsAction(this, drawingModel)));
        fileMenu.add(new JMenuItem(new ExportAction(this, drawingModel)));
        fileMenu.addSeparator();

        fileMenu.add(new JMenuItem(new ExitAction(this, drawingModel)));

        setJMenuBar(menuBar);
    }

    /**
     * Creates tool-bar for this {@code JVDraw} frame and adds it to the main
     * panel. This tool-bar provides two {@code JColorArea} which represent
     * foreground and background color, and toggle buttons for every
     * {@code GeometricalObject} which can be painted on central canvas.
     */
    private void createToolBar() {
        JToolBar toolBar = new JToolBar("ToolBar");
        toolBar.setLayout(new FlowLayout(FlowLayout.LEFT));
        toolBar.setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY,
                Color.LIGHT_GRAY));

        foregroundColorArea = new JColorArea(Color.RED);
        backgroundColorArea = new JColorArea(Color.BLUE);

        // Attach canvas as observer on this color areas
        foregroundColorArea.addColorChangeListener(canvas);
        backgroundColorArea.addColorChangeListener(canvas);

        toolBar.add(foregroundColorArea);
        toolBar.add(backgroundColorArea);

        toolBar.addSeparator();

        // Add button for every geometric object which can be painted
        JToggleButton lineButton = createToggleButton("Line", (e) -> {
            canvas.setCurrentDrawer(new LineDrawer());
        });
        JToggleButton circleButton = createToggleButton("Circle", (e) -> {
            canvas.setCurrentDrawer(new CircleDrawer());
        });
        JToggleButton filledCircleButton = createToggleButton("Filled circle",
                (e) -> {
                    canvas.setCurrentDrawer(new FilledCircleDrawer());
                });

        ButtonGroup toggleButtonGroup = new ButtonGroup();
        toggleButtonGroup.add(lineButton);
        toggleButtonGroup.add(circleButton);
        toggleButtonGroup.add(filledCircleButton);

        toolBar.add(lineButton);
        toolBar.add(circleButton);
        toolBar.add(filledCircleButton);

        mainPanel.add(toolBar, BorderLayout.PAGE_START);
    }

    /**
     * Creates new {@code JToggleButton} with specified text and action and
     * returns its instance as a result of this call. For this button previously
     * defined border and font are being set.
     * 
     * @param text
     *            Text to be set as current for this {@code JToggleButton}
     * @param action
     *            {@code ActionListener} to be set for this
     *            {@code JToggleButton}
     * @return Newly created {@code JToggleButton} with specified parameters
     */
    private JToggleButton createToggleButton(String text, ActionListener action) {
        JToggleButton toggleButton = new JToggleButton(text);

        toggleButton.setFocusable(false);
        toggleButton.addActionListener(action);
        toggleButton.setBorder(BorderFactory.createEtchedBorder(
                Color.LIGHT_GRAY, Color.LIGHT_GRAY));
        toggleButton.setFont(new Font(Font.DIALOG, Font.BOLD, 11));

        return toggleButton;
    }

    /**
     * Creates status bar for this {@code JVDraw} frame and adds it to the main
     * panel. On this status bar information about {@code JColorArea}s is being
     * showed, their RGB representations.
     */
    private void createStatusBar() {
        JPanel statusBar = new JPanel(new GridLayout(1, 1));
        statusBar.setBorder(BorderFactory.createEtchedBorder(Color.LIGHT_GRAY,
                Color.LIGHT_GRAY));

        colorLabel = new JLabel();
        colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        setColorLabelValues();

        ColorChangeListener colorChangeListener = (source, oldColor, newColor) -> {
            setColorLabelValues();
        };

        // Attach listener when color change occurs
        foregroundColorArea.addColorChangeListener(colorChangeListener);
        backgroundColorArea.addColorChangeListener(colorChangeListener);

        statusBar.add(colorLabel);
        mainPanel.add(statusBar, BorderLayout.PAGE_END);
    }

    /**
     * Sets color label text based on {@code Color}s specified by foreground and
     * background {@code JColorArea}. Only their RGB components are being
     * showed.
     */
    private void setColorLabelValues() {
        Color f = foregroundColorArea.getCurrentColor();
        Color b = backgroundColorArea.getCurrentColor();

        colorLabel.setText(String.format("Foreground color: (%d, %d, %d), "
                + "background color: (%d, %d, %d).", f.getRed(), f.getGreen(),
                f.getBlue(), b.getRed(), b.getGreen(), b.getBlue()));
    }

    /**
     * This method is called when one of line cells is being mouse clicked. When
     * this happens, this method determines what kind of object is selected and
     * delegates dialog opening to other methods.
     * 
     * @param index
     *            {@code GeometricalObject} index which has been pressed
     */
    private void openDialogForObject(int index) {
        GeometricalObject object = drawingModel.getObject(index);

        try {
            if (object instanceof Line) {
                openLineDialog((Line) object);
            } else if (object instanceof Circle) {
                openCircleDialog((Circle) object);
            } else if (object instanceof FilledCircle) {
                openFilledCircleDialog((FilledCircle) object);
            }

            drawingModel.objectChanged(index);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Illegal input!", "Warning",
                    JOptionPane.WARNING_MESSAGE);
        }

    }

    /**
     * Opens dialog when {@code Line} is selected in list. This method opens new
     * dialog which allows user to define new {@code Line} properties and sets
     * them as current ones for this {@code Line}.
     * 
     * @param line
     *            {@code Line} instance which is being changed
     */
    private void openLineDialog(Line line) {
        JTextArea startXArea = createTextArea(line.getStartX());
        JTextArea startYArea = createTextArea(line.getStartY());
        JTextArea endXArea = createTextArea(line.getEndX());
        JTextArea endYArea = createTextArea(line.getEndY());
        JTextArea colorRedArea = createTextArea(line.getColor().getRed());
        JTextArea colorGreenArea = createTextArea(line.getColor().getGreen());
        JTextArea colorBlueArea = createTextArea(line.getColor().getBlue());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2));
        panel.add(new JLabel("Start-X: "));
        panel.add(startXArea);
        panel.add(new JLabel("Start-Y: "));
        panel.add(startYArea);
        panel.add(new JLabel("End-X: "));
        panel.add(endXArea);
        panel.add(new JLabel("End-Y: "));
        panel.add(endYArea);
        panel.add(new JLabel("Red: "));
        panel.add(colorRedArea);
        panel.add(new JLabel("Green: "));
        panel.add(colorGreenArea);
        panel.add(new JLabel("Blue: "));
        panel.add(colorBlueArea);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Insert values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            line.setStartX(Integer.parseInt(startXArea.getText()));
            line.setStartY(Integer.parseInt(startYArea.getText()));
            line.setEndX(Integer.parseInt(endXArea.getText()));
            line.setEndY(Integer.parseInt(endYArea.getText()));

            int r = Integer.parseInt(colorRedArea.getText());
            int g = Integer.parseInt(colorGreenArea.getText());
            int b = Integer.parseInt(colorBlueArea.getText());

            line.setColor(new Color(r, g, b));
        }
    }

    /**
     * Opens dialog when {@code Circle} is selected in list. This method opens
     * new dialog which allows user to define new {@code Circle} properties and
     * sets them as current ones for this {@code Circle}.
     * 
     * @param circle
     *            {@code Circle} instance which is being changed
     */
    private void openCircleDialog(Circle circle) {
        JTextArea centerXArea = createTextArea(circle.getCenterX());
        JTextArea centerYArea = createTextArea(circle.getCenterY());
        JTextArea radiusArea = createTextArea(circle.getRadius());
        JTextArea colorRedArea = createTextArea(circle.getColor().getRed());
        JTextArea colorGreenArea = createTextArea(circle.getColor().getGreen());
        JTextArea colorBlueArea = createTextArea(circle.getColor().getBlue());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));
        panel.add(new JLabel("Center-X: "));
        panel.add(centerXArea);
        panel.add(new JLabel("Center-Y: "));
        panel.add(centerYArea);
        panel.add(new JLabel("Radius: "));
        panel.add(radiusArea);
        panel.add(new JLabel("Red: "));
        panel.add(colorRedArea);
        panel.add(new JLabel("Green: "));
        panel.add(colorGreenArea);
        panel.add(new JLabel("Blue: "));
        panel.add(colorBlueArea);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Insert values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            circle.setCenterX(Integer.parseInt(centerXArea.getText()));
            circle.setCenterY(Integer.parseInt(centerYArea.getText()));
            circle.setRadius(Integer.parseInt(radiusArea.getText()));

            int r = Integer.parseInt(colorRedArea.getText());
            int g = Integer.parseInt(colorGreenArea.getText());
            int b = Integer.parseInt(colorBlueArea.getText());

            circle.setColor(new Color(r, g, b));
        }
    }

    /**
     * Opens dialog when {@code FilledCircle} is selected in list. This method
     * opens new dialog which allows user to define new {@code FilledCircle}
     * properties and sets them as current ones for this {@code FilledCircle}.
     * 
     * @param filledCircle
     *            {@code FilledCircle} instance which is being changed
     */
    private void openFilledCircleDialog(FilledCircle filledCircle) {
        JTextArea centerXArea = createTextArea(filledCircle.getCenterX());
        JTextArea centerYArea = createTextArea(filledCircle.getCenterY());
        JTextArea radiusArea = createTextArea(filledCircle.getRadius());
        JTextArea outlineColorRedArea = createTextArea(filledCircle
                .getOutlineColor().getRed());
        JTextArea outlineColorGreenArea = createTextArea(filledCircle
                .getOutlineColor().getGreen());
        JTextArea outlineColorBlueArea = createTextArea(filledCircle
                .getOutlineColor().getBlue());
        JTextArea areaColorRedArea = createTextArea(filledCircle.getAreaColor()
                .getRed());
        JTextArea areaColorGreenArea = createTextArea(filledCircle
                .getAreaColor().getGreen());
        JTextArea areaColorBlueArea = createTextArea(filledCircle
                .getAreaColor().getBlue());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 2));
        panel.add(new JLabel("Center-X: "));
        panel.add(centerXArea);
        panel.add(new JLabel("Center-Y: "));
        panel.add(centerYArea);
        panel.add(new JLabel("Radius: "));
        panel.add(radiusArea);
        panel.add(new JLabel("Outline-Red: "));
        panel.add(outlineColorRedArea);
        panel.add(new JLabel("Outline-Green: "));
        panel.add(outlineColorGreenArea);
        panel.add(new JLabel("Outline-Blue: "));
        panel.add(outlineColorBlueArea);
        panel.add(new JLabel("Area-Red: "));
        panel.add(areaColorRedArea);
        panel.add(new JLabel("Area-Green: "));
        panel.add(areaColorGreenArea);
        panel.add(new JLabel("Area-Blue: "));
        panel.add(areaColorBlueArea);

        int result = JOptionPane.showConfirmDialog(this, panel,
                "Insert values", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            filledCircle.setCenterX(Integer.parseInt(centerXArea.getText()));
            filledCircle.setCenterY(Integer.parseInt(centerYArea.getText()));
            filledCircle.setRadius(Integer.parseInt(radiusArea.getText()));

            int outR = Integer.parseInt(outlineColorRedArea.getText());
            int outG = Integer.parseInt(outlineColorGreenArea.getText());
            int outB = Integer.parseInt(outlineColorBlueArea.getText());

            filledCircle.setOutlineColor(new Color(outR, outG, outB));

            int areaR = Integer.parseInt(areaColorRedArea.getText());
            int areaG = Integer.parseInt(areaColorGreenArea.getText());
            int areaB = Integer.parseInt(areaColorBlueArea.getText());

            filledCircle.setAreaColor(new Color(areaR, areaG, areaB));
        }
    }

    /**
     * Creates new {@code JTextArea} with specified initial {@code Integer}
     * value to be set. Created instance is returned as a result of this call.
     * 
     * @param initialValue
     *            Initial value to be set as text
     * @return New instance of {@code JTextArea} with specified initial value
     */
    private JTextArea createTextArea(int initialValue) {
        JTextArea textArea = new JTextArea(String.valueOf(initialValue));
        textArea.setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        return textArea;
    }

    /**
     * Sets specified path to be current one for this object which is being
     * painted.
     * 
     * @param path
     *            {@code Path} to be set as current one
     */
    public void setCurrentPath(Path path) {
        currentPath = path;
    }

    /**
     * Sets file saved flag for this file.
     * 
     * @param saved
     *            File saved flag to be set
     */
    public void setFileSaved(boolean saved) {
        fileSaved = saved;
    }

    /**
     * Writes file specified by current path and writes its content. If path is
     * not specified before this call is made, user is requested to chose new
     * path.
     */
    public void writeCurrentFile() {
        if (currentPath == null) {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Save As document");

            if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                return;
            }

            currentPath = fc.getSelectedFile().toPath();
        }

        try {
            int numberOfObjects = drawingModel.getSize();
            StringBuilder sb = new StringBuilder();

            for (int index = 0; index < numberOfObjects; index++) {
                GeometricalObject object = drawingModel.getObject(index);
                String objectAsText = String.format("%s%n", object.asText());
                sb.append(objectAsText);
            }

            Files.write(currentPath,
                    sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error while writing file "
                    + currentPath + ": " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }

        fileSaved = true;
    }

    /**
     * If file is not saved, user is requested to save current file. After file
     * is saved or saving is neglected, this application is being closed.
     */
    public void exitApplication() {
        if (!fileSaved) {
            int rez = JOptionPane.showConfirmDialog(JVDraw.this,
                    "File is not saved. Do you want to save it?", "Warning",
                    JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

            if (rez == JOptionPane.YES_OPTION) {
                if (currentPath == null) {
                    JFileChooser fc = new JFileChooser();
                    fc.setDialogTitle("Save As document");

                    if (fc.showSaveDialog(JVDraw.this) != JFileChooser.APPROVE_OPTION) {
                        dispose();
                        System.exit(-1);
                    }

                    currentPath = fc.getSelectedFile().toPath();
                }

                writeCurrentFile();
            }
        }

        dispose();
    }

    @Override
    public void objectsAdded(DrawingModel source, int index0, int index1) {
        fileSaved = false;
    }

    @Override
    public void objectsRemoved(DrawingModel source, int index0, int index1) {
        fileSaved = false;
    }

    @Override
    public void objectsChanged(DrawingModel source, int index0, int index1) {
        fileSaved = false;
    }

    /**
     * Method called once program is run. Arguments are described below.
     * 
     * @param args
     *            Command line arguments
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager
                        .getSystemLookAndFeelClassName());
            } catch (Exception ignorable) {
            }

            JFrame frame = new JVDraw();
            frame.setVisible(true);
        });
    }

}
