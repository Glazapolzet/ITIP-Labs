package org.example;

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
    public int numIterations(double x, double y) {
        int iterations = 0;
        double xReal = x;
        double yImaginary = y;

        while (iterations < MAX_ITERATIONS && xReal * xReal + yImaginary * yImaginary < 4)
        {
            double xRealNext = xReal * xReal - yImaginary * yImaginary + x;
            double yImaginaryNext = 2 * xReal * yImaginary + y;
            xReal = xRealNext;
            yImaginary = yImaginaryNext;
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
        return "Mandelbrot";
    }
}
