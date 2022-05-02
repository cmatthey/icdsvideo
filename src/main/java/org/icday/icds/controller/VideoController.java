package org.icday.icds.controller;

import org.icday.icds.model.Video;
import org.icday.icds.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
@RequestMapping("/v1/videos")
public class VideoController {

    @Autowired
    VideoService videoService;

    @RequestMapping(value = "/{id}")
    public ResponseEntity<Video> getVideo(@PathVariable("id") Integer id) throws Exception {
        Video video = videoService.getVideo(id);
        return new ResponseEntity<>(video, HttpStatus.OK);
    }

    @RequestMapping
    public ResponseEntity<Video> getVideos() {
        return ResponseEntity.noContent().build();
    }
}
