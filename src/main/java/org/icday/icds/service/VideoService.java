package org.icday.icds.service;


import org.icday.icds.model.Video;

public interface VideoService {
    public Video createVideo(Video video);

    public Video getVideo(Integer id) throws Exception;

    public Video updateVideo(Video video);

    public void deleteVideo(Integer id);
}
