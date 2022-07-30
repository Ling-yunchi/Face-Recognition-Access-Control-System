package com.hitwh.device.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.opencv.core.Mat;

/**
 * @author wangrong
 * @date 2022/7/30 21:34
 */
@Data
@AllArgsConstructor
public class DetectedFace {
    // Output image
    private Mat image;
    // Number of faces
    private int nbrOfFaces;
}
