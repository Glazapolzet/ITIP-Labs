package org.example;

import java.awt.geom.Rectangle2D;

public class Tricorn extends FractalGenerator {
    public static final int MAX_ITERATIONS = 2000;

    @Override
    public void getInitialRange(Rectangle2D.Double range) {
        range.x = -2;
        range.y = -2;
        range.width = 4;
        range.height = 4;
    }

    @Override
    public int numIterations(double x, double y) {
        int iterations = 0;
        double xReal = x;
        double yImaginary = y;
        double xRealNext;
        while (iterations < MAX_ITERATIONS && xReal * xReal + yImaginary * yImaginary < 4)
        {
            xRealNext = xReal * xReal - yImaginary * yImaginary + x;
            yImaginary = -2 * xReal * yImaginary + y;
            xReal = xRealNext;
            iterations += 1;
        }

        if (iterations == MAX_ITERATIONS)
        {
            return -1;
        }

        return iterations;
    }

    @Override
    public String toString() {
        return "Tricorn";
    }
}
