package com.bzzr.scrapr;

import org.opencv.core.*;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.Objdetect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

@Service
public class ScraprProcessor {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScraprProcessor.class);

    @Value("${img.input}")
    String inputImagePath;

    @Value("${img.output}")
    String outputImagePath;

    @Value("${model.path}")
    String modelPath;

    @Autowired
    ResourceLoader resourceLoader;

    private ImageProcessorDAO imageProcessor;

    public void initialize(ImageProcessorDAO imageProcessor) {
        this.imageProcessor = imageProcessor;
    }

    public void run() {
        Mat image = null;
        try {
            LOGGER.info("Relative Input Image Path: " + inputImagePath);
            String fullInputImagePath = ClassLoader.getSystemClassLoader().getResource(inputImagePath).getFile();
            LOGGER.info("Full Input Image Path: " + fullInputImagePath);
            image = imageProcessor.loadImage(fullInputImagePath);
        } catch (NullPointerException e) {
            LOGGER.error("Caught NPE trying to load input image. Check that it exists! file[{}]", inputImagePath);
        } catch (Exception e) {
            LOGGER.error("Caught generic exception when trying to load input image. msg[{}]", e.getMessage());
        }

        CascadeClassifier model = null;
        try {
            LOGGER.info("Relative Model Path: " + modelPath);
            String fullModelPath = ClassLoader.getSystemClassLoader().getResource(modelPath).getFile();
            LOGGER.info("Full Model Path: " + fullModelPath);
            model = imageProcessor.loadModel(fullModelPath);
        } catch (NullPointerException e) {
            LOGGER.error("Caught NPE trying to load model. Check that it exists! file[{}]", modelPath);
        } catch (Exception e) {
            LOGGER.error("Caught generic exception when trying to load model. msg[{}]", e.getMessage());
        }

        Rect[] faces = imageProcessor.processImage(image, model);
        for (Rect eachFace : faces) {
            LOGGER.info("Found a face!");
        }
    }

}
