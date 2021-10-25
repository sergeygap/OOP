package com.company.Laba6;

import java.awt.geom.Rectangle2D;

public class BurningShip extends FractalGenerator {

    public static final int MAX_ITERATIONS = 80000;

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }

    @Override
    public int numIterations(double x0, double y0) {
        int i = 0;
        double x = 0, y = 0;
        while (x * x + y * y <= 2 * 2 && i < MAX_ITERATIONS) {
            double temp = x * x - y * y + x0;
            y = 2 * Math.abs(x) * Math.abs(y) + y0;
            x = temp;
            i++;
        }
        return i == MAX_ITERATIONS ? -1 : i;
    }

//    @Override
//    public String toString() {
//        return "Burning Ship";
//    }
}
