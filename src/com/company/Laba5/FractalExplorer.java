package com.company.Laba5;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import javax.imageio.ImageIO;

public class FractalExplorer {
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int displaySize) {
        this.displaySize = displaySize;
        this.range = new Rectangle2D.Double();
        this.fractalGenerator = new Mandelbrot();
        this.display = new JImageDisplay(displaySize, displaySize);
    }

    public void createAndShowGUI() {
        JFrame frame = new JFrame("Fractal Explorer");

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Fractal: ");
        JComboBox comboBox = new JComboBox();
        comboBox.addItem("Mandelbrot");
        comboBox.addItem("Tricorn");
        comboBox.addItem("Burning Ship");
        panel.add(label);
        panel.add(comboBox);
        comboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectItem = (String) comboBox.getSelectedItem();
                switch (selectItem) {
//                    case "Mandelbrot" -> fractalGenerator = new Mandelbrot();  is duplicate of default branch
                    case "Tricorn" -> fractalGenerator = new Tricorn();
                    case "Burning Ship" -> fractalGenerator = new BurningShip();
                    default -> fractalGenerator = new Mandelbrot();
                }
                fractalGenerator.getInitialRange(range);
                drawFractal();

            }
        });
        frame.getContentPane().add(panel, BorderLayout.NORTH);


        Button buttonReset = new Button("Reset");
        buttonReset.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fractalGenerator.getInitialRange(range);
                drawFractal();
            }
        });
        Button buttonSave = new Button("Save");
        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                FileFilter filter = new FileNameExtensionFilter("PNG Images", "png");
                jFileChooser.setFileFilter(filter);
                jFileChooser.setAcceptAllFileFilterUsed(false);
                if (jFileChooser.showSaveDialog(display) == JFileChooser.APPROVE_OPTION){
                    File file = jFileChooser.getSelectedFile();
                    try {
                        ImageIO.write(display.image, "png", file);
                    } catch (IOException exception) {
                        JOptionPane.showMessageDialog(display, exception.getMessage(),
                                "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    } catch (NullPointerException exception) {
                        JOptionPane.showMessageDialog(display, "Save error",
                                "Cannot Save Image", JOptionPane.ERROR_MESSAGE);
                    }

                }
            }
        });
        JPanel btnContainer = new JPanel();
        btnContainer.add(buttonReset);
        btnContainer.add(buttonSave);
        frame.getContentPane().add(btnContainer, BorderLayout.SOUTH);

        frame.addMouseListener(new MouseListener() {

            /**
             * Invoked when the mouse button has been clicked (pressed
             * and released) on a component.
             *
             * @param e the event to be processed
             */
            public void mouseClicked(MouseEvent e) {
                double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
                double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
                fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord, 0.5);
                drawFractal();
//            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
//            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
//            System.out.println(Double.toString(xCoord) + " x " + Double.toString(yCoord));
            }

            /**
             * Invoked when a mouse button has been pressed on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mousePressed(MouseEvent e) {

            }

            /**
             * Invoked when a mouse button has been released on a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseReleased(MouseEvent e) {

            }

            /**
             * Invoked when the mouse enters a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseEntered(MouseEvent e) {

            }

            /**
             * Invoked when the mouse exits a component.
             *
             * @param e the event to be processed
             */
            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        frame.getContentPane().add(display, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal() {
        double xCoord, yCoord;
        for (int x = 0; x < displaySize; x++) {
            for (int y = 0; y < displaySize; y++) {
                xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                int iterations = fractalGenerator.numIterations(xCoord, yCoord);
                int rgbColor;
                if (iterations == -1) {
                    rgbColor = 0;
                } else {
                    float hue = 0.7f + (float) iterations / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                display.drawPixel(x, y, rgbColor);
            }
        }
        display.repaint();
    }


    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.fractalGenerator.getInitialRange(fractalExplorer.range);
        fractalExplorer.drawFractal();
    }

}
