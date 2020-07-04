package com.bzzr.scrapr;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ImageProcessorDAOLocalImpl implements ImageProcessorDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ImageProcessorDAOLocalImpl.class);

    @Override
    public Mat loadImage(String path) {
        Imgcodecs imageCodecs = new Imgcodecs();
        return imageCodecs.imread(path);
    }

    @Override
    public CascadeClassifier loadModel(String path) {
        CascadeClassifier cascadeClassifier = new CascadeClassifier();
        cascadeClassifier.load(path);
        return cascadeClassifier;
    }

    @Override
    public Rect[] processImage(Mat image, CascadeClassifier classifer) {
        MatOfRect facesDetected = new MatOfRect();

        int minFaceSize = Math.round(image.rows() * 0.1f);
        classifer.detectMultiScale(image,
                facesDetected,
                1.1,
                3,
                Objdetect.CASCADE_SCALE_IMAGE,
                new Size(minFaceSize, minFaceSize),
                new Size()
        );
        Rect[] facesArray = facesDetected.toArray();
        for(Rect face : facesArray) {
            Imgproc.rectangle(image, face.tl(), face.br(), new Scalar(0, 0, 255), 3);
            System.out.println("Drawing a rectangle!");
        }
        return facesArray;
    }

    @Override
    public void saveImage(String path, Mat imageMatrix) {
        Imgcodecs imgcodecs = new Imgcodecs();
        imgcodecs.imwrite(path, imageMatrix);
    }
}
