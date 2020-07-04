package com.bzzr.scrapr;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.objdetect.CascadeClassifier;
import org.springframework.stereotype.Component;

@Component
public interface ImageProcessorDAO {

    Mat loadImage(String path);
    CascadeClassifier loadModel(String path);
    Rect[] processImage(Mat image, CascadeClassifier classifer);
    void saveImage(String path, Mat imageMatrix);

}
