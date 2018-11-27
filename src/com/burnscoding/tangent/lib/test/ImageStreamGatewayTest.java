package com.burnscoding.tangent.lib.test;

import com.burnscoding.tangent.lib.stream.ImageStreamGateway;

import java.io.IOException;

public class ImageStreamGatewayTest {
    public static void main(String[] args) throws IOException {
        ImageStreamGateway gateway = new ImageStreamGateway(args[0], Integer.parseInt(args[1]), Integer.parseInt(args[2]));
    }
}
