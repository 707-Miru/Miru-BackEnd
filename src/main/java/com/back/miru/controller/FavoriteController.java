package com.back.miru.controller;

import com.back.miru.model.dto.FavoriteUser;
import com.back.miru.model.dto.Picture;
import com.back.miru.model.service.FavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/favorite")
public class FavoriteController {
    public static final Logger logger = LoggerFactory.getLogger(FavoriteController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping("/user")
    public ResponseEntity<Map<String, Object>> registFavoriteUser(@RequestBody Map<String, String> map) throws Exception {
        System.out.println("resistFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            favoriteService.registFavoriteUser(map);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @DeleteMapping("/user/{followId}")
    public ResponseEntity<Map<String, Object>> deleteFavoriteUser(@PathVariable String followId, @RequestBody Map<String, String> map) {
        System.out.println("deleteFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            favoriteService.deleteFavoriteUser(followId, map);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<Map<String, Object>> getFavoriteUserInfo(@PathVariable String id) {
        System.out.println("FavoriteInfo controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<FavoriteUser> favoriteUserInfo = favoriteService.infoFavoriteUser(id);
            System.out.println(favoriteUserInfo);
            resultMap.put("favoriteUserInfo", favoriteUserInfo);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 좋아요 사진 등록
    @PostMapping("/picture")
    public ResponseEntity<Map<String, Object>> registFavoritePicture(@RequestBody Map<String, String> map) throws Exception {
        System.out.println("resistFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            favoriteService.registFavoritePicture(map);
	int likeCnt = favoriteService.getFavoritepictureCnt(Integer.parseInt(map.get("pictureIdx")));
	resultMap.put("likeCnt", likeCnt);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 좋아요 사진 제거
    @DeleteMapping("/picture/{pictureIdx}")
    public ResponseEntity<Map<String, Object>> deleteFavoritePicture(@PathVariable String pictureIdx, @RequestBody Map<String, String> map) {
        System.out.println("deleteFavorite controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            favoriteService.deleteFavoritePicture(pictureIdx, map);
	int likeCnt = favoriteService.getFavoritepictureCnt(Integer.parseInt(map.get("pictureIdx")));
	resultMap.put("likeCnt", likeCnt);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @GetMapping("/picture/{id}")
    public ResponseEntity<Map<String, Object>> getFavoritePictureInfo(@PathVariable String id, @RequestParam Map<String, String> map) {
        System.out.println("FavoriteInfo controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Picture> favoritePictureInfo = favoriteService.infoFavoritePicture(id, map);
            resultMap.put("favoritePictureInfo", favoritePictureInfo);
            int totalPictureCnt = favoriteService.getTotalPictureCnt(id);
            resultMap.put("totalPictureCnt", totalPictureCnt);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }
}
