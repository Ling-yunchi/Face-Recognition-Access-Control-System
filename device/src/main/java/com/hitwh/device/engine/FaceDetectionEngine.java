package com.hitwh.device.engine;

import com.hitwh.device.model.DetectedFace;
import com.hitwh.device.utils.Const;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

import java.util.ResourceBundle;

/**
 * @author wangrong
 * @date 2022/7/30 21:29
 */
public class FaceDetectionEngine {
    public static DetectedFace detectFaces(Mat imgMat) {
        CascadeClassifier classifier = new CascadeClassifier(Const.RESOURCES_PATH + "xml/lbpcascade_frontalface.xml");

        MatOfRect faceDetection = new MatOfRect();
        classifier.detectMultiScale(imgMat, faceDetection);

        Rect[] faceDetectionArray = faceDetection.toArray();
        for (Rect rect : faceDetectionArray) {
            Imgproc.rectangle(imgMat, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height), new Scalar(0, 0, 255), 3);
        }

        return new DetectedFace(imgMat, faceDetectionArray.length);
    }
}
