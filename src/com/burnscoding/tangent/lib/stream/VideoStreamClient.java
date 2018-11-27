package com.burnscoding.tangent.lib.stream;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

public class VideoStreamClient {
    private final VideoStreamClientFrameHandler handler;
    private Socket conn;
    public VideoStreamClient(String host, int port, VideoStreamClientFrameHandler handler) throws IOException {
        this.handler = handler;
        conn = new Socket(host, port);

        new Thread(new InitHandler()).start();
    }

    private class InitHandler implements Runnable {
        @Override
        public void run() {
            try {
                DataInputStream in = new DataInputStream(conn.getInputStream());
                while (true) {
                    try {
                        BufferedImage img = StreamUtil.readBufferedImage(in);
                        new Thread(() -> handler.handle(img)).start();
                    } catch(IOException e) {
                        System.out.println("WARN: Dropped frame. Clearing buffer");
                        while(in.available() > 0) {
                            in.readByte();
                        }
                    }
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
}
