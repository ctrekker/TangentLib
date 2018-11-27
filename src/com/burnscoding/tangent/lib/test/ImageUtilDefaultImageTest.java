package com.burnscoding.tangent.lib.test;

import com.burnscoding.tangent.lib.image.ImageDisplay;
import com.burnscoding.tangent.lib.image.ImageUtil;

public class ImageUtilDefaultImageTest {
    public static void main(String[] args) {
        new ImageDisplay().canvas.setImage(ImageUtil.getDefaultImage());
    }
}
