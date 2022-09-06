package com.back.miru.model.service;

import com.back.miru.model.dto.FavoriteUser;
import com.back.miru.model.dto.Picture;

import java.util.List;
import java.util.Map;

public interface FavoriteService {
    void registFavoriteUser(Map<String, String> map) throws Exception;

    void deleteFavoriteUser(String followId, Map<String, String> map) throws Exception;

    List<FavoriteUser> infoFavoriteUser(String id) throws Exception;

    void registFavoritePicture(Map<String, String> map) throws Exception;

    void deleteFavoritePicture(String pictureIdx, Map<String, String> map) throws Exception;

    List<Picture> infoFavoritePicture(String id, Map<String, String> map) throws Exception;

    int getTotalPictureCnt(String id) throws Exception;

    int getFavoritepictureCnt(int pictureIdx) throws Exception;
}
