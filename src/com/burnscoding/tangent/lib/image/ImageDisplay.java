package com.burnscoding.tangent.lib.image;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.util.Timer;
import java.util.TimerTask;

public class ImageDisplay extends JFrame {
    private int width = 640;
    private int height = 480;
    public ImageCanvas canvas;
    public ImageDisplay() {
        super("Image display");
        setSize(width, height);
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                width = e.getComponent().getWidth();
                height = e.getComponent().getHeight();
                setSize(width, height);
            }
        });
        canvas = new ImageCanvas();
        add(canvas);
        setVisible(true);
    }
    public class ImageCanvas extends Component {
        private BufferedImage img = null;
        public ImageCanvas() {
            new Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    repaint();
                }
            }, 0, (int)(1000.0/60.0));
        }
        @Override
        public void paint(Graphics g) {
            Graphics2D g2 = (Graphics2D)g;
            g2.clearRect(0, 0, width, height);
            if(img != null) {
                g2.drawImage(img, 0, 0, width, height, null);
            }
        }
        public void setImage(BufferedImage img) {
            this.img = img;
        }
    }
}
