package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class JImageDisplay extends JComponent {
    public BufferedImage image;

    public JImageDisplay(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        Dimension imageDimension = new Dimension(width, height);
        super.setPreferredSize(imageDimension);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, image.getWidth(), image.getHeight(), null);
    }

    public void clearImage () {
        int[] blankArr = new int[getWidth() * getHeight()];
        image.setRGB(0, 0, getWidth(), getHeight(), blankArr, 0, 1);
    }

    public void drawPixel(int x, int y, int rgbColor)
    {
        image.setRGB(x, y, rgbColor);
    }
}
