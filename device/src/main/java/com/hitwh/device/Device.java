package com.hitwh.device;

import com.hitwh.device.engine.FaceDetectionEngine;
import com.hitwh.device.model.DetectedFace;
import com.hitwh.device.socket.Server;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfByte;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.videoio.VideoCapture;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * @author wangrong
 * @date 2022/7/30 21:28
 */
public class Device extends JFrame {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private JLabel detectStatus;
    private JLabel serverStatus;
    private Server server;

    private static VideoCapture capture;
    public JLabel videoPlayer;

    public void init() {
        setTitle("Device");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        setVisible(true);

        JPanel header = new JPanel();
        header.setLayout(new FlowLayout());
        detectStatus = new JLabel("人脸数量: 0");
        detectStatus.setHorizontalAlignment(SwingConstants.LEFT);
        detectStatus.setVerticalAlignment(SwingConstants.CENTER);
        header.add(detectStatus);

        serverStatus = new JLabel("服务器状态: 未连接");
        serverStatus.setHorizontalAlignment(SwingConstants.RIGHT);
        serverStatus.setVerticalAlignment(SwingConstants.CENTER);
        header.add(serverStatus);

        add(header, BorderLayout.NORTH);

        server = new Server(8888, "123456");
        server.start();

        capture = new VideoCapture();
        capture.open(0);
        if (!capture.isOpened()) {
            System.out.println("Camera doesn't exists!");
            detectStatus.setText("Camera doesn't exists!");
        }

        videoPlayer = new JLabel();
        videoPlayer.setHorizontalAlignment(JLabel.CENTER);
        videoPlayer.setVerticalAlignment(JLabel.CENTER);
        add(videoPlayer, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        footer.setLayout(new FlowLayout());
        JButton btnCapture = new JButton("Capture");
        btnCapture.addActionListener(e -> {
            Mat img = new Mat();
            capture.read(img);
            if (!img.empty()) {
                Imgcodecs.imwrite("capture.png", img);
            }
        });
        JToggleButton btnAutoDetect = new JToggleButton("Auto Detect");
        footer.add(btnCapture);
        footer.add(btnAutoDetect);
        add(footer, BorderLayout.SOUTH);

        new VideoTacker().start(); // Start camera capture
    }

    class VideoTacker extends Thread {
        @Override
        public void run() {
            while (true) {
                serverStatus.setText("服务器状态: " + (server.getConnected() ? "已连接" : "未连接"));

                Mat imgMat = new Mat();
                capture.read(imgMat);
                if (!imgMat.empty()) {
                    DetectedFace detectedFace = FaceDetectionEngine.detectFaces(imgMat);
                    detectStatus.setText("人脸数量: " + detectedFace.getNbrOfFaces());
                    videoPlayer.setIcon(new ImageIcon(HighGui.toBufferedImage(imgMat)));
                }
                try {
                    sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Device device = new Device();
        device.init();
    }
}
