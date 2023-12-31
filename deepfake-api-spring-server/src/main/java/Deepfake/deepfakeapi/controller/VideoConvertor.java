package Deepfake.deepfakeapi.controller;

import org.springframework.scheduling.annotation.Async;

import java.io.File;

public class VideoConvertor {

    /*
        동영상 이미지 자르기
     */
    public void getImageFrames(double videoDuration, File video, String filename) throws Exception {
        double plusSize = 0.5;
        int threadSize = 30;

        // 동영상 프레임을 잘라 저장할 디렉토리 생성
        String path = System.getProperty("user.dir") + "\\files\\video_" + filename;
        new File(path).mkdir();

        // 쓰레드 사용
        VideoThread[] videoThreads = new VideoThread[threadSize];

        for(int i=0; i<videoThreads.length; i++){
            videoThreads[i] = new VideoThread(video, threadSize, i, plusSize, videoDuration, path);
            videoThreads[i].start();
        }

        boolean isContinued = true;
        while(isContinued){
            Thread.sleep(1000);

            isContinued = false;
            for(int i=0; i<threadSize; i++){
                if(videoThreads[i].isAlive()){
                    isContinued = true;
                }
            }
        }
    }
}
