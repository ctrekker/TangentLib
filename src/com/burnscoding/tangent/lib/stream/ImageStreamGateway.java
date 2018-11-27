package com.burnscoding.tangent.lib.stream;

import java.io.IOException;

public class ImageStreamGateway {
    private ImageStreamClient client;
    private ImageStreamServer server;
    public ImageStreamGateway(String clientHost, int clientPort, int serverPort) throws IOException {
        client = new ImageStreamClient(clientHost, clientPort);
        server = new ImageStreamServer(serverPort, () -> {
            try {
                return client.requestImage();
            } catch(IOException e) {
                e.printStackTrace();
                return null;
            }
        });
        server.start();
    }
}
