package org.example;

import java.awt.*;
import javax.imageio.*;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.geom.Rectangle2D;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractal;
    private Rectangle2D.Double range;

    public JComboBox<Object> fractalList;
    public JButton resetButton;
    public JButton saveButton;

    public int rowsRemaining;

    public FractalExplorer(int size) {
        displaySize = size;
        fractal = new Mandelbrot();
        range = new Rectangle2D.Double();
        fractal.getInitialRange(range);
        display = new JImageDisplay(displaySize, displaySize);
        fractalList = new JComboBox<>();
    }

    public void createAndShowGUI()
    {
        display.setLayout(new BorderLayout());
        JFrame frame = new JFrame("Fractal Explorer");
        frame.add(display, BorderLayout.CENTER);

        MouseHandler click = new MouseHandler();
        display.addMouseListener(click);

        JLabel label = new JLabel("Fractal:");

        fractalList.addItem(new Mandelbrot());
        fractalList.addItem(new Tricorn());
        fractalList.addItem(new BurningShip());

        ButtonHandler pickFractal = new ButtonHandler();
        fractalList.addActionListener(pickFractal);

        JPanel ComboBoxPanel = new JPanel();
        ComboBoxPanel.add(label);
        ComboBoxPanel.add(fractalList);

        frame.add(ComboBoxPanel, BorderLayout.NORTH);

        resetButton = new JButton("Reset");
        ButtonHandler resetHandler = new ButtonHandler();
        resetButton.addActionListener(resetHandler);

        saveButton = new JButton("Save Image");
        ButtonHandler saveHandler = new ButtonHandler();
        saveButton.addActionListener(saveHandler);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(saveButton);
        buttonsPanel.add(resetButton);

        saveButton.setActionCommand("save");
        resetButton.setActionCommand("reset");

        frame.add(buttonsPanel, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void enableUI(boolean val) {
        resetButton.setEnabled(val);
        saveButton.setEnabled(val);
        fractalList.setEnabled(val);
    }

    private void drawFractal() {
        enableUI(false);
        rowsRemaining = displaySize;
        for (int y = 0; y < displaySize; y++) {
            FractalWorker fractalWorker = new FractalWorker(y);
            fractalWorker.execute();
        }
    }

    private class FractalWorker extends SwingWorker<Object, Object> {
        private int y;
        private int[] strokeComputedRGBs;

        public FractalWorker(int yCoord) {
            y = yCoord;
        }

        @Override
        public Object doInBackground() {
            strokeComputedRGBs = new int[displaySize];
            for (int x=0; x<displaySize; x++){
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);

                int numIters = fractal.numIterations(xCoord, yCoord);

                if (numIters == -1){
                    strokeComputedRGBs[x] = 0;
                }
                else {
                    float hue = 0.7f + (float) numIters / 200f;
                    int rgbColor = Color.HSBtoRGB(hue, 1f, 1f);

                    strokeComputedRGBs[x] = rgbColor;
                }
            }
            return null;
        }

        @Override
        public void done() {
            for (int x=0; x<strokeComputedRGBs.length; x++) {
                display.drawPixel(x, y, strokeComputedRGBs[x]);
            }
            display.repaint(0, 0, y, displaySize, 1);
            rowsRemaining--;
            if (rowsRemaining == 0) {
                enableUI(true);
            }
        }
    }

    private class ButtonHandler implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            String command = e.getActionCommand();

            if (command.equals("reset")) {
                fractal.getInitialRange(range);
                drawFractal();
            } else if (command.equals("save")) {
                JFileChooser fileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                fileChooser.setFileFilter(filter);
                fileChooser.setAcceptAllFileFilterUsed(false);
                int SaveDialogStatus = fileChooser.showSaveDialog(display);
                if(SaveDialogStatus == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        ImageIO.write(display.image, "png", selectedFile);
                    } catch (IOException ex) {
                        JOptionPane optionPane = new JOptionPane();
                        JOptionPane.showMessageDialog(
                                display,
                                optionPane.getMessage(),
                                "Cannot Save Image",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                }
            } else {
                fractal = (FractalGenerator) fractalList.getSelectedItem();
                range = new Rectangle2D.Double(0,0,0,0);
                fractal.getInitialRange(range);
                drawFractal();
            }
        }

    }

    private class MouseHandler extends MouseAdapter
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (rowsRemaining == 0) {
                int x = e.getX();
                double xCoord = FractalGenerator.getCoord(range.x,
                        range.x + range.width, displaySize, x);

                int y = e.getY();
                double yCoord = FractalGenerator.getCoord(range.y,
                        range.y + range.height, displaySize, y);

                fractal.recenterAndZoomRange(range, xCoord, yCoord, 0.5);

                drawFractal();
            }
        }
    }

    public static void main(String[] args)
    {
        FractalExplorer displayExplorer = new FractalExplorer(800);
        displayExplorer.createAndShowGUI();
        displayExplorer.drawFractal();
    }
}

