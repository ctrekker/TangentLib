package com.burnscoding.tangent.lib.test;

import com.burnscoding.tangent.lib.image.ImageDisplay;
import com.burnscoding.tangent.lib.stream.VideoStreamClient;
import com.burnscoding.tangent.lib.stream.VideoStreamServer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class VideoStreamTest {
    public static void main(String[] args) throws IOException {
        new File("testing/video-stream-test/").mkdirs();

        ImageDisplay display = new ImageDisplay();

        VideoStreamServer server = new VideoStreamServer(3000);
        server.start();
        VideoStreamClient client = new VideoStreamClient("127.0.0.1", 3000, (BufferedImage img) -> {
//            try {
//                ImageIO.write(img, "PNG", new File("testing/video-stream-test/"+System.currentTimeMillis()+".png"));
//            } catch(IOException e) {
//                System.out.println("ERROR: Could not save streamed image");
//                e.printStackTrace();
//            }
            display.canvas.setImage(img);
        });

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
            try {
                Rectangle screenRect = new Rectangle(640, 480);
                BufferedImage capture = new Robot().createScreenCapture(screenRect);

                server.sendImage(capture);
            } catch(AWTException e) {
                System.out.println("ERROR: Could not take screenshot");
                e.printStackTrace();
            }
            }
        }, 0, (int)(1000.0/24.0));
    }
}
