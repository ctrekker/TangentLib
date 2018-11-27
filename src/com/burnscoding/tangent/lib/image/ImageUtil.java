package com.burnscoding.tangent.lib.image;

import java.awt.*;
import java.awt.image.BufferedImage;

public class ImageUtil {
    public static BufferedImage takeScreenshot() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        return takeScreenshot(0, 0, d.width, d.height);
    }
    public static BufferedImage takeScreenshot(int width, int height) {
        return takeScreenshot(0, 0, width, height);
    }
    public static BufferedImage takeScreenshot(int x, int y, int width, int height) {
        try {
            return new Robot().createScreenCapture(new Rectangle(x, y, width, height));
        } catch(AWTException e) {
            return null;
        }
    }

    private static BufferedImage defaultImage = null;
    public static BufferedImage getDefaultImage() {
        if(defaultImage == null) {
            int w = 640;
            int h = 480;

            defaultImage = new BufferedImage(w, h, BufferedImage.TYPE_BYTE_INDEXED);
            Graphics2D g2 = defaultImage.createGraphics();

            g2.setPaint(Color.BLACK);
            g2.fillRect(0, 0, w, h);

            g2.setPaint(Color.RED);
            g2.setStroke(new BasicStroke(30));
            g2.drawArc(w/2-h/3/2, h/2-h/3/2, h/3, h/3, 0, 360);
            g2.drawLine(0, 0, w, h);
            g2.drawLine(0, h, w, 0);
        }
        return defaultImage;
    }
}
