package com.back.miru.model.service;

import com.back.miru.model.dto.Picture;

import java.util.List;
import java.util.Map;

public interface PictureService {

    List<Picture> getPictureList(Map<String, String> map) throws Exception;

    void registPicture(Map<String, String> map) throws Exception;

    void deletePicture(String pictureIdx) throws Exception;

    List<Picture> searchPictureList(String keyword, Map<String, String> map);

    Map<String, Object> getPictureDetail(String pictureIdx) throws Exception;

    void showPicture(Map<String, String> map) throws Exception;

}
