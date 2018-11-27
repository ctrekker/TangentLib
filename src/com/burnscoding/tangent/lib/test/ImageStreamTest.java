package com.burnscoding.tangent.lib.test;

import com.burnscoding.tangent.lib.image.ImageDisplay;
import com.burnscoding.tangent.lib.image.ImageUtil;
import com.burnscoding.tangent.lib.stream.ImageStreamClient;
import com.burnscoding.tangent.lib.stream.ImageStreamServer;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class ImageStreamTest {
    public static void main(String[] args) throws IOException {
        ImageStreamServer server = new ImageStreamServer(3001, () -> ImageUtil.takeScreenshot(640/2, 480/2));
        server.start();
        ImageStreamClient client = new ImageStreamClient("127.0.0.1", 3001);

        ImageDisplay display = new ImageDisplay();

        new Timer().scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    BufferedImage img = client.requestImage();
                    if(img != null) {
                        display.canvas.setImage(img);
                    }
                    else {
                        throw new IOException("Received null image");
                    }
                } catch(IOException e) {
                    e.printStackTrace();
                }
            }
        }, 0, (int)(1000.0/60));
    }
}
