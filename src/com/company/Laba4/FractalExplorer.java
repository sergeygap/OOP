package com.company.Laba4;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Rectangle2D;

public class FractalExplorer{
    private int displaySize;
    private JImageDisplay display;
    private FractalGenerator fractalGenerator;
    private Rectangle2D.Double range;

    public FractalExplorer(int displaySize){
        this.displaySize = displaySize;
        this.range = new Rectangle2D.Double();
        this.fractalGenerator = new Mandelbrot();
        this.display = new JImageDisplay(displaySize, displaySize);
    }

    public void createAndShowGUI(){
        JFrame frame = new JFrame("Fractal Explorer");

        Button buttonReset = new Button("Reset");
        ActionListener actionListener = new buttonResetClick();
        buttonReset.addActionListener(actionListener);
        frame.getContentPane().add(buttonReset, BorderLayout.SOUTH);

        MouseListener mouseListener = new displayMouseClick();
        frame.addMouseListener(mouseListener);

        frame.getContentPane().add(display, BorderLayout.CENTER);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setResizable(false);
    }

    private void drawFractal(){
        double xCoord, yCoord;
        for (int x = 0; x < displaySize; x++){
            for (int y = 0; y < displaySize; y++){
                xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, x);
                yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, y);
                int iterations = fractalGenerator.numIterations(xCoord, yCoord);
                int rgbColor;
                if (iterations == -1){
                    rgbColor = 0;
                }else{
                    float hue = 0.7f + (float) iterations / 200f;
                    rgbColor = Color.HSBtoRGB(hue, 1f, 1f);
                }
                display.drawPixel(x, y, rgbColor);
            }
        }
        display.repaint();
    }
    private class buttonResetClick implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            fractalGenerator.getInitialRange(range);
            drawFractal();
        }
    }

    private class displayMouseClick implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            double xCoord = FractalGenerator.getCoord(range.x, range.x + range.width, displaySize, e.getX());
            double yCoord = FractalGenerator.getCoord(range.y, range.y + range.height, displaySize, e.getY());
            fractalGenerator.recenterAndZoomRange(range, xCoord, yCoord,0.5);
            drawFractal();
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

    }

    public static void main(String[] args) {
        FractalExplorer fractalExplorer = new FractalExplorer(800);
        fractalExplorer.createAndShowGUI();
        fractalExplorer.fractalGenerator.getInitialRange(fractalExplorer.range);
        fractalExplorer.drawFractal();
    }

}
