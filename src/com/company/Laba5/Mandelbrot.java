package com.company.Laba5;

import java.awt.geom.Rectangle2D;

public class Mandelbrot extends FractalGenerator {

    public static final int MAX_ITERATIONS = 2000;

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -1.5;
        range.width = 3;
        range.height = 3;
    }

    @Override
    public int numIterations(double x0, double y0) {
        int i = 0;          //z^2 = (x + y(i))^2 = x^2 + 2xyi - y^2
        double x = 0, y = 0;
        while (x * x + y * y <= 2 * 2 && i < MAX_ITERATIONS) {
            double temp = x * x - y * y + x0;
            y = 2 * x * y + y0;
            x = temp;
            i++;
        }
        return i == MAX_ITERATIONS ? -1 : i;
    }

//    @Override
//    public String toString() {
//        return "Mandelbrot";
//    }
}
