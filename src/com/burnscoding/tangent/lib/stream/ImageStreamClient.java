package com.burnscoding.tangent.lib.stream;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ImageStreamClient {
    private static final int DEFAULT_RETRY_ATTEMPTS = 3;
    private Socket conn;
    private DataInputStream connIn;
    private DataOutputStream connOut;
    public ImageStreamClient(String host, int port) throws IOException {
        conn = new Socket(host, port);
        connIn = new DataInputStream(conn.getInputStream());
        connOut = new DataOutputStream(conn.getOutputStream());
    }

    public BufferedImage requestImage() throws IOException {
        return requestImage(DEFAULT_RETRY_ATTEMPTS);
    }
    public BufferedImage requestImage(int retryAttempts) throws IOException {
        connOut.writeUTF("REQUEST");
        connOut.flush();

        int attempts = 0;
        while(attempts <= retryAttempts) {
            attempts++;
            try {
                return StreamUtil.readBufferedImage(connIn);
            } catch(Exception e) { }
        }
        throw new IOException("Image request failure exceeded retry attempt max");
    }

    public void close() {
        try {
            connOut.writeUTF("EXIT");
            conn.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
