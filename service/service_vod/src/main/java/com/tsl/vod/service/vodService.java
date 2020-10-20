package com.tsl.vod.service;

import org.springframework.web.multipart.MultipartFile;

public interface vodService {
    String uploadVideo(MultipartFile file);
}
