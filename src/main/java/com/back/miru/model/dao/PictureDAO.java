package com.back.miru.model.dao;

import com.back.miru.model.dto.ListParameterDto;
import com.back.miru.model.dto.Picture;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface PictureDAO {

    // getPictureList
    List<Picture> selectAllPictures(ListParameterDto listParameterDto) throws Exception;

    void registPicture(Map<String, String> map) throws Exception;

    void deletePicture(String pictureIdx) throws Exception;

    List<Picture> searchPictureList(ListParameterDto listParameterDto);

    Map<String, Object> getPictureDetail(String pictureIdx) throws Exception;

    String getPicturePath(Map<String, String> map) throws Exception;

    String[] getPicturePathList(Map<String, String> map) throws Exception;
}