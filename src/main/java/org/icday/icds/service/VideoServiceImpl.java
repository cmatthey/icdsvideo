package org.icday.icds.service;

import ch.qos.logback.core.pattern.color.BoldYellowCompositeConverter;
import com.google.cloud.vision.v1.*;
import com.google.protobuf.ByteString;
import lombok.extern.slf4j.Slf4j;
import org.icday.icds.model.Video;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.icday.icds.repository.VideoRepository;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    VideoRepository videoRepository;

    @Override
    public Video createVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public Video getVideo(Integer id) throws Exception {
        getVis();
        return videoRepository.findById(id).orElse(null);
    }

    @Override
    public Video updateVideo(Video video) {
        return videoRepository.save(video);
    }

    @Override
    public void deleteVideo(Integer id) {
        videoRepository.deleteById(id);
    }

    private void getVis() throws Exception {
        log.info("--dir {}", System.getProperty("user.dir"));
        try (ImageAnnotatorClient vision = ImageAnnotatorClient.create()) {
            InputStream is = getClass().getClassLoader().getResourceAsStream("wakeupcat.jpg");
            byte[] data = new byte[is.available()];
            is.read(data);
            ByteString imgBytes = ByteString.copyFrom(data);

            List<AnnotateImageRequest> requests = new ArrayList<>();
            Image img = Image.newBuilder().setContent(imgBytes).build();
            Feature feat = Feature.newBuilder().setType(Feature.Type.LABEL_DETECTION).build();
            AnnotateImageRequest request = AnnotateImageRequest.newBuilder().addFeatures(feat).setImage(img).build();
            requests.add(request);
            BatchAnnotateImagesResponse response = vision.batchAnnotateImages(requests);
            List<AnnotateImageResponse> responses = response.getResponsesList();

            for (AnnotateImageResponse res : responses) {
                if (res.hasError()) {
                    log.info("Error: {} {}", res.getError().getMessage());
                    return;
                } else {
                    for (EntityAnnotation annotation : res.getLabelAnnotationsList()) {
                        annotation
                                .getAllFields()
                                .forEach((k, v) -> log.info("{} : {}", k, v.toString()));
                    }
                }
            }
        }
    }
}