package com.bzzr.scrapr;

import nu.pattern.OpenCV;
import org.opencv.core.Mat;
import org.opencv.objdetect.CascadeClassifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.core.io.ResourceLoader;

@SpringBootApplication
public class Application implements CommandLineRunner {

	@Autowired
	ImageProcessorDAOLocalImpl localImageProcessor;

	@Autowired
	ScraprProcessor scraprProcessor;

	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		// Start the Spring Application context
		new SpringApplicationBuilder(Application.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}

	@Override
	public void run(String... args) throws Exception {

		// Initialize the library
		OpenCV.loadShared();

		// Do the scrapr processing
		ImageProcessorDAO imageProcessor = localImageProcessor;
		scraprProcessor.initialize(imageProcessor);
		scraprProcessor.run();
	}

}
