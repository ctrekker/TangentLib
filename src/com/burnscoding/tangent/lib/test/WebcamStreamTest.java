package com.burnscoding.tangent.lib.test;

import com.burnscoding.tangent.lib.image.ImageDisplay;
import com.burnscoding.tangent.lib.image.ImageUtil;
import com.burnscoding.tangent.lib.stream.ImageStreamClient;
import com.burnscoding.tangent.lib.stream.ImageStreamServer;
import com.burnscoding.tangent.lib.stream.WebcamStreamServer;
import com.github.sarxos.webcam.Webcam;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class WebcamStreamTest {
    public static void main(String[] args) throws IOException {
        System.out.println(Webcam.getWebcams());
        if(args[0].equals("server")) {
            WebcamStreamServer server = new WebcamStreamServer(Integer.parseInt(args[1]));
            server.start();
        }
        else if(args[0].equals("client")) {
            ImageStreamClient client = new ImageStreamClient(args[1], Integer.parseInt(args[2]));

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
            }, 0, (int)(1000.0/30));
        }
    }
}
